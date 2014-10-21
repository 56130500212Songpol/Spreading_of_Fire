/**
 * This cell class that contains the state of cell
 *
 * @author SpreadingOfFire
 * @version 21/10/2014
 */
public class Cell {
  
  public static final int EMPTY = 0, TREE = 1, BURNING = 2;
  private int state;
  
  /**
   * Constructor - create the cell by EMPTY state
   */
  public Cell() {
    state = Cell.EMPTY;
  }
  
}
