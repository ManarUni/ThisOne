package de.krenbeh.controllers;

import de.krenbeh.gui.automaton.AutomatonException;
import de.krenbeh.gui.menubar.MenuItemsFactory;
import javafx.event.ActionEvent;

import java.util.logging.Logger;

/**
 * The MenuBarController class is responsible for handling actions
 * performed on the various menu items in the application's menu bar.
 * It extends the MenuItemsFactory class and provides specific
 * implementations for the abstract methods defined in its parent class.
 * The class interacts with an instance of AutomatonController to handle
 * various actions like creating new items, loading, editing, resizing,
 * and other functionalities as defined in the menu bar.
 */
public class MenuBarController extends MenuItemsFactory {
  private static final Logger logger = Logger.getLogger(
    MenuBarController.class.getName()
  );
  private final AutomatonController controller;

  public MenuBarController(AutomatonController controller) {
    this.controller = controller;
  }

  @Override
  public void handleNeu(ActionEvent actionEvent) {
    controller.handleNewClicked();
  }

  @Override
  public void handleLaden(ActionEvent actionEvent) {
    controller.handleOpenClicked();
    logger.info("Laden pressed");
  }

  @Override
  public void handleEditor(ActionEvent actionEvent) {
    controller.handleEditor();
    logger.info("Editor pressed");
  }

  @Override
  public void handleBeenden(ActionEvent actionEvent) {
    logger.info("Beenden pressed");
    controller.handleBeendenClicked();
  }

  @Override
  public void handleGroesseAendern(ActionEvent actionEvent) {
    controller.handleSizeClicked();
    logger.info("Groesse Aendern pressed");
  }

  @Override
  public void handleLoeschen(ActionEvent actionEvent) {
    controller.handleDeleteClicked();
    logger.info("Loeschen pressed");
  }

  @Override
  public void handleErzeugen(ActionEvent actionEvent) {
    controller.handleRandomClicked();
    logger.info("Erzeugen pressed");
  }

  @Override
  public void handleTorus(ActionEvent event) {
    controller.handleTorusClicked();

    logger.info("Torus pressed");
  }

  @Override
  public void handleVergroessern(ActionEvent actionEvent) {
    controller.handleZoomInClicked();
    logger.info("Vergroessern pressed");
  }

  @Override
  public void handleVerkleinern(ActionEvent actionEvent) {
    controller.handleZoomOutClicked();
    logger.info("Verkleinern pressed");
  }

  @Override
  public void handleXmlSpeichern(ActionEvent actionEvent) {
    logger.info("Xml Speichern pressed");
  }

  @Override
  public void handleSerialisieren(ActionEvent actionEvent) {
    logger.info("Serialisieren pressed");
  }

  @Override
  public void handleLadenPopulation(ActionEvent actionEvent) {
    logger.info("Laden Population pressed");
  }

  @Override
  public void handleDrucken(ActionEvent actionEvent) {
    logger.info("Drucken pressed");
  }

  @Override
  public void handleSchritt(ActionEvent actionEvent)
    throws AutomatonException {
    try {
      controller.handleStepClicked();
    } catch (AutomatonException e) {
      throw new AutomatonException("Failed to step", e);
    } catch (Exception e) {
      throw new AutomatonException(
        "unexpected Exception by running Step", e);
    }
    logger.info("Schritt pressed");
  }

  @Override
  public void handleStart(ActionEvent actionEvent) {
    controller.handlePlayClicked();
    logger.info("Start pressed");
  }

  @Override
  public void handleStop(ActionEvent actionEvent) {
    controller.handleStopClicked();
    logger.info("Stop pressed fddfdfd");
  }
}