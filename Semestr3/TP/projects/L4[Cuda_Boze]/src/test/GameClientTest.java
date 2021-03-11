import static org.junit.Assert.*;

import javax.swing.JFrame;

import org.junit.Test;

import Model.Peg;

public class GameClientTest {
	GameClient gameClient;
	
	public GameClientTest() {
		gameClient = new GameClient();
		gameClient.Create(new JFrame(), false);
	}
	
	@Test
	public void SetSelectedPegTest() {
		gameClient.SetSelectedPeg(true, 4, 2);
		Peg peg1 = gameClient.getBoard().GetPeg(4, 2);
		Peg peg2 = gameClient.getBoard().selectedPeg;
		
		assertEquals(peg1, peg2);
	}

}
