package de.krenbeh;

import de.krenbeh.automaten.KrummelmonsterAutomaton;
import de.krenbeh.controllers.TooltipHelper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import de.krenbeh.controllers.AutomatonController;
import de.krenbeh.controllers.ButtonsController;
import de.krenbeh.controllers.MenuBarController;
import de.krenbeh.gui.PopulationPanel;
import de.krenbeh.gui.Style;
import de.krenbeh.gui.automaton.Automaton;
import de.krenbeh.gui.colorpicker.ColorPickers;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;
public class Main extends Application {
  public static final Map<String, Stage> openedAutomatons = new HashMap<>();

  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage stage) throws Exception {
    initializeStage(stage, "Replikator");
  }

  public static void initializeStage(Stage stage, String automatonName) throws Exception {
    BorderPane root = new BorderPane();
    Automaton automaton = new KrummelmonsterAutomaton(43, 59, false);
    root.setLeft(new ColorPickers(automaton.getNumberOfStates()));
    root.setStyle(Style.getAppStyle());

    Label willkommenLabel = new Label("Herzlich willkommen!");
    HBox unten = new HBox();
    unten.setPadding(new Insets(2));
    unten.setStyle(Style.getBarsstyle());
    willkommenLabel.setStyle(Style.getBarsstyle());
    unten.getChildren().addAll(willkommenLabel);

    root.setBottom(unten);

    PopulationPanel center = new PopulationPanel(automaton);
    AutomatonController controller = new AutomatonController(automaton);
    ButtonsController buttonsBar = new ButtonsController(controller);
    MenuBarController menuBar = new MenuBarController(controller);
    controller.setMenuBar(menuBar);
    controller.setButtonsBar(buttonsBar);
    VBox oben = new VBox();

    oben.getChildren().addAll(menuBar.creatMenu(), buttonsBar.getAllButtons());
    root.setTop(oben);
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setContent(center);
    scrollPane.setPannable(false);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);
    scrollPane.setStyle(Style.scrollPaneStyle());

    Platform.runLater(() ->
      scrollPane.lookupAll(".scroll-bar").forEach(node -> {
        node.setStyle(Style.scrollBarStyle()); // Stil für die Scrollbars
        node.lookup(".thumb").setStyle(Style.scrollBarThumbStyle()); // Stil für den Thumb
      })
    );

    root.setCenter(scrollPane);

    Scene scene = new Scene(root, 1200, 800);

    stage.setTitle(automatonName);
    stage.setScene(scene);
    stage.getIcons().add(new Image(Main.class.getResourceAsStream(
      "/icons/MainIcon.gif")
    ));
    stage.setOnCloseRequest(event -> {
      synchronized (openedAutomatons) {
        openedAutomatons.remove(automatonName);
      }
    });

    stage.show();
    synchronized (openedAutomatons) {
      openedAutomatons.put(automatonName, stage);
    }
  }

  public static Stage openNewWindow(String automatonName) {
    synchronized (openedAutomatons) {
      if (openedAutomatons.containsKey(automatonName)) {
        new TooltipHelper().showAlert("Automaton bereits geöffnet: " + automatonName);
        return openedAutomatons.get(automatonName);
      } else {
        Platform.runLater(() -> {
          try {
            Stage newStage = new Stage();
            initializeStage(newStage, automatonName);
          } catch (Exception e) {
            e.printStackTrace();
          }
        });
      }
    }
    return null;
  }
}