package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class {@code HexdumpShellCommand} represents a command used for
 * producing a hex-output of a file and writing it into the console.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class HexdumpShellCommand implements ShellCommand {

	@Override
	/**
	 * {@inheritDoc}
	 */
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.isBlank()) {
			env.writeln("This command should be called with exactly one argument.");
			return ShellStatus.CONTINUE;
		}
		
		String[] filePath = CommandUtil.toArrayArguments(arguments.trim().split("\\s+"));
		if(filePath.length != 1) {
			env.writeln("This command should be called with exactly one argument.");
			return ShellStatus.CONTINUE;
		}
		
		Path path = Paths.get(filePath[0]);
		if(Files.isDirectory(path)) {
			env.writeln("The provided file cannot be a directory.");
			return ShellStatus.CONTINUE;
		}
		
		try(InputStream is = Files.newInputStream(path)) {
			byte[] buff = new byte[16];	
			int index = 0;
			
			while(true) {
				StringBuilder sb = new StringBuilder();
				int r = is.read(buff);
				if(r == -1) break;
				
				sb.append(String.format("%08X:", index * 16));
				
				for(int i = 0; i < 16; i++) {
					if(i < 8) {
						sb.append(String.format(" %02X", buff[i]));
					} else if (i > 8) {
						sb.append(String.format("%02X ", buff[i]));
					} else {
						sb.append("|");
					}
				}
				
				sb.append("| ");
				
				for(int i = 0; i < 16; i++) {
					sb.append(buff[i] < 32 || buff[i] > 127 ? "." : (char)buff[i]);
				}
				
				env.writeln(sb.toString());
				index++;
			}
		} catch(IOException e) {
			throw new ShellIOException(e.getMessage());
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public List<String> getCommandDescription() {
		return List.of("Produces a hex-output of the provided file and writes in into the console.",
				"Accepts a single argument.",
				"The argument represents the path of the file.",
				"Only a standard subset of characters is shown in the output. All other characters are replaced by a \".\"");
	}

}
