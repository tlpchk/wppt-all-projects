import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;;


public class Client implements ClientListener{
    private BufferedReader in;
    private PrintWriter out;
    private JFrame frame = new JFrame("Client");
    private JTextArea messageArea = new JTextArea(3, 10);
    private JCheckBox isBot = new JCheckBox("is bot", false);
    private String serverIp = null;
	private Boolean isConnected = false;
    
    public GameClient game;

    
    public Client() {

    }

    
    public void connectToServer() throws IOException {

    	if (serverIp == null)
    	{
            // Get the server address from a dialog box.
    		serverIp = JOptionPane.showInputDialog(
                frame,
                "Enter IP Address of the Server:",
                "Welcome to the Capitalization Program",
                JOptionPane.QUESTION_MESSAGE);
    	}

        // Make connection and initialize streams
        Socket socket = new Socket(serverIp, 9898);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        messageArea.append(in.readLine() + "\n");
        
        while(true) {
        	GetServerResponce(); 
        }
    }

    /**
     * Runs the client application.
     */
    public static void main(String[] args) throws Exception {
    	
        Client client = new Client();
        
        client.serverIp = null;
        client.messageArea.setEditable(false);
        
        JScrollPane scroll = new JScrollPane(client.messageArea);
        scroll.setPreferredSize(new Dimension(200, 600));
        client.frame.add(scroll, BorderLayout.EAST);
        
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setSize(800, 600);
        client.frame.setVisible(true);
        
        JPanel buttonsPanel = new JPanel();
        JButton create = new JButton("CREATE");
        JButton join = new JButton("JOIN");
        
        JComboBox<String> playersCombo = new JComboBox<String>();
        playersCombo.setEditable(false);
        playersCombo.addItem("Two Players");
        playersCombo.addItem("Three Players");
        playersCombo.addItem("Six Players");
        
        buttonsPanel.add(playersCombo);
        buttonsPanel.add(create);
        buttonsPanel.add(join);
        buttonsPanel.add(client.isBot);
        
        client.frame.add(buttonsPanel);
        client.frame.pack();
        client.frame.setSize(800, 600);
        client.frame.repaint();
        
        create.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				client.serverIp = "127.0.0.1";
				
				System.out.println("SEL_" + playersCombo.getSelectedItem().toString());
				
				Server.StartServerInNewThread(playersCombo.getSelectedItem().toString());
				
		        client.frame.remove(buttonsPanel);
		        
		        client.game = new GameClient();
		        client.game.Create(client.frame, client.isBot.isSelected());
		        client.game.registerListener(client);
		        
		        client.frame.pack();
		        client.frame.setSize(800, 600);
		        client.frame.repaint();
			}
        });
        
        join.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				client.serverIp = null; //"127.0.0.1";
				
		        client.frame.remove(buttonsPanel);
		        
		        client.game = new GameClient();
		        client.game.Create(client.frame, client.isBot.isSelected());
		        client.game.registerListener(client);
		        
		        client.frame.pack();
		        client.frame.setSize(800, 600);
		        client.frame.repaint();
			}
        });
        
        while (client.game == null)
        {
        	TimeUnit.SECONDS.sleep(1);
        }
        
        try {
			client.connectToServer();       	
        } catch(ConnectException ex) {
        	client.messageArea.append("CANT_CONNECT_TO_SERVER");
    		System.out.println("CANT_CONNECT_TO_SERVER");
        }
    }

	@Override
	public void PlayerInput(int playerID, int x, int y) {
		
		if (isConnected == false) return;
		
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		hmap.put("playerID", playerID);
		hmap.put("x", x);
		hmap.put("y", y);
		hmap.put("eventID", NetEventID.PlayerInput);
		
		out.println(ParseHelper.ToJSON(hmap));
		 
		messageArea.append("CLIENT_SEND_" + hmap.toString() + "\n");
		 
		System.out.println("CLIENT_SEND_" + hmap.toString());
	}

	@Override
	public void SwitchMove(int playerID) {
		
		if (isConnected == false) return;
		
		 HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		 hmap.put("playerID", playerID);
		 hmap.put("eventID", NetEventID.SwitchMove);
		 
		 out.println(ParseHelper.ToJSON(hmap));
		 
		 System.out.println("CLIENT_SEND_" + hmap.toString());
		 
		 messageArea.append("CLIENT_SEND_" + hmap.toString() + "\n");
	}
	
	public void GetServerResponce() { 
		String response;
		 
		 try {
		     response = in.readLine();
		     if (response == null || response.equals("")) {
		           System.exit(0);
		       }
		 } catch (IOException ex) {
		        response = "Error: " + ex;
		 }
		 
		 if (response.contains("Error"))
		 {
			return;
		 }
		 
		HashMap<String, Integer> hmap = ParseHelper.ToMap(response);
		 
		messageArea.append("CLIENT_RECEIVE_" + hmap.toString() + "\n"); 
		
		System.out.println("CLIENT_RECEIVE_" + hmap.toString());
         
		switch(hmap.get("eventID")) {
		 case NetEventID.PegSelect:
			 game.SetSelectedPeg(hmap.get("select") > 0 ? true:false, hmap.get("x"), hmap.get("y"));
			 break;
		 case NetEventID.PegMove:
			 game.PegMove(hmap.get("x"), hmap.get("y"), hmap.get("newX"), hmap.get("newY"));
			 break;
		 case NetEventID.SwitchMove:
			 game.SwitchMove(hmap.get("playerID"));
			 break;
		 case NetEventID.CreatePlayer:
			 isConnected = true;
			 game.CreatePlayer(hmap.get("playerID"), hmap.get("thisPlayerID"), hmap.get("serverSize"));
			 break;
		 case NetEventID.PlayerWin:
			 JDialog dialog = new JDialog(frame, "Player " + hmap.get("playerID") + "is Winnwer !", true);
			 
			 int id = hmap.get("playerID");
			 JLabel label = new JLabel();
			 label.setText("Player " + id + " is Winnwer !");
			 dialog.add(label);
			 
			 dialog.pack();
			 dialog.setSize(new Dimension(300, 200));
			 dialog.setVisible(true);
			 break;
		 }
	}
}
