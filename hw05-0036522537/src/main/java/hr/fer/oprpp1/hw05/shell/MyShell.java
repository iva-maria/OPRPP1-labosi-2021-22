package hr.fer.oprpp1.hw05.shell;

/**
 * Class {@code MyShell} is a command-line application which represents a shell.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class MyShell {

	/**
	 * First function in the program.
	 * 
	 * @param args command-line arguments.
	 */
	public static void main(String[] args) {
		MyShellEnvironment environment = new MyShellEnvironment();
		environment.writeln(environment.getGreetingMessage());
		
		do {
			environment.write(environment.getPromptSymbol() + " ");
			
			String[] input = environment.readLine().trim().split("\\s+", 2); //dodaje sve iza commandName u jedan dio
			String commandName = input[0];
			String commandArguments = "";
			if(input.length == 2) commandArguments = input[1];
			
			ShellCommand command = environment.commands().get(commandName);
			if(command == null & !commandName.isBlank()) {
				environment.writeln("Unknown command.");
				continue;
			}
			
			environment.setShellStatus(command.executeCommand(environment, commandArguments));
		} while(environment.getShellStatus() != ShellStatus.TERMINATE);
	}

}
