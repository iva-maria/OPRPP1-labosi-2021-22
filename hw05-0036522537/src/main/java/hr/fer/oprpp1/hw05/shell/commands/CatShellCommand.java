package hr.fer.oprpp1.hw05.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class {@code CatShellCommand} represents a command which is used to
 * open a file and write its content into the console.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class CatShellCommand implements ShellCommand {

	@Override
	/**
	 * {@inheritDoc}
	 */
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.isBlank()) {
			env.writeln("This command should be called with one or two arguments.");
			return ShellStatus.CONTINUE;
		}
		
		String[] argumentsArray = CommandUtil.toArrayArguments(arguments.trim().split("\\s+"));
		
		if(argumentsArray.length < 1 || argumentsArray.length > 2) {
			env.writeln("This command should be called with one or two arguments.");
		} else {
			Path path = Paths.get(argumentsArray[0]);
			Charset charset = (arguments.length() == 2 ? Charset.forName(argumentsArray[1]) : Charset.defaultCharset());
			
			try(BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(Files.newInputStream(path)), charset))) {
				String line = reader.readLine();
				while(line != null) {
					env.writeln(line);
					line = reader.readLine();
				}
			} catch (IOException e) {
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
		return "cat";
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public List<String> getCommandDescription() {
		return List.of("Opens given file and writes its content into the console.",
				"Accepts one or two arguments.",
				"The first argument represents the file.",
				"The second argument, if provided, represents the charset to be used to interpret bytes.");
	}

}
