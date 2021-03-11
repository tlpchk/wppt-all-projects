package ModelTest;

import static org.junit.Assert.*;

import org.junit.Test;

import Model.Board;
import Model.Cell;
import Model.Peg;
import Model.Cell.CellColor;

public class BoardTest {
	private Board board;
	private Cell cell;
	private Peg peg;

	public BoardTest() {
		board=new Board();
		cell = new Cell(CellColor.RED);
	}
	@Test
	public void InitializeTest() {
		board.Initialize("src/main/star.txt");
		cell.cellColor=CellColor.RED;
		assertSame(cell.cellColor,board.matrix[4][2].cellColor);
		
		cell.cellColor=CellColor.PINK;
		assertSame(cell.cellColor,board.matrix[10][4].cellColor);
	}
	
	@Test
	public void AddPegTest() {
		cell.cellColor=CellColor.GREEN;
		peg = new Peg(cell);
		board.AddPeg(peg);
		boolean res = board.getPegs(CellColor.GREEN).contains(peg);
		
		assertTrue(res);
	}
	@Test
	public void GetPegTest() {
		cell.cellColor=CellColor.GREEN;
		cell.x=10;
		cell.y=10;
		peg = new Peg(cell);
		
		board.AddPeg(peg);
	
		assertSame(peg, board.GetPeg(10,10));
	}

	@Test
	public void checkNearTest() {
		cell.cellColor=CellColor.GREEN;
		cell.x=10;
		cell.y=10;
		peg = new Peg(cell);
		Cell c = new Cell(CellColor.NONE);
		c.x=11;
		c.y=11;
		
		assertTrue(board.checkNear(peg, c));
	}
	
	@Test
	public void checkFarTest() {
		cell.cellColor=CellColor.GREEN;
		cell.x=10;
		cell.y=10;
		peg = new Peg(cell);
		Cell c = new Cell(CellColor.NONE);
		c.x=12;
		c.y=12;
		
		assertTrue(board.checkFar(peg, c));
	}
	
	@Test
	public void isCellFreeTest() {
		cell.cellColor=CellColor.GREEN;
		cell.x=10;
		cell.y=10;
		peg = new Peg(cell);
		
		board.AddPeg(peg);
	
		assertFalse(board.isCellFree(cell));
	}
	
	
}
