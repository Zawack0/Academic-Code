package ZawackiConnorPA1;
// Connor Zawacki
// Cosi 12b, Spring 2021
// Programing Assignment 1
//
// Prob3, convert number to Roman numerals
import java.util.Scanner;

public class Prob2 {

	public static void main(String[] args) {
		Scanner console = new Scanner(System.in); /** get a number from user, convert to Roman numeral */
		System.out.print("Please provide an integer between 0 and 4999 "); 
		int number = (console).nextInt();
		String numeral = ""; /** best to construct the numeral as we go along, and using the number variable as way to mark progress*/
		console.close();
		
		if (number/1000>0) { /** Roman numerals change patters at 4, 5, 9, and with the addition of a digit */
			if (number/1000 == 4) {
				numeral += "MV";
				number%=1000;
				/** please note that the Roman numeral for 4000 in M 
				and a V with a bit of a line over it. I will be omitting the special line, character. MV is 4000 */

			}
			else {
				while(number>=1000) { /** adds an "M" until there are no more thousands */
					number-=1000;
					numeral +="M";
				}
			}
		}
		
		
		if (number/100>0) { /**from here on out is a similar structure. Test to see if in applicable digit is a 9, 5, or 4, */
			if (number>=900) { /** if so, then get rid of the digit and add applicable numerals. Then while loop through whatever */
				number-=900; /** is left in that digit slot */
				numeral += "CM";
			}
			else if (number>=500) {
				number-=500;
				numeral+="D";
			}
			if (number>=400) {
				number-=400;
				numeral+="CD";
			}
			while (number>=100) {
				number-=100;
				numeral+="C";
			}
		}

		if (number/10>0) { /** same as above, but with the 10s digit instead of the 100s */
			if(number>=90) {
				number -= 90;
				numeral += "XC";
			}
			else if (number>=50) {
				number-=50;
				numeral+="L";
			}
			if (number>=40) {
				number-=40;
				numeral+="XL";
			}
			while (number>=10) {
				number-=10;
				numeral+="X";
			}
		}
		
		if (number>0) { /** same as above, but with the 1s digit instead of the 10s */
			if(number>=9) {
				number -= 9;
				numeral += "IX";
			}
			else if (number>=5) {
				number-=5;
				numeral+="V";
			}
			if (number>=4) {
				number-=4;
				numeral+="IV";
			}
			while (number>=1) {
				number-=1;
				numeral+="I";
			}
		}
		
		System.out.println("In Roman Numerals is: " + numeral);

	}

}
