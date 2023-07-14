package ZawackiConnorPA1;
// Connor Zawacki
// Cosi 12b, Spring 2021
// Programing Assignment 1
//
// Prob1, Collatz Conjecture

// Remember to close consoles
import java.util.Scanner;
public class Prob1 {
	public static void main(String[] args) {
		int operations = 0; /** First five34 lines of code simply set up the initial value and operations variables*/
		Scanner console = new Scanner(System.in);
		System.out.print("Initial value is: ");
		int initial = (console).nextInt();
		console.close();
		
		if (initial < 1) { /** if statement for values lower than 1 */
			System.out.println("Error, value too low.");
			return;
		}
		
		
		while (initial>2) { /** we use >2 as opposed to >1 because we want our last line of output to be the final value line */
			if (initial%2==0) { /** if even, divide by 2 and increment operations */
				initial/=2;
				operations++;
				System.out.println("Next value is: " + initial);
			}
			else { /** if odd, multiply by 3, add 1, and increment operations */
				initial = initial*3 + 1;
				operations++;
				System.out.println("Next value is: " + initial);
			}
		}
		/** the final line of our output. By now, initial must equal 1 so we can just use 1 instead of the variable */
		System.out.println("Final value 1, number of operations performed " + (operations + 1)); /** operations + 1 because we stopped at 2
		in the if statement, so this is our last operation */

	}

}
