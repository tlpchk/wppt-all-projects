package View;

import java.awt.Color;
import java.awt.Graphics;
import Model.Peg;

public class PegView {
    private Peg peg;
	
	public PegView(Peg peg) {
		this.peg=peg;
	}
	
	public void Draw(Graphics g) {
		
		g.setColor(Color.CYAN);
		if(peg.isSelected()) {
			g.fillOval(getStartX(), getStartY(), 2 * MyBoardView.radius, 2 * MyBoardView.radius);
		}
		
		g.setColor(ColorHelper.TranslateColor(peg.getColor()));

	    g.fillArc(getStartX() + (MyBoardView.radius - MyBoardView.innerRadius),
	    		  getStartY() + (MyBoardView.radius - MyBoardView.innerRadius),
	    		  2 * MyBoardView.innerRadius, 2 * MyBoardView.innerRadius,
	    		  MyBoardView.angle,
	    		  360 - 2 * MyBoardView.angle);
	}
	
	public int getStartX() {
		return (int)(MyBoardView.offsetx + peg.getX() * MyBoardView.radius * MyBoardView.kfx - MyBoardView.radius);
	}
	
	public int getStartY() {
		return (int)(MyBoardView.offsety + peg.getY() * MyBoardView.radius * MyBoardView.kfy - MyBoardView.radius);
	}
	
	public int getCentreX() {
		return getStartX() + MyBoardView.radius;
	}
	
	public int getCentreY() {
		return getStartY() + MyBoardView.radius;
	}
	
	public int getPegX() {
		return peg.getX();
	}
	
	public int getPegY() {
		return peg.getY();
	}
	
	public Boolean Hit(int x, int y) {
		int dx = (getCentreX() - x);
		int dy = (getCentreY() - y);
		double res = Math.sqrt(dx*dx + dy*dy);
		return res < MyBoardView.innerRadius;
	}

}