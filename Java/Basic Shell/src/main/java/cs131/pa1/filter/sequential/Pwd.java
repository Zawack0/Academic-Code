package cs131.pa1.filter.sequential;

import cs131.*;

public class Pwd extends SequentialFilter{
	//filter returns current working directory
	private String directory;
	private String Command;

	public Pwd(String command) {
		directory = SequentialREPL.currentWorkingDirectory;
		Command = command;
	}
	@Override
	protected String processLine(String line) {
			return directory;

	}
}
