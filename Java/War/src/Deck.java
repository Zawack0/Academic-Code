/**
 * Connor Zawacki
 * connorzawacki@braneis.edu
 * 3/20/21
 * PA#4
 * 
 * Deck class is responsible for all the actions that would need to be taken with an array of objects of the class Card
 */
import java.util.*;
public class Deck {
final int deck_size = 52; // 52 cards in a typical deck
final int different_values = 13; // 13 different kinds of cards, 1-10 + J + Q + K
private Card[] Stack;  // Named "Stack" to avoid confusion. Still gets the point across
private Card[] Discard;
private int cards_drawn; // this variable keeps track of how many cards out of the current stack have already been drawn. Much simpler than null value-ing every card after drawn, and helpful for keeping track

/**
 * initializing a deck requires initializing an array the size of the deck, and constructing a card of each suit for each different value
 */
public Deck() {
	Stack = new Card[deck_size];
	int card_counter = 0; // only want to increment i after each value has one of each suit. This counter is the way I decided to construct the next card in line
	for (int i = 1; i<=different_values; i++) {
		Stack[card_counter] = new Card(i, "Spades");
		card_counter++;
		Stack[card_counter] = new Card(i, "Clubs");
		card_counter++;
		Stack[card_counter] = new Card(i, "Hearts");
		card_counter++;
		Stack[card_counter] = new Card(i, "Diamonds");
		card_counter++;
	}
	Discard = new Card[deck_size]; // also initializes the discard pile
}

/**
 * A mock version of the Fisher-Yates shuffle. Not sure if it works as originally intended, but definitely does a sufficient job at randomizing the array, which is the point
 */
public void shuffle() {
	Random rand = new Random();
	Card Dummy;
	int swapper;
	for (int i = 0; i < deck_size; i++) {
		if (Stack[i] == null) {
			Stack[i] = Discard[i];
		}
	}
	for (int i = Stack.length-1; i >1; i--) {
		swapper = rand.nextInt(i);
		Dummy = Stack[i];
		Stack[i] = Stack[swapper];
		Stack[swapper] = Dummy;
	}
	cards_drawn = 0;
}

/**
 * @returns the next card in deck. 
 */
public Card drawNextCard() {
	cards_drawn++; // must be incremented first because return statement ends the execution
	Card draw = Stack[cards_drawn - 1];
	if (cards_drawn == deck_size) { // when we've drawn through the whole deck, reset the discard, shuffle the deck, and reset cards drawn
		this.shuffle();
		Discard = new Card[deck_size];
		cards_drawn = 1;
	}
	discard(draw); // gotta make sure the discard pile is up to date
	return(draw); // even though cards have already changed position, draw stored the card drawn. Because we are returning a value this had to be done after the suffle
}

/**
 * Discards the card just drawn
 * @param c drawNextCard passes the card just drawn as a parameter to be discarded
 */
public void discard(Card c) {
	Discard[cards_drawn -1] = c;
	Stack[cards_drawn - 1] = null;
}
}


