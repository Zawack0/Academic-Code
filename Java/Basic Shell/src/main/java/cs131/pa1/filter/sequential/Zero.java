package cs131.pa1.filter.sequential;

import java.util.LinkedList;
import java.util.Queue;

public class Zero extends SequentialFilter{

	public Zero() {
		input = new LinkedList<String>();
		input.add(" ");
	}
	@Override
	protected String processLine(String line) {
		return "";
	}

}
