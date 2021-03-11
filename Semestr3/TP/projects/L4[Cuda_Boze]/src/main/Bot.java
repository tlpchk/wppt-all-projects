import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import Model.Board;
import Model.Cell;
import Model.Peg;
import Model.Player;


public class Bot {
	
	public class Move {
		public Peg peg = null;
		public Cell cell = null;
		public Boolean near = false;
	}
	
	private Board board;
	private Player player;
	private ArrayList<Peg> playerPegs;
	private Cell enemyFar = null;
	private Cell center = null;
	
	public Bot(Board board, Player player) {
		this.board = board;
		this.player = player;
		
		playerPegs = board.GetPlayerPegs(player.myColor);
		center = board.getCell(Board.Width / 2, Board.Hight / 2);
		
		ArrayList<Cell> enemySector = board.GetSector(player.enemyColor);
		
		enemySector.sort((c1, c2) -> {
			float d1 = GetDistance(c1, center);
			float d2 = GetDistance(c1, center);
			return Float.compare(d1, d2);
		});;
		
		Collections.reverse(enemySector);
		
		enemyFar = enemySector.get(0);
	}
	
	public Move GetMove() {

		playerPegs.sort((p1, p2) -> {
			float d1 = GetDistance(p1, enemyFar);
			
			if (p1.getColor() != board.getCell(p1.getX(), p1.getY()).cellColor) {
				d1 *= 0.01f;
			}
			
			float d2 = GetDistance(p2, enemyFar);
			
			if (p2.getColor() != board.getCell(p2.getX(), p2.getY()).cellColor) {
				d2 *= 0.01f;
			}
			
			return Float.compare(d1, d2) * -1;
		});
		
		ArrayList<Cell> freeCellsToMove = null;
		for (Peg p : playerPegs) {
			freeCellsToMove = GetFarFreeCells(p);
			
			if (freeCellsToMove.size() > 0)
			{
				Cell cell = null;				
				for (Cell c : freeCellsToMove) {
					if (GetDistance(c, enemyFar) >= GetDistance(p, enemyFar)) continue;
					
					cell = c;
					break;
				}
				
				if (cell == null) continue;
				
				Move move = new Move();
				move.cell = cell;
				move.peg = p;
				move.near = false;
				
				return move;
			}
		}
		
		for (Peg p : playerPegs) {
			freeCellsToMove = GetNearFreeCells(p);
			
			if (freeCellsToMove.size() > 0)
			{
				Cell cell = null;				
				for (Cell c : freeCellsToMove) {
					if (GetDistance(c, enemyFar) >= GetDistance(p, enemyFar)) continue;
					
					cell = c;
					break;
				}
				
				if (cell == null) continue;
				
				Move move = new Move();
				move.cell = cell;
				move.peg = p;
				move.near = true;
				
				return move;
			}
		}
		
		return null;
	}
	
	private float GetDistance(Cell c1, Cell c2){
		float dx = c1.x - c2.x;
		float dy = c1.y - c2.y;
		float d = (float)Math.sqrt((float)(dx * dx + dy * dy));
		return d;
	}
	
	private float GetDistance(Peg p, Cell c){
		float dx = p.getX() - c.x;
		float dy = p.getY() - c.y;
		float d = (float)Math.sqrt((float)(dx * dx + dy * dy));
		return d;
	}
	
	private ArrayList<Cell> GetFarFreeCells(Peg peg) {
		ArrayList<Cell> farFreeCells = new ArrayList<Cell>();
		
		Cell c = null;
		c = board.getCell(peg.getX() + 2, peg.getY() + 2);
		if (CheckFarCell(peg, c)) farFreeCells.add(c);
		c = board.getCell(peg.getX() + 2, peg.getY() - 2);
		if (CheckFarCell(peg, c)) farFreeCells.add(c);
		c = board.getCell(peg.getX() - 2, peg.getY() + 2);
		if (CheckFarCell(peg, c)) farFreeCells.add(c);
		c = board.getCell(peg.getX() - 2, peg.getY() - 2);
		if (CheckFarCell(peg, c)) farFreeCells.add(c);
		
		c = board.getCell(peg.getX() + 4, peg.getY());
		if (CheckFarCell(peg, c)) farFreeCells.add(c);
		c = board.getCell(peg.getX() - 4, peg.getY());
		if (CheckFarCell(peg, c)) farFreeCells.add(c);

		return farFreeCells;
	}
	
	private Boolean CheckFarCell(Peg p, Cell c) {
		
		if (c == null) return false;
		
		int midleX = p.getX() + (c.x - p.getX()) / 2;
		int midleY = p.getY() + (c.y - p.getY()) / 2;
		
		Cell midleCell = board.getCell(midleX, midleY);
		
		return board.isCellFree(c) && board.isCellFree(midleCell) == false;
	}
	
	private ArrayList<Cell> GetNearFreeCells(Peg peg) {
		ArrayList<Cell> farFreeCells = new ArrayList<Cell>();
		
		Cell c = null;
		c = board.getCell(peg.getX() + 1, peg.getY() + 1);
		if (c != null && board.isCellFree(c)) farFreeCells.add(c);
		c = board.getCell(peg.getX() + 1, peg.getY() - 1);
		if (c != null && board.isCellFree(c)) farFreeCells.add(c);
		c = board.getCell(peg.getX() - 1, peg.getY() + 1);
		if (c != null && board.isCellFree(c)) farFreeCells.add(c);
		c = board.getCell(peg.getX() - 1, peg.getY() - 1);
		if (c != null && board.isCellFree(c)) farFreeCells.add(c);
		
		c = board.getCell(peg.getX() + 2, peg.getY());
		if (c != null && board.isCellFree(c)) farFreeCells.add(c);
		c = board.getCell(peg.getX() - 2, peg.getY());
		if (c != null && board.isCellFree(c)) farFreeCells.add(c);

		return farFreeCells;
	}
}
