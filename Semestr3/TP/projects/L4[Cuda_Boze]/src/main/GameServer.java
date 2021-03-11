
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import Model.Board;
import Model.Cell;
import Model.Cell.CellColor;
import Model.Peg;
import Model.Player;
import View.ColorHelper;
import View.InputListener;
import View.PegView;

public class GameServer implements InputListener {
	private Board board;
	private int currentPlayerIndex;
	private ArrayList<ServerListener> listeners = new ArrayList<ServerListener>();
	private int serverSize = 2;
	
	public boolean isServerFull() {
		return board.players.size() == serverSize;
	}
	
	public int GetServerSize()
	{
		return serverSize;
	}
	
	public int getCurrentPlayerID() {
		return board.players.get(currentPlayerIndex).getId();
	}
	
	public void SetFirstMovePlayerID() {
		
		Random rand = new Random();

		currentPlayerIndex = rand.nextInt(board.players.size());
		
		listeners.forEach(i -> i.OnSwitchMove(board.players.get(currentPlayerIndex).getId()));
	}

	public void registerListener(ServerListener l) {
		listeners.add(l);
	}
	
	public void unregisterListener(ServerListener l) {
		listeners.remove(l);
	}
	
	public void Create(int size) {
		serverSize = size;
		board = new Board();
    	board.Initialize("src/main/star.txt");  	      
  
	}

	public void SetSelectedPeg(Peg peg) {
		
		if(board.selectedPeg != null) {
			board.selectedPeg.setSelect(false);	
			listeners.forEach(i -> i.OnPegSelect(false, board.selectedPeg.getX(), board.selectedPeg.getY()));
		}
		
		board.selectedPeg = peg;
		
		if(peg != null) {
			board.selectedPeg.setSelect(true);	
			listeners.forEach(i -> i.OnPegSelect(true, board.selectedPeg.getX(), board.selectedPeg.getY()));
		}

	}
	
	@Override
	public void OnInput(int x, int y) {

		Peg peg = board.GetPeg(x, y);
		
		if(peg != null) {
			
			if(board.selectedPeg == peg && board.shortJump == true) { 
				SetSelectedPeg(null);
			}
			else if(board.players.get(currentPlayerIndex).myColor == (peg.getColor())) { 
				if(board.selectedPeg != null) {
					if(board.selectedPeg.getWasMoved() == true) {
						SetSelectedPeg(null);
						OnChangePlayer();
					}
					else {
						SetSelectedPeg(peg);
					}
				}   
				
				else {
					SetSelectedPeg(peg);
				}
			}
		}
		else {
			Cell cell = board.getCell(x, y);
			if(cell != null && board.selectedPeg != null) {
				
				if(board.checkNear(board.selectedPeg, cell) && board.shortJump) {
					
					listeners.forEach(i -> i.OnPegMove(board.selectedPeg.getX(), board.selectedPeg.getY(), x, y));
					board.selectedPeg.setPosition(x, y);
					
					OnChangePlayer();
				}
				else if(board.checkFar(board.selectedPeg, cell)) {
					int midleX = board.selectedPeg.getX() + (x - board.selectedPeg.getX()) / 2;
					int midleY = board.selectedPeg.getY() + (y - board.selectedPeg.getY()) / 2;
					
					Cell midleCell = board.getCell(midleX, midleY);
					
					if(midleCell != null && board.isCellFree(midleCell) == false) {
						
						System.out.println("OLOLOLOLO___" + midleX + "__" + midleY);
						
						listeners.forEach(i -> i.OnPegMove(board.selectedPeg.getX(), board.selectedPeg.getY(), x, y));
						
						board.selectedPeg.setPosition(x, y);
						board.shortJump=false;
						board.selectedPeg.setWasMoved(true);
					}
				}
			}
		}
		
	}
	
    public void CreatePegs(Player player) {
  	  CellColor c = player.myColor;	
  	  board.GetSector(c).forEach(cell -> {
  		 Peg p = new Peg(cell);
  		 board.AddPeg(p);
  		 PegView pv = new PegView(p);
  	  });
	}
	
	public void OnChangePlayer() {
		if(board.selectedPeg != null) {
			board.selectedPeg.setSelect(false);
			board.selectedPeg = null;
		}
		board.shortJump=true;
		
		if (win(board.players.get(currentPlayerIndex)))
		{
			listeners.forEach(i -> i.OnWin(board.players.get(currentPlayerIndex).getId()));
		}
		else
		{
			currentPlayerIndex = (currentPlayerIndex + 1) % board.players.size();
		
			listeners.forEach(i -> i.OnSwitchMove(board.players.get(currentPlayerIndex).getId()));
		}
	}
	
	public boolean win(Player player) {
		boolean result = false;
		int i = 0;
		for(Cell c : board.GetSector(player.enemyColor)) {
			Peg peg = board.GetPeg(c.x, c.y);
			if(peg != null && peg.getColor() == player.myColor) {
				i++;
			}
		}
		if(i == 10) {
			System.out.println("WIN!");
			result=true;
		}
		return result;
	}
	
	public ArrayList<Player> GetPlayers() {
		return board.players;
	}
	
	public void CreatePlayer(int playerID) {
		
		CellColor color = ColorHelper.GetColor(playerID, serverSize);
		CellColor enemyColor = ColorHelper.oppositColors.get(color);
		Player p = new Player(playerID, color, enemyColor);
		board.players.add(p);
		CreatePegs(p);
		listeners.forEach(i -> i.OnCreatePlayer(playerID));
	}
}
