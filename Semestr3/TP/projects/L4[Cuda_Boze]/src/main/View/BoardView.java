package View;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class BoardView extends JPanel implements MouseAdapter {
	protected ArrayList<PegView> pegs;
	protected ArrayList<CellView> cells;
	protected int mouseX, mouseY;
	protected JButton endMoveButton;
	protected JLabel movePlayer;
	protected JLabel thisPlayer;
	
	public static int radius;
	public static int innerRadius;
	public static int angle;
	public static float  offsetx ;
	public static float  offsety;
	public static float kfx;
	public static float kfy;
	public static int distance;
	public static int w = 26;
	
	public BoardView() {
		radius=20;
		innerRadius = 15;
		angle = 30;
		offsetx = 50;
		offsety = 70;
		kfx = 1.0f;
		kfy = 1.7f;
		distance = 4;
		w = 26;//width
	}
	
	public JLabel getMovePlayer() {
		return movePlayer;
	}
	
	public JLabel getThisPlayer() {
		return thisPlayer;
	}
}
