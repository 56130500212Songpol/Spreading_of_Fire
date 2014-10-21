import java.awt.Container;
import javax.swing.JFrame;

/**
 * The visualization class of project
 *
 * @author SpreadingOfFire
 * @version 21/10/2014
 */
public class Spreading_of_Fire extends JFrame {
  
  private Model model;
  private View view;
  
  /**
   * Constructor - create the frame
   */
  public Spreading_of_Fire() {
    super("Spreading of Fire"); //change the name to "Spreading of Fire"
    Container contentPane = this.getContentPane();
    view = new View();
    view.setSeeValue(true); //user can see the value of each cell. If do not want to see, set to false
    view.setSeeEdge(true); //user can see the edge of each cell. If do not want to see, set to false
    model = new Model(view,25);
    model.initForest();
    setResizable(false); // cannot change the size of frame
    setSize(660, 683); 
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    contentPane.add(view);
    setVisible(true);
  }
}
