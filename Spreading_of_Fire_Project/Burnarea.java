

package spreadingofFire;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author 5427030,56130500212,56130500222
 */
public class BurnArea extends JPanel {
    
    
    public burnarea(){
        this.setSize(600   , 600);
        this.setBackground(Color.YELLOW);  
    }
    
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
