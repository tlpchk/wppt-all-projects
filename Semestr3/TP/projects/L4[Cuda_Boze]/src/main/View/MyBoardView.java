package View;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Model.Cell.CellColor;

public class MyBoardView extends BoardView {
	private ArrayList<InputListener> listeners = new ArrayList<InputListener>();
	
	public void registerListener(InputListener l) {
		listeners.add(l);
	}
	
	public void unregesterListener(InputListener l) { 
		listeners.remove(l);
	}
	
	public void setCurrentPlayerColor(CellColor c, int plyerID) {
		movePlayer.setForeground(ColorHelper.TranslateColor(c));
		movePlayer.setText("Moves Player " + plyerID);
	}
	
	public void setWaitingPlayers(int playersCount) {
		movePlayer.setText("Waiting for " + playersCount + " Player(s)");
	}
	
	public void setThisPlayerColor(CellColor c, int plyerID) {
		thisPlayer.setForeground(ColorHelper.TranslateColor(c));
		thisPlayer.setText("I'am Player " + plyerID);
	}
	
	public MyBoardView(Boolean bot) {
		super();
		setBackground(Color.GRAY);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(600, 40));
		panel.setBackground(Color.darkGray);
		this.add(panel, BorderLayout.SOUTH);
		
		if (bot) {
			JLabel botLabel = new JLabel("BOT ");
			botLabel.setForeground(Color.white);
			panel.add(botLabel);			
		}
		
    	thisPlayer = new JLabel("I'am Player ");
    	panel.add(thisPlayer);
		
		movePlayer = new JLabel("Moves Player ");
		movePlayer.setForeground(Color.white);
		panel.add(movePlayer);
    	
		endMoveButton = new JButton("END MOVE");
		panel.add(endMoveButton);
		
    	endMoveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listeners.forEach(l -> l.OnChangePlayer());
				System.out.println("button end move presed");
			}
        });
    	
		cells = new ArrayList<CellView>();
		pegs = new ArrayList<PegView>();
	}

	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        cells.forEach(i -> i.Draw(g));
        pegs.forEach(i -> i.Draw(g));
        
    }
	
	public void AddCell(CellView c) {
		cells.add(c);
	}
	
	public void AddPeg(PegView p) {
		pegs.add(p);
	}
	

    
    public void mousePressed(MouseEvent e) {
    	
        mouseX = e.getPoint().x;
        mouseY = e.getPoint().y;
        
		for (CellView c : cells) {
			if(c.Hit(mouseX, mouseY)) {
				listeners.forEach(i -> i.OnInput(c.cell.x, c.cell.y));
				break;
			}
		}
    }	
}
