/**
 * Connor Zawacki
 * connorzawacki@braneis.edu
 * 3/20/21
 * PA#4
 * 
 * Card Class is responsible for all things related to an individual card, including its value, suit, and color
 */
public class Card {
private int value;
private String suit;
private String color;

/**
 * 
 * @param _value is the number value of the card being created
 * @param _suit is the suit of the card being created
 * Constructor method to initialize a card. Also initializes color based off suit.
 */
public Card(int _value, String _suit) {
	value = _value;
	suit = _suit;
	if (_suit.equals("Hearts") || _suit.equals("Diamonds")){
		color = "Red";
	}
	else {
		color = "Black";
	}
}

/**
 * 
 * @return just returns value of card. Useful for tostring
 */
public int getValue() {
	return value;
}

/**
 * 
 * @return just returns color of card (not sure why this is necessary but hey)
 */
public String getColor(){
	return color;
}

/**
 * 
 * @return just returns suit of applicable card. Useful for tostring
 */
public String getSuit() {
	return suit;
}

/**
 * Needs to have a name for the card, which is usually just its value
 * other than that, just a simple string representation of the card's value and suit
 */
public String toString() {
	String Card_Name = String.valueOf(value);
	if (value == 11) {
		Card_Name = "Jack";
	}
	if (value == 12) {
		Card_Name = "Queen";
	}
	if (value == 13) {
		Card_Name = "King";
	}
	if (value == 1) {
		Card_Name = "Ace";
	}
	return(Card_Name + " of " + suit);
}
}
