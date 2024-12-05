package de.krenbeh.gui.automaton;

import de.krenbeh.gui.Cell;
import javafx.application.Platform;

import java.util.Arrays;
import java.util.Random;

/**
 * The Automaton class represents a cellular automaton, which is a grid of cells
 * that can evolve through multiple states according to a set of rules. It
 * supports both Moore and von Neumann neighborhoods and can be configured as a
 * toroidal grid.
 * <p>
 * The class includes methods for initializing the grid, changing its size,
 * setting cell states, and populating the grid randomly. It also provides
 * methods to toggle between Moore and von Neumann neighborhoods, and to enable
 * or disable the toroidal grid.
 * <p>
 * This abstract class must be extended to define the specific state transition
 * rules for the automaton.
 */
public abstract class Automaton extends AutomatonNeighborhood {
  private final int numberOfStates;
  private final Random random = new Random();
  private int cellSize = 20;

  protected Automaton(
    int rows, int columns, int numberOfStates, boolean isMooreNeighborHood,
    boolean isTorus) throws AutomatonException {
    super(rows, columns, isTorus, isMooreNeighborHood);
    this.numberOfStates = numberOfStates;

  }

  public synchronized void changeSize(int newRows, int newColumns) {
    lock.lock();
    try{
    int rowOffset = (newRows > rows) ? (newRows - rows) / 2 : 0;
    int colOffset = (newColumns > columns) ? (newColumns - columns) / 2 : 0;
    Cell[][] newGrid = new Cell[newRows][newColumns];
    for (int row = 0; row < newRows; row++) {
      for (int col = 0; col < newColumns; col++) {
        int oldRow = row - rowOffset;
        int oldCol = col - colOffset;
        if (oldRow >= 0 && oldRow < rows && oldCol >= 0 && oldCol < columns) {
          newGrid[row][col] = this.grid[oldRow][oldCol];
        } else {
          newGrid[row][col] = new Cell(0);
        }
      }
    }
    this.grid = newGrid;
    this.rows = newRows;
    this.columns = newColumns;
    if (listener != null) {
      listener.initializeCanvas();
    }
    }finally {
      lock.unlock();
    }
  }

  public synchronized void setState(int row, int column, int state) {
    if (this.grid[row][column].getState() != state) {
      grid[row][column].setState(state);
      if (listener != null) {
        listener.repaintCanvas();
      }
    }
  }

  public void randomPopulation() {
    lock.lock();
    try {
    new Thread(() -> {
      int numOfStates = this.getNumberOfStates();
      Arrays.stream(grid).parallel().forEach(row -> {
        for (Cell cell : row) {
          int randomState = random.nextInt(numOfStates);
          cell.setState(randomState);
        }
      });


      if (listener != null) {
        Platform.runLater(listener::repaintCanvas);
      }
    }).start();
    }finally {
      lock.unlock();
    }
  }


  public void clearPopulation() {
    lock.lock();
    try {
      setState(0, 0, this.getRows(), this.getColumns(), 0);
    }finally {
      lock.unlock();
    }

  }

  public synchronized void setState(
    int fromRow, int fromColumn, int toRow, int toColumn, int state) {
    fromRow = Math.clamp(fromRow, 0, getRows() - 1);
    fromColumn = Math.clamp(fromColumn, 0, getColumns() - 1);
    toRow = Math.clamp(toRow, 0, getRows() - 1);
    toColumn = Math.clamp(toColumn, 0, getColumns() - 1);
    for (int row = fromRow; row <= toRow; row++) {
      for (int col = fromColumn; col <= toColumn; col++) {
        grid[row][col].setState(state);
      }
    }
    if (listener != null) {
      listener.repaintCanvas();
    }
  }

  public Cell getCell(int row, int column) {
    return grid[row][column];
  }

  public int getNumberOfStates() {
    return numberOfStates;
  }

  public int getRows() {
    return this.rows;
  }

  public synchronized int getColumns() {
    return this.columns;
  }

  public synchronized void setColumns(int columns) {
    this.columns = columns;
  }

  protected abstract Cell transform(Cell cell, Cell[] neighbors)
    throws AutomatonException;


  public int getCellSize() {
    return cellSize;
  }

  public void setCellSize(int cellSize) {
    this.cellSize = cellSize;
    if (listener != null) {
      listener.cellSize();
      listener.initializeCanvas();
    }
  }


}