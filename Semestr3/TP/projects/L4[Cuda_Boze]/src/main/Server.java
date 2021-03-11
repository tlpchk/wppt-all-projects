import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Cell.CellColor;


public class Server {
	
    public static void main(String[] args) throws Exception {
        System.out.println("The server is running.");
        int clientNumber = 1;
        
        GameServer game = new GameServer();
        game.Create(2);
        
        ServerSocket listener = new ServerSocket(9898);
        try {
            while (clientNumber <= game.GetServerSize()) {
                Player player = new Player(listener.accept(), clientNumber++, game);
                game.registerListener(player);
                player.start();
            }
        } finally {
            listener.close();
        }
    }

    public static void StartServerInNewThread(String mode) {
        (new Thread() {
            @Override
            public void run() {
            	System.out.println("The server is running.");
                int clientNumber = 1;
                
                GameServer game = new GameServer();
                
                switch (mode)
                {
	                case "Two Players": game.Create(2); break;
	                case "Three Players": game.Create(3); break;
	                case "Six Players": game.Create(6); break;
	                default : game.Create(2); break;
                }
                
                ServerSocket listener = null;
                
                try {
                	listener = new ServerSocket(9898);

                    while (clientNumber <= game.GetServerSize()) {
                        Player player = new Player(listener.accept(), clientNumber++, game);
                        game.registerListener(player);
                        player.start();
                    }
                } catch (IOException e) {
					e.printStackTrace();
				} finally {
					
                    try {
						listener.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
                }
            }
        }).start();
    }
    
    /**
     * A private thread to handle capitalization requests on a particular
     * socket.  The client terminates the dialogue by sending a single line
     * containing only a period.
     */
    private static class Player extends Thread implements ServerListener{
        private Socket socket;
        private int clientNumber;
        private GameServer game;
        private PrintWriter out;

        public Player(Socket socket, int clientNumber, GameServer game) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            this.game = game;
            log("Nowy gracz# " + clientNumber + " socket: " + socket);
        }

        
       
        /**
         * Services this thread's client by first sending the
         * client a welcome message then repeatedly reading strings
         * and sending back the capitalized version of the string.
         */
        public void run() {
            try {

                // Decorate the streams so we can send characters
                // and not just bytes.  Ensure output is flushed
                // after every newline.
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Send a welcome message to the client.
                out.println("Hello, you are player #" + clientNumber + ".");

                
                game.GetPlayers().forEach(i -> OnCreatePlayer(i.getId()));
                game.CreatePlayer(clientNumber);
                if (game.isServerFull()) {
                	game.SetFirstMovePlayerID();
                }
                
                // Get messages from the client, line by line; return them
                // capitalized
                while (true) {
                    String input = in.readLine();
                    
                    HandleMessage(clientNumber, input);
                    //out.println(input);
                }
            } catch (IOException e) {
                log("Error handling client# " + clientNumber + ": " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    log("Couldn't close a socket, what's going on?");
                }
                log("Connection with client# " + clientNumber + " closed");
            }
        }

        /**
         * Logs a simple message.  In this case we just write the
         * message to the server applications standard output.
         */
        private void log(String message) {
            System.out.println(message);
        }

        public void HandleMessage(int clientNumber, String json) {
        	
        	HashMap<String, Integer> hmap = ParseHelper.ToMap(json);
            
            System.out.println("SERVER_RECEIVE_" + hmap.toString());
            
            if(game.isServerFull() == false) {
            	SendServerMessage("SERVER IS NOT FULL");
            	return;
            }
            
            if(clientNumber != game.getCurrentPlayerID()) {
            	SendServerMessage("NOT YOUR MOVE");
            	return;
            }
            
            switch(hmap.get("eventID")) {
            case NetEventID.PlayerInput:
            	game.OnInput(hmap.get("x"), hmap.get("y"));
            	break;
            case NetEventID.SwitchMove:
            	game.OnChangePlayer();
            	break;
            }
        }

        private void SendServerMessage(String message) {
			HashMap<String, Integer> hmap = new HashMap<String, Integer>();
			 hmap.put(message, 777);
			 hmap.put("eventID", NetEventID.ServerMessage);

			 out.println(ParseHelper.ToJSON(hmap));
			 
			 System.out.println("SERVER_SEND_" + hmap.toString());
        }
        
        public void OnCreatePlayer(int playerID) {
       	 	HashMap<String, Integer> hmap = new HashMap<String, Integer>();
       	 	hmap.put("playerID", playerID);
       	 	hmap.put("thisPlayerID", clientNumber);
       	 	hmap.put("serverSize", game.GetServerSize());
			hmap.put("eventID", NetEventID.CreatePlayer);

			out.println(ParseHelper.ToJSON(hmap));
			 
			System.out.println("SERVER_SEND_" + hmap.toString());
       }
        
		@Override
		public void OnPegMove(int x, int y, int newX, int newY) {
			
			HashMap<String, Integer> hmap = new HashMap<String, Integer>();
			 hmap.put("x", x);
			 hmap.put("y", y);
			 hmap.put("newX", newX);
			 hmap.put("newY", newY);
			 hmap.put("eventID", NetEventID.PegMove);

			 out.println(ParseHelper.ToJSON(hmap));
			 
			 System.out.println("SERVER_SEND_" + hmap.toString());
		}


		@Override
		public void OnSwitchMove(int player) {
			
			HashMap<String, Integer> hmap = new HashMap<String, Integer>();
			 hmap.put("playerID", player);
			 hmap.put("eventID", NetEventID.SwitchMove);

			 out.println(ParseHelper.ToJSON(hmap));
			 
			 System.out.println("SERVER_SEND_" + hmap.toString());
		}


		@Override
		public void OnPegSelect(boolean select, int x, int y) {
			
			HashMap<String, Integer> hmap = new HashMap<String, Integer>();
			 hmap.put("x", x);
			 hmap.put("y", y);
			 hmap.put("select", select? 1 : 0);
			 hmap.put("eventID", NetEventID.PegSelect);

			 out.println(ParseHelper.ToJSON(hmap));
			 
			 System.out.println("SERVER_SEND_" + hmap.toString());
		}



		@Override
		public void OnWin(int playerId) {
			
			HashMap<String, Integer> hmap = new HashMap<String, Integer>();
			 hmap.put("playerID", playerId);
			 hmap.put("eventID", NetEventID.PlayerWin);

			 out.println(ParseHelper.ToJSON(hmap));
			 
			 System.out.println("SERVER_SEND_" + hmap.toString());
		}
    }
}