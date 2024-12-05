package de.krenbeh.gui.popups;

import de.krenbeh.controllers.ResizingController;
import de.krenbeh.controllers.TooltipHelper;
import de.krenbeh.gui.Style;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The ResizingWidget class provides a graphical interface for adjusting
 * the size of a population grid. It allows the user to input the number
 * of rows and columns, validates these inputs, and updates the canvas size
 * based on the provided values. The interface can be displayed by calling
 * the showWindow method.
 */
public class ResizingWidget {
  private final ResizingController resizingController;
  private final TooltipHelper tooltipHelper;
  private TextField numberOfRowsField;
  private TextField numberOfColumnsField;
  private Stage stage;

  public ResizingWidget(
    ResizingController resizingController, TooltipHelper tooltipHelper
  ) {
    this.resizingController = resizingController;
    this.tooltipHelper = tooltipHelper;
  }

  public void showWindow(Runnable onSizeConfirmed) {
    stage = new Stage();
    GridPane pane = createLayout(onSizeConfirmed);
    pane.setStyle(Style.scrollBarStyle());
    Scene scene = new Scene(pane, 300, 150);
    scene.setOnKeyPressed(event -> {
      KeyCode code = event.getCode();
      switch (code) {
        case ENTER -> handleOkButtonClick(onSizeConfirmed);
        case ESCAPE -> stage.close();
        default -> {
          // Kein spezielles Verhalten für andere Tasten
        }
      }
    });
    stage.setTitle("Populationsgröße ändern");
    stage.setResizable(false);
    stage.getIcons().add(
      new Image(getClass().getResourceAsStream("/icons/Size24.gif"))
    );
    stage.setScene(scene);
    stage.show();
  }

  private GridPane createLayout(Runnable onSizeConfirmed) {
    GridPane pane = new GridPane();
    pane.setPadding(new Insets(25));
    pane.setHgap(10);
    pane.setVgap(10);
    // Set up rows and columns input
    Label rowsLabel = new Label("Reihen");
    Label columnsLabel = new Label("Spalten");
    numberOfRowsField = createNumberInputField(
      resizingController.getMaxCellsPerRow()
    );
    numberOfColumnsField = createNumberInputField(
      resizingController.getMaxCellsPerRow()
    );

    Button okButton = new Button("OK");
    okButton.setOnAction(e -> handleOkButtonClick(onSizeConfirmed));
    Button cancelButton = new Button("Cancel");
    cancelButton.setOnAction(e -> stage.close());
    okButton.setStyle(Style.buttons2Style());
    cancelButton.setStyle(Style.buttons2Style());

    HBox buttons = new HBox(5, okButton, cancelButton);
    buttons.setAlignment(Pos.CENTER_RIGHT);

    VBox inputBox = new VBox(
      5, createHBox(rowsLabel, numberOfRowsField),
      createHBox(columnsLabel, numberOfColumnsField)
    );
    inputBox.setAlignment(Pos.CENTER);

    pane.add(inputBox, 0, 0);
    pane.add(buttons, 0, 1);
    BorderPane.setMargin(buttons, new Insets(10));

    return pane;
  }

  private TextField createNumberInputField(int maxValue) {
    TextField field = new TextField();
    field.setStyle(Style.getTextFieldStyle());
    tooltipHelper.formatToInteger(field, maxValue);
    field.setPromptText("1 to " + maxValue);
    return field;
  }

  private HBox createHBox(Label label, TextField textField) {
    HBox hbox = new HBox(10, label, textField);
    hbox.setAlignment(Pos.CENTER);
    return hbox;
  }

  private void handleOkButtonClick(Runnable onSizeConfirmed) {
    if (tooltipHelper.validateField(numberOfRowsField,
      "Darf nicht leer sein") &&
      tooltipHelper.validateField(numberOfColumnsField,
        "Darf nicht leer sein")) {

      int rows = Integer.parseInt(numberOfRowsField.getText());
      int columns = Integer.parseInt(numberOfColumnsField.getText());
      resizingController.updateCanvasSize(rows, columns);

      if (onSizeConfirmed != null) {
        onSizeConfirmed.run();
      }
      stage.close();
    }
  }

  public boolean isShowing() {
    return stage != null && stage.isShowing();
  }

  public void toFront() {
    if (stage != null) {
      stage.toFront();
    }
  }
  public Stage getStage() {
    return stage;
  }

}