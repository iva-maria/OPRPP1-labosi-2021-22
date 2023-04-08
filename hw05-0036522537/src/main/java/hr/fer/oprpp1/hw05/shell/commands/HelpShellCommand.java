package hr.fer.oprpp1.hw05.shell.commands;

import java.util.List;
import java.util.Map;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class {@code HelpShellCommand} represents a command which is used to
 * provide information on available commands within the current environment.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class HelpShellCommand implements ShellCommand {

	@Override
	/**
	 * {@inheritDoc}
	 */
	public ShellStatus executeCommand(Environment env, String arguments) {
		Map<String, ShellCommand> commands = env.commands();
		
		if(arguments.isBlank()) {
			for(String command : commands.keySet()) env.writeln(command);
			
			return ShellStatus.CONTINUE;
		}
		
		String[] argumentsArray = arguments.trim().split("\\s+");
		if(argumentsArray.length == 1){
			ShellCommand command = commands.get(argumentsArray[0]);
			if(command != null) {
				for(String descriptionSentence : command.getCommandDescription()) env.writeln(descriptionSentence);
			} else {
				env.writeln("Unknown command. Try help for list of supported commands.");
			}
		} else {
			env.writeln("This command should be called with one argument or no arguments.");
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public String getCommandName() {
		return "help";
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public List<String> getCommandDescription() {
		return List.of("Provides more information on other commands.",
				"Accepts no arguments or a single argument.",
				"If used with no arguments, it provides information on all commands within the environment.",
				"If used with an argument, which should be a command name, it provides information on that specific command.");
	}

}
