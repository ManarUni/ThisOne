package de.krenbeh.controllers;

import de.krenbeh.Main;
import de.krenbeh.gui.popups.AutomatonNamenDialog;
import de.krenbeh.gui.popups.Editor;
import de.krenbeh.gui.popups.ResizingWidget;
import de.krenbeh.gui.Style;
import de.krenbeh.gui.automaton.Automaton;
import de.krenbeh.gui.automaton.AutomatonException;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Slider;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;



/**
 * AutomatonController class provides the control logic for handling various user interactions in an automaton
 * application. It implements the Controllable interface to manage user actions such as button clicks, slider adjustments,
 * and other interactions related to the automaton's state and visualization.
 */
public class AutomatonController implements Controllable {
  private static final Logger logger = Logger.getLogger(
    AutomatonController.class.getName());

  private static final int MAX_TEXTURE_SIZE = 8100; // Maximal erlaubte Texturgröße
  // IDs
  private static final String ZOOM_OUT_ID = "ZoomOut";
  private static final String PLAY_ID = "Play";
  private static final String STOP_ID = "Stop";
  private static final String STEP_ID = "Step";
  private static final String TORUS_ID = "Torus";
  private static final String ZOOM_IN_ID = "ZoomIn";
  private static final String LOAD_ID = "Load";
  private final AtomicBoolean stop;
  private final Automaton automaton;
  boolean isButtonActivated = false;
  ResizingWidget resizingWidget;
  private MenuBarController menuBar;
  private ButtonsController buttonsbar;
  Editor editor;
  AutomatonNamenDialog namenDialog;
  private AnimationTimer playAnimationTimer;


  public AutomatonController(Automaton automaton) {
    this.stop = new AtomicBoolean(false);
    this.automaton = automaton;
    Platform.runLater(()-> {
      buttonsbar.getButton(STOP_ID).setDisable(true);
      menuBar.getMenuItem(STOP_ID).setDisable(true);
      Stage mainStage = (Stage) buttonsbar.getButton(STOP_ID).getScene().getWindow();
      mainStage.onCloseRequestProperty().set(event -> {
        handleBeendenClicked();
        Main.openedAutomatons.remove(mainStage.getTitle());
      });
    });




  }

  @Override
  public void handleLoadClicked() {
    // This method is not used jet

  }

  @Override
  public void handleCompileClicked() {
    logger.info("Compile button clicked");
    // This method is not implemented jet. Implementation follows later

  }


  @Override
  public void handleNewClicked() {
    namenDialog = new AutomatonNamenDialog();
    namenDialog.showWindow();
    logger.info("New button clicked");
    // This method is not implemented jet. Implementation follows later

  }

  @Override
  public void handleEditor() {
    Scene scene = buttonsbar.getButton(LOAD_ID).getScene();
     editor = new Editor();


      if (scene != null && scene.getWindow() instanceof Stage currentStage) {
        String title = currentStage.getTitle();
        editor.showWindow(title);
      }

  }
  @Override
  public void handleSliderValue(Slider slider) {
    // This method is not implemented jet. Implementation follows later

  }

  @Override
  public void handleBeendenClicked() {
    stop.set(true);
    handleDeleteClicked();
    if(resizingWidget != null) {
      resizingWidget.getStage().close();
    }
    if(editor != null) {
      editor.getStage().close();
    }
    if(namenDialog != null) {
      namenDialog.getStage().close();
    }
    Stage mainStage = (Stage) buttonsbar.getButton(STOP_ID).getScene().getWindow();
    if (mainStage != null) {
      mainStage.close();
    }
    logger.info("Beenden button clicked");
    }

  @Override
  public void handleSizeClicked() {

    if (resizingWidget != null && resizingWidget.isShowing()) {
      resizingWidget.toFront();
      return;
    }
    ResizingController resizingController = new ResizingController(
      automaton, automaton.getCellSize());

    resizingWidget = new ResizingWidget(resizingController, new TooltipHelper());
    Platform.runLater(() -> {
      resizingWidget.showWindow(() ->
        buttonsbar.getButton(ZOOM_IN_ID).setDisable(resizingController.isExceedsMaxSize())
      );
      menuBar.getMenuItem(ZOOM_IN_ID).setDisable(resizingController.isExceedsMaxSize());
      logger.info("Size button clicked");
    });

  }

  @Override
  public void handleDeleteClicked() {
    buttonsbar.getButton(PLAY_ID).setDisable(false);
    buttonsbar.getButton(STEP_ID).setDisable(false);
    menuBar.getMenuItem(STEP_ID).setDisable(false);
    menuBar.getMenuItem(PLAY_ID).setDisable(false);
    stop.set(true);
    if (playAnimationTimer != null) {
      playAnimationTimer.stop();
      playAnimationTimer = null; // Reset the timer reference to allow re-starting
    }
    automaton.clearPopulation();
    logger.info("Delete button clicked");
  }

  @Override
  public void handleRandomClicked() {
    automaton.randomPopulation();
    logger.info("Random button clicked");
  }

