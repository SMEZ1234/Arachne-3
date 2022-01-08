package arachne.test.dashboard;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arachne.lib.dashboard.Dashboard;
import arachne.lib.dashboard.LocalDashboard;

public class TestLocalDashboard
{
	Dashboard dashboard;
	
	@BeforeEach
	void beforeEach() {
		dashboard = new LocalDashboard();
	}
	
	@Test
	void storeAndRetrieveBoolean() {
		// Store
		assertFalse(dashboard.containsKey(""));
		dashboard.putBoolean("", true);
		assertTrue(dashboard.containsKey(""));
		
		// Retrieve
		assertTrue(dashboard.getBoolean("", false));
	}
	
	@Test
	void storeAndRetrieveNumber() {
		// Store
		assertFalse(dashboard.containsKey(""));
		dashboard.putNumber("", 4613);
		assertTrue(dashboard.containsKey(""));
		
		// Retrieve
		assertEquals(dashboard.getNumber("", 0), 4613);
	}
	
	@Test
	void storeAndRetrieveString() {
		// Store
		assertFalse(dashboard.containsKey(""));
		dashboard.putString("", "Arachne");
		assertTrue(dashboard.containsKey(""));
		
		// Retrieve
		assertEquals(dashboard.getString("", ""), "Arachne");
	}
	
	@Test
	void storeAndRetrievePrimitiveBooleanArray() {
		boolean[] array = {true, false, false, true};
		
		// Store
		assertFalse(dashboard.containsKey(""));
		dashboard.putBooleanArray("", array);
		assertTrue(dashboard.containsKey(""));
		
		// Retrieve primitive
		boolean[] privitive = dashboard.getBooleanArray("", new boolean[0]);
		for(int i = 0; i < array.length; i++) assertEquals(privitive[i], array[i]);
		
		// Retrieve complex
		Boolean[] complex = dashboard.getBooleanArray("", new Boolean[0]);
		for(int i = 0; i < array.length; i++) assertEquals(complex[i], array[i]);
	}
	
	@Test
	void storeAndRetrieveComplexBooleanArray() {
		Boolean[] array = {true, false, false, true};
		
		// Store
		assertFalse(dashboard.containsKey(""));
		dashboard.putBooleanArray("", array);
		assertTrue(dashboard.containsKey(""));
		
		// Retrieve primitive
		boolean[] privitive = dashboard.getBooleanArray("", new boolean[0]);
		for(int i = 0; i < array.length; i++) assertEquals(privitive[i], array[i]);
		
		// Retrieve complex
		Boolean[] complex = dashboard.getBooleanArray("", new Boolean[0]);
		for(int i = 0; i < array.length; i++) assertEquals(complex[i], array[i]);
	}
	
	@Test
	void storeAndRetrievePrimitiveNumberArray() {
		double[] array = {3.141, -2.718, 1.618, 4613, Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, 0};
		
		// Store
		assertFalse(dashboard.containsKey(""));
		dashboard.putNumberArray("", array);
		assertTrue(dashboard.containsKey(""));
		
		// Retrieve primitive
		double[] privitive = dashboard.getNumberArray("", new double[0]);
		for(int i = 0; i < array.length; i++) assertEquals(privitive[i], array[i]);
		
		// Retrieve complex
		Double[] complex = dashboard.getNumberArray("", new Double[0]);
		for(int i = 0; i < array.length; i++) assertEquals(complex[i], array[i]);
	}
	
	@Test
	void storeAndRetrieveComplexNumberArray() {
		Double[] array = {3.141, -2.718, 1.618, 4613D, Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, 0D};
		
		// Store
		assertFalse(dashboard.containsKey(""));
		dashboard.putNumberArray("", array);
		assertTrue(dashboard.containsKey(""));
		
		// Retrieve primitive
		double[] privitive = dashboard.getNumberArray("", new double[0]);
		for(int i = 0; i < array.length; i++) assertEquals(privitive[i], array[i]);
		
		// Retrieve complex
		Double[] complex = dashboard.getNumberArray("", new Double[0]);
		for(int i = 0; i < array.length; i++) assertEquals(complex[i], array[i]);
	}
	
	@Test
	void storeAndRetrieveStringArray() {
		String[] array = {"", "Hello", "\u03a9\t\b\n"};
		
		// Store
		assertFalse(dashboard.containsKey(""));
		dashboard.putStringArray("", array);
		assertTrue(dashboard.containsKey(""));
		
		// Retrieve
		String[] retrieved = dashboard.getStringArray("", new String[0]);
		for(int i = 0; i < array.length; i++) assertEquals(retrieved[i], array[i]);
	}
	
	@Test
	void storeAndRetrieveRawFromArray() {
		byte[] array = {(byte) 0xFF, (byte) 0x0, (byte) 0x80};
		
		// Store
		assertFalse(dashboard.containsKey(""));
		dashboard.putRaw("", array);
		assertTrue(dashboard.containsKey(""));
		
		// Retrieve
		byte[] retrieved = dashboard.getRaw("", new byte[0]);
		for(int i = 0; i < array.length; i++) assertEquals(retrieved[i], array[i]);
	}
	
	@Test
	void storeAndRetrieveRawFromBuffer() {
		// TODO
	}
	
	// TODO tests for flags
	// TODO tests for updating and overriding
}
