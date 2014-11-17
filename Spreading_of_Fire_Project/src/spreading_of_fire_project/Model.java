package spreading_of_fire_project;

/**
 * The model class of project contain logic of spreading fire of forest
 *
 * @author OOSD Project Group 5
 * @version 15/11/2014
 */
import java.util.Random;

public class Model {

    private Cell[][] cell;                              //Assume every cells are on the component
    private View view;                                  //Painting on component 
    private Random random;                              //Random number compare with probCatch, probTree, probBurning
    private int windSpeed, delay, numCell, positionX, positionY, stepToBurn[][], stepToNotBurn[][], previousState[][];
    private double probCatch, probTree, probBurning, probLightning;
    private boolean isVary, stop, lightningStatus, checkCellCannotFire[][], checkCellBurnByLightning[][],
            checkCellNotBurnByLightning[][], checkCellNotEmptyByBurn[][];
    private String direction;
    public static final int NONE = 0, LOW = 1, HIGH = 2;

    /**
     * Constructor - create simulation of Spreading of Fire with default initial
     * burn cell(middle)
     *
     * @param view
     * @param numCell
     * @param probCatch
     * @param probTree
     * @param probBurning
     * @param probLightning
     * @param delay
     * @param direction
     * @param windSpeed
     * @param lightningStatus
     * @param isVary
     */
    public Model(View view, int numCell, double probCatch, double probTree,
            double probBurning, double probLightning, int delay, String direction,
            int windSpeed, boolean lightningStatus, boolean isVary) {
        this.view = view;
        this.numCell = numCell;
        this.probCatch = probCatch;
        this.probTree = probTree;
        this.probBurning = probBurning;
        this.probLightning = probLightning;
        this.positionX = numCell / 2;                           //on the middle
        this.positionY = numCell / 2;                           //on the middle
        random = new Random();
        this.delay = delay;
        this.direction = direction;
        this.windSpeed = windSpeed;
        this.lightningStatus = lightningStatus;
        this.isVary = isVary;
        checkCellCannotFire = new boolean[numCell][numCell];
        checkCellBurnByLightning = new boolean[numCell][numCell];
        checkCellNotBurnByLightning = new boolean[numCell][numCell];
        checkCellNotEmptyByBurn = new boolean[numCell][numCell];
        stepToBurn = new int[numCell][numCell];
        stepToNotBurn = new int[numCell][numCell];
        previousState = new int[numCell][numCell];
    }

    /**
     * Constructor - create simulation of Spreading of Fire with customize
     * initial burn cell(random or middle)
     *
     * @param view
     * @param numCell
     * @param positionX
     * @param positionY
     * @param probCatch
     * @param probTree
     * @param probBurning
     * @param probLightning
     * @param delay
     * @param direction
     * @param windSpeed
     * @param lightningStatus
     * @param isVary
     */
    public Model(View view, int numCell, int positionX, int positionY, double probCatch,
            double probTree, double probBurning, double probLightning, int delay, String direction,
            int windSpeed, boolean lightningStatus, boolean isVary) {
        this.view = view;
        this.numCell = numCell;
        this.probCatch = probCatch;
        this.probTree = probTree;
        this.probBurning = probBurning;
        this.probLightning = probLightning;
        this.positionX = positionX;
        this.positionY = positionY;
        random = new Random();
        this.delay = delay;
        this.direction = direction;
        this.windSpeed = windSpeed;
        this.lightningStatus = lightningStatus;
        this.isVary = isVary;
        checkCellCannotFire = new boolean[numCell][numCell];
        checkCellBurnByLightning = new boolean[numCell][numCell];
        checkCellNotBurnByLightning = new boolean[numCell][numCell];
        checkCellNotEmptyByBurn = new boolean[numCell][numCell];
        stepToBurn = new int[numCell][numCell];
        stepToNotBurn = new int[numCell][numCell];
        previousState = new int[numCell][numCell];
    }

