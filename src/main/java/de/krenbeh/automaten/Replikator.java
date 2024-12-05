package de.krenbeh.automaten;

import de.krenbeh.gui.Cell;
import de.krenbeh.gui.automaton.Automaton;
import de.krenbeh.gui.automaton.AutomatonException;

public class Replikator extends Automaton {
  public Replikator(
    int rows, int columns, boolean isTorus
  ) throws AutomatonException {
    super(rows, columns, 2, false, isTorus);
  }

  public Replikator() throws AutomatonException {
    this(100, 100, false);
  }

  @Override
  protected Cell transform(Cell cell, Cell[] neighbors) throws AutomatonException {
    int numberOfAliveNeighbors = 0;
    for(Cell neighbor : neighbors){
      if(neighbor != null && neighbor.getState() == 1){
        numberOfAliveNeighbors++;
      }
    }
    if(numberOfAliveNeighbors%2==0){
      return new Cell(0);
    }

    return new Cell(1);
  }
}
