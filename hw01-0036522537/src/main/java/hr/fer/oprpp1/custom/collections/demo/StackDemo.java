package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * The {@code StackDemo} class represents a command-line application which accepts a single command-line argument: an expression
 * in postfix notation which should be evaluated.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class StackDemo {

	/**
	 * Evaluates an expression which is passed as a command line argument.
	 * 
	 * @param args an array of arguments passed through the command line.
	 * @throws ArithmeticException when there is an attempt to divide by zero.
	 */
	public static void main(String[] args) {
		
		String[] split = args[0].split("\\s+");
		
		ObjectStack stack = new ObjectStack();
		
		for(String symbol : split) {
			if(isNumeric(symbol)) {
				stack.push(symbol);
			} else {
				int operator2 = Integer.parseInt(stack.pop().toString());
				int operator1 = Integer.parseInt(stack.pop().toString());
				
				switch(symbol) {
					case "+":
						stack.push(operator1 + operator2);
						break;
					case "-":
						stack.push(operator1 - operator2);
						break;
					case "*":
						stack.push(operator1 * operator2);
						break;
					case "/":
						if(operator2 == 0) throw new ArithmeticException("Cannot divide by zero.");
						stack.push(operator1 / operator2);
						break;
					case "%":
						if(operator2 == 0) throw new ArithmeticException("Cannot divide by zero.");
						stack.push(operator1 % operator2);
						break;
				}
			}
		}
		
		if(stack.size() != 1) {
			System.err.println("The stack size should be one, but isn't. Wrong arguments were provided.");
		} else {
			System.out.println("The evaluated expression equals to " + stack.pop() + ".");
		}
	}
	
	public static boolean isNumeric(String string) { 
		  try {  
		    Integer.parseInt(string);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
	}
	
}
