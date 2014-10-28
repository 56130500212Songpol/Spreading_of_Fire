import java.util.Random;

/**
 * The model class of project contain logic of spreading fire of forest
 *
 * @author OOSD Project Group 5
 * @version 28/10/2014
 */
public class Model {
  
  private Cell[][] cell;                              //Assume every cells are on the component
  private View view;                                  //Painting on component 
  private Random random;                              //Random number compare with probCatch, probTree, probBurning
  private int delay, numCell, positionX, positionY;
  private double probCatch, probTree, probBurning;
  private boolean checkCellCannotFire[][];            //check the cell can burn or not
  
  /**
   * Constructor - create the model 
   */
  public Model(View view, int numCell, double probCatch, double probTree, double probBurning, int delay) {
    this.view = view;
    this.numCell = numCell;
    this.probCatch = probCatch;
    this.probTree = probTree;
    this.probBurning = probBurning;
    random = new Random();
    this.delay = delay;
    checkCellCannotFire = new boolean[numCell][numCell];
  }
  
  /** 
   *  Create the forest(view) that contain many(cell) 
   */
  public void initForest() {
    cell = new Cell[getNumCell()][getNumCell()];//create array of cell, size = number of cell x number of cell
    for (int i = 0; i <= cell.length - 1; i++) {
      for (int j = 0; j <= cell.length - 1; j++) {
        cell[i][j] = new Cell(Cell.TREE);//assign every cell is a TREE
        if (random.nextDouble() < probTree) {           //tree at site
          if (random.nextDouble() < probBurning) {    //tree is burning
            cell[i][j].setState(Cell.BURNING);
          } else {                                    //tree is not buring
            cell[i][j].setState(Cell.TREE);
          }
        } else {                                        //no tree at site
          cell[i][j].setState(Cell.EMPTY);
        }
        if (i == 0 || i == cell.length - 1 || j == 0 || j == cell.length - 1) {
          cell[i][j] = new Cell(0); //create absorbing boundary condition
        }
      }
    }
    cell[cell.length/2][cell.length/2].setState(2); //set the initial burning cell in the middle
    view.updateView(cell); //update the color of each cell
  }
  
  /**
   * Spreading the fire from the burning cell to north cell, east cell, west
   * cell and south cell by . After burning cell spread the fire, burning cell
   * transform to empty cell. **Do not spread on absorbing boundary
   * condition**
   *
   */
  public void spreading() {
    for (int i = 1; i < cell.length - 1; i++) {
      for (int j = 1; j < cell[0].length - 1; j++) {
        if (cell[i][j].getState() == Cell.BURNING && checkCellCannotFire[i][j] == false) {
          cell[i][j].setState(0);
          if (cell[i + 1][j].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) {//spread to south tree
            cell[i + 1][j].setState(2);
            checkCellCannotFire[i + 1][j] = true;                   //Cannot burn this tree again
          }
          if (cell[i - 1][j].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) {//spread to north tree
            cell[i - 1][j].setState(2);
            checkCellCannotFire[i - 1][j] = true;                   //Cannot burn this tree again
          }
          if (cell[i][j + 1].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) {//spread to east tree
            cell[i][j + 1].setState(2);
            checkCellCannotFire[i][j + 1] = true;                   //Cannot burn this tree again
          }
          if (cell[i][j - 1].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) {//spread to west tree
            cell[i][j - 1].setState(2);
            checkCellCannotFire[i][j + 1] = true;                   //Cannot burn this tree again
          }
        }
      }
    }
  }
  
  /**
   * Reset the boolean of every cell to false **Do not reset on absorbing
   * boundary condition**
   *
   */
  public void resetCheck() {
    for (int i = 1; i < checkCellCannotFire.length - 1; i++) {
      for (int j = 1; j < checkCellCannotFire.length - 1; j++) {
        checkCellCannotFire[i][j] = false;
      }
    }
  }
  
