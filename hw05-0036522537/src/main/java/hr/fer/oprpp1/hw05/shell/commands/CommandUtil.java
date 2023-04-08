package hr.fer.oprpp1.hw05.shell.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Class {@code CommandUtil} is used for performing operations
 * on parameters of {@code ShellCommand} instances.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class CommandUtil {

	/**
	 * Stores all arguments of a command into an array.
	 * 
	 * @param arguments arguments of a command.
	 * @return an array of arguments.
	 */
	public static String[] toArrayArguments(String[] arguments) {
		List<String> argumentsList = new ArrayList<>();
		
		for(int i = 0, arraySize = arguments.length; i < arraySize; i++) {
			if(arguments[i].startsWith("\"")) {
				StringBuilder sb = new StringBuilder();
				
				sb.append(arguments[i]);
				
				for(int j = i + 1; j < arraySize; j++) {
					if(arguments[j].endsWith("\"")) {
						sb.append(" ").append(arguments[j]); //mozda ovo izbaciti van?
						i++;
						break;
					}
					sb.append(" ").append(arguments[j]);
					i++;
				}
				
				argumentsList.add(sb.toString().replace("\"", ""));
 			} else {
 				argumentsList.add(arguments[i]);
 			}
		}
		
		String[] argumentsArray = new String[argumentsList.size()];
		
		return argumentsList.toArray(argumentsArray);
	}
	
}
