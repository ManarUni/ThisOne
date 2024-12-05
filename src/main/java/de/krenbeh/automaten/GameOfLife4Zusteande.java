package de.krenbeh.automaten;

import de.krenbeh.gui.Cell;
import de.krenbeh.gui.automaton.Automaton;
import de.krenbeh.gui.automaton.AutomatonException;

/**
 * GameOfLife4Zusteande is a variant of the cellular automaton "Game of Life".
 * It extends the Automaton class to support a custom set of state transformations.
 * This version includes four states: Dead, Alive, Sleeping, Cooldown1,
 * and Cooldown2. State transitions are defined as follows:
 * - Dead (0): Becomes Alive if exactly 2 alive neighbors exist.
 * - Alive (1): Becomes Sleeping.
 * - Sleeping (2): Becomes Cooldown1.
 * - Cooldown1 (3): Becomes Cooldown2.
 * - Cooldown2 (4): Becomes Dead.cd my name
 */
public class GameOfLife4Zusteande extends Automaton {
  public GameOfLife4Zusteande(
    int rows, int columns, boolean isTorus
  ) throws AutomatonException {
    super(rows, columns, 5, true, isTorus);
  }

  public GameOfLife4Zusteande() throws AutomatonException {
    this(100, 100, true);
  }

  // ########## ChatGPT #############
  public Cell transform(Cell cell, Cell[] neighbors) {
    int aliveNeighbors = 0;

    // Zähle die "lebenden" Nachbarn (Zustand 1)
    for (Cell neighbor : neighbors) {
      if (neighbor != null && neighbor.getState() == 1) {
        aliveNeighbors++;
      }
    }

    int currentState = cell.getState();
    int newState;

    switch (currentState) {
      case 0: // Tot
        newState = (aliveNeighbors == 2) ? 1 : 0;
        // Wird lebendig, wenn genau 2 Nachbarn leben
        break;
      case 1: // Lebendig
        newState = 2;
        // Wechselt in den Schlafzustand
        break;
      case 2: // Schlafend
        newState = 3;
        // Wechsel in den "Cooldown"-Zustand
        break;
      case 3: // Cooldown-Zustand 1
        newState = 4;
        // Wechselt in den zweiten Cooldown-Zustand
        break;
      case 4: // Cooldown-Zustand 2
        newState = 0;
        // Zelle wird wieder tot
        break;
      default:
        newState = 0;
        // Sicherheitsnetz, falls ein unbekannter Zustand auftritt
        break;
    }

    // Erstelle eine neue Zelle mit dem neuen Zustand
    Cell newCell = new Cell();
    newCell.setState(newState);

    return newCell;
  }
}