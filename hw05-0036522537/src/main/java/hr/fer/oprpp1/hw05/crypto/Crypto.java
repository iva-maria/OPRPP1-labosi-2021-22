package hr.fer.oprpp1.hw05.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class {@code Crypto} is a representation of a command-line application
 * used for digesting and encrypting/decrypting files.
 * 
 * @author Iva Maria Ivanković
 * @version 1.0
 */
public class Crypto {

	/**
	 * Method from which the program execution begins.
	 * 
	 * @param args an array of command-line arguments.
	 * @throws NoSuchAlgorithmException 
	 * @throws IOException 
	 * @throws NoSuchPaddingException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws InvalidKeyException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		if(!(args.length == 2 || args.length == 3)) throw new IllegalArgumentException("Only 2 or 3 arguments allowed.");
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		
		String fileName = args[1];
		
		switch (args[0]) {
			case "checksha":
				//dodati provjere i iznimke za nedozvoljeni format datoteke				
				System.out.println("Please provide expected sha-256 digest for " + fileName + ":\n> ");			
				String expectedDigest = sc.nextLine().trim();
				
				String digest = digest(fileName);
				
				System.out.print("Digesting completed. ");
				
				if(digest.equalsIgnoreCase(expectedDigest)) {
					System.out.println("Digest of " + fileName + " matches expected digest.");
				} else {
					System.out.println("Digest of " + fileName + " does not match the expected digest. Digest was: " + digest);
				}
				
				break;
			case "encrypt": //popravi ovo glupo propadanje, koristi lambde
			case "decrypt":
				//dodati provjere i iznimke za nedozvoljeni format datoteke
				System.out.print("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):\n> ");
				String keyText = sc.nextLine().trim();
				
				System.out.print("Please provide initialization vector as hex-encoded text (32 hex-digits):\n> ");
				String ivText = sc.nextLine().trim();
				
				boolean encrypt = args[0].equalsIgnoreCase("encrypt");
				
				SecretKeySpec keySpec = new SecretKeySpec(Util.hexToByte(keyText), "AES");
				AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hexToByte(ivText));
				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
				
				String outputFileName = args[2];
				crypt(fileName, outputFileName, cipher);
				
				if(encrypt) {
					System.out.print("Encryption completed. ");
				} else {
					System.out.print("Decryption completed. ");
				}
				System.out.print("Generated file " + args[2] + " based on file " + args[1] + ".\n");
				
				break;
			default:
				throw new IllegalArgumentException("Unknown command.");
		}
	}
	
	/**
	 * Calculates a digest for the provided file.
	 * 
	 * @param fileName name of the file for which the digest needs to be calculated.
	 * @return digest of the provided file.
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static String digest(String fileName) throws NoSuchAlgorithmException, IOException {
		MessageDigest sha = MessageDigest.getInstance("SHA-256");
		InputStream is = Files.newInputStream(Path.of(fileName));
		byte[] buff = new byte[4096];
		
		while(true) {
			int r = is.read(buff);
			if(r == -1) {
				break;
			}
			
			sha.update(buff, 0, r);
		}
		is.close();
		
		byte[] checkSum = sha.digest();
		return Util.byteToHex(checkSum);
	}
	
	/**
	 * Encrypts or decrypts the provided file and creates a new file.
	 * 
	 * @param inputFileName name of the provided file.
	 * @param outputFileName name of the file that the file is to be encrypted/decrypted to.
	 * @param cipher
	 * @throws IOException 
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static void crypt(String inputFileName, String outputFileName, Cipher cipher) throws IOException, IllegalBlockSizeException, BadPaddingException {
		InputStream is = new BufferedInputStream(Files.newInputStream(Path.of(inputFileName)));
		OutputStream os = new BufferedOutputStream(Files.newOutputStream(Path.of(outputFileName)));
		byte[] buff = new byte[4096];
		
		while(true) {
			int r = is.read(buff);
			if(r == -1) {
				os.write(cipher.doFinal());
				break;
			}
			
			os.write(cipher.update(buff, 0, r));
		}
		os.flush();
		os.close();
	}
	
}
