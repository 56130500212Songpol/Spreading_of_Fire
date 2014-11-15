package spreading_of_fire_project;

import java.awt.BorderLayout;
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
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

/**
 * The controller class of project which contain buttons and sliders
 *
 * @author OOSD Project Group 5
 * @version 15/11/2014
 */
public class Controller extends JPanel {

    private JButton reset, auto, step, seeValue, help;
    private JSlider probCatchValue, probTreeValue, probBurningValue, probLightningValue, delayValue,
            windSpeed;
    private JRadioButton tiny, small, medium, large, huge, world, north, east, west, south;
    private JCheckBox lightning;
    private ButtonGroup group, group2;
    private JLabel size, probCatch, probTree, probBurning, probLightning, delay,
            how, probCatchShow, probTreeShow, probBurningShow, probLightningShow, delayShow,
            windSpeedText;
    private JPanel buttonArea2, buttonArea4;
    private View view;
    private Model model;
    public static boolean clickAuto;
    public static boolean isAuto = false;

    /**
     * Constructor - create controller panel
     */
    public Controller() {
        view = new View(24);
        model = new Model(view, 25, 1.0, 1.0, 0.0, 0.5, 100, "No", Model.NONE, false); //make simulation of field
        model.initForest();
        setLayout(new GridLayout());
        addInteractButton(); // add reset button, and auto button,step button to controller panel
        addForestSizeRadioButton(); // add resize forest button, tiny, small, medium, large, huge, and world
        addProbSlider(); // add probCatch slider, probTree slider, probBuring slider, and delay slider to controller panel
        addWindController(); // add wind controller to panel
        addLightningController(); //add lightning strike to panel
        add(view); // add view to controller panel
    }

