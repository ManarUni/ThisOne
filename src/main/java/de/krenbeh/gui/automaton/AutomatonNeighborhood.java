package de.krenbeh.gui.automaton;

import de.krenbeh.controllers.AutomatonController;
import de.krenbeh.gui.Cell;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * Abstract class representing the neighborhood of cells in a cellular automaton grid.
 * Handles the logic for determining neighbors and applying transformation rules for each cell.
 */
public abstract class AutomatonNeighborhood {
  protected Cell[][] grid;
  protected int rows;
  protected int columns;
  protected AutomatonChangesListener listener;
  private boolean isMooreNeighborHood;
  private boolean isTorus;
  protected final ReentrantLock lock = new ReentrantLock();

  protected AutomatonNeighborhood(
    int rows, int columns, boolean isTorus, boolean isMooreNeighborHood)
    throws AutomatonException {
    this.grid = new Cell[rows][columns];
    this.rows = rows;
    this.columns = columns;
    this.isTorus = isTorus;
    this.isMooreNeighborHood = isMooreNeighborHood;
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < columns; col++) {
        grid[row][col] = new Cell();
      }
    }
    try {
      nextGeneration();
    } catch (AutomatonException e) {
      throw new AutomatonException(
        "Failed to proceed to the next generation", e);
    }
  }

  public void nextGeneration() throws AutomatonException {
    lock.lock();
    try {
      Cell[][] newGrid = new Cell[rows][columns];

      int availableProcessors = Runtime.getRuntime().availableProcessors();
      int threads = Math.min(availableProcessors, 16);
      ConcurrentLinkedQueue<int[]> changedCells = new ConcurrentLinkedQueue<>();

      try (ExecutorService executorService = Executors.newFixedThreadPool(threads)) {
        int chunkSize = (rows + threads - 1) / threads;
        for (int i = 0; i < threads; i++) {
          int startRow = i * chunkSize;
          int endRow = Math.min(startRow + chunkSize, rows);
          executorService.submit(() -> processChunk(startRow, endRow, newGrid, changedCells));
        }
        executorService.shutdown();
        if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
          executorService.shutdownNow();
          throw new AutomatonException("Parallel computation timed out.");
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new AutomatonException("Parallel computation interrupted.", e);
      }

      grid = newGrid;
      if (changedCells.isEmpty()) {
        return;
      }

      Platform.runLater(() -> {
        if (changedCells.size() > (rows * columns * 0.5)) {
          listener.repaintCanvas();
        } else {
          changedCells.forEach(cell -> listener.repaintCell(cell[0], cell[1]));
        }
      });
    } finally {
      lock.unlock();
    }
  }

  private void processChunk(int startRow, int endRow, Cell[][] newGrid, ConcurrentLinkedQueue<int[]> changedCells) {
    for (int row = startRow; row < endRow; row++) {
      for (int col = 0; col < columns; col++) {
        List<Cell> neighbors = getNeighbors(row, col);
        try {
          Cell newCell = transform(grid[row][col], neighbors.toArray(new Cell[0]));
          newGrid[row][col] = newCell;
          if (newCell.getState() != grid[row][col].getState()) {
            changedCells.add(new int[]{row, col});
          }
        } catch (AutomatonException e) {
          Logger.getLogger(AutomatonController.class.getName()).severe("Error transforming cell: " + e.getMessage());
        }
      }
    }
  }

  protected List<Cell> getNeighbors(int row, int col) {
    List<Cell> neighbors = new ArrayList<>();
    if (isTorus) {
      addTorusNeighbors(row, col, neighbors);
    } else {
      addStandardNeighbors(row, col, neighbors);
    }
    return neighbors;
  }

  private void addStandardNeighbors(int row, int col, List<Cell> neighbors) {
    addVonNeumannNeighbors(row, col, neighbors);
    if (isMooreNeighborHood) {
      addMooreNeighbors(row, col, neighbors);
    }
  }

  private void addTorusNeighbors(int row, int col, List<Cell> neighbors) {
    addTorusWrapNeighbors(row, col, neighbors);
    if (isMooreNeighborHood) {
      addTorusWrapDiagonalNeighbors(row, col, neighbors);
    }
  }

  private void addVonNeumannNeighbors(int row, int col, List<Cell> neighbors) {
    if (isValidCell(row - 1, col)) neighbors.add(grid[row - 1][col]); // Top
    if (isValidCell(row + 1, col)) neighbors.add(grid[row + 1][col]); // Bottom
    if (isValidCell(row, col - 1)) neighbors.add(grid[row][col - 1]); // Left
    if (isValidCell(row, col + 1)) neighbors.add(grid[row][col + 1]); // Right
  }

  private void addMooreNeighbors(int row, int col, List<Cell> neighbors) {
    if (isValidCell(row - 1, col - 1)) {
      neighbors.add(grid[row - 1][col - 1]); // Top-left
    }
    if (isValidCell(row - 1, col + 1)) {
      neighbors.add(grid[row - 1][col + 1]); // Top-right
    }
    if (isValidCell(row + 1, col - 1)) {
      neighbors.add(grid[row + 1][col - 1]); // Bottom-left
    }
    if (isValidCell(row + 1, col + 1)) {
      neighbors.add(grid[row + 1][col + 1]); // Bottom-right
    }
  }

  private void addTorusWrapNeighbors(int row, int col, List<Cell> neighbors) {
    neighbors.add(grid[(row - 1 + rows) % rows][col]); // Top
    neighbors.add(grid[(row + 1) % rows][col]); // Bottom
    neighbors.add(grid[row][(col - 1 + columns) % columns]); // Left
    neighbors.add(grid[row][(col + 1) % columns]); // Right
  }

  private void addTorusWrapDiagonalNeighbors(
    int row, int col, List<Cell> neighbors) {
    neighbors.add(grid[(row - 1 + rows) % rows]
      [(col - 1 + columns) % columns]); // Top-left
    neighbors.add(grid[(row - 1 + rows) % rows][(col + 1) % columns]); // Top-right
    neighbors.add(grid[(row + 1) % rows]
      [(col - 1 + columns) % columns]); // Bottom-left
    neighbors.add(grid[(row + 1) % rows][(col + 1) % columns]); // Bottom-right
  }

  protected abstract Cell transform(Cell cell, Cell[] neighbors)
    throws AutomatonException;

  public boolean isValidCell(int row, int col) {
    return row >= 0 && row < rows && col >= 0 && col < columns;
  }

  public synchronized boolean isTorus() {
    return isTorus;
  }

  public boolean isMooreNeighborHood() {
    return this.isMooreNeighborHood;
  }

  public void setListener(AutomatonChangesListener listener) {
    this.listener = listener;
  }

  public synchronized void setTorus() {
    this.isTorus = !this.isTorus();

  }

  // nur zum Testen habe ich die entstellt.
  public void disableTorus() {
    this.isTorus = false;
  }

  public void enableTorus() {
    this.isTorus = true;
  }

  public void enableMooreNeighborHood() {
    this.isMooreNeighborHood = true;
  }

  public void disableMooreNeighborHood() {
    this.isMooreNeighborHood = false;
  }
}