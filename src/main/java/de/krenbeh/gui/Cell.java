package de.krenbeh.gui;

/**
 * Represents a cell in a grid-based automaton.
 * Each cell has a state and can have up to 8 neighboring cells.
 */
public class Cell {
  protected volatile int state;
  protected  Cell[] neighbors;

  public Cell() {
    this.state = 0;
    this.neighbors = new Cell[8];
  }

  // ModelView Presenter
  // ModelView Controller


  public Cell(int state) {
    this.state = state;
  }

  public Cell(Cell cell) {
    this.state = cell.getState();
  }

  public synchronized int getState() {
    return this.state;
  }

  public synchronized void setState(int state) {
    this.state = state;
  }

  public Cell[] getNeighbors() {
    return neighbors;
  }

  public void setNeighbors(Cell[] neighbors) {
    this.neighbors = neighbors;
  }
}