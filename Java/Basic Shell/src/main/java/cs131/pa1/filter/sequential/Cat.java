package cs131.pa1.filter.sequential;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.Scanner;

import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;

public class Cat extends SequentialFilter {
	//Writes a passed files contents into a pipe or standard output
	String Command;
	String param;

	

	public Cat(String command) {
		Command = command;
		String[] spaced = Command.split(" ");
		param = "";

		if (Command.length()>4) {
			param = Command.substring(4);
		}

	}
	@Override
	protected String processLine(String line) {
		if (param.equals("")) {
			System.out.print(Message.REQUIRES_PARAMETER.with_parameter("cat"));
			return null;
		}
		String path = SequentialREPL.currentWorkingDirectory + Filter.FILE_SEPARATOR + param;
		File toread = new File(path);
		Scanner scan;
		String out = "";
		try {
			scan = new Scanner(toread);
		} catch (FileNotFoundException e) {
			System.out.print(Message.FILE_NOT_FOUND.with_parameter(Command));
			return null;
		}
		while (scan.hasNextLine()) {
			out+=scan.nextLine();
			if (scan.hasNextLine()){
				out+="\n";
			}
		}
		scan.close();
		return out;
		
	}

}
