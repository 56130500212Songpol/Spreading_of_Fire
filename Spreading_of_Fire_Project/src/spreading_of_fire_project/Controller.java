package spreading_of_fire_project;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * The controller class of project which contain buttons and sliders
 *
 * @author OOSD Project Group 5
 * @version 9/11/2014
 */
public class Controller extends JPanel {

    private JButton reset, auto, step, seeValue, seeBorder, help, sendXY;
    private JSlider probCatchValue, probTreeValue, probBurningValue, probLightningValue, delayValue;
    private JRadioButton tiny, small, medium, large, huge, world;
    private JRadioButton middle, random;
    private JTextField X, Y;
    private ButtonGroup group, group2;
    private JLabel size, probCatch, probTree, probBurning, probLightning, delay, position,
            how, probCatchShow, probTreeShow, probBurningShow, probLightningShow, delayShow,
            setXY, labelX, labelY;
    private JPanel buttonArea, buttonArea2, buttonArea3, buttonArea4;
    private View view;
    private Model model;
    private boolean isRandom = false, isMiddle = false, isChangeSetting = false,
            isOnWorld = false;

    /**
     * Constructor - create controller panel
     */
    public Controller() {
        view = new View();
        model = new Model(view, 25, 1.0, 1.0, 0.0, 0.0, 100); //make simulation of field
        model.initForest();
        setLayout(new GridLayout());
        addInteractButton(); // add reset button, and auto button,step button to controller panel
        addForestSizeRadioButton(); // add resize forest button, tiny, small, medium, large, huge, and world
        addInitialBurningTreeRadioButton(); // add initial burning tree button, random or middle
        addProbSlider(); // add probCatch slider, probTree slider, probBuring slider, and delay slider to controller panel
        addTextFieldBurnCell(); // add set burning position X and Y 
        add(view); // add view to controller panel
    }

