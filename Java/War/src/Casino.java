/**
 * Connor Zawacki
 * connorzawacki@braneis.edu
 * 3/20/21
 * PA#4
 * 
 * Casino class is the actual client code. This allows a user to play a simplified version of the card game War with a dealer
 */
import java.util.*;
public class Casino {

	/**
	 * War pretty much writes itself after Card and Deck classes are taken care of, but in short, main preps the game
	 * by shuffling a new deck, initializing how much money the player has, and while looping though the game until either
	 * the user runs out of money or asks to stop playing
	 */
	public static void main(String[] args) {
		Deck gamble = new Deck(); // cool sounding deck deck name achieved :)
		gamble.shuffle();
		welcome();
		boolean flag = true;
		int money = 100;
		Scanner console = new Scanner(System.in);
		if(console.next().toLowerCase().charAt(0) == 'n') {
			System.out.println("Then leave."); //I thought this would be funny. It was funny.
		}
		else {
			System.out.println("Great, here we go!");
			while (flag == true) { // sentinel values are bae
				int bet = getbet(console, money);
				int modify = GameRound(bet, gamble);
				money += modify;
				if (money==0) {
					System.out.println("Yikes, you're outta cash! Thanks for playing!");
					flag = false;
				}
				else {
					flag = stop(money, console); // stop only returns false if the player wants to stop, so this works
				}
				
			}
		}

	}
	
	/**
	 * I just didn't want this block of print statements in main
	 */
	public static void welcome() {
		System.out.println("Welcome to Iraklis's Cyber Casino!");
		System.out.println("Because this is your first time here, we'll give you 100$ absolutely free!");
		System.out.println("However, due to budget cuts and lazy staff, the only game available currently is War.");
		System.out.println("Here are the rules, 1 deck, you bet however much you'd like before each draw, then you and I both");
		System.out.println("draw a card. If yours is higher, you'll win back your bet and then some! If not, your bet is all mine!");
		System.out.println("Are ya ready to play?");
		
	}
	
	/**
	 * Gets the user's bet for the next play
	 * @param console because we still need user input
	 * @param money because its good to know how much you have before you bet.
	 * @returns the total amount bet
	 */
	public static int getbet(Scanner console, int money) {
		System.out.println("You have $"+money+". How much ya bettin'?");
		int bet = console.nextInt();
		while (bet>money) { // just here to make sure nobody goes into debt
			System.out.println("Nice try, but there are no IOUs! You can only bet what you got!"); 
			bet = console.nextInt();
		}
		if (bet == money) {
			System.out.println("Risky Risky...");
		}
		return bet;
	}
	/**
	 * The actual drawing and comparing of card values happens here
	 * @param bet because we need to know how much to return
	 * @param gamble because we need the actual deck of cards
	 * @return returns the amount that the user's money will be modified by. Positive if they won, negative if they lost
	 */
	public static int GameRound(int bet, Deck gamble) {
		int result;
		System.out.print("You draw...");
		Card player = gamble.drawNextCard();
		System.out.println(player);
		System.out.print("The house draws...");
		Card house = gamble.drawNextCard();
		System.out.println(house);
		if (house.getValue() >= player.getValue()) {
			System.out.println("Ooooh unlucky...");
			result = bet * (-1);
		}
		else {
			System.out.println("Winner Winner!");
			result = bet;
		}
		return result;
		
	}
	
/**
 * 
* @param money because its good to know how much you've got before you make the decision to leave
* @param console because we still need user input
* @returns false if the user wants to stop playing
*/
	public static boolean stop(int money, Scanner console) {
		System.out.println("You now have $"+ (money-100) +" more than you started with! ($" + money + " total)"); // I realize this can say that you have - money more than you started with, but thats hilarious so I'm not coding around it.//
		System.out.println("Feel like playing more?");
		System.out.println("(yes to continue, no to stop)");
		String play = console.next();
		while (!play.equals("yes") && !play.equals("no")) { // user can only input "yes" or "no", otherwise this will continue
			System.out.println("(yes to continue, no to stop)");
			play = console.next();
		}
		if (play.equals("yes")) {
			return true;
		}
		System.out.println("Thanks for playing! You ended with $" + money); // doesnt need an if statement because the code has already stopped being executed if they want to continue
		return false;
}
}
