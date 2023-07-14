package ZawackiConnorPA1;
// Connor Zawacki
// Cosi 12b, Spring 2021
// Programing Assignment 1
//
// Prob4, Convert a name into pig Latin
import java.util.*;

public class prob4 {

	public static void main(String[] args) {
		Scanner console = new Scanner(System.in); /** first chunk of code is just for receiving and setting up the first and last variables*/
		System.out.print("First name? ");
		String first = (console).nextLine().toLowerCase(); /** we need it all in lowercase. This can be done when initializing */
		System.out.print("Last name? ");
		String last = (console).nextLine().toLowerCase();
		console.close();
		

		String pig_first = ""; /** setting up two new variables makes our life slightly easier than changing the strings directly */
		String pig_last = "";

		pig_first = first.substring(1); /** cuts off everything except first letter */
		pig_first += first.charAt(0) + "ay"; /** re-adds the first letter at the end plus "ay" */
		pig_first = Character.toUpperCase(pig_first.charAt(0)) + pig_first.substring(1); /** easiest way I could find to capitalize first letter*/
		
		pig_last = last.substring(1); /** same as above, but with the last name now */
		pig_last += last.charAt(0) + "ay";
		pig_last = Character.toUpperCase(pig_last.charAt(0)) + pig_last.substring(1);
		
		System.out.println(pig_first + ' ' + pig_last); /** Make sure to add a space in between */
	}

}
