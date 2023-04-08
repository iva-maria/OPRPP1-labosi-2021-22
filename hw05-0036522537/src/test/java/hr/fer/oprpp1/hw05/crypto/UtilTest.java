package hr.fer.oprpp1.hw05.crypto;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class UtilTest {
	
	@Test
	public void testHexToByteOddNumberThrows() {
		assertThrows(IllegalArgumentException.class, () -> Util.hexToByte("abc"));
	}
	
	@Test
	public void testHexToByteNullThrows() {
		assertThrows(NullPointerException.class, () -> Util.hexToByte(null));
	}
	
	@Test
	public void testHexToByte() {
		assertArrayEquals(new byte[] {1, -82, 34}, Util.hexToByte("01aE22"));
	}
	
	@Test
	public void testByteToHexZeroLengthArray() {
		assertEquals("", Util.byteToHex(new byte[0]));
	}
	
	@Test
	public void testbyteToHexNullThrows() {
		assertThrows(NullPointerException.class, () -> Util.byteToHex(null));
	}
	
}
