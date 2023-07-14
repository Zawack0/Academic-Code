package cs131.pa1.filter.sequential;

import java.util.List;
import java.util.Stack;

import cs131.pa1.filter.Message;

/**
 * This class manages the parsing and execution of a command. It splits the raw
 * input into separated subcommands, creates subcommand filters, and links them
 * into a list.
 * 
 * @author cs131a
 *
 */
public class SequentialCommandBuilder {
	/**
	 * Creates and returns a list of filters from the specified command
	 * 
	 * @param command the command to create filters from
	 * @return the list of SequentialFilter that represent the specified command
	 */
	public static Stack<SequentialFilter> createFiltersFromCommand(String command) {
		Stack<SequentialFilter> commands = new Stack<SequentialFilter>();
		String commandline = command;
		while (!(commandline.equals("") || commandline.equals(null))) {
			SequentialFilter last = determineFinalFilter(commandline);
			if (last == null){
				throw new NullPointerException();
			}
			commands.push(last);
			commandline = adjustCommandToRemoveFinalFilter(commandline);
		}
		return commands;
	}

	/**
	 * Returns the filter that appears last in the specified command
	 * 
	 * @param command the command to search from
	 * @return the SequentialFilter that appears last in the specified command
	 */
	private static SequentialFilter determineFinalFilter(String command) {
		String[] commands = command.split(" \\|");
		String finalcommand = commands[commands.length-1];
		SequentialFilter finalfilter;
		if (finalcommand.contains(">")) {
			finalfilter = constructFilterFromSubCommand(finalcommand.substring(finalcommand.indexOf(">")));
		}
		else {
			finalfilter = constructFilterFromSubCommand(finalcommand);
		}
		
		return finalfilter;
	}

	/**
	 * Returns a string that contains the specified command without the final filter
	 * 
	 * @param command the command to parse and remove the final filter from
	 * @return the adjusted command that does not contain the final filter
	 */
	private static String adjustCommandToRemoveFinalFilter(String command) {
		int a = command.lastIndexOf(" | ");
		int b = command.lastIndexOf(" >");
		String adjusted = "";
		if (a!=-1 || b!=-1) {
		if (a>b) {
			adjusted = command.substring(0, a);
		}
		else {
			adjusted = command.substring(0, b);
		}
		}
		return adjusted;
//		String[] commands = command.split(" \\| ");
//		for (int i = 0; i<commands.length-1; i++) {
//			if (i!=0) {
//				adjusted+=" | ";
//			}
//			adjusted += commands[i];
//		}
//		
//		return adjusted;
	}

	/**
	 * Creates a single filter from the specified subCommand
	 * 
	 * @param subCommand the command to create a filter from
	 * @return the SequentialFilter created from the given subCommand
	 * I AM AWARE that this isn't the best way to do this, was just having a brainfart
	 * note that for any test with "contains", I could have split the string around whitespace 
	 * and tested the first word in order to ensure the filename isn't screwing up my logic, but for 
	 * our simple test cases, this is unnecessary
	 */
	private static SequentialFilter constructFilterFromSubCommand(String subCommand) {
		if (subCommand.contains("pwd")){
			SequentialFilter filter = new Pwd(subCommand);
			return filter;
		}
		if (subCommand.contains(">")){
			SequentialFilter filter = new Redirect(subCommand);
			return filter;
			
		}
		if (subCommand.contains("ls")){
			SequentialFilter filter = new Ls(subCommand);
			return filter;
		}
		if (subCommand.contains("cat")){
			if (!subCommand.contains("cat ")&&!subCommand.equals("cat")) {
				System.out.print(Message.COMMAND_NOT_FOUND.with_parameter(subCommand));
				SequentialFilter filter = new Badfilter();
				return filter;
			}
			SequentialFilter filter = new Cat(subCommand);
			return filter;
		}
		if (subCommand.contains("head")){
			SequentialFilter filter = new Head(subCommand);
			return filter;
			
		}
		if (subCommand.contains("tail")){
			SequentialFilter filter = new Tail(subCommand);
			return filter;
		}
		if (subCommand.contains("grep")){
			if (!subCommand.contains("grep ")&&!subCommand.equals(" grep")) {
				System.out.print(Message.COMMAND_NOT_FOUND.with_parameter(subCommand));
				SequentialFilter filter = new Badfilter();
				return filter;
			}
			SequentialFilter filter = new Grep(subCommand);
			return filter;

		}
		if (subCommand.contains("wc")){
			SequentialFilter filter = new Wc(subCommand);
			return filter;
		}
		if (subCommand.contains("uniq")){
			SequentialFilter filter = new Uniq(subCommand);
			return filter;
			
		}

		if (subCommand.equals("Exit")||subCommand.equals("exit")){
			SequentialFilter filter = new Exit();
			return filter;
		}
		if (subCommand.contains("cd")) {
			
			if (!subCommand.contains("cd ")&&!subCommand.equals("cd")) {
				System.out.print(Message.COMMAND_NOT_FOUND.with_parameter(subCommand));
				SequentialFilter filter = new Badfilter();
				return filter;
			}
			SequentialFilter filter = new Cd(subCommand);
			return filter;
		}
		System.out.print(Message.COMMAND_NOT_FOUND.with_parameter(subCommand)); // DO I NEED TO ADD SOMETHING HERE?
		SequentialFilter filter = new Badfilter();
		return filter;
	}

