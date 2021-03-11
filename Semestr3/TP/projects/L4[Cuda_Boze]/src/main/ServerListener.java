
public interface ServerListener {

	
	void OnPegMove(int x, int y, int newX, int newY);
	
	void OnSwitchMove(int player);
	
	void OnPegSelect(boolean select, int x, int y);
	
	void OnCreatePlayer(int playerID);
	
	void OnWin(int playerId);
}
