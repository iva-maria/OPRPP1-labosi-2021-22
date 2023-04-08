package hr.fer.oprpp1.hw05.crypto;

/**
 * Class {@code Util} is used for converting hexadecimal (String)
 * to byte (array) and vice-versa.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class Util {
	
	/**
	 * Converts a String representation of a hexadecimal value into a byte array.
	 * 
	 * @param keyText hexadecimal string.
	 * @return an array of {@code byte} values.
	 */
	public static byte[] hexToByte(String keyText) { //provjeriti return za prazno polje bajtova
		if(keyText == null) throw new NullPointerException("The provided key text cannot be null.");
		if(keyText.length() % 2 != 0) throw new IllegalArgumentException("The provided string is not valid.");
		
		byte[] byteArray = new byte[keyText.length() / 2];
		for(int i = 0; i < keyText.length(); i += 2) {
			byteArray[i / 2] = (byte) ((Character.digit(keyText.charAt(i), 16) << 4) 
					+ Character.digit(keyText.charAt(i + 1), 16));
		}
		
		return byteArray;
	}
	
	/**
	 * Converts a byte array into a hexadecimal string.
	 * 
	 * @param byteArray an array of bytes.
	 * @return hexadecimal string representation of the array.
	 */
	public static String byteToHex(byte[] byteArray) { //provjeriti return za prazni string
		if(byteArray == null) throw new NullPointerException("The provided byte array cannot be null.");
		StringBuilder sb = new StringBuilder();
		for(byte b : byteArray) {
			sb.append(String.format("%02x", b));
		}	
		
		return sb.toString();
	}
	
}
