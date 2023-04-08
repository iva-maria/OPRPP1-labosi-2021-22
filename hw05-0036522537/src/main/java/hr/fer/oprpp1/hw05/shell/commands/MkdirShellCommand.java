package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class {@code MkdirShellCommand} represents a command used to create
 * a directory.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class MkdirShellCommand implements ShellCommand {
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.isBlank()) {
			env.writeln("This command should be called with exactly one argument.");
			return ShellStatus.CONTINUE;
		}
		
		String[] argumentsArray = CommandUtil.toArrayArguments(arguments.trim().split("\\s+"));
		
		if(argumentsArray.length != 1) {
			env.writeln("This command should be called with exactly one argument.");
			return ShellStatus.CONTINUE;
		} else {
			Path path = Paths.get(argumentsArray[0]);
			
			try {
				Files.createDirectories(path);
				env.writeln("Created directory " + path.toString());
			} catch(IOException e) {
				throw new ShellIOException(e.getMessage());
			}
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public List<String> getCommandDescription() {
		return List.of("Creates the appropriate directory structure.",
				"Accepts a single argument.",
				"The provided argument represents a path to the newly created directory.");
	}

}
