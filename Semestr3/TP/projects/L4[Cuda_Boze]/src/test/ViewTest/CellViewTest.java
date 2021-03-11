package ViewTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Model.Board;
import Model.Cell;
import Model.Cell.CellColor;
import View.BoardView;
import View.CellView;
import View.MyBoardView;

public class CellViewTest {
	private CellView cellView;
	
	
	public CellViewTest() {
		cellView=new CellView(new Cell(CellColor.GREEN));

	}
	@Test
	public void getCentreXtest() {
		BoardView boardView=new MyBoardView(false);
		cellView.cell.x=5;
		int centreX=cellView.getCentreX();
		
		assertEquals(centreX, 150);
	}
	
	@Test
	public void getCentreYtest() {
		BoardView boardView=new MyBoardView(false);
		cellView.cell.y=10;
		int centreY=cellView.getCentreY();
		
		assertEquals(centreY, 410);
	}
	
	@Test
	public void hitTest() {
		cellView.cell.x=5;
		cellView.cell.y=10;
		boolean p=cellView.Hit(155, 418);
		
		assertEquals(p, true);
	}

}
