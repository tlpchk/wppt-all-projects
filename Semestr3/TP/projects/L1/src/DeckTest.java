import static org.junit.Assert.*;

import junit.framework.TestCase;

public class DeckTest extends TestCase {
	private final String[] sortedDeck52 = { "2D", "2C", "2H", "2S", "3D", "3C", "3H", "3S", "4D", "4C", "4H", "4S",
											"5D", "5C", "5H", "5S", "6D", "6C", "6H", "6S", "7D", "7C", "7H", "7S",
											"8D", "8C", "8H", "8S", "9D", "9C","9H", "9S","10D", "10C", "10H", "10S",
											"JD", "JC", "JH", "JS", "QD", "QC", "QH", "QS", 
											"KD", "KC", "KH","KS", "AD", "AC", "AH", "AS" };
	private final String[] sortedDeck32 = { "7D", "7C", "7H", "7S", "8D", "8C", "8H", "8S", "9D", "9C", "9H", "9S",
											"10D", "10C", "10H", "10S", "JD", "JC", "JH", "JS", "QD", "QC", "QH", "QS",
											"KD", "KC", "KH", "KS", "AD","AC", "AH", "AS" };
	private final String[] sortedDeck24 = { "9D", "9C", "9H", "9S", "10D", "10C", "10H", "10S", "JD", "JC", "JH", "JS",
											"QD", "QC", "QH", "QS", "KD", "KC", "KH", "KS", "AD", "AC", "AH", "AS" };
	private final int count = 52;

	Deck deck;

	public void setUp() {
		deck = new Deck(count);
	}
	public void testSort() throws Exception {
		deck.sortDeck();
		assertArrayEquals(toArray(deck), sortedDeck52);
		assertArrayEquals(toArray(deck), sortedDeck52);
	}

	public void testShuffling() throws Exception {
		deck.shuffling();
		deck.sortDeck();

		assertNotNull(deck);
		assertFalse(deck.getDeck().isEmpty());
		assertArrayEquals(toArray(deck), sortedDeck52);
	}

	public void testTakeFirst() throws Exception {
		deck.sortDeck();
		assertEquals("2D", deck.getDeck().get(0).getRank() + deck.getDeck().get(0).getSuit());
		assertNotEquals("2H", deck.getDeck().get(0).getRank() + deck.getDeck().get(0).getSuit());
	}

	public void tearDown() {
		deck = null;
	}

	public String[] toArray(Deck d) {
		String[] deckArray = new String[count];
		for (int i = 0; i < count; i++) {
			deckArray[i] = d.getDeck().get(i).getRank() + d.getDeck().get(i).getSuit();
		}
		return deckArray;
	}
}
