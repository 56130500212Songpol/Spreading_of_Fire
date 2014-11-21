package spreading_of_fire_project;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * The view class of project
 *
 * @author OOSD Project Group 5
 * @version 21/11/2014
 */
public class View extends JPanel {

    private Cell[][] cell;
    private int step, pixel, burn, tree;
    private boolean seeValue, isTen;
    public static final Color EMPTY_COLOR = new Color(255, 255, 0);
    public static final Color YOUNG_COLOR = new Color(128, 255, 0);
    public static final Color ADULT_COLOR = new Color(60, 204, 0);
    public static final Color OLD_COLOR = new Color(30, 189, 10);
    public static final Color TREE_COLOR = new Color(10, 150, 0);
    public static final Color BURN_COLOR = new Color(255, 178, 20);
    public static final Color BURNING_COLOR = new Color(255, 10, 10);
    public static final Color LIGHTNING_COLOR = new Color(0,0,255);
    public static final Color VALUE_COLOR = new Color(0, 0, 0);
    public static final Font DEFAULT_FONT = new Font("Dialog", Font.BOLD, 12);

    /**
     * Constructor - create the view
     *
     * @param pixel
     */
    public View(int pixel) {
        //the default size of cell
        this.pixel = pixel;
        //do not see value of each cell
        seeValue = false;

        setLayout(new FlowLayout(5, 655, -1));

    }

    /**
     * Update the view.
     *
     * @param cell[][]
     */
    public void updateView(Cell[][] cell) {
        this.cell = cell;
        repaint();
    }

    /**
     * Paint the component in the view
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("TimesRoman", Font.PLAIN, pixel / 2)); //set the font of value
        for (int i = 0; i < cell.length; i++) {
            for (int j = 0; j < cell.length; j++) {
                if (cell[i][j] != null) {
                    int x = (i + 1) * getPixel(); //position x of each cell
                    int y = (j + 1) * getPixel(); //position y of ecah cell
                    if (cell[i][j].getState() == Cell.EMPTY) {
                        g.setColor(EMPTY_COLOR); //if cell is empty cell, set color to yellow
                    } else if (cell[i][j].getState() == Cell.YOUNG) {
                        g.setColor(YOUNG_COLOR); //if cell is tree cell, set color to green
                    } else if (cell[i][j].getState() == Cell.ADULT) {
                        g.setColor(ADULT_COLOR); //if cell is tree cell, set color to green
                    } else if (cell[i][j].getState() == Cell.OLD) {
                        g.setColor(OLD_COLOR); //if cell is tree cell, set color to green
                    } else if (cell[i][j].getState() == Cell.TREE) {
                        g.setColor(TREE_COLOR); //if cell is tree cell, set color to green
                    } else if (cell[i][j].getState() == Cell.BURN) {
                        g.setColor(BURN_COLOR); //if cell is tree cell, set color to green
                    } else if (cell[i][j].getState() == Cell.LIGHTNING) {
                        g.setColor(LIGHTNING_COLOR); //if cell is 
                    } else {
                        g.setColor(BURNING_COLOR); //if cell is burning cell, set color to red
                    }
                    g.fillRect(x, y, getPixel(), getPixel()); //draw fill rectangular for each cell
                    g.setColor(VALUE_COLOR); //set color of font to black
                    if (isSeeValue()) { //if user set the boolean seeValue to true, it will show value of each cell
                        if (cell.length == 25) {
                            if (cell[i][j].getState() == Cell.EMPTY) {
                                g.drawString("0", x + getPixel() - 14, y + getPixel() - 6);//if cell is empty cell, set string to 0
                            } else if (cell[i][j].getState() == Cell.YOUNG) {
                                g.drawString("1", x + getPixel() - 14, y + getPixel() - 6);//if cell is tree cell, set string to 1
                            } else if (cell[i][j].getState() == Cell.ADULT) {
                                g.drawString("2", x + getPixel() - 14, y + getPixel() - 6);//if cell is tree cell, set string to 1
                            } else if (cell[i][j].getState() == Cell.OLD) {
                                g.drawString("3", x + getPixel() - 14, y + getPixel() - 6);//if cell is tree cell, set string to 1
                            } else if (cell[i][j].getState() == Cell.TREE) {
                                g.drawString("4", x + getPixel() - 14, y + getPixel() - 6);//if cell is tree cell, set string to 1
                            } else if (cell[i][j].getState() == Cell.BURN) {
                                g.drawString("5", x + getPixel() - 14, y + getPixel() - 6);//if cell is tree cell, set string to 1
                            } else {
                                g.drawString("6", x + getPixel() - 14, y + getPixel() - 6);//if cell is burning cell, set string to 2
                            }
                            g.setColor( new Color(200,200,200));
                            g.drawRect(x, y, getPixel(), getPixel());
                        }
                    }
                }
            }
        }
        g.setColor(BURNING_COLOR);
        g.setFont(DEFAULT_FONT);
        // compute the percent burned trees
        if (isTen) { // if run 10 times
            g.drawString("Average burned : " + getAvgBurn() + " %", 656, 640); // show average percentage
        } else { // if run one times
            g.drawString("Forest burned : " + getAvgBurn() + " %", 656, 640); // show percentage
        }
        g.setColor(VALUE_COLOR);
        g.setFont(DEFAULT_FONT);
        // show the value of step
        g.drawString("Step : " + getStep(), 805, 640);
    }

    /**
     * Set the size of cell
     *
     * @param pixel
     */
    public void setPixel(int pixel) {
        this.pixel = pixel;
    }

    /**
     * Get the size of cell
     *
     * @return pixel
     */
    public int getPixel() {
        return this.pixel;
    }

    /**
     * Check the boolean of see value
     *
     * @return seeValue
     */
    public boolean isSeeValue() {
        return this.seeValue;
    }

    /**
     * Set the boolean of seeValue
     *
     * @param seeValue
     */
    public void setSeeValue(boolean seeValue) {
        this.seeValue = seeValue;
    }

    /**
     * Set the step count
     *
     * @param step
     */
    public void setStep(int step) {
        this.step = step;
    }

    /**
     * Set the step count increase by one
     *
     */
    public void setStep() {
        this.step = this.step + 1;
    }

    /**
     * Get number of step count
     *
     * @return
     */
    public int getStep() {
        return this.step;
    }

    /**
     * Get number of burned tree
     *
     * @return burn
     */
    public int getBurn() {
        return burn;
    }

    /**
     * Set number of burned tree
     *
     */
    public void setBurn() {
        this.burn++;
    }

    /**
     * Set number of burned tree
     *
     * @param burn
     */
    public void setBurn(int burn) {
        this.burn = burn;
    }

    /**
     * Get number of tree
     *
     * @return tree
     */
    public int getTree() {
        return tree;
    }

    /**
     * Set number of tree
     *
     */
    public void setTree() {
        this.tree++;
    }

    /**
     * Set number of tree
     *
     * @param tree
     */
    public void setTree(int tree) {
        this.tree = tree;
    }

    /**
     * Get the percentage of average tree burn for 10 times spread
     *
     * @return percentage of tree burn
     */
    public double getAvgBurn() {
        return (double) ((int) ((double) getBurn() / (double) getTree() * 10000)) / 100;
    }
    
    /**
     * Get boolean of isTen
     *
     * @return
     */
    public boolean isIsTen() {
        return isTen;
    }

    /**
     * Set boolean of isTen
     *
     * @param isTen
     */
    public void setIsTen(boolean isTen) {
        this.isTen = isTen;
    }

}