    /**
     * Create the initial forest
     *
     */
    public void initForest() {
        view.setStep(0);
        cell = new Cell[getNumCell()][getNumCell()];
        for (int i = 0; i <= cell.length - 1; i++) {
            for (int j = 0; j <= cell.length - 1; j++) {
                cell[i][j] = new Cell(Cell.TREE);
                stepToBurn[i][j] = 0;
                checkCellNotEmptyByBurn[i][j] = false;
                if (random.nextDouble() < probTree) {           //tree at site
                    if (random.nextDouble() < probBurning) {    //tree is burning
                        cell[i][j].setState(Cell.BURNING);
                    } else {                                    //tree is not buring
                        cell[i][j].setState(Cell.TREE);
                    }
                } else {                                        //no tree at site
                    cell[i][j].setState(Cell.EMPTY);
                    checkCellNotEmptyByBurn[i][j] = true;
                }
                if (i == 0 || i == cell.length - 1 || j == 0 || j == cell.length - 1) {// site is absorbing boundary condition
                    cell[i][j] = new Cell(0);
                }
            }
        }
        cell[getPositionX()][getPositionY()].setState(2);       //initail burning tree
        countTree(); // count the number of tre in forest
        view.updateView(cell);
    }

    /**
     * Spreading the fire from the burning cell to north cell, east cell, west
     * cell and south cell by compare random number with probCatch. After
     * burning cell spread the fire, burning cell transform to empty cell. **Do
     * not spread on absorbing boundary condition**
     *
     */
    public void spreading() {
        for (int i = 1; i < cell.length - 1; i++) {
            for (int j = 1; j < cell[0].length - 1; j++) {
                try {
                    if (cell[i][j].getState() == Cell.BURNING && checkCellCannotFire[i][j] == false
                            && checkCellBurnByLightning[i][j] == false) { // if find burning cell
                        cell[i][j].setState(0); // burn this cell
                        if (getDirection().equals("North") && getWindSpeed() == Model.LOW) { // if wind is north and speed is low
                            if (cell[i + 1][j].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to east tree
                                cell[i + 1][j].setState(2); // tree caught fire
                                checkCellCannotFire[i + 1][j] = true;                   // Cannot burn this tree again
                            }
                            if (cell[i - 1][j].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to west tree
                                cell[i - 1][j].setState(2); // tree caught fire
                                checkCellCannotFire[i - 1][j] = true;                   // Cannot burn this tree again
                            }
                            if (cell[i][j + 1].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) {// spread to south tree
                                cell[i][j + 1].setState(2); // tree caught fire
                                checkCellCannotFire[i][j + 1] = true;                   // Cannot burn this tree again
                            }
                            if (cell[i][j - 1].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to north tree
                                cell[i][j - 1].setState(2); // tree caught fire
                                checkCellCannotFire[i][j - 1] = true;                   // Cannot burn this tree again
                                if (cell[i][j - 2].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to north again
                                    cell[i][j - 2].setState(2); // tree caught fire 
                                    checkCellCannotFire[i][j - 2] = true;                   // Cannot burn this tree again
                                }
                            }
                        } else if (getDirection().equals("East") && getWindSpeed() == Model.LOW) { // if wind is east and speed is low
                            if (cell[i + 1][j].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to east tree
                                cell[i + 1][j].setState(2); // tree caught fire
                                checkCellCannotFire[i + 1][j] = true;                   // Cannot burn this tree again
                                if (cell[i + 2][j].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to east again
                                    cell[i + 2][j].setState(2);
                                    checkCellCannotFire[i + 2][j] = true;                   // Cannot burn this tree again
                                }
                            }
                            if (cell[i - 1][j].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to west tree
                                cell[i - 1][j].setState(2); // tree caught fire
                                checkCellCannotFire[i - 1][j] = true;                   // Cannot burn this tree again
                            }
                            if (cell[i][j + 1].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to ssouth tree
                                cell[i][j + 1].setState(2); // tree caught fire
                                checkCellCannotFire[i][j + 1] = true;                   // Cannot burn this tree again
                            }
                            if (cell[i][j - 1].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to north tree
                                cell[i][j - 1].setState(2); // tree caught fire
                                checkCellCannotFire[i][j - 1] = true;                   // Cannot burn this tree again
                            }
                        } else if (getDirection().equals("West") && getWindSpeed() == Model.LOW) { // if wind is west and speed is low
                            if (cell[i + 1][j].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to east tree
                                cell[i + 1][j].setState(2); // tree caught fire
                                checkCellCannotFire[i + 1][j] = true;                   // Cannot burn this tree again
                            }
                            if (cell[i - 1][j].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to west tree
                                cell[i - 1][j].setState(2); // tree caught fire
                                checkCellCannotFire[i - 1][j] = true;                   // Cannot burn this tree again
                                if (cell[i - 2][j].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to west again
                                    cell[i - 2][j].setState(2); // tree caught fire
                                    checkCellCannotFire[i - 2][j] = true;                   // Cannot burn this tree again
                                }
                            }
                            if (cell[i][j + 1].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to south tree
                                cell[i][j + 1].setState(2); // tree caught fire
                                checkCellCannotFire[i][j + 1] = true;                   // Cannot burn this tree again
                            }
                            if (cell[i][j - 1].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to north tree
                                cell[i][j - 1].setState(2); // tree caught fire
                                checkCellCannotFire[i][j - 1] = true;                   // Cannot burn this tree again
                            }
                        } else if (getDirection().equals("South") && getWindSpeed() == Model.LOW) { // if wind is south and speed is low
                            if (cell[i + 1][j].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to east tree
                                cell[i + 1][j].setState(2); // tree caught fire
                                checkCellCannotFire[i + 1][j] = true;                   // Cannot burn this tree again
                            }
                            if (cell[i - 1][j].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to west tree
                                cell[i - 1][j].setState(2); // tree caught fire
                                checkCellCannotFire[i - 1][j] = true;                   // Cannot burn this tree again

                            }
                            if (cell[i][j + 1].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to south tree
                                cell[i][j + 1].setState(2); // tree caught fire
                                checkCellCannotFire[i][j + 1] = true;                   // Cannot burn this tree again
                                if (cell[i][j + 2].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to south tree
                                    cell[i][j + 2].setState(2);  // tree caught fire
                                    checkCellCannotFire[i][j + 2] = true;                   // Cannot burn this tree again
                                }
                            }
                            if (cell[i][j - 1].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to north tree
                                cell[i][j - 1].setState(2); // tree caught fire
                                checkCellCannotFire[i][j - 1] = true;                   // Cannot burn this tree again
                            }
                        } else if (getDirection().equals("North") && getWindSpeed() == Model.HIGH) { // if wind is north and speed is high
                            if (cell[i][j - 1].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to north tree
                                cell[i][j - 1].setState(2); // tree caught fire
                                checkCellCannotFire[i][j - 1] = true;                   // Cannot burn this tree again
                                if (cell[i][j - 2].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to north again
                                    cell[i][j - 2].setState(2); // tree caught fire
                                    checkCellCannotFire[i][j - 2] = true;                   // Cannot burn this tree again
                                }
                                if (cell[i][j - 3].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to north again
                                    cell[i][j - 3].setState(2); // tree caught fire
                                    checkCellCannotFire[i][j - 3] = true;                   //Cannot burn this tree again
                                }
                            }
                            if (cell[i + 1][j].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to east tree
                                cell[i + 1][j].setState(2); // tree caught fire
                                checkCellCannotFire[i + 1][j] = true;                   // Cannot burn this tree again
                            }
                            if (cell[i - 1][j].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to west tree
                                cell[i - 1][j].setState(2); // tree caught fire
                                checkCellCannotFire[i - 1][j] = true;                   // Cannot burn this tree again

                            }
                        } else if (getDirection().equals("East") && getWindSpeed() == Model.HIGH) { // if wind is east and speed is high
                            if (cell[i + 1][j].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to east tree
                                cell[i + 1][j].setState(2); // tree caught fire
                                checkCellCannotFire[i + 1][j] = true;                   // Cannot burn this tree again
                                if (cell[i + 2][j].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to east again
                                    cell[i + 2][j].setState(2); // tree caught fire
                                    checkCellCannotFire[i + 2][j] = true;                   // Cannot burn this tree again
                                }
                                if (cell[i + 3][j].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to east again
                                    cell[i + 3][j].setState(2); // tree caught fire
                                    checkCellCannotFire[i + 3][j] = true;                   // Cannot burn this tree again
                                }
                            }
                            if (cell[i][j + 1].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to south tree
                                cell[i][j + 1].setState(2); // tree caught fire
                                checkCellCannotFire[i][j + 1] = true;                   // Cannot burn this tree again
                            }
                            if (cell[i][j - 1].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to north tree
                                cell[i][j - 1].setState(2); // tree caught fire
                                checkCellCannotFire[i][j - 1] = true;                   // Cannot burn this tree again
                            }
                        } else if (getDirection().equals("West") && getWindSpeed() == Model.HIGH) { // if wind is west and speed is high
                            if (cell[i - 1][j].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to west tree
                                cell[i - 1][j].setState(2); // tree caught fire
                                checkCellCannotFire[i - 1][j] = true;                   // Cannot burn this tree again
                                if (cell[i - 2][j].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to west again
                                    cell[i - 2][j].setState(2); // tree caught fire
                                    checkCellCannotFire[i - 2][j] = true;                   // Cannot burn this tree again
                                }
                                if (cell[i - 3][j].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to west again
                                    cell[i - 3][j].setState(2); // tree caught fire
                                    checkCellCannotFire[i - 3][j] = true;                   // Cannot burn this tree again
                                }
                            }
                            if (cell[i][j + 1].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to south tree
                                cell[i][j + 1].setState(2); // tree caught fire
                                checkCellCannotFire[i][j + 1] = true;                   // Cannot burn this tree again
                            }
                            if (cell[i][j - 1].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to north tree
                                cell[i][j - 1].setState(2); // tree caught fire
                                checkCellCannotFire[i][j - 1] = true;                   // Cannot burn this tree again
                            }
                        } else if (getDirection().equals("South") && getWindSpeed() == Model.HIGH) { // if wind is south and speed is high
                            if (cell[i][j + 1].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to south tree
                                cell[i][j + 1].setState(2); // tree caught fire
                                checkCellCannotFire[i][j + 1] = true;                   // Cannot burn this tree again
                                if (cell[i][j + 2].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to south again
                                    cell[i][j + 2].setState(2); // tree caught fire
                                    checkCellCannotFire[i][j + 2] = true;                   // Cannot burn this tree again
                                }
                                if (cell[i][j + 3].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to south again
                                    cell[i][j + 3].setState(2); // tree caught fire
                                    checkCellCannotFire[i][j + 3] = true;                   // Cannot burn this tree again
                                }
                            }
                            if (cell[i + 1][j].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to east tree
                                cell[i + 1][j].setState(2); // tree caught fire
                                checkCellCannotFire[i + 1][j] = true;                   // Cannot burn this tree again
                            }
                            if (cell[i - 1][j].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to west tree
                                cell[i - 1][j].setState(2); // tree caught fire
                                checkCellCannotFire[i - 1][j] = true;                   // Cannot burn this tree again

                            }
                        } else {
                            if (cell[i + 1][j].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to east tree
                                cell[i + 1][j].setState(2); // tree caught fire
                                checkCellCannotFire[i + 1][j] = true;                   // Cannot burn this tree again
                            }
                            if (cell[i - 1][j].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to west tree
                                cell[i - 1][j].setState(2); // tree caught fire
                                checkCellCannotFire[i - 1][j] = true;                   // Cannot burn this tree again
                            }
                            if (cell[i][j + 1].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to south tree
                                cell[i][j + 1].setState(2); // tree caught fire
                                checkCellCannotFire[i][j + 1] = true;                   // Cannot burn this tree again
                            }
                            if (cell[i][j - 1].getState() == Cell.TREE && random.nextDouble() < getProbCatch()) { // spread to north tree
                                cell[i][j - 1].setState(2); // tree caught fire
                                checkCellCannotFire[i][j - 1] = true;                   // Cannot burn this tree again
                            }
                        }
                    }
                } catch (Exception e) { // cacth array out of bound

                }
            }
        }
        view.setStep(); //count step of spreading
    }

