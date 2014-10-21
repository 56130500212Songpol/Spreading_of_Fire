/**
 * The model class of project
 *
 * @author SpreadingOfFire
 * @version 21/10/2014
 */
public class Model {
  
  private Cell[][] cell;
  private View view;
  private int numCell;
  
  /**
   * Constructor - create the model with number of cell
   */
  public Model(View view, int numCell) {
    this.view = view;
    this.numCell = numCell;
  }
  
  /** 
   *  The method use to create the forest(view) that contain many(cell) 
   */
  public void initForest() {
    cell = new Cell[getNumCell()][getNumCell()];//create array of cell, size = number of cell x number of cell
    for (int i = 0; i <= cell.length - 1; i++) {
      for (int j = 0; j <= cell.length - 1; j++) {
        cell[i][j] = new Cell(Cell.TREE);//assign every cell is a TREE
        if (i == 0 || i == cell.length - 1 || j == 0 || j == cell.length - 1) {
          cell[i][j] = new Cell(0); //create absorbing boundary condition
        }
      }
    }
    cell[cell.length/2][cell.length/2].setState(2); //set the initial burning cell in the middle
    view.updateView(cell); //update the color of each cell
  }
  
  /**
   * The method use to set the number of cell
   *
   * @param numCell
   */
  public void setNumCell(int numCell) {
    this.numCell = numCell;
  }
  
  /**
   * The method use to get the number of cell
   *
   * @return numCell
   */
  public int getNumCell() {
    return this.numCell;
  }
}