    /**
     * Add text and button to controller panel
     *
     */
    private void addInteractButton() {
        buttonArea4 = new JPanel();
        buttonArea4.setLayout(new GridLayout(3, 2, 5, 5));

        how = new JLabel("**Please setting and regrow trees**");
        view.add(how);
        reset = new JButton("Regrow Trees");
        auto = new JButton("Auto Spread");
        step = new JButton("Step-by-Step");
        seeValue = new JButton("See Value");
        seeBorder = new JButton("See Border");
        help = new JButton("Help");

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
                if (isRandom) {
                    model.setRandomPositionX();
                    model.setRandomPositionY();
                }
                view.setStep(0);
                model = new Model(view, model.getNumCell(), model.getPositionX(), model.getPositionY(),
                        model.getProbCatch(), model.getProbTree(), model.getProbBurning(),
                        model.getProbLightning(), model.getDelay()); // create new forest
                model.initForest(); // reset the forest
                isChangeSetting = false;
            }
        });

        seeValue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isOnWorld) {
                    if (view.isSeeValue() == false) {
                        view.setSeeValue(true);
                    } else if (view.isSeeValue() == true) {
                        view.setSeeValue(false);
                    }
                    if (!isChangeSetting) {
                        view.repaint();
                    }
                }
            }
        });

        seeBorder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isOnWorld) {
                    if (view.isSeeBorder() == false) {
                        view.setSeeBorder(true);
                    } else if (view.isSeeBorder() == true) {
                        view.setSeeBorder(false);
                    }
                    if (!isChangeSetting) {
                        view.repaint();
                    }
                }
            }
        });

        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Yellow cell : EMPTY(value 0)\n"
                        + "Green cell : TREE(value 1)\n"
                        + "Red cell : BURNING(value 2)\n\n"
                        + "Regrow Trees : Reset field\n"
                        + "Auto Spread : Spread fire automatically\n"
                        + "Step-by-Step : Spread fire step by step\n"
                        + "See Value : See value of each cell\n"
                        + "See Border : See border of each cell\n"
                        + "Help : Decribe program\n\n"
                        + "Choose Forest size :\n"
                        + "-Tiny : 25x25 cells\n"
                        + "-Small : 41x41 cells\n"
                        + "-Medium : 63x63 cells\n"
                        + "-Large : 79x79 cells\n"
                        + "-Huge : 107x107 cells\n"
                        + "-World : 650x650 cells (Do not try if your PC slow)\n\n"
                        + "Initial burn tree : \n"
                        + "-Middle : Start spread from middle of forest\n"
                        + "-Random : Start spread from random point in forest\n\n"
                        + "Probability : \n"
                        + "-ProbCatch : Probability that trees can catched fire\n"
                        + "-ProbTree : Probability that trees are grown when begin simulation(density)\n"
                        + "-ProbBurn : Probability that trees are burned when begin simulation\n"
                        + "-ProbLightning : Probability that trees are burned by lightning strike\n"
                        + "**If tree was striked by lightning, tree will spread after 5 steps itearation**\n\n"
                        + "Set burning position X and Y : set position of burning cell according to forest size\n\n"
                        + "Others :\n"
                        + "-Delay : Delay of animation\n"
                        + "-Step : Step count the spread of fire in forest\n", "Help?", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        buttonArea4.add(reset);
        buttonArea4.add(auto);
        buttonArea4.add(step);
        buttonArea4.add(seeValue);
        buttonArea4.add(seeBorder);
        buttonArea4.add(help);
        view.add(buttonArea4);
    }

    /**
     * Add text and radiobutton to resize the field
     *
     */
    private void addForestSizeRadioButton() {
        size = new JLabel("Choose Forest size :");
        view.add(size);
        group = new ButtonGroup();
        buttonArea2 = new JPanel();
        buttonArea2.setLayout(new GridLayout(2, 3));

        tiny = new JRadioButton("Tiny");
        tiny.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.setPixel(24);
                model.setNumCell(25);
                if (isRandom) {
                    model.setRandomPositionX();
                    model.setRandomPositionY();
                } else {
                    model.setPositionXonMiddle();
                    model.setPositionYonMiddle();
                }
                isChangeSetting = true;
                isOnWorld = false;
            }
        });

        small = new JRadioButton("Small");
        small.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.setPixel(15);
                model.setNumCell(41);
                if (isRandom) {
                    model.setRandomPositionX();
                    model.setRandomPositionY();
                } else {
                    model.setPositionXonMiddle();
                    model.setPositionYonMiddle();
                }
                isChangeSetting = true;
                isOnWorld = false;
            }
        });

        medium = new JRadioButton("Medium");
        medium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.setPixel(10);
                model.setNumCell(63);
                if (isRandom) {
                    model.setRandomPositionX();
                    model.setRandomPositionY();
                } else {
                    model.setPositionXonMiddle();
                    model.setPositionYonMiddle();
                }
                isChangeSetting = true;
                isOnWorld = false;
            }
        });

        large = new JRadioButton("Large");
        large.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.setPixel(8);
                model.setNumCell(79);
                if (isRandom) {
                    model.setRandomPositionX();
                    model.setRandomPositionY();
                } else {
                    model.setPositionXonMiddle();
                    model.setPositionYonMiddle();
                }
                isChangeSetting = true;
                isOnWorld = false;
            }
        });

        huge = new JRadioButton("Huge");
        huge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.setPixel(6);
                model.setNumCell(107);
                if (isRandom) {
                    model.setRandomPositionX();
                    model.setRandomPositionY();
                } else {
                    model.setPositionXonMiddle();
                    model.setPositionYonMiddle();
                }
                isChangeSetting = true;
                isOnWorld = false;
            }
        });

        world = new JRadioButton("World");
        world.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.setPixel(1);
                model.setNumCell(650);
                if (isRandom) {
                    model.setRandomPositionX();
                    model.setRandomPositionY();
                } else {
                    model.setPositionXonMiddle();
                    model.setPositionYonMiddle();
                }
                view.setSeeBorder(false);
                view.setSeeValue(false);
                isChangeSetting = true;
                isOnWorld = true;
            }
        });

        group.add(tiny);
        group.add(small);
        group.add(medium);
        group.add(large);
        group.add(huge);
        group.add(world);
        buttonArea2.add(tiny);
        buttonArea2.add(small);
        buttonArea2.add(medium);
        buttonArea2.add(large);
        buttonArea2.add(huge);
        buttonArea2.add(world);
        view.add(buttonArea2);
    }

    /**
     * Add text and radiobutton to set the initial burning tree
     *
     */
    private void addInitialBurningTreeRadioButton() {
        position = new JLabel("Select position of initial burn tree :");
        view.add(position);
        buttonArea = new JPanel();
        buttonArea.setLayout(new GridLayout(1, 2));
        group2 = new ButtonGroup();

        middle = new JRadioButton("Middle Tree");
        random = new JRadioButton("Random tree");

        middle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isRandom = false;
                isMiddle = true;
                model.setPositionXonMiddle();
                model.setPositionYonMiddle();
                System.out.println(model.getPositionX());
                System.out.println(model.getPositionY());
            }
        });
        random.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isRandom = true;
                isMiddle = false;
                model.setRandomPositionX();
                model.setRandomPositionY();
                System.out.println(model.getPositionX());
                System.out.println(model.getPositionY());
            }
        });

        group2.add(middle);
        group2.add(random);
        buttonArea.add(middle);
        buttonArea.add(random);
        view.add(buttonArea);
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

        probLightning = new JLabel("Choose Forest Probability Lightning :");
        probLightningShow = new JLabel("ProbLightning : 0 %");
        view.add(probLightning);
        view.add(probLightningShow);
        probLightningValue = new JSlider(JSlider.HORIZONTAL, 0, 100, 0); // slide on horizontal
        probLightningValue.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (probLightningValue.getValueIsAdjusting()) { // if slide bar
                    probLightningShow.setText("ProbLightning : " + (double) probLightningValue.getValue() + " %");// show the value of probLightning
                    model.setProbLightning((double) probLightningValue.getValue() / 100); // set probability lightning equal value of slider
                }
            }

        }
        );
        view.add(probLightningValue);

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

    /**
     * Add text field that use for set burning position X and Y coordinate
     *
     */
    private void addTextFieldBurnCell() {
        setXY = new JLabel("Set burning position X and Y coordinate :");
        labelX = new JLabel("X :");
        labelY = new JLabel("Y :");
        buttonArea3 = new JPanel();
        buttonArea3.setLayout(new FlowLayout());

        X = new JTextField(4);

        Y = new JTextField(4);

        sendXY = new JButton("Submit");
        sendXY.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    model.setXsetY(Integer.parseInt(X.getText()), Integer.parseInt(Y.getText()));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Input X and Y follow the size of forest :\n"
                            + "-Tiny : 25x25 cells\n"
                            + "-Small : 41x41 cells\n"
                            + "-Medium : 63x63 cells\n"
                            + "-Large : 79x79 cells\n"
                            + "-Huge : 107x107 cells\n"
                            + "-World : 650x650 cells", "Caution!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        buttonArea3.add(labelX);
        buttonArea3.add(X);
        buttonArea3.add(labelY);
        buttonArea3.add(Y);
        buttonArea3.add(sendXY);
        view.add(setXY);
        view.add(buttonArea3);
    }
}
