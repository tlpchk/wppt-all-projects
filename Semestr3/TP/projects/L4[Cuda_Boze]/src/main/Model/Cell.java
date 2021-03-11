package Model;

import java.awt.Color;

public class Cell {
	public int x = -1;
	public int y = -1;
	
	public enum CellColor { NONE, GREEN, BLUE, YELLOW, RED, PINK, ORANGE; }
	
	public CellColor cellColor;
	Peg peg = null;
	
	public Cell(CellColor cellCollor) {
		this.cellColor=cellCollor;
	}
}

