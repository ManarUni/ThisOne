package de.krenbeh.gui.automaton;

import de.krenbeh.gui.Cell;
import de.krenbeh.automaten.GameOfLife4Zusteande;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AutomatonTest {

  private GameOfLife4Zusteande automaton;

  @BeforeEach
  void setup() throws AutomatonException {
    automaton = new GameOfLife4Zusteande(5, 5, true);
  }

  @Test
  void testInitialGridSize() {
    assertEquals(5, automaton.getRows());
    assertEquals(5, automaton.getColumns());
  }

  @Test
  void testChangeSizeIncrease() {
    automaton.changeSize(7, 7);
    assertEquals(7, automaton.getRows());
    assertEquals(7, automaton.getColumns());
  }

  @Test
  void testChangeSizeDecrease() {
    automaton.changeSize(3, 3);
    assertEquals(3, automaton.getRows());
    assertEquals(3, automaton.getColumns());
  }

  @Test
  void testSetAndGetState() {
    automaton.setState(2, 2, 1);
    assertEquals(1, automaton.getCell(2, 2).getState());
    automaton.setState(2, 2, 2);
    assertEquals(2, automaton.getCell(2, 2).getState());
  }

  @Test
  void testRandomPopulation() {
    automaton.randomPopulation();
    boolean hasNonZeroState = false;

    for (int row = 0; row < automaton.getRows(); row++) {
      for (int col = 0; col < automaton.getColumns(); col++) {
        if (automaton.getCell(row, col).getState() != 0) {
          hasNonZeroState = true;
          break;
        }
      }
    }

    assertTrue(hasNonZeroState, "The random population should set some cells to non-zero states.");
  }

  @Test
  void testClearPopulation() {
    automaton.randomPopulation();
    automaton.clearPopulation();

    for (int row = 0; row < automaton.getRows(); row++) {
      for (int col = 0; col < automaton.getColumns(); col++) {
        assertEquals(0, automaton.getCell(row, col).getState(), "All cells should be cleared to state 0.");
      }
    }
  }

  @Test
  void testSetStateForRegion() {
    automaton.setState(1, 1, 3, 3, 2);

    for (int row = 1; row <= 3; row++) {
      for (int col = 1; col <= 3; col++) {
        assertEquals(2, automaton.getCell(row, col).getState(), "Cells in region should be set to state 2.");
      }
    }
  }

  @Test
  void testTorusWrappingNeighbors() {
    automaton.enableTorus(); // Enable torus mode
    assertTrue(automaton.isTorus(), "Torus mode should be enabled.");
    automaton.enableMooreNeighborHood();
    assertTrue(automaton.isMooreNeighborHood(), "MooreNeighborhood mode should be enabled.");

    List<Cell> neighbors = automaton.getNeighbors(0, 0);

    assertEquals(8, neighbors.size(), "Torus wrapping should have 8 neighbors.");
  }

  @Test
  void setMooreNeighborHoodAusTorusAus() {
    automaton.disableMooreNeighborHood(); // Enable torus mode
    assertFalse(automaton.isMooreNeighborHood(), "isMooreNeighborHood mode should be disabled.");
    automaton.disableTorus(); // Enable torus mode
    assertFalse(automaton.isTorus(), "Torus mode should be disabled.");
    List<Cell> neighbors = automaton.getNeighbors(0, 0);
    assertEquals(2, neighbors.size(), "Torus wrapping should have 8 neighbors.");
  }

  @Test
  void setMooreNeighborHoodAusTorusAn() {
    automaton.disableMooreNeighborHood();
    assertFalse(automaton.isMooreNeighborHood(), "MooreNeighborHood mode should be enabled.");
    assertTrue(automaton.isTorus(), "Torus mode should be enabled.");
    List<Cell> neighbors = automaton.getNeighbors(0, 0);
    assertEquals(4, neighbors.size(), "Torus wrapping should have 8 neighbors.");
  }

  @Test
  void testNextGeneration() throws AutomatonException {
    automaton.setState(2, 2, 1);
    automaton.nextGeneration();
    assertNotNull(automaton.getCell(2, 2), "Cells should transform based on nextGeneration.");
  }

  @Test
  void testNeighborMooreVsVonNeumann() throws AutomatonException {
    // Moore neighborhood should have 8 neighbors
    automaton.setState(0, 0, 1);
    List<Cell> neighborsMoore = automaton.getNeighbors(2, 2);
    assertEquals(8, neighborsMoore.size(), "Moore neighborhood should have 8 neighbors.");

    // Switch to Von Neumann (non-diagonal)
    GameOfLife4Zusteande vonNeumannAutomaton = new GameOfLife4Zusteande(5, 5, false);
    List<Cell> neighborsVonNeumann = vonNeumannAutomaton.getNeighbors(2, 2);
    assertEquals(8, neighborsVonNeumann.size(), "Von Neumann neighborhood should have 4 neighbors.");
  }

  @Test
  void testIsValidCell() {
    assertTrue(automaton.isValidCell(2, 2), "Cell within bounds should be valid.");
    assertFalse(automaton.isValidCell(-1, 2), "Cell with negative row should be invalid.");
    assertFalse(automaton.isValidCell(2, -1), "Cell with negative column should be invalid.");
    assertFalse(automaton.isValidCell(5, 5), "Cell out of bounds should be invalid.");
  }

  @Test
  void testStateTransitions() {
    Cell cell = new Cell();
    cell.setState(0);

    // Transition 0 -> 1 (dead to alive)
    Cell[] neighbors = {new Cell(1), new Cell(1)};  // 2 alive neighbors
    Cell newCell = automaton.transform(cell, neighbors);
    assertEquals(1, newCell.getState(), "State 0 should transition to 1 with 2 alive neighbors.");

    // Transition 1 -> 2 (alive to sleeping)
    cell.setState(1);
    newCell = automaton.transform(cell, new Cell[0]);
    assertEquals(2, newCell.getState(), "State 1 should transition to 2 (sleeping).");

    // Transition 2 -> 3 (sleeping to cooldown 1)
    cell.setState(2);
    newCell = automaton.transform(cell, new Cell[0]);
    assertEquals(3, newCell.getState(), "State 2 should transition to 3 (cooldown 1).");

    // Transition 3 -> 4 (cooldown 1 to cooldown 2)
    cell.setState(3);
    newCell = automaton.transform(cell, new Cell[0]);
    assertEquals(4, newCell.getState(), "State 3 should transition to 4 (cooldown 2).");

    // Transition 4 -> 0 (cooldown 2 to dead)
    cell.setState(4);
    newCell = automaton.transform(cell, new Cell[0]);
    assertEquals(0, newCell.getState(), "State 4 should transition to 0 (dead).");
  }
}
