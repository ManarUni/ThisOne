package de.krenbeh.gui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

 class CellTest {

    @Test
     void testNeighborNotNull() {
        Cell cell = new Cell();
        assertNotNull(cell.getNeighbors());
    }

  @Test
   void testSetStateZero() {
    Cell cell = new Cell();
    cell.setState(0);
    assertEquals(0, cell.getState());
  }

  @Test
   void testSetStatePositive() {
    Cell cell = new Cell();
    cell.setState(5);
    assertEquals(5, cell.getState());
  }

  @Test
   void testSetStateNegative() {
    Cell cell = new Cell();
    cell.setState(-3);
    assertEquals(-3, cell.getState());
  }

  @Test
   void testSetStateChange() {
    Cell cell = new Cell();
    cell.setState(2);
    assertEquals(2, cell.getState());
    cell.setState(7);
    assertEquals(7, cell.getState());
  }
  @Test
   void testinitialState() {
    Cell cell = new Cell();
    assertEquals(0, cell.getState());
  }
}