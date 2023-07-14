package cs131.pa1.filter.sequential;

import cs131.pa1.filter.Message;

public class Exit extends SequentialFilter{
	// prints "goodbye" and terminates REPL
	@Override
	protected String processLine(String line) {
		SequentialREPL.exitFlag=true;
		
		return (Message.GOODBYE.toString());
	}

}
