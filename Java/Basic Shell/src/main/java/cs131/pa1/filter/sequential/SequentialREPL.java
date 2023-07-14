package cs131.pa1.filter.sequential;
import java.util.*;
import cs131.pa1.filter.Message;

/**
 * The main implementation of the REPL loop (read-eval-print loop). It reads
 * commands from the user, parses them, executes them and displays the result.
 * 
 * @author cs131a
 *
 */
public class SequentialREPL {
	/**
	 * the path of the current working directory
	 */
	static String currentWorkingDirectory;
	static boolean exitFlag;

	/**
	 * The main method that will execute the REPL loop
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		boolean firstoutput=true;
		currentWorkingDirectory = System.getProperty("user.dir");
		SequentialCommandBuilder REPL = new SequentialCommandBuilder();
		Scanner user = new Scanner(System.in);
		exitFlag=false;
		System.out.print(Message.WELCOME);
		while (exitFlag==false) {
			System.out.print(Message.NEWCOMMAND);
			String input = user.nextLine();
			Stack<SequentialFilter> curline = REPL.createFiltersFromCommand(input);
			if (!REPL.linkFilters(curline)==false) { // if curline does not contain null? and linkfilters is not false
				if (curline.size()==1) {
					String out = curline.pop().processLine(input);
					if (out!=null) {
					System.out.print(out);
					if (out.length()>1&& !out.contains("Goodbye")) { // I know this is evil and I'm sorry
						System.out.print("\n");
					}
					}
				}
				else {
					SequentialFilter first = curline.pop();
					SequentialFilter zero = new Zero();
					first.setPrevFilter(zero);
					zero.process();
					first.process();
					while (!curline.isEmpty()) {
						SequentialFilter next = curline.pop();
						if (curline.isEmpty()){
							next.setNextFilter(new Badfilter());
						}
						next.process();
						if (curline.isEmpty()){
							String notnull = next.output.poll();
							if (notnull!=null) {
							System.out.print(notnull); //THERE IS A PROBLEM HERE 
							firstoutput=false;
							}
						}
					}
				}
			
			}
		}
		user.close();
		
	}

}