    /**
     * Reset the boolean of every cell cannot burn to false **Do not reset on
     * absorbing boundary condition**
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
                if (cell[i][j].getState() == Cell.BURNING || cell[i][j].getState() == Cell.LIGHTNING) { // still have fire in forest
                    return false;
                }
            }
        }
        return true; // no fire in forest
    }

    /**
     * Activate spreading method by check fire in the forest. After that, reset
     * the boolean of every cell(every cell can burn), and update the component.
     * Use Thread class for make multi thread with delay (sleep method).
     *
     */
    public void spread() {
        try {
            if (!noFire()) { // still have fire in forest
                spreading(); // spreading the fire 
                resetCheck(); // reset tree cannot burn twice
                view.updateView(cell); // update the component
                Thread.sleep(getDelay()); // multi thread
            }
        } catch (InterruptedException e) {

        }
    }

    /**
     * Activate spreading method by check fire in the forest. After that, reset
     * the boolean of every cell(every cell can burn), and update the component.
     * Use Thread class for make multi thread with delay (sleep method). ** Step
     * - by - Step **
     *
     */
    public void spreadStepbyStep() {
        try {
            if (!noFire()) { // still have fire in forest
                spreading(); // spreading the fire 
                if (isLightningStatus() && getProbLightning() > 0) { // if enable lightning strike function
                    int x = (int) (Math.random() * (getNumCell() - 2) + 1); // random stike lightning
                    int y = (int) (Math.random() * (getNumCell() - 2) + 1); // random stike lightning
                    if (cell[x][y].getState() != Cell.EMPTY) { // if random to the tree that is not empty
                        if (cell[x][y].getState() == Cell.TREE) { // if random to normal tree
                            previousState[x][y] = Cell.TREE; // set previous state of this tree to tree
                        } else { // if random to burning tree
                            previousState[x][y] = Cell.BURNING; // set previous state of this tree to burn
                        }
                        cell[x][y].setState(Cell.LIGHTNING); // change the state to lightning struck tree
                    }
                    if (previousState[x][y] == Cell.TREE && random.nextDouble() < getProbLightning()) {
                        // if random less than problightning, tree catched fire
                        checkCellCannotFire[x][y] = true; // stop to burn
                        checkCellBurnByLightning[x][y] = true; // wait 5 step to burn this tree
                    } else if (previousState[x][y] == Cell.BURNING && random.nextDouble() < getProbCatch() * getProbLightning()) {
                        // if random less than probCatch * problightning, tree catched fire
                        checkCellCannotFire[x][y] = true; // stop to burn
                        checkCellBurnByLightning[x][y] = true; // wait 5 step to burn this tree
                    } else { // tree not catch fire by lightning 
                        if (previousState[x][y] != Cell.EMPTY) { // change tree back to normal tree 
                            checkCellNotBurnByLightning[x][y] = true;
                        }
                    }

                }
                if (isIsVary() && getWindSpeed() != 0) { // if users select wind vary, and set win speed more than 0
                    int randomNumber = random.nextInt(100); // random number from 0 to 100
                    if (randomNumber <= 25) { // if random number less than or equal 25
                        setDirection("North"); // set direction of wind to north
                    } else if (randomNumber > 25 && randomNumber <= 50) { // if random number more than 25 and less than or equal 50
                        setDirection("East"); // set direction of wind to east
                    } else if (randomNumber > 50 && randomNumber <= 75) { // if random number more than 50 and less than or equal 75
                        setDirection("West"); // set direction of wind to west
                    } else { // if random number more than 75
                        setDirection("South"); // set direction of wind to south
                    }
                }
                resetStepToBurn(); // count fire step, and burn tree that was striked by lightning
                resetNotBurn(); // reset cell that not burn by lightning
                resetCheck(); // reset check tree that cannot burn twice
                view.updateView(cell); // update the component
                Thread.sleep(2); // multi thread
            }
            countBurn(); // count burned tree
        } catch (InterruptedException e) {

        }
    }

