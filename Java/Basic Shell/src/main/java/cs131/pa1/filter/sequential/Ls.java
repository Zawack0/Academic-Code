package cs131.pa1.filter.sequential;

import java.io.File;

public class Ls extends SequentialFilter{
	//filter lists the files in the current working directory
	String Command;

	public Ls(String command){
		Command = command;
		
	}
	@Override
	protected String processLine(String line) {
		File helper = new File(SequentialREPL.currentWorkingDirectory);
		File[] filelist = helper.listFiles();
		String output = "";
		for (int i=0; i<filelist.length; i++) {
			if (i>0) {
				output+="\n";
			}
			output+=filelist[i].getName();
		}
		return output;
	}

}
