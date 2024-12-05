package de.krenbeh.controllers;

import de.krenbeh.gui.automaton.AutomatonException;
import de.krenbeh.gui.buttonsbar.ButtonsFactory;

import java.util.logging.Logger;

/**
 * ButtonsController is responsible for handling button click events and
 * delegating these actions to the AutomatonController. This class extends
 * ButtonsFactory, thus inheriting methods for button creation and management.
 * Each overridden method corresponds to a specific button action, such as
 * loading, compiling, opening, resizing, deleting, and more. When a button
 * is clicked, the corresponding method in the AutomatonController is invoked.
 */

public class ButtonsController extends ButtonsFactory {
  @SuppressWarnings("checkstyle:JavadocVariable")
  private static final Logger logger =
    Logger.getLogger(ButtonsController.class.getName());
  private final AutomatonController controller;

  public ButtonsController(AutomatonController controller) {
    this.controller = controller;
  }

  @Override
  public void onLoadClicked() {
    controller.handleOpenClicked();
  }

  @Override
  public void onNewClicked() {
    controller.handleNewClicked();
  }

  @Override
  public void onOpenClicked() {
    controller.handleOpenClicked();
  }

  @Override
  public void onSizeClicked() {
    controller.handleSizeClicked();
  }

  @Override
  public void onDeleteClicked() {
    controller.handleDeleteClicked();
  }

  @Override
  public void onRandomClicked() {
    controller.handleRandomClicked();
  }

  @Override
  public void onTorusClicked() {
    controller.handleTorusClicked();
  }

  @Override
  public void onPrintClicked() {
    controller.handlePrintClicked();
  }

  @Override
  public void onZoomInClicked() {
    controller.handleZoomInClicked();
  }

  @Override
  public void onZoomOutClicked() {
    controller.handleZoomOutClicked();
  }

  @Override
  public void onStepClicked() throws AutomatonException {
    try {
      controller.handleStepClicked();
    } catch (AutomatonException e) {
      throw new AutomatonException(
        "Failed to process step in onStepClicked.", e);
    } catch (Exception e) {
      throw new AutomatonException(
        "Unexpected error occurred in onStepClicked.", e);
    }
  }

  @Override
  public void onPlayClicked() {
    controller.handlePlayClicked();
  }

  @Override
  public void onStopClicked() {
    controller.handleStopClicked();
  }
}