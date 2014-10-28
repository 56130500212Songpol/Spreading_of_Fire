import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The controller class of project which contain buttons and sliders
 *
 * @author OOSD Project Group 5
 * @version 28/10/2014
 */
public class Controller extends JPanel {
  
  private JButton reset, auto, step;
  private JSlider probCatchValue, probTreeValue, probBurningValue, delayValue;
  private JLabel how,probCatch, probTree, probBurning, delay, probCatchShow, probTreeShow, probBurningShow, delayShow;
  private View view;
  private Model model;
  
  /**
   * Constructor - create controller panel 
   */
  public Controller() {
    view = new View(); 
    model = new Model(view, 25, 1.0, 1.0, 0.0, 100); //make simulation of field
    model.initForest();
    setLayout(new GridLayout());
    addInteractButton(); // add reset button, and auto button,step button to controller panel
    addProbSlider(); // add probCatch slider, probTree slider, probBuring slider, and delay slider to controller panel
    add(view); // add view to controller panel
  }
  
  /**
   * Add text and button to controller panel
   *
   */
  private void addInteractButton() {
    how = new JLabel("**Please setting and regrow trees**");
    view.add(how); 
    reset = new JButton("Regrow Trees");
    auto = new JButton("Auto Spread");
    step = new JButton("Step-by-Step");
    
    step.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        model.spreadStepbyStep(); // Spread the fire step by step
      }
    });
    
    auto.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Thread t = new Thread(new Runnable() {
          @Override
          public void run() {
            try {
              model.applySpread(); // Auto spread the fire 
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
                          model.getProbBurning(), model.getDelay()); // create new forest
        model.initForest(); // reset the forest
      }
    });
    
    view.add(reset);
    view.add(auto);
    view.add(step);
  }
  
  
  /**
   * Add slider to controller panel
   *
   */
  private void addProbSlider() {
    probCatch = new JLabel("Choose Forest Probability Catching Fire :");
    probCatchShow = new JLabel("ProbCatch : 100 %");
    view.add(probCatch);
    view.add(probCatchShow);
    probCatchValue = new JSlider(JSlider.HORIZONTAL, 0, 100, 100); // slide on horizontal
    probCatchValue.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        if (probCatchValue.getValueIsAdjusting()) { // if slide bar
          probCatchShow.setText("ProbCatch : " + (double) probCatchValue.getValue() + " %"); // show the value of probCatch
          model.setProbCatch((double) probCatchValue.getValue() / 100); // set probability catching fire equal value of slider
        }
      }
      
    }
    );
    view.add(probCatchValue);
    
    probTree = new JLabel("Choose Forest Probability Tree density :");
    probTreeShow = new JLabel("ProbTree : 100 %");
    view.add(probTree);
    view.add(probTreeShow);
    probTreeValue = new JSlider(JSlider.HORIZONTAL, 0, 100, 100); // slide on horizontal
    probTreeValue.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        if (probTreeValue.getValueIsAdjusting()) { // if slide bar
          probTreeShow.setText("ProbTree : " + (double) probTreeValue.getValue() + " %"); // show the value of probTree
          model.setProbTree((double) probTreeValue.getValue() / 100); // set probability tree density equal value of slider
        }
      }
      
    }
    );
    view.add(probTreeValue);
    
    probBurning = new JLabel("Choose Forest Probability Tree burning :");
    probBurningShow = new JLabel("ProbBurning : 0 %");
    view.add(probBurning);
    view.add(probBurningShow);
    probBurningValue = new JSlider(JSlider.HORIZONTAL, 0, 100, 0); // slide on horizontal
    probBurningValue.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        if (probBurningValue.getValueIsAdjusting()) { // if slide bar
          probBurningShow.setText("ProbBurning : " + (double) probBurningValue.getValue() + " %");// show the value of probBurning
          model.setProbBurning((double) probBurningValue.getValue() / 100); // set probability burning tree equal value of slider
        }
      }
      
    }
    );
    view.add(probBurningValue);
    
    delay = new JLabel("Choose Forest Animation delay :");
    delayShow = new JLabel("Delay : 100 ms");
    view.add(delay);
    view.add(delayShow);
    delayValue = new JSlider(JSlider.HORIZONTAL, 2, 1000, 100); // slide on horizontal
    delayValue.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        if (delayValue.getValueIsAdjusting()) { // if slide bar
          delayShow.setText("Delay : " + delayValue.getValue() + " ms"); // show the value of delay
          model.setDelay(delayValue.getValue()); // set delay equal value of slider
        }
      }
      
    }
    );
    view.add(delayValue);
  }
}


