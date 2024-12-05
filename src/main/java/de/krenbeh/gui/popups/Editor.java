package de.krenbeh.gui.popups;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Editor {
  Stage stage;

  public void showWindow(String title){
    stage = new Stage();
    BorderPane root = new BorderPane();

    // Menüleiste erstellen

    Menu editor = new Menu("_Editor");

    MenuItem speichern =  new MenuItem("Speichern");
    MenuItem compilieren = new MenuItem("Compilieren");
    MenuItem exit = new MenuItem("schließen");
    exit.setOnAction(e -> stage.close());

    speichern.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
    compilieren.setAccelerator(KeyCombination.keyCombination("Ctrl+K"));
    exit.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
    editor.getItems().addAll(speichern, compilieren, new SeparatorMenuItem(), exit);

    ToolBar toolBar = new ToolBar();
    Button btnSave = new Button("Save");
    Button compile = new Button("Open");
    btnSave.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/Save16.gif"))));
    compile.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/Compile24.gif"))));
    toolBar.getItems().addAll(btnSave, compile);

    TextArea textArea = new TextArea();
    textArea.setPromptText("Geben Sie hier Ihren Code ein...");
    loadFile(textArea, title);
    btnSave.setOnAction(e -> saveFile(textArea, title));
    // Statusleiste unten
    Label statusBar = new Label("Herzlich willkommen!");

    // Layout zusammenfügen
    root.setTop(new VBox(new MenuBar(editor), toolBar));
    root.setCenter(textArea);
    root.setBottom(statusBar);

    // Szene erstellen
    Scene scene = new Scene(root, 600, 400);

    // Fenster konfigurieren
    stage.setTitle("Editor - DefaultAutomaton");
    stage.setScene(scene);
    stage.show();
  }


  private void saveFile(TextArea textArea, String stage) {
    String fileName = stage + ".java";
    Path filePath = Paths.get("automata", fileName);  // Annahme: Dateien befinden sich im Verzeichnis "automata"

    try {
      Files.write(filePath, textArea.getText().getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void loadFile(TextArea textArea, String stage) {
    String fileName = stage + ".java";
    Path filePath = Paths.get("automata", fileName);  // Annahme: Dateien befinden sich im Verzeichnis "automata"

    try {
      if (!Files.exists(filePath)) {
        Files.createDirectories(filePath.getParent());
        URI dummyFileURI = getClass().getClassLoader().getResource("Dummy.txt").toURI();
        Path dummyFilePath = Paths.get(dummyFileURI);
        String dummyContent = new String(Files.readAllBytes(dummyFilePath), StandardCharsets.UTF_8);
        String adjustedContent = dummyContent.replace("Dummy", stage);
        Files.write(filePath, adjustedContent.getBytes(StandardCharsets.UTF_8));
      }

      String content = new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);
      textArea.setText(content);
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    }
  }
  public Stage getStage() {
    return stage;
  }
}
