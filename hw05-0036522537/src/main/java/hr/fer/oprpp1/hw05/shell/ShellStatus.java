package hr.fer.oprpp1.hw05.shell;

/**
 * Enumeration {@code ShellStatus} represents the current state of the shell.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public enum ShellStatus {

	/**
	 * Status which determines that after the current command is executed, the
	 * environment should continue running and wait for the next command.
	 */
	CONTINUE,
	
	/**
	 * Status which determines that after the current command is executed, the
	 * environment should stop running, i.e. the shell should be terminated.
	 */
	TERMINATE
	
}
