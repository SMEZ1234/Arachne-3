package arachne.test.logic;

import org.junit.jupiter.api.Test;

import arachne.lib.listeners.DoubleProperty;
import arachne.lib.listeners.SimpleDoubleProperty;
import arachne.lib.logic.LinearMap;
import arachne.lib.pipeline.DoublePipe;
import arachne.lib.pipeline.SimpleDoublePipe;

import static arachne.lib.logic.DoubleComparison.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class LinearMapTest
{
	DoublePipe input;
	DoubleProperty output;
	
	@BeforeEach
	void beforeEach() {
		input = new SimpleDoublePipe();
		output = new SimpleDoubleProperty();
	}
	
	@AfterEach
	void afterEach() {
		input.detachAllOutputs();
		
		input = null;
		output = null;
	}
	
	@Test
	void noBounds() {
		input.setModifier(LinearMap.map(-100, 100).to(-5, 3));
		input.attachOutput(output);
		
		// Regular inputs
		input.accept(-100);
		assertTrue(output.is(-5));
		
		input.accept(0);
		assertTrue(output.is(-1));
		
		input.accept(100);
		assertTrue(output.is(3));
		
		// Out of bounds inputs
		input.accept(-500);
		assertTrue(output.is(-21));
		
		input.accept(200);
		assertTrue(output.is(7));
	}
	
	@Test
	void noBoundsInverse() {
		input.setModifier(LinearMap.map(100, 200).to(10, 5));
		input.attachOutput(output);
		
		// Regular inputs
		input.accept(100);
		assertTrue(output.is(10));
		
		input.accept(150);
		assertTrue(output.is(7.5));
		
		input.accept(200);
		assertTrue(output.is(5));
		
		// Out of bounds inputs
		input.accept(-500);
		assertTrue(output.is(40));
		
		input.accept(300);
		assertTrue(output.is(0));
	}
	
//	@Test
//	void stopOnMin() {
//		input
//		.attachTranslator(LinearMap.map(-1024, 1024).to(-1, 1).until(LinearMap.REACHES_MIN))
//		.attachOutput(new PredicatedDoublePipe())
//		.attachOutput(output);
//
//		input.accept(512);
//		assertTrue(output.is(0.5));
//		
//		input.accept(0);
//		assertTrue(output.is(0));
//		
//		input.accept(-1023);
//		assertTrue(output.is(greaterThan(-1)));
//		
//		input.accept(-1024);
//		assertTrue(output.is(-1));
//		
//		input.accept(256);
//		assertTrue(output.is(-1));
//	}
}
