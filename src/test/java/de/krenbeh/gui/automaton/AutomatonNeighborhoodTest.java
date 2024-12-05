package de.krenbeh.gui.automaton;

import de.krenbeh.gui.Cell;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class AutomatonNeighborhoodTest {

  @Test
  void testGetNeighborsWithStandardNonMooreSetup() throws AutomatonException {
    Cell[][] grid = new Cell[3][3];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        grid[i][j] = new Cell();
      }
    }
    AutomatonNeighborhood neighborhood = new AutomatonNeighborhood(3, 3, false, false) {
      @Override
      protected Cell transform(Cell cell, Cell[] neighbors) {
        return null;
      }
    };
    List<Cell> neighbors = neighborhood.getNeighbors(1, 1);
    Assertions.assertEquals(4, neighbors.size(), "Neighbor count for standard non-Moore setup should be 4");
  }

  @Test
  void testGetNeighborsWithStandardMooreSetup() throws AutomatonException {
    AutomatonNeighborhood neighborhood = new AutomatonNeighborhood(3, 3, false, true) {
      @Override
      protected Cell transform(Cell cell, Cell[] neighbors) {
        return null;
      }
    };
    List<Cell> neighbors = neighborhood.getNeighbors(1, 1);
    Assertions.assertEquals(8, neighbors.size(), "Neighbor count for standard Moore setup should be 8");
  }

  @Test
  void testGetNeighborsWithTorusNonMooreSetup() throws AutomatonException {
    AutomatonNeighborhood neighborhood = new AutomatonNeighborhood(3, 3, true, false) {
      @Override
      protected Cell transform(Cell cell, Cell[] neighbors) {
        return null;
      }
    };
    List<Cell> neighbors = neighborhood.getNeighbors(0, 0);
    Assertions.assertEquals(4, neighbors.size(), "Neighbor count for torus non-Moore setup should be 4");
  }

  @Test
  void testGetNeighborsWithTorusMooreSetup() throws AutomatonException {
    AutomatonNeighborhood neighborhood = new AutomatonNeighborhood(3, 3, true, true) {
      @Override
      protected Cell transform(Cell cell, Cell[] neighbors) {
        return null;
      }
    };
    List<Cell> neighbors = neighborhood.getNeighbors(0, 0);
    Assertions.assertEquals(8, neighbors.size(), "Neighbor count for torus Moore setup should be 8");
  }


  @Test
  void torusAusMoorneighborhoodAus() throws AutomatonException {
    AutomatonNeighborhood neighborhood = new AutomatonNeighborhood(3, 3, false, false) {
      @Override
      protected Cell transform(Cell cell, Cell[] neighbors) {
        return null;
      }
    };
    List<Cell> neighbors = neighborhood.getNeighbors(0, 0);
    Assertions.assertEquals(2, neighbors.size(), "Neighbor count for torus Moore setup should be 2");
  }
}