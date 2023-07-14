// Connor Zawacki
// Cosi 12b, Spring 2021
// Programing Assignment 2
//
// Prob2, Guessing game. Playing a game of reverse hang-man with the user
import java.util.*;
public class Prob2 {

	public static void main(String[] args) {
		Boolean flag = true; /** sentinels are my personal favorite way of conditionalizing while loops */
		Scanner console = new Scanner(System.in);
		Random rand = new Random();
		String alphabet = "abcdefghijklmnopqrstuvwxyz"; /** we initialize this, then remove the letters that we randomly guessed */
		int guessed = 0;
		int wrong = 0;
		intro();
		System.out.print("How many letters are in your word? ");
		int letters = console.nextInt();
		System.out.print("please enter the word for me to guess (letters only): ");
		String word = console.next(); 
		String incomplete = "-";
		for(int i=0; i<(letters-1); i++) { /** simple for loop to get the 'empty' representation of the word */
			incomplete += " -";
		}                                  /** all code up until this point is initializing necessary variables */
		incomp(incomplete);
		while(flag==true) { /** this while loop is the real 'meat' of the game. Decided to not make its own method because of the potentially large number of parameters and returns that would be needed */
			draw(wrong);  
			System.out.println();
			System.out.println("I've got " + guessed + " of the " + letters + " letters so far");
			int index = rand.nextInt(alphabet.length());
			char my_guess = alphabet.charAt(index);
			alphabet = alphabet.substring(0, index) + alphabet.substring(index + 1); /** removes the guessed letter from alphabet variable*/
			System.out.println("I guess: " + Character.toUpperCase(my_guess));
			System.out.print("Is that letter in the word? ");
			char success = console.next().charAt(0);
			if (success == 'n' || success == 'N') { /** output if guessed incorrectly */
				wrong++;
				if (wrong == 7) { /** lose condition */
					flag = false;
					System.out.println();
					incomp(incomplete);
					end(incomplete);
				}
				else {
					incomp(incomplete);
				}
			}
			if (success == 'y' || success == 'Y') { /** output if guessed correctly */
				System.out.print("How many of that letter are in the word? ");
				int instances = console.nextInt();
				guessed+= instances;
				incomplete = modify(incomplete, word, my_guess, console); 
				incomp(incomplete);
			}
			if (guessed==letters) { /** win condition */
				flag = false;
				System.out.println("I win this time!");
			}
		}
	}
	
	public static void intro() { /** just prints the introduction to the game */
		System.out.println("This program plays a game of reverse hangman.");
		System.out.println("You think up a word (by typing it on the computer) and I'll try to guess");
		System.out.println("the letters.");
		System.out.println();
	}
	
	
	public static void draw(int guessed){ /** Draws hangman board based off number of incorrect guesses */
		System.out.println(" +--+");		
		System.out.println(" |  |");
		if (guessed > 0) {
			System.out.println(" |  O"); 
		}
		else {
			System.out.println(" |"); 
		}
		if (guessed >= 6) { 			  /** assignment shows full man drawn on 6 incorrect, but game over after 7, so we use >= */
			System.out.println(" | /|\\");
		}
		else if (guessed == 5) {
			System.out.println(" |  |\\");
		}
		else if (guessed >= 2) {
			System.out.println(" |  |");
		}
		else {
			System.out.println(" |");
		}
		if (guessed >= 4) {
			System.out.println(" | / \\");
		}
		else if (guessed == 3) {
			System.out.println(" |   \\");
		}
		else {
			System.out.println(" |");
		}
		System.out.println(" |");
		System.out.println(" +-----"); /** last two lines are always the same*/
	}
	public static String modify(String incomplete, String word, char letter, Scanner console) { /** replaces the dashes in the string representing the number of letters left with the appropriate letter*/
		for (int i = 0; i<word.length(); i++) {
			if (Character.toLowerCase(word.charAt(i)) == letter) {
				int index = i*2; /** turns out the index of any letter in our original word is half of the corresponding '-' in the incomplete string */
				incomplete = incomplete.substring(0, index) + letter + incomplete.substring(index +1);
			}
		}
		return incomplete;
	}

	public static void incomp(String incomplete) { /** I noticed that we were printing out the incomplete string with empty lines before and after often, so I made it its own method to declutter the code*/
		System.out.println();
		System.out.println(incomplete);
		System.out.println();
	}
	
	public static void end(String complete) { /** same concept as intro, declutters the main */
		draw(7);
		System.out.println("You beat me this time.");
	}
}
