package ZawackiConnorPA1;
// Connor Zawacki
// Cosi 12b, Spring 2021
// Programing Assignment 1
//
// Prob5, print each digit of a number individually without using String
import java.util.Scanner;

public class Prob5 {

	public static void main(String[] args) {
		
		Scanner console = new Scanner(System.in); /** First chunk just gets an int input from user */
		System.out.print("Please enter any positive integer: ");
		int initial = (console).nextInt();
		int first = initial;
		console.close();
		

		for(int i = 1000000000; i>0; i = i/10) { /** int only goes the billions, so we only have to test up to there */
			if (initial >= i) { /** ensures we only modify or print anything if there is a number in that digit */
				System.out.println(initial/i); /** This prints the very first digit every time */
				initial %= i; /** this removes the digit just printed */
				}
			else if (initial >= i/10 && !(i>first)){
				System.out.println(0);
			}
		}
	}
}
