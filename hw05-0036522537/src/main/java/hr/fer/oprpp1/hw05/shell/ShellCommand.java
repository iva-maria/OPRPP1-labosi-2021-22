package hr.fer.oprpp1.hw05.shell;

import java.util.List;

/**
 * Interface {@code ShellCommand} represents all commands of the shell {@code MyShell}.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public interface ShellCommand {
	
	/**
	 * Executes the current command within the provided environment,
	 * using provided arguments.
	 * 
	 * @param env the environment in which the command execution will be happening.
	 * @param arguments arguments used for the command execution.
	 * @return status of the shell which determines what action happens next.
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Fetches the name of the current command.
	 * 
	 * @return name of the current command.
	 */
	String getCommandName();
	
	/**
	 * Fetches the description of the current command.
	 * 
	 * @return description of the current command, containing short info on its arguments
	 * and how it should be used.
	 */
	List<String> getCommandDescription();
	
}
