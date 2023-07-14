package cs131.pa1.filter.sequential;

import java.util.ArrayList;
import java.util.Scanner;

public class Uniq extends SequentialFilter {
	// returns all lines from piped input that are not the same as any previous lines
	String Command;
	String inputS;
	
	public Uniq(String command) {
		Command=command;
		inputS = null;
	}
	@Override
	protected String processLine(String line) {
		inputS=line;
		if (inputS==null) {
			System.out.println("we got a problem");
			return null;
		}
		Scanner scan = new Scanner(inputS);
		ArrayList<String> prevLines = new ArrayList<String>();
		String out = "";
		
		while (scan.hasNext()) {
			String curline = scan.nextLine();
			if(!prevLines.contains(curline)) {
				out+=curline;
				out+="\n";
				prevLines.add(curline);

			}
		}

		return out;
	}

}
