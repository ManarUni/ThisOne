package de.krenbeh.gui.popups;

import de.krenbeh.Main;
import de.krenbeh.controllers.TooltipHelper;
import de.krenbeh.gui.Style;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AutomatonNamenDialog {

  private final TooltipHelper tooltipHelper;
  private TextField nameField;
  private Stage stage;

  public AutomatonNamenDialog() {
    this.tooltipHelper = new TooltipHelper();
  }

  public void showWindow() {

    // Label
    Label nameLabel = new Label("Automaton Name:");

    // TextField
    nameField = new TextField();
    nameField.setPromptText("Enter automaton name");
    nameField.setStyle(Style.getTextFieldStyle());
    tooltipHelper.formatJavaRules(nameField);

    // Buttons
    Button okButton = new Button("OK");
    Button cancelButton = new Button("Cancel");

    // Stage
    stage = new Stage(); // Einweisung der Instanzvariablen
    // Button actions
    okButton.setDefaultButton(true);
    okButton.setOnAction(e -> handleOkButtonClick());
    okButton.setOnKeyPressed(event -> {
      switch (event.getCode()) {
        case ENTER:
          handleOkButtonClick();
          break;
        case ESCAPE:
          stage.close();
          break;
        case SPACE:
          nameField.setText(nameField.getText() + " ");
          break;
        default:
          break;
      }
    });
    cancelButton.setOnAction(e -> stage.close());

    // Layout for buttons
    HBox buttonBox = new HBox(10, okButton, cancelButton);
    buttonBox.setAlignment(Pos.CENTER);

    VBox mainLayout = new VBox(10, nameLabel, nameField, buttonBox);
    mainLayout.setAlignment(Pos.CENTER);
    mainLayout.setPadding(new Insets(20));

    // Scene
    Scene scene = new Scene(mainLayout);

    scene.setOnKeyPressed(event -> {
      KeyCode code = event.getCode();
      if (code == KeyCode.ESCAPE) {
        stage.close();
      }
    });

    stage.setTitle("Automaton Name");
    stage.getIcons().add(
      new Image(getClass().getResourceAsStream("/icons/Size24.gif"))
    );

    stage.setScene(scene);
    stage.sizeToScene();
    stage.show();
  }

  private void handleOkButtonClick() {
    if (tooltipHelper.validateField(nameField,
      "Darf nicht leer sein")) {
      String automatonName = nameField.getText();

      File directory = new File("automata");
      if (!directory.exists()) {
        directory.mkdirs();
      }

      File newFile = new File(directory, automatonName + ".java");
      if (newFile.exists()) {
        tooltipHelper.showTooltip(nameField,"Datei mit diesem Namen existiert bereits: " + newFile.getAbsolutePath());
      } else {
        createDummyFile(automatonName, directory);
        stage.close();
      }
    }
  }

  private void createDummyFile(String automatonName, File directory) {
    try {
      String templateContent = new String(Files.readAllBytes(Paths.get(getClass().getResource("/Dummy.txt").toURI())));
      String classContent = templateContent.replace("Dummy", automatonName);

      try (PrintWriter writer = new PrintWriter(new FileWriter(directory.getPath() + "/" + automatonName + ".java"))) {
        writer.println(classContent);
      }
      BorderPane automatonPane = new BorderPane();
      automatonPane.setPadding(new Insets(15));

      Label infoLabel = new Label("Dies ist das Automaton Fenster für: " + automatonName);
      automatonPane.setCenter(infoLabel);

      Platform.runLater(() -> Main.openNewWindow(nameField.getText()));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  public Stage getStage() {
    return stage;
  }
}