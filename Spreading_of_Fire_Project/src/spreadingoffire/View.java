import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * The view class of project
 *
 * @author SpreadingOfFire
 * @version 21/10/2014
 */
class View extends JPanel {
  
  private Cell[][] cell;
  private int pixel;
  private boolean seeValue, seeEdge;
  public static final Color EMPTY_COLOR = new Color(255, 255, 0);
  public static final Color TREE_COLOR = new Color(0, 180, 0);
  public static final Color BURNING_COLOR = new Color(255, 0, 0);
  public static final Color VALUE_COLOR = new Color(0, 0, 0);
  
  /**
   * Constructor - create the view
   */
  public View() {
    //the default size of cell
    pixel = 24;
    //do not see value of each cell
    seeValue = false;
    //do not see the edeg of each cell
    seeEdge = false;
    
    setLayout(new FlowLayout(5, 655, 2));
  }
  
  /**
   * The method use to update the view.
   * 
   * @param cell[][]
   */
  public void updateView(Cell[][] cell) {
    this.cell = cell;
    repaint();
  }
  
  /**
   * The method use to paint the component in the view
   * 
   * @param g
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    //set the font of value
    g.setFont(new Font("TimesRoman", Font.PLAIN, pixel / 2));
    for (int i = 0; i < cell.length; i++) {
      for (int j = 0; j < cell.length; j++) {
        if (cell[i][j] != null) {
          int x = (i + 1) * getPixel(); //position x of each cell
          int y = (j + 1) * getPixel(); //position y of ecah cell
          if (cell[i][j].getState() == Cell.EMPTY) {
            g.setColor(EMPTY_COLOR); //if cell is empty cell, set color to yellow
          } else if (cell[i][j].getState() == Cell.TREE) {
            g.setColor(TREE_COLOR); //if cell is tree cell, set color to green
          } else {
            g.setColor(BURNING_COLOR); //if cell is burning cell, set color to red
          }
          g.fillRect(x, y, getPixel(), getPixel()); //draw fill rectangular for each cell
          g.setColor(VALUE_COLOR); //set color of font to black
          if (isSeeValue()) { //if user set the boolean seeValue to true, it will show value of each cell
            if (cell.length == 25) { 
              if (cell[i][j].getState() == Cell.EMPTY) {
                g.drawString("0", x + getPixel() - 14, y + getPixel() - 6);//if cell is empty cell, set string to 0
              } else if (cell[i][j].getState() == Cell.TREE) {
                g.drawString("1", x + getPixel() - 14, y + getPixel() - 6);//if cell is tree cell, set string to 1
              } else {
                g.drawString("2", x + getPixel() - 14, y + getPixel() - 6);//if cell is burning cell, set string to 2
              }
            } 
          }
          if (isSeeEdge()) { //if user set the seeEdge to true, it will show edge of each cell
            g.drawRect(x, y, getPixel(), getPixel()); //draw rectangular for each cell
          }
        }
      }
    }
  }
  
  /**
   * The method use to set the size of cell
   * 
   * @param pixel
   */
  public void setPixel(int pixel) {
    this.pixel = pixel;
  }
  
  /**
   * The method use to get the size of cell
   * 
   * @return pixel
   */
  public int getPixel() {
    return this.pixel;
  }
  
  /**
   * The method use to check the boolean of see edge
   * 
   * @return seeEdge
   */
  public boolean isSeeEdge() {
    return seeEdge;
  }
  
  /**
   * The method use to set the boolean see edge
   * 
   * @param seeEdge
   */
  public void setSeeEdge(boolean seeEdge) {
    this.seeEdge = seeEdge;
  }
  
  /**
   * The method use to check the boolean of see value
   * 
   * @return seeValue
   */
  public boolean isSeeValue() {
    return this.seeValue;
  }
  
  /**
   * The method use to set the boolean of seeValue
   * 
   * @param seeValue
   */
  public void setSeeValue(boolean seeValue) {
    this.seeValue = seeValue;
  }
  
}

