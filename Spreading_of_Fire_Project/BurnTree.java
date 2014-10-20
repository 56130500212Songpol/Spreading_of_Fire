

package spreadingofFire;

import javax.swing.JFrame;

/**
 *
 * @author 5427030,56130500212,56130500222
 */
public class SpreadingofFire {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final JFrame frame = new JFrame();
        frame.setSize(900,630);
        
        final BurnArea area1 = new burnarea();        
        frame.add(area1);
        
        final OptionArea area2 = new OptionArea();        
        frame.add(area2);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    
}
