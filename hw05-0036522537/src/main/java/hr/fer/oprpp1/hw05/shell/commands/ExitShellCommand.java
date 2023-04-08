package hr.fer.oprpp1.hw05.shell.commands;

import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class {@code ExitShellCommand} represents a command which is used to
 * terminate the current shell.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class ExitShellCommand implements ShellCommand {
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(!arguments.isBlank()) {
			env.writeln("The command should be called with no arguments.");
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.TERMINATE;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public String getCommandName() {
		return "exit";
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public List<String> getCommandDescription() {
		return List.of("Terminates shell.",
				"Accepts no arguments.");
	}

}
