package Model;

import java.util.ArrayList;

import Model.Cell.CellColor;

public class Player {

	public CellColor myColor;
	public CellColor enemyColor;
	private int id;
	
	public Player(int id,CellColor myColor,CellColor enemyColor) {
		this.id=id;
		this.myColor=myColor;
		this.enemyColor=enemyColor;
	}
	
	public int getId() {
		return id;
	}
}
