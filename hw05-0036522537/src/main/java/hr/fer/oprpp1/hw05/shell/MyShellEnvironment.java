package hr.fer.oprpp1.hw05.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.oprpp1.hw05.shell.commands.CatShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.CharsetsShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.CopyShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.ExitShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.HelpShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.HexdumpShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.LsShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.MkdirShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.SymbolShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.TreeShellCommand;

/**
 * Class {@code MyShellEnvironment} is a concrete implementation of the
 * {@code Environment} interface for the current shell.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class MyShellEnvironment implements Environment {

	/**
	 * Used for reading user input.
	 */
	private Scanner sc;
	
	/**
	 * The message which is displayed when the shell is first started.
	 */
	private static final String GREETING_MESSAGE = "Welcome to MyShell v 1.0";
	
	/**
	 * Symbol which represents the beginning of user input.
	 */
	private Character promptSymbol;
	
	/**
	 * Symbol which the user can write so that his input can span over more than one row.
	 */
	private Character moreLinesSymbol;
	
	/**
	 * Symbol which represents the beginning of a non-beginning line in a multiline input.
	 */
	private Character multiLineSymbol;
	
	/**
	 * Current status of the current shell.
	 */
	private ShellStatus shellStatus;
	
	/**
	 * Map of all commands available within the current shell environment.
	 */
	private SortedMap<String, ShellCommand> commands;
	
	/**
	 * Default constructor which initializes all private values.
	 */
	public MyShellEnvironment() {
		promptSymbol = '>';
		moreLinesSymbol = '\\';
		multiLineSymbol = '|';
		
		shellStatus = ShellStatus.CONTINUE;
		sc = new Scanner(System.in);
		commands = new TreeMap<String, ShellCommand>();
		
		//nemoj zaboraviti dodati commandse tu kad ih implementiras!
		commands.put("help", new HelpShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("exit", new ExitShellCommand());
	}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public String readLine() throws ShellIOException {
		StringBuilder sb = new StringBuilder();
		
		String line = sc.nextLine();
		while(line.endsWith(moreLinesSymbol.toString())) {
			sb.append(line, 0, line.length() - 1);
			write(multiLineSymbol + " ");
			line = sc.nextLine();
		}
		
		return sb.append(line).toString();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void write(String text) throws ShellIOException {
		System.out.print(text);		
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void writeln(String text) throws ShellIOException {
		write(text + "\n");		
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public Character getMultilineSymbol() {
		return multiLineSymbol;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void setMultilineSymbol(Character symbol) {
		multiLineSymbol = symbol;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void setPromptSymbol(Character symbol) {
		promptSymbol = symbol;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public Character getMorelinesSymbol() {
		return moreLinesSymbol;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void setMorelinesSymbol(Character symbol) {
		moreLinesSymbol = symbol;
	}
	
	/**
	 * Fetches the current status of the current shell.
	 * 
	 * @return status of the current shell.
	 */
	public ShellStatus getShellStatus() {
		return shellStatus;
	}
	
	/**
	 * Sets the status of the current shell to the provided value.
	 * 
	 * @param status the value which the status of the current shell is to be set to.
	 */
	public void setShellStatus(ShellStatus status) {
		shellStatus = status;
	}
	
	/**
	 * Fetches the greeting message of the current shell.
	 * @return
	 */
	public String getGreetingMessage() {
		return GREETING_MESSAGE;
	}

}
