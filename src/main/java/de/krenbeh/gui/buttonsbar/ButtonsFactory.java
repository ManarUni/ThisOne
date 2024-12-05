package de.krenbeh.gui.buttonsbar;

import de.krenbeh.gui.ExceptionalRunnable;
import de.krenbeh.gui.Style;
import de.krenbeh.gui.automaton.AutomatonException;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * ButtonsFactory is an abstract class that implements the ButtonsActionHandler interface
 * and provides utility methods to create and manage buttons in a user interface. It is
 * responsible for creating various buttons with predefined actions, styles, and tooltips.
 * Additionally, it builds an HBox that contains a set of buttons and a slider for controlling
 * simulation speed.
 */
public abstract class ButtonsFactory implements ButtonsActionHandler {
  private static final Logger logger =
    Logger.getLogger(ButtonsFactory.class.getName());
  private Slider slider;
  private HBox buttonBar;

  private Button getButton() {
    Button button = new Button();
    button.setStyle(Style.buttonsStyle());
    return button;
  }

  protected Button createButton(
    String iconPath, Runnable action, String tooltip, String id) {
    Tooltip tooltip1 = new Tooltip();
    tooltip1.setText(tooltip);
    tooltip1.setStyle(Style.getTooltipStyle());
    Button button = getButton();
    button.setGraphic(new ImageView(new Image(Objects.requireNonNull(
      getClass().getResourceAsStream(iconPath)))));
    button.setTooltip(tooltip1);
    button.setOnMouseClicked(e -> action.run());
    button.setId(id);

    return button;
  }

  public HBox getAllButtons() {
    this.slider = new Slider();
    this.slider.setMax(100);
    this.slider.setMin(10);
    this.slider.setMajorTickUnit(40);
    this.slider.setMinorTickCount(10);

    Tooltip tooltip1 = new Tooltip();
    tooltip1.setText("Simulationsgeschwindigkeit anpassen");
    tooltip1.setStyle(Style.getTooltipStyle());
    slider.setTooltip(tooltip1);

    this.slider.setSnapToTicks(true);
    this.slider.setShowTickMarks(true);
    this.slider.setShowTickLabels(true);
    this.slider.setStyle(Style.sliderStyle());

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    Button compileButton = createButton(
      "/icons/Compile24.gif", this::onNewClicked,
      "Neuen Automaten erzeugen und laden", "Compile");
    Button loadButton = createButton(
      "/icons/Load24.gif", this::onLoadClicked,
      "Existierenden Automaten laden", "Load");
    Button sizeButton = createButton(
      "/icons/Size24.gif", this::onSizeClicked,
      "Größe der Population ändern", "Size");
    Button deleteButton = createButton(
      "/icons/Delete24.gif", this::onDeleteClicked,
      "Alle Zellen in den Zustand 'Tod' versetzen", "Delete");
    Button randomButton = createButton(
      "/icons/Random24.gif", this::onRandomClicked,
      "Zufällige Population erzeugen", "Random");
    Button torusButton = createButton(
      "/icons/Torus24.gif", () -> handleException(this::onTorusClicked),
      "Population als Torus betrachten", "Torus");

    Button printButton = createButton(
      "/icons/Print24.gif", this::onPrintClicked,
      "Population drucken", "Print");
    Button zoomInButton = createButton(
      "/icons/ZoomIn24.gif", this::onZoomInClicked,
      "Ansicht vergrößern", "ZoomIn");
    Button zoomOutButton = createButton(
      "/icons/ZoomOut24.gif", this::onZoomOutClicked,
      "Ansicht verkleinern", "ZoomOut");
    Button stepButton = createButton(
      "/icons/Step24.gif", () -> handleException(this::onStepClicked),
      "Einen einzelnen Simulationszyklus ausführen", "Step");
    Button playButton = createButton(
      "/icons/Play24.gif", this::onPlayClicked,
      "Simulation starten", "Play");
    Button stopButton = createButton(
      "/icons/Stop24.gif", this::onStopClicked,
      "Simulation anhalten", "Stop");

    Separator separator = new Separator();
    Separator separator2 = new Separator();
    separator.setOrientation(Orientation.VERTICAL);
    separator.setStyle(Style.separatorStyle());
    separator2.setOrientation(Orientation.VERTICAL);
    separator2.setStyle(Style.separatorStyle());

    buttonBar = new HBox();
    buttonBar.getChildren().addAll(
      compileButton, loadButton, separator2, sizeButton, deleteButton,
      randomButton, torusButton, printButton, zoomInButton, zoomOutButton,
      stepButton, playButton, stopButton, separator, slider);
    buttonBar.setStyle(Style.getBarsstyle());
    buttonBar.setSpacing(3);
    return buttonBar;
  }

  public Button getButton(String id) {
    return buttonBar.getChildren().stream()
      .filter(Button.class::isInstance)
      .map(Button.class::cast)
      .filter(button -> id.equals(button.getId()))
      .findFirst()
      .orElseThrow(() -> new IllegalArgumentException(
        "No button with id " + id));
  }

  public int getSliderValue() {
    return (int) this.slider.getValue();
  }

  private void handleException(ExceptionalRunnable runnable) {
    try {
      runnable.run();
    } catch (AutomatonException e) {
      logger.severe("Failed to toggle Torus mode: " + e.getMessage());
    }
  }
}