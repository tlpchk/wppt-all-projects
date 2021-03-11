package Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

import Model.Cell.CellColor;


public class Board {
	public final static int Width = 25;
	public final static int Hight = 17;
	public Peg selectedPeg;
	
	public boolean shortJump=true;
	
	private Map<CellColor, ArrayList <Cell>> sectors = new HashMap <CellColor, ArrayList <Cell>>(); 
	private Map<CellColor, ArrayList <Peg>> pegs = new HashMap <CellColor, ArrayList <Peg>>(); 
	public ArrayList<Player> players = new ArrayList <Player>();
	
	public Cell[][] matrix = new Cell[Hight][Width];
	
	
	public void Initialize(String fileName) {
		
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {			
			
			String str = "";
			
			for (Iterator<String> i = stream.iterator(); i.hasNext();) {
				
			    String item = i.next();
			    str += item;
			}
			
			for(int i = 0; i < Hight; i++) {
				for(int j = 0; j < Width; j++) {
					
					int index = i*Width + j;
					
					if(str.charAt(index) == '.') {
						matrix[i][j] = null;
					}
					else {		
						if(str.charAt(index) == 'a') {
							matrix[i][j] = new Cell(CellColor.RED);
						}
						else if(str.charAt(index) == 'p') {
							matrix[i][j] = new Cell(CellColor.BLUE);
						}
						else if(str.charAt(index) == 'x') {
							matrix[i][j] = new Cell(CellColor.GREEN);
						}
						else if(str.charAt(index) == 'n') {
							matrix[i][j] = new Cell(CellColor.YELLOW);
						}
						else if(str.charAt(index) == 'z') {
							matrix[i][j] = new Cell(CellColor.PINK);
						}
						else if(str.charAt(index) == 'k') {
							matrix[i][j] = new Cell(CellColor.ORANGE);
						}
						else {
							matrix[i][j] = new Cell(CellColor.NONE);
						}
						
						if(sectors.containsKey(matrix[i][j].cellColor) == false) {
							sectors.put(matrix[i][j].cellColor, new  ArrayList<Cell>());
						}
						
						matrix[i][j].x = j;
						matrix[i][j].y = i;
						sectors.get(matrix[i][j].cellColor).add(matrix[i][j]);
						
					}
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Cell> GetSector(CellColor color) {
		return sectors.get(color);
	}
	
	public void AddPeg(Peg peg) {
		if(pegs.containsKey(peg.getColor()) == false) {
			pegs.put(peg.getColor(), new  ArrayList<Peg>());
		}
		pegs.get(peg.getColor()).add(peg);
	}
	
	public Peg GetPeg(int x, int y) {
		for (Map.Entry<CellColor, ArrayList <Peg>> entry : pegs.entrySet())
		{
			ArrayList <Peg> p = entry.getValue();
			for(Peg i : p) {
				if(i.getX() == x && i.getY() == y) {
					return i;
				}
			}
		}
		return null;
	}
	
	public ArrayList<Peg> GetPlayerPegs(CellColor playerColor){
		return pegs.get(playerColor);
	}
	
	public Cell getCell(int x, int y) { 
		if(x < Width && x >= 0 && y < Hight && y >= 0)
			return matrix[y][x];
		
		return null;
	}
	
	public boolean checkNear(Peg peg, Cell cell) {

		boolean res = false;
		
		res |= (peg.getX() + 1) == cell.x && (peg.getY() + 1) == cell.y;
		res |= (peg.getX() + 1) == cell.x && (peg.getY() - 1) == cell.y;
		res |= (peg.getX() - 1) == cell.x && (peg.getY() + 1) == cell.y;
		res |= (peg.getX() - 1) == cell.x && (peg.getY() - 1) == cell.y;
		
		res |= (peg.getX() + 2) == cell.x && peg.getY() == cell.y;
		res |= (peg.getX() - 2) == cell.x && peg.getY() == cell.y;
		return res;
	}
	
	public boolean checkFar(Peg peg, Cell cell) {

		boolean res = false;
		
		res |= (peg.getX() + 2) == cell.x && (peg.getY() + 2) == cell.y;
		res |= (peg.getX() + 2) == cell.x && (peg.getY() - 2) == cell.y;
		res |= (peg.getX() - 2) == cell.x && (peg.getY() + 2) == cell.y;
		res |= (peg.getX() - 2) == cell.x && (peg.getY() - 2) == cell.y;
		
		res |= (peg.getX() + 4) == cell.x && peg.getY() == cell.y;
		res |= (peg.getX() - 4) == cell.x && peg.getY() == cell.y;
		return res;
	}
	
	public boolean isCellFree(Cell c) {
		return GetPeg(c.x, c.y) == null;
	}
	
	public Player GetPlayer(int playerID) {
		for(Player p : players) {
			if(p.getId() == playerID)
				return p;
		}
		return null;
	}
	
	public ArrayList<Peg> getPegs(CellColor c) {
		return pegs.get(c);
	}
	
}



