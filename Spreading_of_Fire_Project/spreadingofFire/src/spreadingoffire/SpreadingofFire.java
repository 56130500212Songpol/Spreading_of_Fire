/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spreadingoffire;

import javax.swing.JFrame;

/**
 *
 * @author Pll_lMe
 */
public class SpreadingofFire {

    /**
     * @param args the command line arguments
     */
        static final JFrame frame = new JFrame();
        static final Playarea area1 = new Playarea();        
        static final Optionarea area2 = new Optionarea(); 
    
    public static void main(String[] args) {
        
        
        frame.setSize(900,630);
        frame.add(area1);
        frame.add(area2);
        
        
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    
}
