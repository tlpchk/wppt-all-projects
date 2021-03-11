package ViewTest;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import Model.Cell.CellColor;
import View.MyBoardView;

public class MyBoardViewTest {
	private MyBoardView myBoardView;
	
	public MyBoardViewTest() {
		myBoardView = new MyBoardView(false);
	}

	@Test
	public void setCurrentPlayerColorTest() {
		myBoardView.setCurrentPlayerColor(CellColor.GREEN, 2);
		Color c = myBoardView.getMovePlayer().getForeground();

		assertSame(Color.GREEN, c);
	}
	
	@Test
	public void setThisPlayerColorTest() {
		myBoardView.setThisPlayerColor(CellColor.GREEN, 3);
		Color c = myBoardView.getThisPlayer().getForeground();

		assertSame(Color.GREEN, c);
	}

}
