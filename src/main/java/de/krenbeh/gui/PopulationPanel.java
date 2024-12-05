package de.krenbeh.gui;

import de.krenbeh.gui.automaton.Automaton;
import de.krenbeh.gui.automaton.AutomatonChangesListener;
import de.krenbeh.gui.colorpicker.ColorPickers;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

/**
 * The PopulationPanel class is a custom component that extends StackPane and provides
 * a visual representation of an automaton grid using a Canvas.
 * It allows users to interact with the grid by clicking or dragging on cells
 * to change their states.
 * <p>
 * The grid's appearance, including cell size and border size,
 * can be configured dynamically.
 */
public class PopulationPanel extends StackPane
  implements AutomatonChangesListener {
  private static final double SCALE_FACTOR = 1.0;
  private final Automaton automaton;
  private int cellSize;
  private static final int BORDER_SIZE = 5;
  private Canvas canvas;
  private final Map<Integer, Color> colorsStates = new HashMap<>();
  public PopulationPanel(Automaton automaton) {
    this.automaton = automaton;
    this.cellSize = automaton.getCellSize();
    this.automaton.setListener(this);
    initializeCanvas();
    syncColorsWithColorPickers();
    ColorPickers.addColorChangeListener(this::syncColorsWithColorPickers);
  }
  private void syncColorsWithColorPickers() {
    colorsStates.clear();
    colorsStates.putAll(ColorPickers.stateColors);
    repaintCanvas();
  }


  @Override
  public void initializeCanvas() {
    this.getChildren().remove(canvas);
    canvas = new Canvas(calcCanvasWidth(), calcCanvasHeight());
    this.getChildren().add(canvas);
    setupCanvasClickHandler();
    repaintCanvas();
  }

  private int calcCanvasWidth() {
    return 2 * BORDER_SIZE +
      automaton.getColumns() * cellSize;
  }

  private int calcCanvasHeight() {
    return 2 * BORDER_SIZE
      + automaton.getRows() * cellSize;
  }

  @Override
  public void repaintCanvas() {
    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.setStroke(Color.web("#66ccff"));
    gc.setLineWidth(2);

    drawCells(gc);
    drawGridLines(gc);
  }

  private void drawCells(GraphicsContext gc) {
    for (int r = 0; r < automaton.getRows(); r++) {
      double y = BORDER_SIZE + r * cellSize * SCALE_FACTOR;
      for (int c = 0; c < automaton.getColumns(); c++) {
        int state = automaton.getCell(r, c).getState();
        gc.setFill(colorsStates.get(state));
        double x = BORDER_SIZE + c * cellSize * SCALE_FACTOR;

        // Fill the cell with its corresponding color
        gc.fillRect(
          x, y, cellSize * SCALE_FACTOR,
          cellSize * SCALE_FACTOR
        );
      }
    }
  }

  private void drawGridLines(GraphicsContext gc) {
    int canvasWidth = (int) canvas.getWidth();
    int canvasHeight = (int) canvas.getHeight();

    for (int r = 0; r <= automaton.getRows(); r++) {
      double y = BORDER_SIZE + r * cellSize * SCALE_FACTOR;
      gc.strokeLine(BORDER_SIZE, y, (double) canvasWidth - BORDER_SIZE, y);
      // Horizontal line
    }

    for (int c = 0; c <= automaton.getColumns(); c++) {
      double x = BORDER_SIZE + c * cellSize * SCALE_FACTOR;
      gc.strokeLine(
        x, BORDER_SIZE, x,
        (double) canvasHeight - BORDER_SIZE
      ); // Vertical line
    }
  }

  private void setupCanvasClickHandler() {
    canvas.setOnMousePressed(this::handleCanvasClickOrDrag);
    canvas.setOnMouseDragged(this::handleCanvasClickOrDrag);
  }

  private void handleCanvasClickOrDrag(MouseEvent event) {
    double mouseX = event.getX();
    double mouseY = event.getY();
    int column = (int) ((mouseX - BORDER_SIZE)
      / (cellSize * SCALE_FACTOR));
    int row = (int) ((mouseY - BORDER_SIZE)
      / (cellSize * SCALE_FACTOR));

    if (row >= 0 && row < automaton.getRows() &&
      column >= 0 && column < automaton.getColumns()) {
      if (event.isPrimaryButtonDown()) { // Left click
        automaton.getCell(row, column)
          .setState(ColorPickers.getStateForColor(ColorPickers.selectedColor));
        // Werde ich noch ändern
      } else if (event.isSecondaryButtonDown()) { // Right click
        automaton.getCell(row, column).setState(0); // Reset to default state
      }
      repaintCell(row, column); // Only repaint affected cell
    }
  }
  @Override
  public void repaintCell(int row, int column) {
    GraphicsContext gc = canvas.getGraphicsContext2D();
    int state = automaton.getCell(row, column).getState();
    gc.setFill(colorsStates.get(state));

    double x = BORDER_SIZE + column * cellSize * SCALE_FACTOR;
    double y = BORDER_SIZE + row * cellSize * SCALE_FACTOR;

    gc.fillRect(
      x, y, cellSize * SCALE_FACTOR,
      cellSize * SCALE_FACTOR
    );
    drawGridLineForCell(gc, x, y); // Draw borders around the specific cell
  }

  // Method to draw grid lines around a specific cell (if needed)
  private void drawGridLineForCell(GraphicsContext gc, double x, double y) {
    gc.setStroke(Color.web("#66ccff"));
    gc.setLineWidth(2);

    // Left line
    gc.strokeLine(x, y, x, y + cellSize * SCALE_FACTOR);
    // Top line
    gc.strokeLine(x, y, x + cellSize * SCALE_FACTOR, y);
    // Right line
    gc.strokeLine(
      x + cellSize * SCALE_FACTOR, y,
      x + cellSize * SCALE_FACTOR, y + cellSize * SCALE_FACTOR
    );
    // Bottom line
    gc.strokeLine(
      x, y + cellSize * SCALE_FACTOR,
      x + cellSize * SCALE_FACTOR, y + cellSize * SCALE_FACTOR
    );
  }

  @Override
  public void cellSize() {
    this.cellSize = automaton.getCellSize();
  }

}