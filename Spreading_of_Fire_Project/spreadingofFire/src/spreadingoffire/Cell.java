package spreadingoffire;
//Create each cell


import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.List;
 
import javax.swing.JFrame;
import javax.swing.JPanel;
 
public class Cell extends JFrame{
	private static final char BURNING = 'w'; //w looks like fire, right?
	private static final char TREE = 'T';
	private static final char EMPTY = '.';
	private List<String> land;
	private final JPanel landPanel;
 
	public Cell(List<String> land){
		this.land = land;
		landPanel = new JPanel(){
                @Override
		public void paint(Graphics g) {
                    for(int y = 0; y < Cell.this.land.size();y++){
			String row = Cell.this.land.get(y);
                	for(int x = 0; x < row.length();x++){
                        //set color
                        switch(row.charAt(x)){
                            case BURNING:
                            g.setColor(Color.RED);
                            break;
                            case TREE:
                            g.setColor(Color.GREEN);
                            break;
                            default: //will catch EMPTY
                            g.setColor(Color.YELLOW);
                                }
                                g.fillRect(x*20, y*20, 20, 20);
                            }
                        }
                    }
		};
		landPanel.setSize(this.land.get(0).length() * 30, this.land.size() * 30);
		add(landPanel);
		setSize(1000, 640);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
 
	public static void main(String[] args){
		List<String> land = Arrays.asList("...............",
				".TTTTTTTTTTTTT.",
				".TTTTTTTTTTTTT.",
				".TTTTTTTTTTTTT.",
				".TTTTTTTTTTTTT.",
				".TTTTTTTTTTTTT.",
				".TTTTTTTTTTTTT.",
				".TTTTTTwTTTTTT.",
				".TTTTTTTTTTTTT.",
				".TTTTTTTTTTTTT.",
				".TTTTTTTTTTTTT.",
				".TTTTTTTTTTTTT.",
				".TTTTTTTTTTTTT.",
				".TTTTTTTTTTTTT.",
				"...............");
		Cell fire = new Cell(land);

	}
}
