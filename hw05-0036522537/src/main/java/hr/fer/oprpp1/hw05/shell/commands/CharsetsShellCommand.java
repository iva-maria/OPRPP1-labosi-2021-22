package hr.fer.oprpp1.hw05.shell.commands;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class {@code CharsetsShellCommand} represents a command which is used to
 * provide information on supported charsets within the current environment.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class CharsetsShellCommand implements ShellCommand {

	@Override
	/**
	 * {@inheritDoc}
	 */
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(!arguments.isBlank()) {
			env.writeln("This command should be called with no arguments.");
		} else {
			Map<String, Charset> charsets = Charset.availableCharsets();
			for(String charset : charsets.keySet()) env.writeln(charset);
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public String getCommandName() {
		return "charsets";
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public List<String> getCommandDescription() {
		return List.of("List names of supported charsets.", 
				"Accepts no arguments.");
	}

}
