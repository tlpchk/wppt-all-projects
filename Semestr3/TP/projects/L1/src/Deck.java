import java.util.ArrayList;

public class Deck {
	private final String RANKS[] = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" };
	private final String SUITS[] = { "D", "C", "H", "S" };
	private ArrayList<Card> deckOfCards;
	private final int countOfCards;

	public Deck(int countOfCards) {
		this.deckOfCards = new ArrayList<>();
		this.countOfCards = countOfCards;
		
		int index;
		switch (this.countOfCards) {
			case 24:{index = 7;break;}
			case 32:{index = 5;break;}
			default: index=0;
		}
		
		while(index < 13) {
			for (int j = 0; j < 4; j++) {
				Card c = new Card(RANKS[index], SUITS[j]);
				this.deckOfCards.add(c);
				c=null;
			}
			index++;
		}

		if (countOfCards != 24 && countOfCards != 32 && countOfCards != 52) {
			deckOfCards = null;
		}
	}

	public void shuffling() throws NullPointerException {
		try {
			ArrayList<Card> newDeckOfCards = new ArrayList<Card>();
			for (int i = 0; i < this.countOfCards; i++) {
				int randomCard = (int) (Math.random() * this.deckOfCards.size());
				newDeckOfCards.add(this.deckOfCards.get(randomCard));
				this.deckOfCards.remove(randomCard);
			}
			this.deckOfCards = newDeckOfCards;
		} catch (NullPointerException e) {
			System.out.println("Zla liczba kart");
		}
	}

	public void allDeck() throws NullPointerException {
		try {
			for (Card c : this.deckOfCards) {
				System.out.print("\""+c.toStr()+"\",");
			}
			System.out.println("");
		} catch (NullPointerException e) {
			System.out.println("Zla liczba kart");
		}
	}

	public String takeFirst() throws NullPointerException {
		try {
			Card c = this.deckOfCards.get(0);
			return c.getRank() + c.getSuit();
		} catch (NullPointerException e) {
			System.out.println("Zla liczba kart");
			return null;
		}
	}

	public void sortDeck() throws Exception {
		try {
			for (int i = 1; i < this.countOfCards; i++) {
				int j = i;

				while (j != 0 && this.deckOfCards.get(j).rankNumber() <= this.deckOfCards.get(j - 1).rankNumber()) {
					Card c = this.deckOfCards.get(j);
					if (this.deckOfCards.get(j).rankNumber() < this.deckOfCards.get(j - 1).rankNumber()) {
						this.deckOfCards.remove(j);
						this.deckOfCards.add(j, this.deckOfCards.get(j - 1));
						this.deckOfCards.remove(j - 1);
						this.deckOfCards.add(j - 1, c);
					} else {
						if (this.deckOfCards.get(j).suitNumber() < this.deckOfCards.get(j - 1).suitNumber()) {
							this.deckOfCards.remove(j);
							this.deckOfCards.add(j, this.deckOfCards.get(j - 1));
							this.deckOfCards.remove(j - 1);
							this.deckOfCards.add(j - 1, c);
						}
					}
					j--;
				}
			}
		} catch (NullPointerException e) {
			System.out.println("Zla liczba kart");
		}
	}

	public ArrayList<Card> getDeck() {
		return this.deckOfCards;
	}

	public int getCount() {
		return this.countOfCards;
	}
}