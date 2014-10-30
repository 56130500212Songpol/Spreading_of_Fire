package spreading_of_fire_project;

/**
 * This cell class that contains the state of cell
 *
 * @author OOSD Project Group 5
 * @version 30/10/2014
 */
public class Cell {

    public static final int EMPTY = 0, TREE = 1, BURNING = 2;
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

}