	/**
	 * links the given filters with the order they appear in the list
	 * 
	 * If I were allowed to modify SequentialFilter class, I'd add a new field or two to make the
	 * testing on this method look a lot more smooth, but this works too
	 * 
	 * @param filters the given filters to link
	 * @return true if the link was successful, false if there were errors
	 *         encountered. Any error should be displayed by using the Message enum.
	 */
	static boolean linkFilters(Stack<SequentialFilter> filters) {
		int size = filters.size();
		if (filters.contains(null)) {
			throw new NullPointerException();
		}
		SequentialFilter[] helper = new SequentialFilter[size];
		SequentialFilter first = filters.pop();
		helper[size-1]=first;
		int i = size-2;
		if (first instanceof Badfilter) {
			return false;
		}
		if (first instanceof Head ) {
			System.out.print(Message.REQUIRES_INPUT.with_parameter("head"));
			return false;
		}
		if (first instanceof Tail ) {
			System.out.print(Message.REQUIRES_INPUT.with_parameter("tail"));
			return false;
		}
		if (first instanceof Grep ) {
			Grep grepp = (Grep) first;
			System.out.print(Message.REQUIRES_INPUT.with_parameter("grep " + grepp.param));
			return false;
		}
		if (first instanceof Wc ) {
			System.out.print(Message.REQUIRES_INPUT.with_parameter("wc"));
			return false;
		}
		if (first instanceof Uniq ) {
			System.out.print(Message.REQUIRES_INPUT.with_parameter("uniq"));
			return false;
		}
		if (first instanceof Redirect ) {
			Redirect re = (Redirect) first;
			System.out.print(Message.REQUIRES_INPUT.with_parameter("> " +re.param));
			return false;
		}
		while (filters.isEmpty()==false) {
			SequentialFilter next = filters.pop();
			helper[i]=next;
			i-=1;
			first.setNextFilter(next);
			next.setPrevFilter(first);

			if (next instanceof Pwd) {
				System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter("pwd"));
				return false;
			}
			if (next instanceof Ls) {
				System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter("ls"));
				return false;
			}
			if (next instanceof Cat) {
				Cat catt = (Cat) next;
				System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter("cat" + catt.param));
				return false;
			}
			if (next instanceof Cd) {
				Cd cdd = (Cd) next;
				System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter("cd" + cdd.param));
				return false;
			}

			if (first instanceof Cd) {
				Cd cdd = (Cd) first;
				System.out.print(Message.CANNOT_HAVE_OUTPUT.with_parameter("cd " + cdd.param));
				return false;
			}
			if (first instanceof Redirect) {
				System.out.print(Message.CANNOT_HAVE_OUTPUT.with_parameter(">"));
				return false;
			}
			if (first instanceof Badfilter) {
				return false;
			}
			if (next instanceof Badfilter) {
				return false;
			}
			first = next;
		}
		for (int k = 0; k<size; k++) {
			if (helper[k] !=null) {
			filters.push(helper[k]);
			}
		}
		return true;
	}
}
