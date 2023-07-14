/**
 * Connor Zawacki
 * connorzawacki@braneis.edu
 * 5/1/21
 * PA#7
 * 
 * Receives a filename input from user. File contains specialized TA worklogs
 * TAChecker works with TARecord class to compare the ID number and start time of individual TAs to find the presence of "bad" reports
 * 
 * PLEASE NOTE: all code for the correct return types are present. HOWEVER, the main method is awful cranky about returning an array as the PA
 * instructs. I have commented out code that would be needed to both print the desired output or return it. I only detected this discrepancy 
 * fairly close to the deadline, else I would have asked about it in office hours, I hope this solution is to your liking,
 * as all the necessary code is still present
 */
package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class TAChecker {
	
	static ArrayList<TARecord> TAs = new ArrayList<TARecord>(); // because sortWorkLog takes no parameters and returns no values, but its contents must be analyzed by check Validity, this has to be done here
	static String output_string = ""; //is used in at least two methods that take no parameters, so must be initialized here. Strings are easier to construct as we go than arrays
	
	/**
	 * main just runs the other two methods and "returns" the string representation of TA mishaps
	 * @return the PA asks for a string array return, but the main doesn't allow for a return value. below are several commented out ways of providing algorithm output
	 * the output was tested, it should be correct, just small mishap about the format
	 * @throws FileNotFoundException
	 */
	public static void main(String [] args) throws FileNotFoundException {
		System.out.println("Enter a work log:");
		sortWorkLog();
		checkValidity();
		//System.out.println(output_string); // the printing of the string constructed, mostly used for debugging
		String[] output = output_string.split("\n"); // "output" is the return value the PA asks for. Note that it will have length of 1 even if "empty", so length shouldn't be used as a test. if this is an issue, a simple if statement testing for an empty string could be used to empty the array
		//for (int i = 0; i<output.length; i++) { // this for loop prints out the string array that the PA asks for, 
		//	System.out.println(output[i]);
		//}
		//return(output); //PA says to return a string array, but the main method defaulted to void, and the PA and skeleton code were somewhat ambiguous as to what to do about this, so I have everything needed here
		//return(output);
	}
	
	/**
	 * sortWork Log primarily scans the input file and constructs records for each TA
	 * also begins the process of creating the output_string
	 * @throws FileNotFoundException
	 */
	public static void sortWorkLog() throws FileNotFoundException { 
		
		Scanner Console = new Scanner(System.in); 
		String filename = Console.next();
		Console.close();
		File stuff = new File(filename);
		Scanner scan = new Scanner(stuff); // first few lines set up the scanner for input file
		
		int LineNumber = 1; //useful for keeping track of when jobs are started
		String line; // string representation of current line of input from file
		String TAname; // identifier for TA
		String data; // everything after the TA name
		ArrayList<String> TAtest = new ArrayList<String>(); //
		TARecord TA; // we need to refer to the TA object in question
		int divider; // the index of the semicolon dividing name and data
		
		while (scan.hasNextLine()) {
			line = scan.nextLine();
			divider = line.indexOf(';'); 
			TAname = line.substring(0,divider); //everything before the semicolon in the line of input
			data = line.substring(divider+1); // everything after the semicolon in the line of input
			
			if (!TAtest.contains(TAname)) { // first logic test deals with the case that this is the first instance of the TA in the file
				TA = new TARecord(TAname);
				TAs.add(TA);
				TAtest.add(TAname);
			}
			else {
				TA = TAs.get(TAtest.lastIndexOf(TAname)); // Unfortunately very strange how we must refer to the TA object of choice, but should work because of the specialized .equals method
			}
			
			if (data.equals("START")) {
				TA.StartJob(LineNumber);
			}
			else if (data.contains(",")) { // deals with the case where TA reports a batch of finished jobs in one line
				TA.BatchData(LineNumber);
				String strings[] = data.split(",");
				for (int i = 0; i<strings.length; i++) {
					TA.FinishJob(Integer.valueOf(strings[i]));
				}
			}
			else {
				TA.FinishJob(Integer.valueOf(data));
			}
			
			if (TA.GetData().containsKey(null)) { // easier to just do this here because we are already keeping track of LineNumber
				output_string += (LineNumber+";"+TA.name+";"+"UNSTARTED_JOB\n");
			}
			LineNumber++;
			
		}
		scan.close();
	}
	
	/**
	 * constructs the output string based on the now updated TARecords
	 * somewhat inefficient, but given the likely small number of TAs and/or lines of input, this shouldn't be a huge deal, the program runtime is still significantly under a second
	 */
	public static void checkValidity() { 
		for (int i = 0; i<TAs.size(); i++) {
			for (int j = 0; j<TAs.size(); j++) {
				if (i!=j) { // this line of logic avoids a lot of unnecessary comparisons
					int compare_val = TAs.get(i).Compare(TAs.get(j)); // Initializing a variable for this avoids running the compare method multiple times
					if(compare_val > 0) {
						if (TAs.get(i).IsBatch() == true && compare_val<2) { // only a suspicious batch if there is a batch AND not all the values are shortened jobs
							output_string += (TAs.get(i).getBatchData()+";"+TAs.get(i).name+";"+"SUSPICIOUS_BATCH\n");
						}
						else {
							for (int k = 0; k<TAs.get(i).getBadLine().size(); k++) { // because batches can be shortened jobs, we have to use a for loop here
								output_string += (TAs.get(i).getBadLine().get(k)+";"+TAs.get(i).name+";SHORTENED_JOB\n");
							}
						}
					}
				}
			}
			
		}
	}
}
