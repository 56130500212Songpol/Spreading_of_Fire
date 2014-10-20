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
    public static void main(String[] args) {
        final JFrame frame = new JFrame();
        frame.setSize(900,630);
          
        final Playarea area1 = new Playarea();        
        frame.add(area1);
        
        final Optionarea area2 = new Optionarea();        
        frame.add(area2);
        
        
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    
}
