package de.krenbeh.automaten;

import de.krenbeh.gui.Cell;
import de.krenbeh.gui.automaton.Automaton;
import de.krenbeh.gui.automaton.AutomatonException;

/**
 * KrummelmonsterAutomaton is a cellular automaton with 9 states.
 * Each cell represents the state of the Krümelmonster:
 * 0 - Hungry, 1-7 - Eating cookies, 8 - Satisfied (satiated).
 * Transition rules:
 * - A cell advances to the next state if enough "cookie" states are present in neighbors.
 * - State 8 is stable (does not change).
 */
public class KrummelmonsterAutomaton extends Automaton {
  public KrummelmonsterAutomaton(
    int rows, int columns, boolean isTorus
  ) throws AutomatonException {
    super(rows, columns, 10, false, isTorus);
  }

  public KrummelmonsterAutomaton() throws AutomatonException {
    this(100, 100, false);
  }

  @Override
  public Cell transform(Cell cell, Cell[] neighbors) {
    // Überprüfe die Nachbarn, ob einer den Zustand (currentState + 1) hat.
    for (int i = 0; i < neighbors.length; i++) {
      if (((cell.getState()+1)%10)== neighbors[i].getState()) {
        return new Cell(neighbors[i].getState());
      }
    }
    return new Cell(cell);

  }


}
