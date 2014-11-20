package spreading_of_fire_project;

/**
 * This cell class that contains the state of cell
 *
 * @author OOSD Project Group 5
 * @version 18/11/2014
 */
public class Cell {

    public static final int EMPTY = 0, YOUNG = 1, ADULT = 2, OLD = 3, TREE = 4, BURN = 5, BURNING = 6, LIGHTNING = 7;
    private int state;

    /**
     * Constructor - create the cell by TREE state
     */
    public Cell() {
        state = Cell.TREE;
    }

    /**
     * Constructor - create the cell by state
     *
     * @param state
     */
    public Cell(int state) {
        this.state = state;
    }

    /**
     * get the state of cell
     *
     * @return state
     */
    public int getState() {
        return state;
    }

    /**
     * set the state of cell
     *
     * @param state
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * increase the state(age) of cell
     *
     */
    public void setState() {
        this.state = this.state+1;
    }
}
