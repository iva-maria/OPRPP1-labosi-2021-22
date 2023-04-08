package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class {@code LsShellCommand} represents a command used to write a non-recursive
 * directory listing into the console.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class LsShellCommand implements ShellCommand {

	@Override
	/**
	 * {@inheritDoc}
	 */
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.isBlank()) {
			env.writeln("This command should be called with exactly one argument.");
			return ShellStatus.CONTINUE;
		}
		
		String[] directoryPath = CommandUtil.toArrayArguments(arguments.trim().split("\\s+"));
		if(directoryPath.length != 1) {
			env.writeln("This command should be called with exactly one argument.");
			return ShellStatus.CONTINUE;
		}
		
		Path path = Paths.get(directoryPath[0]);
		if(!Files.isDirectory(path)) {
			env.writeln("The provided file should be a directory.");
			return ShellStatus.CONTINUE;
		}
		
		try(DirectoryStream<Path> ds = Files.newDirectoryStream(path)) {
			for(Path p : ds) {
				StringBuilder sb = new StringBuilder();
				sb.append(Files.isDirectory(p) ? "d" : "-");
				sb.append(Files.isReadable(p) ? "r" : "-");
				sb.append(Files.isWritable(p) ? "w" : "-");
				sb.append(Files.isExecutable(p) ? "x" : "-");
				sb.append(" ");
				sb.append(String.format("%10d", Files.size(p)));
				sb.append(" ");
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				BasicFileAttributeView faView = Files.getFileAttributeView(
						p, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
						);
				BasicFileAttributes attributes = faView.readAttributes();
				FileTime fileTime = attributes.creationTime();
				String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
				
				sb.append(formattedDateTime);
				sb.append(" ");
				sb.append(p.getFileName().toString());
				
				env.writeln(sb.toString());
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
		return "ls";
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public List<String> getCommandDescription() {
		return List.of("Writes a non-recursive directory listing into the console.",
				"Accepts a single argument.",
				"The provided argument represents a path to the directory.");
	}

}
