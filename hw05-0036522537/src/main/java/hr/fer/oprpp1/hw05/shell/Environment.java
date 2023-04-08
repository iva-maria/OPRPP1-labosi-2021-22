package hr.fer.oprpp1.hw05.shell;

import java.util.SortedMap;

/**
 * Interface {@code Environment} represents a model for representation of an
 * environment in which our shell should execute its commands.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public interface Environment {
	
	/**
	 * Reads one line of user input from the shell.
	 * 
	 * @return line of user input.
	 * @throws ShellIOException
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * Writes provided text into the environment.
	 * 
	 * @param text the text that is to be written into the environment.
	 * @throws ShellIOException
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * Writes provided text into the environment as a single row.
	 * 
	 * @param text the text that is to be written into the environment.
	 * @throws ShellIOException
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * Fetches a map of all commands that exist within the current environment.
	 * 
	 * @return a map of commands.
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * Fetches the {@code MULTILINE} symbol of the current environment.
	 * 
	 * @return {@code MULTILINE} symbol of the current environment.
	 */
	Character getMultilineSymbol();
	
	/**
	 * Sets the {@code MULTILINE} symbol of the current environment to the provided value.
	 * 
	 * @param symbol the value that the {@code MULTILINE} symbol is to be set to.
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * Fetches the {@code PROMPT} symbol of the current environment.
	 * 
	 * @return {@code PROMPT} symbol of the current environment.
	 */
	Character getPromptSymbol();
	
	/**
	 * Sets the {@code PROMPT} symbol of the current environment to the provided value.
	 * 
	 * @param symbol the value that the {@code PROMPT} symbol is to be set to.
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * Fetches the {@code MORELINES} symbol of the current environment.
	 * 
	 * @return {@code MORELINES} symbol of the current environment.
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Sets the {@code MORELINES} symbol of the current environment to the provided value.
	 * 
	 * @param symbol the value that the {@code MORELINES} symbol is to be set to.
	 */
	void setMorelinesSymbol(Character symbol);
	
}
