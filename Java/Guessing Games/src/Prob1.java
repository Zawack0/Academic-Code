// Connor Zawacki
// Cosi 12b, Spring 2021
// Programing Assignment 2
//
// Prob1, Guessing game. Program guesses a users number, and takes "higher" and "lower" directions
import java.util.*;
public class Prob1 {
	public static final int max = 100;

	public static void main(String[] args) {
		int total_games = 0;
		int total_guesses = 0;
		int max_guesses = 0;
		boolean flag = true; 					/** keeps track of user's intention to continue playing the game */
		Scanner console = new Scanner(System.in);
		intro(); 								/** decided to stick much of the printing into functions so it doesn't clog up the main */
		
		while (flag==true) { 					/** while loop though playing the game as long as the user wants */
			total_games++;
			int guess = game(console); 			/** game method returns guesses */
			total_guesses += guess;
			if (guess>max_guesses) {
				max_guesses = guess;
			}

			System.out.print("Do you want to play again? ");
			String play = console.next();
			if (play.charAt(0)== 'n' || play.charAt(0) == 'N') { /** Assignment specifies we can assume user response to either start with y/Y or n/N */
				flag = false;                                    /** so we only have to test for one of the two */
			}
		}
		
		results(total_games, total_guesses, max_guesses);
	}
	
	public static void intro() {				/** just prints out the intro, more neat then shoving it all in main */
		System.out.println("This program allows you to play a guessing game.");
		System.out.println("Think of a number between 1 and 100");
		System.out.println("and I will guess until I get it.");
		System.out.println("For each guess, tell me if the");
		System.out.println("right anser is higher or lower than your guess, or if it is correct.");
	}
	
	public static int game(Scanner scan) {		/** plays the game once */
		Random rand = new Random();
		int my_guess = 0;
		int high = max;
		int min = 1;
		int game_guesses = 0;
		boolean correct = false;
		
		System.out.println();
		System.out.println("Think of a number...");
		
		while (correct == false) { 				/*loops through a guessing process until user enters "correct" */
			my_guess = rand.nextInt(high - min + 1) + min; /* this equation always guesses between 1-100, until user changes min/max */
			game_guesses++;
			System.out.println("My guess: " + my_guess);
			String result = scan.nextLine();
			if (result.equals("lower")) {		 /** higher or lower results impact next guess */
				high = my_guess - 1;
			}
			else if (result.toLowerCase().equals("higher")) {
				min = my_guess + 1;
			}
			else if (result.toLowerCase().equals("correct")) { /** only coded for these 3 responses because assignment specified so */
				correct = true;
				System.out.println("I got it right in " + game_guesses + " guesses");
				System.out.println();
			}
		}
		return game_guesses;
	}
	
	public static void results(int games, int guesses, int max_guesses) { /** once again only here to de-clutter main */
		System.out.println();
		System.out.println("Overall results:");
		System.out.println("    total games   = " + games);
		System.out.println("    total guesses = " + guesses);
		System.out.println("    guesses/game  = " + ((double)guesses/games));
		System.out.println("    max guesses   = " + max_guesses);
	}
}
