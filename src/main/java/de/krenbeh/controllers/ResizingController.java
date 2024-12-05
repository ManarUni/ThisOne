package de.krenbeh.controllers;

import de.krenbeh.gui.automaton.Automaton;

public class ResizingController {
  private static final int MAX_TEXTURE_SIZE = 8100;
  private final Automaton automaton;
  private final int cellSize;
  private boolean exceedsMaxSize; // New field to track if the max size is exceeded

  public ResizingController(
    Automaton automaton, int cellSize
  ) {
    this.automaton = automaton;
    this.cellSize = cellSize;
    this.exceedsMaxSize = false; // Initialize to false
  }

  public int getMaxCellsPerRow() {
    return MAX_TEXTURE_SIZE / cellSize;
  }

  public void updateCanvasSize(int rows, int columns) {
    automaton.changeSize(rows, columns);
    checkAndDisableZoomButtons(rows, columns);
  }

  private void checkAndDisableZoomButtons(int rows, int columns) {
    int totalWidth = cellSize * columns;
    int totalHeight = cellSize * rows;
    int totalArea = totalWidth * totalHeight;

    // Prüfen, ob entweder Breite, Höhe oder die Gesamtfläche die Maximalwerte überschreiten
    exceedsMaxSize = totalWidth >= MAX_TEXTURE_SIZE
      || totalHeight >= MAX_TEXTURE_SIZE
      || totalArea >= MAX_TEXTURE_SIZE * MAX_TEXTURE_SIZE;
  }

  // Getter for exceedsMaxSize
  public boolean isExceedsMaxSize() {
    return exceedsMaxSize;
  }
}