import model.Automaton;
import model.Cell;
  public class Replikator extends Automaton {
    final private static int initNumberOfRows = 50;
    final private static int initNumberOfColumns = 50;
    final private static int numberOfStates = 2;
    final private static boolean mooreNeighborhood = true;
    final private static boolean initTorus = true;

    public Replikator() {
      super(initNumberOfRows, initNumberOfColumns,
        numberOfStates, mooreNeighborhood,
        initTorus);
    }
    protected Cell transform(Cell cell, Cell[] neighbors) {
      return cell;
    }
  }