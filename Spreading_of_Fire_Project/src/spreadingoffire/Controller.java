import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The controller class of project which contain buttons
 *
 * @author OOSD Project Group 5
 * @version 26/10/2014
 */
public class Controller extends JPanel {

    private JButton reset, auto, step;
    private JLabel how;
    private View view;
    private Model model;

    public Controller() {
        view = new View();
        model = new Model(view, 25, 1.0, 1.0, 0.0, 100);
        model.initForest();
        setLayout(new GridLayout());
        addInteractButton();
        add(view);
    }

    private void addInteractButton() {
        how = new JLabel("**Please setting and regrow trees**");
        view.add(how);
        reset = new JButton("Regrow Trees");
        auto = new JButton("Auto Spread");
        step = new JButton("Step-by-Step");
        
        step.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    model.spreadStepbyStep();
            }

        });

        auto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                                model.applySpread();
                        } catch (Exception e) {
                        }
                    }
                });
                t.start();
            }
        });

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model = new Model(view, model.getNumCell(), model.getProbCatch(), model.getProbTree(),
                        model.getProbBurning(), model.getDelay());
                model.initForest();
            }
        });

        view.add(reset);
        view.add(auto);
        view.add(step);
    }
}

