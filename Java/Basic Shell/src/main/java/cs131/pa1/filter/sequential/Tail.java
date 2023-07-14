package cs131.pa1.filter.sequential;

import java.util.Scanner;

public class Tail extends SequentialFilter{
	//returns the last ten lines of piped input
	String Command;
	String inputS;
	
	public Tail(String command) {
		Command = command;
		inputS = null;
	}
	
	@Override
	protected String processLine(String line) {
		inputS=line;
		if (inputS==null) {
			return null;
		}
		String out = "";
		Scanner scan = new Scanner(inputS);
		int lines = (int) inputS.lines().count();
		if (lines<=10) {
			while (scan.hasNextLine()){
				out+=scan.nextLine()+"\n";
			}
			scan.close();
			if (lines==0) {
				return"";
			}
			return inputS + "\n";
		}
		int i = 1;
		String curline;
		while (scan.hasNextLine()) {
			curline=scan.nextLine();
			if (i>= lines-9) {
				out+=curline;
				out+="\n";
			}
			i++;
		}
		scan.close();
		return out;
	}

}
