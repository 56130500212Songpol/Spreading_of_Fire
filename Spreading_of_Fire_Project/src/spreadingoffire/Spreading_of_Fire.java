import java.awt.Container;
import javax.swing.JFrame;

/**
 * The visualization class of project(Frame class)
 *
 * @author OOSD Project Group 5
 * @version 26/10/2014
 */
public class Spreading_of_Fire extends JFrame {

    private Controller controller;

    public Spreading_of_Fire() {
        super("Spreading of Fire");
        Container contentPane = this.getContentPane();
        controller = new Controller();
        setResizable(false);
        setSize(900, 683);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        contentPane.add(controller);
        setVisible(true);
    }
}