    /**
     * Apply spread method to forest until forest do not has burning tree on
     * its.
     *
     */
    public void applySpread() {
        while (!noFire() && !isStop()) { // while no fire in forest and not stop spreading
            spread(); // spreading of fire
            if (isLightningStatus() && getProbLightning() > 0) { // if enable lightning strike function
                int x = (int) (Math.random() * (getNumCell() - 2) + 1); // random stike lightning
                int y = (int) (Math.random() * (getNumCell() - 2) + 1); // random stike lightning
                if (cell[x][y].getState() != Cell.EMPTY) { // if random to the tree that is not empty
                    if (cell[x][y].getState() == Cell.TREE) { // if random to normal tree
                        previousState[x][y] = Cell.TREE; // set previous state of this tree to tree
                    } else { // if random to burning tree
                        previousState[x][y] = Cell.BURNING; // set previous state of this tree to burn
                    }
                    cell[x][y].setState(Cell.LIGHTNING); // change the state to lightning struck tree
                }
                if (previousState[x][y] == Cell.TREE && random.nextDouble() < getProbLightning()) {
                    // if random less than problightning, tree catched fire
                    checkCellCannotFire[x][y] = true; // stop to burn
                    checkCellBurnByLightning[x][y] = true; // wait 5 step to burn this tree
                } else if (previousState[x][y] == Cell.BURNING && random.nextDouble() < getProbCatch() * getProbLightning()) {
                    // if random less than probCatch * problightning, tree catched fire
                    checkCellCannotFire[x][y] = true; // stop to burn
                    checkCellBurnByLightning[x][y] = true; // wait 5 step to burn this tree
                } else { // tree not catch fire by lightning 
                    if (previousState[x][y] != Cell.EMPTY) { // change tree back to normal tree 
                        checkCellNotBurnByLightning[x][y] = true;
                    }
                }
            }
            if (isIsVary() && getWindSpeed() != 0) { // if users select wind vary, and set win speed more than 0
                int randomNumber = random.nextInt(100); // random number from 0 to 100
                if (randomNumber <= 25) { // if random number less than or equal 25
                    setDirection("North"); // set direction of wind to north
                } else if (randomNumber > 25 && randomNumber <= 50) { // if random number more than 25 and less than or equal 50
                    setDirection("East"); // set direction of wind to east
                } else if (randomNumber > 50 && randomNumber <= 75) { // if random number more than 50 and less than or equal 75
                    setDirection("West"); // set direction of wind to west
                } else { // if random number more than 75
                    setDirection("South"); // set direction of wind to south
                }
            }
            resetStepToBurn(); // count fire step, and burn tree that was striked by lightning
            resetNotBurn(); // reset cell that not burn by lightning
        }
        Controller.isAuto = false; // can change size of forest and regrow tree when finish spread
        Controller.clickAuto = false;
        countBurn(); // count burned tree
    }

