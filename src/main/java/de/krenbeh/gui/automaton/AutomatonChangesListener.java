package de.krenbeh.gui.automaton;

/**
 * Listener interface for monitoring changes in an Automaton.
 *
 * This interface defines methods that are called in response to various changes
 * in the state of an Automaton, enabling the implementing class to update
 * the visual representation or handle size adjustments accordingly.
 */
public interface AutomatonChangesListener {
  void repaintCanvas();
  void initializeCanvas();
  void cellSize();
  void repaintCell(int row, int column); // Neue Methode hinzufügen
}
