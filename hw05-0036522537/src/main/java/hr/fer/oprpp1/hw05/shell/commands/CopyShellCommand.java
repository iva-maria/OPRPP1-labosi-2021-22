package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class {@code CopyShellCommand} represents a command used to copy contents of
 * a source file into the destination file or folder.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class CopyShellCommand implements ShellCommand {
	
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
			Path srcPath = Paths.get(argumentsArray[0]);
			if(Files.isDirectory(srcPath)) {
				env.writeln("The first argument cannot be a directory.");
				return ShellStatus.CONTINUE;
			}
			
			if(argumentsArray.length == 2) {
				Path dstPath = Paths.get(argumentsArray[1]);
				if(Files.isDirectory(dstPath)) {
					String srcFileName = srcPath.toString().substring(srcPath.toString().lastIndexOf("\\") + 1);
					dstPath = Paths.get(dstPath + "/" + srcFileName);
				} else if(Files.exists(dstPath)) {
					env.writeln("There is already a file of the same name in the destination folder. Do you wish to overwrite? (Y/N)");
					env.write(env.getPromptSymbol().toString() + " ");
					
					while(true) {
						String opt = env.readLine();
	
						if(opt.equalsIgnoreCase("N")) {
							env.writeln("The file won't be copied.");
							return ShellStatus.CONTINUE;
						} else if(!opt.equalsIgnoreCase("Y")) {
							env.writeln("Unknown command. Do you wish to overwrite? (Y/N)");
							env.write(env.getPromptSymbol().toString() + " ");
						} else {
							break;
						}
					}	
				}			
				
				try(InputStream is = Files.newInputStream(srcPath); 
						OutputStream os = Files.newOutputStream(dstPath)) {
					byte[] buff = new byte[4096];
					while(true) {
						int r = is.read(buff);
						if(r == -1) break;
						
						os.write(buff, 0, r);							
					}
				} catch (IOException e) {
					throw new ShellIOException(e.getMessage());
				}
				
				env.writeln("Copied " + srcPath.toString() + " to " + dstPath.toString());
			}			
		}
	
		return ShellStatus.CONTINUE;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public String getCommandName() {
		return "copy";
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public List<String> getCommandDescription() {
		return List.of("Copies contents of a source file into the destination file.",
				"Accepts two arguments.",
				"The first argument represents the path of the source file.",
				"The second argument represents the path of the destination file.",
				"If the destination file exists, the user is asked whether they want to overwrite an existing file.",
				"If the destination file is a directory, the source file is copied into that directory.");
	}

}