    /**
     * Apply spread method to forest until forest do not has burning tree on its
     * ten times.
     *
     */
    public void applySpread10times() {
        if (view.isIsTen()) { // if run spread ten times
            int i = 0; 
            while (i < 10) {
                applySpread(); // apply spread fire 10 times
                i++;
                if (i != 10) {
                    initForest(); // reset forest each time
                }
            }
        }
    }

    /**
     * Reset the step of every cell that burn by lightning strike to false **Do
     * not reset on absorbing boundary condition**
     *
     */
    public void resetStepToBurn() {
        for (int i = 1; i < checkCellBurnByLightning.length - 1; i++) {
            for (int j = 1; j < checkCellBurnByLightning.length - 1; j++) {
                if (checkCellBurnByLightning[i][j] == true) { // if burn by lightning
                    stepToBurn[i][j]++;
                    // if cell has 5 step, reset to 0
                    if (stepToBurn[i][j] - 5 == 0) {
                        checkCellBurnByLightning[i][j] = false; // can spread from these tree
                        cell[i][j].setState(Cell.BURNING); // burn this tree
                        stepToBurn[i][j] = 0;
                    }
                }
            }
        }
        view.updateView(cell); // update the component
    }

    /**
     * Reset the cell that not burn by lightning of every cell to false **Do not
     * reset on absorbing boundary condition**
     *
     */
    public void resetNotBurn() {
        for (int i = 1; i < checkCellNotBurnByLightning.length - 1; i++) {
            for (int j = 1; j < checkCellNotBurnByLightning.length - 1; j++) {
                if (checkCellNotBurnByLightning[i][j] == true) { // if burn by lightning
                    stepToNotBurn[i][j]++;
                    // if cell has 2 step, reset to 0
                    if (stepToNotBurn[i][j] - 2 == 0) {
                        checkCellNotBurnByLightning[i][j] = false; // can struck by lightning again
                        if (previousState[i][j] == Cell.TREE || previousState[i][j] == Cell.BURNING) {
                            cell[i][j].setState(Cell.TREE);
                        }
                        stepToNotBurn[i][j] = 0;
                    }
                }
            }
        }
        view.updateView(cell); // update the component
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
     * Set the value of number of cell
     *
     * @param numCell
     */
    public void setNumCell(int numCell) {
        this.numCell = numCell;
    }

    /**
     * Get the value of number of cell
     *
     * @return numCell
     */
    public int getNumCell() {
        return this.numCell;
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
     * Get the position X
     *
     * @return positionX
     */
    public int getPositionX() {
        return this.positionX;
    }

    /**
     * Get the position Y
     *
     * @return positionY
     */
    public int getPositionY() {
        return this.positionY;
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
     * Set the value of probability lightning
     *
     * @param probLightning
     */
    public void setProbLightning(double probLightning) {
        this.probLightning = probLightning;
    }

    /**
     * Get the value of probability lightning
     *
     * @return probLightning
     */
    public double getProbLightning() {
        return probLightning;
    }

    /**
     * Get the value of windSpeed
     *
     * @return windSpeed
     */
    public int getWindSpeed() {
        return windSpeed;
    }

    /**
     * Set the value of windSpeed
     *
     * @param windSpeed
     */
    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    /**
     * Get the wind direction
     *
     * @return direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Set the wind direction
     *
     * @param direction
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * Set the boolean lightningStatus
     *
     * @return
     */
    public boolean isLightningStatus() {
        return lightningStatus;
    }

    /**
     * Get the boolean lightningStatus
     *
     * @param lightningStatus
     */
    public void setLightningStatus(boolean lightningStatus) {
        this.lightningStatus = lightningStatus;
    }

    /**
     * count the number of trees in the forest
     *
     */
    public void countTree() {
        for (int i = 1; i < cell.length - 1; i++) {
            for (int j = 1; j < cell.length - 1; j++) {
                if (cell[i][j].getState() != Cell.EMPTY) {
                    view.setTree();
                }
            }
        }
        System.out.println("Tree : " + view.getTree());
    }

    /**
     * count the number of burned trees in the forest
     *
     */
    public void countBurn() {
        if (noFire()) {
            for (int i = 1; i < cell.length - 1; i++) {
                for (int j = 1; j < cell.length - 1; j++) {
                    if (cell[i][j].getState() == Cell.EMPTY && !checkCellNotEmptyByBurn[i][j]) {
                        view.setBurn();
                    }
                }
            }
        }
        System.out.println("Burn : " + view.getBurn());
    }

    /**
     * Get the boolean of stop
     *
     * @return stop
     */
    public boolean isStop() {
        return stop;
    }

    /**
     * Set the boolean of stop
     *
     * @param stop
     */
    public void setStop(boolean stop) {
        this.stop = stop;
    }

    /**
     * Get the boolean of isVary
     *
     * @return isVary
     */
    public boolean isIsVary() {
        return isVary;
    }

    /**
     * Set the boolean of isVary
     *
     * @param isVary
     */
    public void setIsVary(boolean isVary) {
        this.isVary = isVary;
    }
}
