
public class Card {	
	private String rank;
	private String suit;
	
	public String getRank(){
		return this.rank;
    }
	
	public String getSuit(){
		return this.suit;
	}
	
	public Card(String rank,String suit){
		this.rank=rank;
		this.suit=suit;
	}
	
	public Card(int rank,String suit){
		this.rank=Integer.toString(rank);
		this.suit=suit;
	}
	
	public int rankNumber(){
		if(this.rank.equals("J")){return 11;}else if(this.rank.equals("Q")){
			return 12;
		}
		else if(this.rank.equals("K")){
			return 13;
		}
		else if(this.rank.equals("A")){
			return 14;
		}
		else{
			return Integer.parseInt(this.rank);
		}
		
	}
	
	public int suitNumber() {
			if (this.suit.equals("D")) return 1;
			else if (this.suit.equals("C"))	return 2;
			else if (this.suit.equals("H")) return 3;
			else return 4;
	}
	
	public String toStr(){
		return this.rank+this.suit;
	}
}