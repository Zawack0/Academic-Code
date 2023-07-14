package cs131.pa1.filter.sequential;

import java.io.File;

import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;

public class Cd extends SequentialFilter {
	//filter changes the current working directory to a passed argument
	String Command;
	String param;

	
	public Cd(String command) {
		Command = command;
		param = "";

		if (Command.length()>3) {
			param = Command.substring(3);
		}
		else {
			param = "";
		}
	}

	@Override
	protected String processLine(String line) {
		// TODO Auto-generated method stub
		if (param.equals("")){
			System.out.print(Message.REQUIRES_PARAMETER.with_parameter("cd").toString());
			return("");
		}
		else {
			if (param.equals(".")){
				return(""); //no change to be made to dir
			}
			if (param.contains("..")) {
				SequentialREPL.currentWorkingDirectory = new File(SequentialREPL.currentWorkingDirectory).toPath().getParent().toString();
				return(""); // dir change but no output
				
			}

			File pathway = new File(SequentialREPL.currentWorkingDirectory + Filter.FILE_SEPARATOR + param);
			if (!pathway.exists()) {
				System.out.print(Message.DIRECTORY_NOT_FOUND.with_parameter(line));
				return "";
			}
			SequentialREPL.currentWorkingDirectory = pathway.getPath();
			
		}
		return "";
	}

}
