package cs131.pa1.filter.sequential;

import java.util.Scanner;

public class Head extends SequentialFilter {
	//returns the first 10 lines of piped input
	String Command;
	String inputS;
	
	public Head(String command) {
		Command=command;
		inputS=null;
	}
	
	@Override
	protected String processLine(String line) {
		inputS=line;
		if (inputS==null) {
			return null;
		}
		if (inputS=="") {
			return inputS;
		}
		String out = "";
		String[] lines = inputS.split("\n");
		for (int i = 0; i<lines.length; i++) {
			if (i<=9) {
				out+=lines[i];
				out+="\n";

			}
		}
		return out;
	}


}
