package View;

import java.awt.Color;
import java.util.HashMap;

import Model.Cell.CellColor;

public class ColorHelper {
	
	public static final Color defaultColor = new Color(220, 220, 220);
	
	private static final HashMap<Integer, CellColor> colors2 = new HashMap<Integer, CellColor>(){{
		put(1, CellColor.YELLOW);
		put(2, CellColor.ORANGE);
	}};
	
	private static final HashMap<Integer, CellColor> colors3 = new HashMap<Integer, CellColor>(){{
		put(1, CellColor.YELLOW);
		put(2, CellColor.BLUE);
		put(3, CellColor.PINK);
	}};
		
	private static final HashMap<Integer, CellColor> colors6 = new HashMap<Integer, CellColor>(){{
		put(1, CellColor.YELLOW);
		put(2, CellColor.GREEN);
		put(3, CellColor.BLUE);
		put(4, CellColor.ORANGE);
		put(5, CellColor.PINK);
		put(6, CellColor.RED);
	}};
	
	public static final HashMap<CellColor, CellColor> oppositColors = new HashMap<CellColor, CellColor>(){{
		put(CellColor.PINK, CellColor.GREEN);
		put(CellColor.RED, CellColor.BLUE);
		put(CellColor.YELLOW, CellColor.ORANGE);
		put(CellColor.GREEN, CellColor.PINK);
		put(CellColor.BLUE, CellColor.RED);
		put(CellColor.ORANGE, CellColor.YELLOW);
	}};
	
	public static CellColor GetColor(int playerID, int playerCount)
	{
		switch (playerCount)
		{
		case 2: return colors2.get(playerID);
		case 3: return colors3.get(playerID);
		case 6: return colors6.get(playerID);
		}
		return CellColor.NONE;
	}
	
	public static Color TranslateColor(CellColor c) {
		
		switch (c) {
		case GREEN:
			return Color.GREEN;
		case BLUE:
			return Color.BLUE;
		case YELLOW:
			return Color.YELLOW;
		case RED:
			return Color.RED;
		case PINK:
			return Color.PINK;
		case ORANGE:
			return Color.ORANGE;
		default:
			return defaultColor;
		}
	}
}
