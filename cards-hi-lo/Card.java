
public class Card {
	
	// Rank  and suit of the card.
	private int rank = 0;
	private int suit = 0;
	private int card;

	public Card(int c) {
		card = c;
	
	}// constructor
	
	public Card(int r, int s) {
		rank = r; //rank - Ace, deuce (2), 3,.... Queen, King
		suit = s; //suit - diamonds, spades, hearts and clubs
	}// constructor
	
	public int getRank() {
		return rank;
	}// getRank()
	
	public int getSuit() {
		return suit;
	}// getSuit()
	
	public boolean rankIsLessThanCard(Card c) {
		boolean rankIsLess = false;
		
		if(rank < c.getRank()) {
			rankIsLess = true;
		}//if
		
		return rankIsLess;
	}// rankIsGreaterThan()
	
	public boolean rankIsGreatherThanCard(Card c) {
		boolean rankIsGreater = false;
		
		if(rank > c.getRank()) {
			rankIsGreater = true;
		}//if
		
		return rankIsGreater;
	}// rankIsGreaterThan()
	
	public boolean rankIsEqualTo(Card c) {
		boolean rankIsEqualTo = false;
		
		if(rank == c.getRank()) {
			rankIsEqualTo = true;
		}//if
		
		return rankIsEqualTo;
	}// rankIsGreaterThan()
	

	
	public String toString() {
		String cardSuit = "";
		String cardRank = "";
		String cardString = "";
		
		int cs = getSuit();
		int cr = getRank();
		
		switch(cr) {
		
		case 1:
			cardRank = "ace";
			break;
			
		case 2:
			cardRank = "2";
			break;
			
		case 3:
			cardRank = "3";
			break;
			
		case 4:
			cardRank = "4";
			break;
			
		case 5:
			cardRank = "5";
			break;
			
		case 6:
			cardRank = "6";
			break;
		
		case 7:
			cardRank = "7";
			break;
			
		case 8:
			cardRank = "8";
			break;
			
		case 9:
			cardRank = "9";
			break;	
			
		case 10:
			cardRank = "10";
			break;	
			
		case 11:
			cardRank = "jack";
			break;	
		
		case 12:
			cardRank = "queen";
			break;
			
		case 13:
			cardRank = "king";
			break;
			
		default:
			cardRank = "n/a";	
		}// switch rank		
		
		
		// Got a string representation of the rank.
		// Now get a string representation of the suit.
		
		switch(cs) {
		
		case 0:
			cardSuit = "hearts";
			break;
		
		case 1:	
			cardSuit = "diamonds";
			break;
			
		case 2:
			cardSuit = "clubs";
			break;
			
		case 3:
			cardSuit = "spades";
			break;		
		}
		
		// concataned
		cardString = "file:cards/" + cardRank + "_of_" + cardSuit+".png";
		return cardString;
	}// toString()
}
