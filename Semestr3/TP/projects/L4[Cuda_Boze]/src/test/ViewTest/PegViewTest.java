package ViewTest;

import static org.junit.Assert.*;

import org.junit.Test;


import Model.Cell;
import Model.Peg;
import Model.Cell.CellColor;
import View.MyBoardView;
import View.PegView;

public class PegViewTest {
	private PegView pegView;
	private Peg peg;
	
	public PegViewTest() {
		peg=new Peg(new Cell(CellColor.GREEN));
		pegView=new PegView(peg);
	}
	
	@Test
	public void getStartXTest() {
		MyBoardView m = new MyBoardView(false);
		peg.setPosition(10, 10);
		updatePegView();
		
		assertEquals(pegView.getStartX(), 230);
	}
	
	@Test
	public void getStartYTest() {
		MyBoardView m = new MyBoardView(false);
		peg.setPosition(10, 10);
		updatePegView();
		
		assertEquals(pegView.getStartY(), 390);
	}
	
	private void updatePegView() {
		pegView=new PegView(peg);
	}

}