  @Override
  public void handleTorusClicked() {
    automaton.setTorus();
    if (!isButtonActivated) {
      buttonsbar.getButton(TORUS_ID).setStyle(Style.recttOnClick());
      if (menuBar.getMenuItem(TORUS_ID) instanceof CheckMenuItem checkMenuItem) {
        checkMenuItem.setSelected(true);
      }
      isButtonActivated = true;
    } else {
      buttonsbar.getButton(TORUS_ID).setStyle(Style.buttonsStyle());
      if (menuBar.getMenuItem(TORUS_ID) instanceof CheckMenuItem checkMenuItem) {
        checkMenuItem.setSelected(false);
      }
      isButtonActivated = false;
    }
  }

  @Override
  public void handlePrintClicked() {
    // This method is not implemented jet. Implementation follows later
    logger.info("Print button clicked");
  }

  @Override
  public void handleZoomInClicked() {
    int cellSize = automaton.getCellSize();
    updateZoom(cellSize + 5);
  }

  @Override
  public void handleZoomOutClicked() {
    int cellSize = automaton.getCellSize();
    updateZoom(cellSize - 5);
  }


  private void updateZoom(int potentialCellSize) {
    int totalSize = potentialCellSize * automaton.getRows();
    if (potentialCellSize > 60 || totalSize > MAX_TEXTURE_SIZE) {
      logger.warning("Zoom In aborted: Exceeds max texture size.");
      return;
    }
    if (potentialCellSize < 5) {
      logger.warning("Zoom Out aborted: Below minimum cell size.");
      return;
    }

    // Set cell size and update automaton
    automaton.setCellSize(potentialCellSize);
    buttonsbar.getButton(ZOOM_IN_ID).setDisable(potentialCellSize >= 60
      || totalSize >= MAX_TEXTURE_SIZE);
    menuBar.getMenuItem(ZOOM_IN_ID).setDisable(potentialCellSize >= 60
      || totalSize >= MAX_TEXTURE_SIZE);
    buttonsbar.getButton(ZOOM_OUT_ID).setDisable(potentialCellSize <= 10);
    menuBar.getMenuItem(ZOOM_OUT_ID).setDisable(potentialCellSize <= 10);
  }


    // other fields and methods


    // other fields and methods

  @Override
  public void handleOpenClicked() {
    Platform.runLater(() -> {
      // Create a FileChooser
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Automaton Datei auswählen");
      fileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("Java Dateien", "*.java"));

      File initialDirectory = new File("automata");
      if (!initialDirectory.exists()) {
        initialDirectory.mkdirs(); // Create the directory if it doesn't exist
      }
      fileChooser.setInitialDirectory(initialDirectory);


      // Show the open file dialog
      File selectedFile = fileChooser.showOpenDialog(new Stage());

      if (selectedFile != null) {
        String fileName = selectedFile.getName();
        if (fileName.endsWith(".java")) {
          fileName = fileName.substring(0, fileName.length() - 5);
        }
        String finalFileName = fileName;

        Platform.runLater(() -> Main.openNewWindow(finalFileName));
        logger.info("Load button clicked");
      } else {
        logger.warning("Keine Datei ausgewählt.");
      }

      // Log the action
      logger.info("Open button clicked");
    });
  }

  @Override
  public void handleStepClicked() throws AutomatonException {
    try {
      automaton.nextGeneration();
    } catch (AutomatonException e) {
      throw new AutomatonException("Failed to process the next generation step: "
        + e.getMessage(), e);
    }
  }


  @Override
  public void handlePlayClicked() {
    // Disable play button to prevent starting multiple threads
    buttonsbar.getButton(STOP_ID).setDisable(false);
    menuBar.getMenuItem(STOP_ID).setDisable(false);
    buttonsbar.getButton(PLAY_ID).setDisable(true);
    menuBar.getMenuItem(PLAY_ID).setDisable(true);

    stop.set(false); // Ensure the stop flag is reset

    Thread playThread = new Thread(() -> {
      long lastUpdate = System.nanoTime();
      while (!stop.get()) {
        long now = System.nanoTime();
        if (now - lastUpdate >= buttonsbar.getSliderValue() * 5000_000L) {
          try {
            automaton.nextGeneration();
          } catch (AutomatonException e) {
            logger.severe("Error in onStepClicked: " + e.getMessage());
            stop.set(true); // Stop on error
          }
          lastUpdate = now;
        }
      }
      Platform.runLater(() -> {
        buttonsbar.getButton(PLAY_ID).setDisable(false);
        menuBar.getMenuItem(PLAY_ID).setDisable(false);
        buttonsbar.getButton(STOP_ID).setDisable(true);
        menuBar.getMenuItem(STOP_ID).setDisable(true);
        logger.info("Play operation finished");
      });
    });

    playThread.setDaemon(true);
    playThread.start();
    logger.info("Play button clicked");
  }




  @Override
  public void handleStopClicked() {
    stop.set(true);
    if (playAnimationTimer != null) {
      playAnimationTimer.stop();
      playAnimationTimer = null; // Reset the timer reference to allow re-starting
    }
    buttonsbar.getButton(PLAY_ID).setDisable(false);
    menuBar.getMenuItem(PLAY_ID).setDisable(false);
    buttonsbar.getButton(STOP_ID).setDisable(true);
    menuBar.getMenuItem(STOP_ID).setDisable(true);
    logger.info("Stop button clicked");

  }

  public void setMenuBar(MenuBarController menuBar) {
    this.menuBar = menuBar;
  }

  public void setButtonsBar(ButtonsController buttonsbar) {
    this.buttonsbar = buttonsbar;
  }
}
