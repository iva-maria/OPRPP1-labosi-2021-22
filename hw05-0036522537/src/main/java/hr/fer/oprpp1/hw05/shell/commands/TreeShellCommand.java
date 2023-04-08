package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class {@code TreeShellCommand} represents a command used to write a recursive
 * tree representation of the directory structure below the current directory into
 * the console.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class TreeShellCommand implements ShellCommand {

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
		
		try {
			Files.walkFileTree(path, new FileVisitor<Path>() {
				private int depth = 1;
				
				@Override
				public FileVisitResult postVisitDirectory(Path path, IOException exc) {
					depth--;
					
					return FileVisitResult.CONTINUE;
				}
				
				@Override
				public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attrs) {
					for(int i = 0; i < depth; i++) env.write("  ");
					env.write(path.getFileName().toString() + "\n");
					depth++;
					
					return FileVisitResult.CONTINUE;
				}
				
				@Override
				public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
					for(int i = 0; i < depth; i++) env.write("  ");
					env.write(path.getFileName().toString() + "\n");
					
					return FileVisitResult.CONTINUE;
				}
				
				@Override
				public FileVisitResult visitFileFailed(Path path, IOException exc) {
					return FileVisitResult.CONTINUE;
				}
			});
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
		return "tree";
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public List<String> getCommandDescription() {
		return List.of("Creates a recursive tree representation of the directory structure.",
				"Accepts a single argument.",
				"The provided argument represents a path to the directory.");
	}

}
