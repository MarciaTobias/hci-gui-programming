import java.util.Collections;
import java.util.Stack;

public class DeckOfCards {

	// Stack with 52 cards
	Stack<Card> deckCards = new Stack<>();

	public void deckOfCards() {
		for (int k = 1; k <= 13; k++) {
			for (int j = 0; j <= 3; j++) {
				deckCards.push(new Card(k, j));
			}
		}
	}

	public Card dealTopCard() {
		return deckCards.pop();
	}

	public boolean isEmpty() {

		if (deckCards.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public void shuffle() {
		Collections.shuffle(deckCards);

	}
}
