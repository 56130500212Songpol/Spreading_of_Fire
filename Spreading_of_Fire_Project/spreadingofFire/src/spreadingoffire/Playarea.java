/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spreadingoffire;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Pll_lMe
 */
public class Playarea extends JPanel {


    public Playarea() {
        this.setSize(600   , 600);
        //set default background 
        this.setBackground(Color.YELLOW);  
    }
      
    //Draw grid
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.black);
       
      
       for(int i = 0 ; i <= 40 ; i++)
        {
            g.drawLine(0,25*i,700,25*i);
            g.drawLine(25*i,700,25*i,0);
            
        }
        
        
    }
}
