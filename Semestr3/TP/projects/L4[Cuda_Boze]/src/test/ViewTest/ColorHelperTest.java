package ViewTest;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import Model.Cell.CellColor;
import View.ColorHelper;

public class ColorHelperTest {
	private ColorHelper colorHelper;
	private CellColor cellColor;
	
	public ColorHelperTest() {
		colorHelper=new ColorHelper();
		cellColor=CellColor.GREEN;
	}
	@Test
	public void getColortest() {
		CellColor c = colorHelper.GetColor(1, 2);
		
		assertEquals(CellColor.YELLOW, c);
		
		c=colorHelper.GetColor(2, 6);
		
		assertEquals(CellColor.GREEN, c);
	}
	
	@Test
	public void translateColorTest() {
		cellColor=CellColor.GREEN;
		Color color=colorHelper.TranslateColor(cellColor);
		
		assertSame(color, Color.GREEN);
	}

}
