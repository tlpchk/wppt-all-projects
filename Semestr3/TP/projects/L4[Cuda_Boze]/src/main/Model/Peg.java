package Model;

import java.util.ArrayList;

import Model.Cell.CellColor;


public class Peg {
	private CellColor color;
	private int x;
	private int y;
	private boolean selected;
	private boolean moved;
	
	public Peg(Cell c) {
		this.x = c.x;
		this.y = c.y;
		this.color = c.cellColor;
		this.selected=false;
		this.moved = false;
	}
	
	public void setSelect(boolean selected) {
		this.selected = selected;
	}
	
	public void setWasMoved(boolean moved) {
		this.moved = moved;
	}
	
	public boolean getWasMoved() {
		return moved;
	}
		
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public CellColor getColor() {
		return color;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}