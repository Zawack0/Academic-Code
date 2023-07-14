package cs131.pa1.filter.sequential;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;

public class Redirect extends SequentialFilter {
	// redirects output of preceding command into a given file
	String Command;
	String inputS;
	String param;
	
	
	public Redirect(String command) {
		Command=command;
		inputS=null;
		if (Command.length()>1){
			
		if (Command.substring(2).equals(" >")){
			param = Command.substring(4); //catches a bug with how redirects are separated sometimes in sequentialcommand builder
		}
		else {
		param = Command.substring(2);
		}
		}

		
	}
	@Override
	
	protected String processLine(String line) {
		inputS = line;
		if (inputS.equals(null)) {
			return null;
		}
		if (param==null||param.equals("")) {
			System.out.print(Message.REQUIRES_PARAMETER.with_parameter(">"));
			return "";
		}
		if (param.contains("|")) { //exactly one of the tests for some reason uses "|" instead of " | ". as a programmer I'd handle this as an invalid command, but the test wants me to throw can not have output for some reason
			System.out.print(Message.CANNOT_HAVE_OUTPUT.with_parameter("> "+param.substring(0,param.lastIndexOf("|"))));
			return"";
		}
		File output = new File(SequentialREPL.currentWorkingDirectory + Filter.FILE_SEPARATOR +  param);
		try {
			FileWriter write = new FileWriter(output);
			write.write(inputS);
			write.close();
		} catch (IOException e) {
			System.out.println("Debugger tool: redirect"); // not a feature, just here to help debug
			System.out.println("param is " + param);
			System.out.println("intput is " + line);
			return null;
		}
		
		
		return null;
	}


}
