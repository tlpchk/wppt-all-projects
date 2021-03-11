import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import Model.Board;
import Model.Cell;
import Model.Cell.CellColor;
import Model.Peg;
import Model.Player;
import View.MyBoardView;
import View.BoardView;
import View.CellView;
import View.ColorHelper;
import View.InputListener;
import View.PegView;

public class GameClient implements InputListener {
	private Factory factory;
	private MyBoardView boardView;
	private Board board;
	private int currentPlayerID;
	private Player player = null;
	private ArrayList<ClientListener> listeners = new ArrayList<ClientListener>();
	private Bot bot = null;
	private Boolean isBot = false;
	private Bot.Move move = null;
	
	public void registerListener(ClientListener l) {
		listeners.add(l);
	}
	
	public void unregisterListener(ClientListener l) {
		listeners.remove(l);
	}
	
	public void Create(JFrame frame, Boolean bot) {
		isBot = bot;
		factory = new Factory();
		board = new Board();
        boardView = new MyBoardView(bot);
    	board.Initialize("src/main/star.txt");  	      
    	boardView.registerListener(this);
    	
        frame.getContentPane().add(boardView, "Center");
   
    	for(int i = 0; i < Model.Board.Hight; i++) {
    		for(int j = 0; j < Model.Board.Width; j++) {
    			if(board.matrix[i][j] != null) {
    				CellView cellview = factory.CreateCellView(board.matrix[i][j]);
    				boardView.AddCell(cellview);
    			}
    		}
    	}
    	boardView.addMouseListener(boardView);
	}

	
	public void SetSelectedPeg(boolean selected, int x, int y) {
		
		Peg peg = board.GetPeg(x, y);
		
		if(peg == null)  {
			System.out.println("NULL PEG");
			return;
		}
			
		
		if(board.selectedPeg != null) {
			board.selectedPeg.setSelect(false);	
		}
		
		board.selectedPeg = peg;
		
		if(peg != null) {
			board.selectedPeg.setSelect(true);	
		}
		
		boardView.repaint();

		if (isBot && player.getId() == currentPlayerID && move != null) {
			listeners.forEach(i -> i.PlayerInput(player.getId(), move.cell.x, move.cell.y));
			System.out.println("-------------------------SELECT");
		}
	}
	
	public void PegMove(int x, int y, int newX, int newY) {
		
		Peg peg = board.GetPeg(x, y);
		
		if(peg == null) {
			System.out.println("NULL PEG");
			return;
		}
		
		peg.setPosition(newX, newY);
		
		boardView.repaint();
		
		if (isBot && player.getId() == currentPlayerID && move != null && move.near == false) {
			listeners.forEach(i -> i.SwitchMove(player.getId()));
			System.out.println("-------------------------MOVE");
		}
	}
	
	public void SwitchMove(int playerID) {
		if(board.selectedPeg != null) {
			board.selectedPeg.setSelect(false);
			board.selectedPeg = null;
		}
		currentPlayerID = playerID;
		boardView.setCurrentPlayerColor(board.GetPlayer(playerID).myColor, playerID);
		
		boardView.repaint();
		
		if (isBot && player.getId() == currentPlayerID) {
			BotMove();
		}
	}
	
	@Override
	public void OnInput(int x, int y) {
		
		listeners.forEach(i -> i.PlayerInput(currentPlayerID, x, y));
	}
	
    public void CreatePegs(Player player) {
    	  CellColor c = player.myColor;	
    	  board.GetSector(c).forEach(cell -> {
    		 Peg p = new Peg(cell);
    		 board.AddPeg(p);
    		 PegView pv = new PegView(p);
    		 boardView.AddPeg(pv);
    	  });
	}
	
	public void OnChangePlayer() {
		listeners.forEach(i -> i.SwitchMove(currentPlayerID));
	}
	
	public void CreatePlayer(int playerID, int thisPlayerId, int playersCount) {
		CellColor color = ColorHelper.GetColor(playerID, playersCount);
		CellColor enemyColor = ColorHelper.oppositColors.get(color);
		
		Player p = new Player(playerID, color, enemyColor);
		board.players.add(p);
		CreatePegs(p);
		
		if (playerID == thisPlayerId) {
			player = p;
			
	    	if (isBot) {
	    		bot = new Bot(board, p);
	    	}
		}
		
		CellColor thisPlayerColor = ColorHelper.GetColor(thisPlayerId, playersCount);
		boardView.setThisPlayerColor(thisPlayerColor, thisPlayerId);
		boardView.setWaitingPlayers(playersCount - board.players.size());
		boardView.repaint();
	}
	
	public Board getBoard() {
		return board;
	}
	
	public void BotMove()
	{	
		System.out.println("---------------------------------");
		
		move = bot.GetMove();
		
		if (move != null) {
			listeners.forEach(i -> i.PlayerInput(player.getId(), move.peg.getX(), move.peg.getY()));
		} else{
			listeners.forEach(i -> i.SwitchMove(player.getId()));
		}
		
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
