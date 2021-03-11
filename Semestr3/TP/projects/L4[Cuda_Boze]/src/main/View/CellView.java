package View;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Polygon;

public class CellView {

	public Model.Cell cell;
	private Polygon polygon;
	
	private int[][] points;
	
	public CellView(Model.Cell c) {
		cell = c;	
		points=new int[2][6];
	}
	
	public void Draw(Graphics g) {
		polygon = new Polygon();
		
	    for (int i = 0; i < 6; i++) {
	    	points[0][i]=(int) (getCentreX() + MyBoardView.radius * Math.cos((i / 6f + 1f / 12f) * 2 * Math.PI)); 
	    	points[1][i]=(int) (getCentreY() + MyBoardView.radius * Math.sin((i / 6f + 1f / 12f) * 2 * Math.PI));
	    }
	    
	    //g.setColor(ColorHelper.defaultColor);
	    //g.fillPolygon(points[0], points[1], 6);
	    g.setColor(ColorHelper.TranslateColor(cell.cellColor));
	    g.drawPolygon(points[0], points[1], 6);
	}

	
	public int getCentreX() {
		return (int)(MyBoardView.offsetx + cell.x * MyBoardView.radius * MyBoardView.kfx);
	}
	
	public int getCentreY() {
		return (int)(MyBoardView.offsety + cell.y * MyBoardView.radius * MyBoardView.kfy);
	}
	
	public Boolean Hit(int x, int y) {
		int dx = (getCentreX() - x);
		int dy = (getCentreY() - y);
		double res = Math.sqrt(dx*dx + dy*dy);
		return res < MyBoardView.innerRadius;
	}
}
