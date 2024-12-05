package de.krenbeh.controllers;

import de.krenbeh.gui.automaton.AutomatonException;
import javafx.scene.control.Slider;

/**
 * The Controllable interface provides a contract for handling various user interactions
 * within an automaton application. Implementing classes must define behavior for
 * handling button clicks, slider adjustments, and other control actions.
 */
public interface Controllable {
  void handleLoadClicked();

  void handleCompileClicked();

  void handleSliderValue(Slider slider);
  void handleBeendenClicked();

  void handleOpenClicked();

  void handleSizeClicked();

  void handleDeleteClicked();

  void handleRandomClicked();

  void handleTorusClicked();

  void handlePrintClicked();

  void handleZoomInClicked();

  void handleZoomOutClicked();

  void handleStepClicked() throws AutomatonException;

  void handlePlayClicked();

  void handleStopClicked();
  void handleNewClicked();
  void handleEditor();
}
