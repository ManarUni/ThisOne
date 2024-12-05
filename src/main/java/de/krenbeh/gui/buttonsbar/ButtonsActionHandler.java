package de.krenbeh.gui.buttonsbar;

import de.krenbeh.gui.automaton.AutomatonException;

/**
 * Interface for handling various button actions within the application.
 * Implementations of this interface will define the behavior for each action.
 */
public interface ButtonsActionHandler {
  void onLoadClicked();

  void onNewClicked();

  void onOpenClicked();

  void onSizeClicked();

  void onDeleteClicked();

  void onRandomClicked();

  void onTorusClicked() throws AutomatonException;

  void onPrintClicked();

  void onZoomInClicked();

  void onZoomOutClicked();

  void onStepClicked() throws AutomatonException;

  void onPlayClicked();

  void onStopClicked();
}