  /**
   * Find burning tree in the forest **Do not check on absorbing boundary
   * condition**
   *
   * @return true if has fire in forest, false if do not has fire in forest
   */
  public boolean noFire() {
    for (int i = 1; i < getNumCell() - 1; i++) {
      for (int j = 1; j < getNumCell() - 1; j++) {
        if (cell[i][j].getState() == Cell.BURNING) {
          return false;
        }
      }
    }
    return true;
  }
  
  /**
   * Activate spreading method by check fire in the forest. After that, reset
   * the boolean of every cell(every cell can burn), and update the component.
   * Use Thread class for make multi thread with delay (sleep method).
   * ** Auto Spread **
   *
   */
  public void spread() {
    try {
      if (!noFire()) {
        spreading();
      }
      resetCheck();
      view.updateView(cell);
      Thread.sleep(getDelay());
    } catch (InterruptedException e) {
      
    }
  }
  
  /**
   * Activate spreading method by check fire in the forest. After that, reset
   * the boolean of every cell(every cell can burn), and update the component.
   * Use Thread class for make multi thread with delay (sleep method).
   * ** Step - by - Step **
   *
   */
  public void spreadStepbyStep() {
    try {
      if (!noFire()) {
        spreading();
      }
      resetCheck();
      view.updateView(cell);
      Thread.sleep(2);
    } catch (InterruptedException e) {
      
    }
  }
  
  /**
   * Apply spread method to forest until forest do not has burning tree on
   * its.
   *
   */
  public void applySpread() {
    while (!noFire()) {
      spread();
    }
  }
  
  /**
   * Set the value of delay
   *
   * @param delay
   */
  public void setDelay(int delay) {
    this.delay = delay;
  }
  
  /**
   * Get the value of delay
   *
   * @return delay
   */
  public int getDelay() {
    return this.delay;
  }
  
  /**
   * Set the number of cell
   *
   * @param numCell
   */
  public void setNumCell(int numCell) {
    this.numCell = numCell;
  }
  
  /**
   * Get the number of cell
   *
   * @return numCell
   */
  public int getNumCell() {
    return this.numCell;
  }
  
  /**
   * Set the value of probability catching fire of tree
   *
   * @param probCatch
   */
  public void setProbCatch(double probCatch) {
    this.probCatch = probCatch;
  }
  
  /**
   * Get the value of probability catching fire of tree
   *
   * @return probCatch
   */
  public double getProbCatch() {
    return this.probCatch;
  }
  
  /**
   * Set the value of probability tree density on forest
   *
   * @param probTree
   */
  public void setProbTree(double probTree) {
    this.probTree = probTree;
  }
  
  /**
   * Get the value of probability tree density on forest
   *
   * @return probTree
   */
  public double getProbTree() {
    return probTree;
  }
  
  /**
   * Set the value of probability burning tree appear on forest
   *
   * @param probBurning
   */
  public void setProbBurning(double probBurning) {
    this.probBurning = probBurning;
  }
  
  /**
   * Get the value of probability burning tree appear on forest
   *
   * @return probBurning
   */
  public double getProbBurning() {
    return probBurning;
  }
  /**
   * Set the position of initial burning tree on the middle of forest
   *
   */
  public void setPositionXonMiddle() {
    this.positionX = getNumCell() / 2;
  }
  
  /**
   * Set the position of initial burning tree on the middle of forest
   *
   */
  public void setPositionYonMiddle() {
    this.positionY = getNumCell() / 2;
  }
  
  /**
   * Set the position of initial burning tree on random position.
   *
   */
  public void setRandomPositionX() {
    this.positionX = (int) (Math.random() * (getNumCell() - 2) + 1);
  }
  
  /**
   * Get the position X
   *
   * @return positionX
   */
  public int getPositionX() {
    return this.positionX;
  }
  
  /**
   * Set the position of initial burning tree on random position.
   *
   */
  public void setRandomPositionY() {
    this.positionY = (int) (Math.random() * (getNumCell() - 2) + 1);
  }
  
  /**
   * Get the position Y
   *
   * @return positionY
   */
  public int getPositionY() {
    return this.positionY;
  }
}



