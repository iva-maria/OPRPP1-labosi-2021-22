package hr.fer.oprpp1.hw05.shell.commands;

import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class {@code SymbolShellCommand} represents a command which is used to provide information on
 * or change values of {@code PROMPT}, {@code MORELINES} or {@code MULTILINE} symbols within the
 * current environment.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class SymbolShellCommand implements ShellCommand {

	@Override
	/**
	 * {@inheritDoc}
	 */
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.isBlank()) {
			env.write("This command should be called with one or two arguments.");
			return ShellStatus.CONTINUE;
		}
		
		String[] argumentsArray = arguments.trim().split("\\s+");
		
		if(argumentsArray.length < 1 || argumentsArray.length > 2) {
			env.write("This command should be called with one or two arguments.");
			return ShellStatus.CONTINUE;
		}
		
		if(argumentsArray.length == 1) {
			if(argumentsArray[0].equalsIgnoreCase("PROMPT")
					|| argumentsArray[0].equalsIgnoreCase("MORELINES")
					|| argumentsArray[0].equalsIgnoreCase("MULTILINE")) {
				env.write("Symbol for " + argumentsArray[0].toUpperCase() + " is ");
				switch(argumentsArray[0].toUpperCase()) {
					case "PROMPT":
						env.write("\'" + env.getPromptSymbol() + "\'.\n");
						break;
					case "MORELINES":
						env.write("\'" + env.getMorelinesSymbol() + "\'.\n");
						break;
					case "MULTILINE":
						env.write("\'" + env.getMultilineSymbol() + "\'.\n");
						break;
				}
			} else {
				env.write("Unknown symbol.");
				return ShellStatus.CONTINUE;
			}
		} else if(argumentsArray.length == 2) {
			Character oldValue = '.';
			Character newValue = '.';
			switch(argumentsArray[0].toUpperCase()) {
			case "PROMPT":
				oldValue = env.getPromptSymbol();
				env.setPromptSymbol(argumentsArray[1].toCharArray()[0]);
				newValue = env.getPromptSymbol();
				break;
			case "MORELINES":
				oldValue = env.getMorelinesSymbol();
				env.setMorelinesSymbol(argumentsArray[1].toCharArray()[0]);
				newValue = env.getMorelinesSymbol();
				break;
			case "MULTILINE":
				oldValue = env.getMultilineSymbol();
				env.setMultilineSymbol(argumentsArray[1].toCharArray()[0]);
				newValue = env.getMultilineSymbol();
				break;
			}
			env.writeln("Symbol for " + argumentsArray[0].toUpperCase() + " changed from " + oldValue + " to " + newValue + ".");
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public String getCommandName() {
		return "symbol";
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public List<String> getCommandDescription() {
		return List.of("Used to change or provide information on PROMPTSYMBOL, MORELINESYMBOL or MULTILINESYMBOL.",
				"Accepts one or two arguments.",
				"The first argument is the symbol which is to be changed.",
				"The second argument is the new value of the provided symbol.",
				"If only the first argument is provided, the current value of the symbol will be written into the console.",
				"If a second argument is provided, the value od the symbol will be changed accordingly.");
	}

}
