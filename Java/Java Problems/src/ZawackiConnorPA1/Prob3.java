package ZawackiConnorPA1;
// Connor Zawacki
// Cosi 12b, Spring 2021
// Programing Assignment 1
//
// Prob3, Caesar cipher a given key and string
import java.util.Scanner;

public class Prob3 {

	public static void main(String[] args) {
		Scanner console = new Scanner(System.in); /** first few lines just get the message and key used to create the new message */
		System.out.print("Your message? "); /** to note, can not handle punctuation */
		String message = (console).nextLine();
		System.out.print("Encoding key? ");
		int key = (console).nextInt();
		console.close();
		
		if (key>26) { /** small if statement to assure a larger than needed key doesn't screw us up */
			key-=(26*(key/26));
		}
		
		message = message.toUpperCase(); /** converting to upper case makes ASCII easier, as does setting up a new string */
		String encoded = "";
		
		for(int i = 0; i<=(message.length()-1); i++) { /** for loop through every char in original message */
			if (message.charAt(i) == ' ') {
				encoded += " "; /** spaces are not changed when encoding, so we if/else through them */
			}
			else {
				int charnum = (int) message.charAt(i);
				charnum += key; /** uses ASCII values to change char from original message to encoded message */
				if (charnum>90) {
					charnum-=26; /** if charnum goes past Z or 90, then to loop back around we subtract the number of letters in the alphabet*/
				}
				encoded += (char) charnum;
			}
		}	
		
		System.out.println("Your message: " + encoded);
	}

}
