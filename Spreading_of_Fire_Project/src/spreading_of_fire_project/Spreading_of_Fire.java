package spreading_of_fire_project;

import java.awt.Container;
import javax.swing.JFrame;

/**
 * The visualization class of project(Frame class)
 *
 * @author OOSD Project Group 5
 * @version 15/10/2014
 */
public class Spreading_of_Fire extends JFrame {

    private Controller controller;

    public Spreading_of_Fire() {
        super("Spreading of Fire"); // change the name of frame
        Container contentPane = this.getContentPane();
        controller = new Controller(); // create new Controller
        setResizable(false); // cannot resize frame
        setSize(905, 680); // set size of frame
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        contentPane.add(controller); //add controller to frame
        setVisible(true);
    }
}