    /**
     * Add text and button to controller panel
     *
     */
    private void addInteractButton() {
        buttonArea4 = new JPanel();
        buttonArea4.setLayout(new GridLayout(3, 2, 5, 2));

        how = new JLabel("**Please setting and regrow trees**");
        view.add(how);
        reset = new JButton("Regrow Trees");
        auto = new JButton("Auto Spread");
        step = new JButton("Step-by-Step");
        seeValue = new JButton("See Value");
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
                isAuto = true;
                if (!clickAuto) {
                    model.setStop(false);
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
                    auto.setText("Stop Spread"); // while auto spread, change to stop spread button
                    clickAuto = true;
                } else {
                    model.setStop(true);
                    clickAuto = false;
                    auto.setText("Auto Spread"); // When finished auto spread, change back to auto spread
                }
            }
        });

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAuto) {
                    view.setStep(0); // reset the value of step
                    view.setTree(0); // reset the value of tree in the forest
                    view.setBurn(0); // reset the burned cell in the forest
                    auto.setText("Auto Spread"); // change to auto spread button
                    model = new Model(view, model.getNumCell(), model.getPositionX(), model.getPositionY(),
                            model.getProbCatch(), model.getProbTree(), model.getProbBurning(),
                            model.getProbLightning(), model.getDelay(), model.getDirection(),
                            model.getWindSpeed(), model.isLightningStatus()); // create new forest
                    model.initForest(); // reset the forest
                }
            }
        });

        seeValue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!view.isSeeValue()) {
                    view.setSeeValue(true); // show value of every cell
                } else {
                    view.setSeeValue(false); // hide the value of every cell
                }
                view.repaint(); // repaint the forest
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
                        + "Probability : \n"
                        + "-ProbCatch : Probability that trees can catched fire\n"
                        + "-ProbTree : Probability that trees are grown when begin simulation(density)\n"
                        + "-ProbBurn : Probability that trees are burned when begin simulation\n"
                        + "-ProbLightning : Probability that trees are burned by lightning strike\n"
                        + "**If tree was struck by lightning, tree will spread after 5 steps itearation**\n\n"
                        + "Others :\n"
                        + "-Delay : Delay of animation\n"
                        + "-Step : Step count the spread of fire in forest\n", "Help?", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        buttonArea4.add(reset);
        buttonArea4.add(auto);
        buttonArea4.add(step);
        buttonArea4.add(seeValue);
        buttonArea4.add(help);
        view.add(buttonArea4); // add button to panel
    }

    /**
     * Add text and JRadioButton about resize the field //1.tiny - have 25 trees
     * including absorb boundary and 24 cell size both width and height
     * //2.small - have 41 trees including absorb boundary and 15 cell size both
     * width and height //3.medium - have 63 trees including absorb boundary and
     * 10 cell size both width and height //4.large - have 79 trees including
     * absorb boundary and 8 cell size both width and height //5.tiny - have 107
     * trees including absorb boundary and 6 cell size both width and height
     * //6.tiny - have 649 tree including absorb boundary and 1 cell size both
     * width and height
     */
    private void addForestSizeRadioButton() {
        size = new JLabel("Choose Forest size :");
        view.add(size);
        group = new ButtonGroup();
        buttonArea2 = new JPanel();
        buttonArea2.setLayout(new GridLayout(2, 3));

        tiny = new JRadioButton("Tiny"); // set forest size 25x25 cell including absorb boundary
        tiny.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAuto) { // if auto, user cannot resize the forest
                    size.setText("Choose Forest size : TINY"); // Show the current forest size that user select
                    auto.setText("Auto Spread");
                    view.setPixel(24); // set the pixel of every cell to 24 unit
                    model.setNumCell(25); // set the number of cell to 25 cells 
                    model.setPositionXonMiddle(); //set the position of initial burning tree to the middle of the forest
                    model.setPositionYonMiddle();
                    view.setStep(0); // reset the value of step
                    view.setTree(0); // reset the value of tree in the forest
                    view.setBurn(0); // reset the value of burned tree in the forest
                    model = new Model(view, model.getNumCell(), model.getPositionX(), model.getPositionY(),
                            model.getProbCatch(), model.getProbTree(), model.getProbBurning(),
                            model.getProbLightning(), model.getDelay(), model.getDirection(),
                            model.getWindSpeed(), model.isLightningStatus()); // create new forest
                    model.initForest(); // reset the forest
                }
            }
        });

        small = new JRadioButton("Small"); // set forest size 41x41 cell including absorb boundary
        small.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAuto) { // if auto, user cannot resize the forest
                    size.setText("Choose Forest size : SMALL"); // Show the current forest size that user select
                    auto.setText("Auto Spread");
                    view.setPixel(15); // set the pixel of every cell to 15 unit
                    model.setNumCell(41); // set the number of cell to 41 cells
                    model.setPositionXonMiddle(); //set the position of initial burning tree to the middle of the forest
                    model.setPositionYonMiddle();
                    view.setStep(0); // reset the value of step
                    view.setTree(0); // reset the value of tree in the forest
                    view.setBurn(0); // reset the value of burned tree in the forest
                    model = new Model(view, model.getNumCell(), model.getPositionX(), model.getPositionY(),
                            model.getProbCatch(), model.getProbTree(), model.getProbBurning(),
                            model.getProbLightning(), model.getDelay(), model.getDirection(),
                            model.getWindSpeed(), model.isLightningStatus()); // create new forest
                    model.initForest(); // reset the forest
                }
            }
        });

        medium = new JRadioButton("Medium"); // set forest size 63x63 cell including absorb boundary
        medium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAuto) { // if auto, user cannot resize the forest
                    size.setText("Choose Forest size : MEDIUM"); // Show the current forest size that user select
                    auto.setText("Auto Spread");
                    view.setPixel(10); // set the pixel of every cell to 10 unit
                    model.setNumCell(63); // set the number of cell to 63 cells
                    model.setPositionXonMiddle();//set the position of initial burning tree to the middle of the forest
                    model.setPositionYonMiddle();
                    view.setStep(0); // reset the value of step
                    view.setTree(0); // reset the value of tree in the forest
                    view.setBurn(0); // reset the value of burned tree in the forest
                    model = new Model(view, model.getNumCell(), model.getPositionX(), model.getPositionY(),
                            model.getProbCatch(), model.getProbTree(), model.getProbBurning(),
                            model.getProbLightning(), model.getDelay(), model.getDirection(),
                            model.getWindSpeed(), model.isLightningStatus()); // create new forest
                    model.initForest(); // reset the forest
                }
            }
        });

        large = new JRadioButton("Large"); // set forest size 79x79 cell including absorb boundary
        large.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAuto) { // if auto, user cannot resize the forest
                    size.setText("Choose Forest size : LARGE"); // Show the current forest size that user select
                    auto.setText("Auto Spread");
                    view.setPixel(8); // set the pixel of every cell to 8 unit
                    model.setNumCell(79); // set the number of cell to 79 cells
                    model.setPositionXonMiddle(); //set the position of initial burning tree to the middle of the forest
                    model.setPositionYonMiddle();
                    view.setStep(0); // reset the value of step
                    view.setTree(0); // reset the value of tree in the forest
                    view.setBurn(0); // reset the value of burned tree in the forest
                    model = new Model(view, model.getNumCell(), model.getPositionX(), model.getPositionY(),
                            model.getProbCatch(), model.getProbTree(), model.getProbBurning(),
                            model.getProbLightning(), model.getDelay(), model.getDirection(),
                            model.getWindSpeed(), model.isLightningStatus()); // create new forest
                    model.initForest(); // reset the forest
                }
            }
        });

        huge = new JRadioButton("Huge"); // set forest size 107x107 cell including absorb boundary
        huge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAuto) { // if auto, user cannot resize the forest
                    size.setText("Choose Forest size : HUGE"); // Show the current forest size that user select
                    auto.setText("Auto Spread");
                    view.setPixel(6); // set the pixel of every cell to 6 unit
                    model.setNumCell(107); // set the number of cell to 107 cells
                    model.setPositionXonMiddle(); //set the position of initial burning tree to the middle of the forest
                    model.setPositionYonMiddle();
                    view.setStep(0); // reset the value of step
                    view.setTree(0); // reset the value of tree in the forest
                    view.setBurn(0); // reset the value of burned tree in the forest
                    model = new Model(view, model.getNumCell(), model.getPositionX(), model.getPositionY(),
                            model.getProbCatch(), model.getProbTree(), model.getProbBurning(),
                            model.getProbLightning(), model.getDelay(), model.getDirection(),
                            model.getWindSpeed(), model.isLightningStatus()); // create new forest
                    model.initForest(); // reset the forest
                }
            }
        });

        world = new JRadioButton("World"); // set forest size 649x649 cell including absorb boundary
        world.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAuto) { // if auto, user cannot resize the forest
                    size.setText("Choose Forest size : WORLD"); // Show the current forest size that user select
                    auto.setText("Auto Spread");
                    view.setPixel(1);  // set the pixel of every cell to 1 unit
                    model.setNumCell(649); // set the number of cell to 649 cells
                    model.setPositionXonMiddle(); //set the position of initial burning tree to the middle of the forest
                    model.setPositionYonMiddle();
                    view.setStep(0); // reset the value of step
                    view.setTree(0); // reset the value of tree in the forest
                    view.setBurn(0); // reset the value of burned tree in the forest
                    model = new Model(view, model.getNumCell(), model.getPositionX(), model.getPositionY(),
                            model.getProbCatch(), model.getProbTree(), model.getProbBurning(),
                            model.getProbLightning(), model.getDelay(), model.getDirection(),
                            model.getWindSpeed(), model.isLightningStatus()); // create new forest
                    model.initForest(); // reset the forest
                }
            }
        });
        // group all of the JRadioButton together
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
        view.add(buttonArea2); // add all JRadioButton to panel
    }

    /**
     * Add slider to controller panel //1.Probability of tree catching fire from
     * the neighbor tree //2.Probability of cell might be tree or empty when
     * start simulation //3.Probability of tree might be burn when start
     * simulation
     */
    private void addProbSlider() {
        probCatch = new JLabel("Choose Forest Probability Catching Fire :");
        probCatchShow = new JLabel("ProbCatch : 100 %"); // set the default of text following the default value of probCatch
        view.add(probCatch);
        view.add(probCatchShow);
        probCatchValue = new JSlider(JSlider.HORIZONTAL, 0, 100, 100); // slide on horizontal
        probCatchValue.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (probCatchValue.getValueIsAdjusting()) { // if sliding this bar
                    probCatchShow.setText("ProbCatch : " + (double) probCatchValue.getValue() + " %"); // show the value of probCatch
                    model.setProbCatch((double) probCatchValue.getValue() / 100); // set probability catching fire equal value of slider
                }
            }

        }
        );
        view.add(probCatchValue); // add this JSlider to panel

        probTree = new JLabel("Choose Forest Probability Tree density :");
        probTreeShow = new JLabel("ProbTree : 100 %"); // set the default of text following the default value of probTree
        view.add(probTree);
        view.add(probTreeShow);
        probTreeValue = new JSlider(JSlider.HORIZONTAL, 0, 100, 100); // slide on horizontal
        probTreeValue.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (probTreeValue.getValueIsAdjusting()) { // if sliding this bar
                    probTreeShow.setText("ProbTree : " + (double) probTreeValue.getValue() + " %"); // show the value of probTree
                    model.setProbTree((double) probTreeValue.getValue() / 100); // set probability tree density equal value of slider
                }
            }

        }
        );
        view.add(probTreeValue); // add this JSlider to panel

        probBurning = new JLabel("Choose Forest Probability Tree burning :");
        probBurningShow = new JLabel("ProbBurning : 0 %"); // set the default of text following the default value of probBurn
        view.add(probBurning);
        view.add(probBurningShow);
        probBurningValue = new JSlider(JSlider.HORIZONTAL, 0, 100, 0); // slide on horizontal
        probBurningValue.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (probBurningValue.getValueIsAdjusting()) { // if sliding this bar
                    probBurningShow.setText("ProbBurning : " + (double) probBurningValue.getValue() + " %");// show the value of probBurning
                    model.setProbBurning((double) probBurningValue.getValue() / 100); // set probability burning tree equal value of slider
                }
            }

        }
        );
        view.add(probBurningValue); // add this JSlider to panel

        delay = new JLabel("Choose Forest Animation delay :");
        delayShow = new JLabel("Delay : 100 ms"); // set the default of text following the default value of delay
        view.add(delay);
        view.add(delayShow);
        delayValue = new JSlider(JSlider.HORIZONTAL, 1, 1000, 100); // slide on horizontal
        delayValue.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (delayValue.getValueIsAdjusting()) { // if sliding this bar
                    delayShow.setText("Delay : " + delayValue.getValue() + " ms"); // show the value of delay
                    model.setDelay(delayValue.getValue()); // set delay equal value of slider
                }
            }

        }
        );
        view.add(delayValue); // add this JSlider to panel
    }

    /**
     * Add the wind controller that can control direction and speed of wind
     * ///***direction*** 1.N = North 2.E = East 3.W = West 4.S = South
     * ///***speed*** 1.NONE = no wind 2.LOW = have low speed wind 3.HIGH = have
     * high speed wind
     */
    private void addWindController() {
        JLabel wind = new JLabel("Wind Controller : ");
        windSpeedText = new JLabel("Wind speed : NONE"); // default of the wind speed is none
        north = new JRadioButton("N");
        east = new JRadioButton("E");
        west = new JRadioButton("W");
        south = new JRadioButton("S");

        JPanel areaCen1 = new JPanel(new FlowLayout());
        JPanel areaCen2 = new JPanel(new FlowLayout());
        JPanel area = new JPanel(new GridLayout(1, 2));

        windSpeed = new JSlider(JSlider.HORIZONTAL, 0, 2, 0); // slide only horizontal min = 0(LOW) max = 2(HIGH)
        windSpeed.setMinorTickSpacing(1); // change only 1 value
        windSpeed.setMajorTickSpacing(1);
        windSpeed.setPaintTicks(true);
        windSpeed.setPaintLabels(true);

        group2 = new ButtonGroup();
        buttonArea2 = new JPanel();
        buttonArea2.setLayout(new BorderLayout(10, 2));

        north.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setDirection("N"); // Click on north JRadioButton, set the wind in the simulation to north
            }
        });

        east.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setDirection("E"); // Click on east JRadioButton, set the wind in the simulation to east
            }
        });

        west.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setDirection("W"); // Click on west JRadioButton, set the wind in the simulation to west
            }
        });

        south.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setDirection("S"); // Click on south JRadioButton, set the wind in the simulation to south
            }
        });

        windSpeed.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (windSpeed.getValueIsAdjusting()) { // if sliding wind JSlider
                    if (windSpeed.getValue() == 0) { // if sliding to 0
                        windSpeedText.setText("Wind speed : NONE"); // set the the of wind spreed to none 
                    } else if (windSpeed.getValue() == 1) { // if sliding to 1
                        windSpeedText.setText("Wind speed : LOW"); // set the the of wind spreed to low
                    } else { // if sliding to 2
                        windSpeedText.setText("Wind speed : HIGH"); // set the the of wind spreed to high
                    }
                    model.setWindSpeed(windSpeed.getValue()); // set the vlue of wind speed by value of wind speed JSlider                }
                }
            }
        });

        areaCen1.add(north);
        areaCen2.add(south);

        // group all of JRadioButton together
        group2.add(north);
        group2.add(east);
        group2.add(west);
        group2.add(south);

        buttonArea2.add(areaCen1, BorderLayout.NORTH);
        buttonArea2.add(east, BorderLayout.EAST);
        buttonArea2.add(west, BorderLayout.WEST);
        buttonArea2.add(areaCen2, BorderLayout.SOUTH);

        area.add(wind);
        area.add(buttonArea2);

        view.add(area); // add wind controller to panel
        view.add(windSpeedText);
        view.add(windSpeed);
    }

    /**
     * Add JCheckBox for enable or disable lightning strike and JSlider that use
     * for change the value of probability of lightning strike
     */
    private void addLightningController() {
        lightning = new JCheckBox("Lightning : Disable"); // default of the lightning function is disable
        lightning.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (lightning.isSelected()) { // if select this check box, change to enable lightning
                    lightning.setText("Lightning : Enable");
                    view.add(probLightning); // add lightning control to panel
                    view.add(probLightningShow);
                    view.add(probLightningValue);
                    model.setLightningStatus(true); // enable lightning strike in the forest
                } else { // if did not select this check box, change to disable lightning
                    lightning.setText("Lightning : Disable");
                    view.remove(probLightning); // remove lightning control from panel
                    view.remove(probLightningShow);
                    view.remove(probLightningValue);
                    model.setLightningStatus(false); // disable lightning strike in the forest
                }
                view.repaint();
            }

        }
        );
        probLightning = new JLabel("Choose Forest Probability Lightning :");
        probLightningShow = new JLabel("ProbLightning : 50 %"); // set the default of text following the default value of delay 
        probLightningValue = new JSlider(JSlider.HORIZONTAL, 0, 100, 50); // slide on horizontal
        probLightningValue.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (probLightningValue.getValueIsAdjusting()) { // if sliding this bar
                    probLightningShow.setText("ProbLightning : " + (double) probLightningValue.getValue() + " %");// show the value of probLightning
                    model.setProbLightning((double) probLightningValue.getValue() / 100); // set probability lightning equal value of slider
                }
            }

        }
        );
        view.add(lightning); // add lightning controller to panel 
    }
}
