package cs131.pa1.filter.sequential;


public class Wc extends SequentialFilter{
	//counts number of lines words and characters in the piped input
	String Command;
	String inputS;
	int chars;
	int words;
	int lines;
	
	public Wc(String command) {
		Command = command;
		inputS = null;
	}
	
	@Override
	protected String processLine(String line) {
		inputS=line;
		if (inputS==null) {
			return null;
		}
		chars = inputS.replaceAll("\n", "").length();
		
		words = inputS.replaceAll("\n\n"," ").replaceAll("\n", " ").replaceAll("\s\s", " ").replaceAll("\s", " ").split("\s").length;
		//words = inputS.replaceAll("\n", " ").split(" ").length;
		if (inputS!="" && inputS.charAt(0)==' ') {
			words--;
		}
		
		lines = (int) inputS.lines().count();
		
		if (chars==0) { 
			words=chars;
		}
		String out = lines + " " + words + " " + chars +"\n";
		return out;
		
	}

}
