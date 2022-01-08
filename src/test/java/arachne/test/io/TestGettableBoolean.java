package arachne.test.io;

import static arachne.lib.io.GettableBoolean.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestGettableBoolean
{
	@Test
	void constants() {
		assertTrue(TRUE);
		assertFalse(FALSE);
	}
	
	@Test
	void and() {
		assertTrue(TRUE.and(TRUE));
		assertFalse(TRUE.and(FALSE));
		assertFalse(FALSE.and(TRUE));
		assertFalse(FALSE.and(FALSE));
	}
	
	@Test
	void or() {
		assertTrue(TRUE.or(TRUE));
		assertTrue(TRUE.or(FALSE));
		assertTrue(FALSE.or(TRUE));
		assertFalse(FALSE.or(FALSE));
	}
	
	@Test
	void xor() {
		assertFalse(TRUE.xor(TRUE));
		assertTrue(TRUE.xor(FALSE));
		assertTrue(FALSE.xor(TRUE));
		assertFalse(FALSE.xor(FALSE));
	}
	
	@Test
	void is() {
		assertTrue(TRUE.is(true));
		assertFalse(TRUE.is(false));
		assertFalse(FALSE.is(true));
		assertTrue(FALSE.is(false));
		
		assertTrue(TRUE.is((b) -> b));
		assertFalse(TRUE.is((b) -> !b));
		assertFalse(FALSE.is((b) -> b));
		assertTrue(FALSE.is((b) -> !b));
	}
}
