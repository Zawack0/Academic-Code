package cs131.pa1.filter.sequential;

import java.util.Scanner;

import cs131.pa1.filter.Message;

public class Grep extends SequentialFilter{
	//Returns all lines from piped input containing a given argument
	String Command;
	String inputS;
	String param;
	
	public Grep(String command) {
		Command = command;
		inputS = null;
		if (command.length()<6){
			param = "";
		}
		else {
			if (Command.charAt(5)==' '){
				param = Command.substring(6);
			}
			else {
				param=Command.substring(5);
			}
		}
	}
	@Override
	protected String processLine(String line) {
		if (param.equals("")||param==null) {
			System.out.print(Message.REQUIRES_PARAMETER.with_parameter("grep"));
			return null;
		}
		inputS = line;
		if (inputS==null||inputS=="") {
			System.out.println("debugger tool: grep");
			return null;
		}
		String out = "";
		Scanner scan = new Scanner(inputS);
		String curline;
		while (scan.hasNextLine()) {
			curline=scan.nextLine();
			if (curline.contains(param)) {
				out+=curline+"\n";
			}
		}
		scan.close();
		return out;
	}

}
