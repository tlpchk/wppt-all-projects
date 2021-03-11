import javax.swing.JPanel;

import Model.Cell;
import Model.Cell.CellColor;
import Model.Peg;
import View.CellView;
import View.PegView;

public class Factory {
	
	public CellView CreateCellView(Cell cell) {
		CellView cellview = new CellView(cell);
		return cellview;
	}
	
	public PegView CreatePegView(Peg peg) {
		PegView pegview = new PegView(peg);
		return pegview;
	}
}
