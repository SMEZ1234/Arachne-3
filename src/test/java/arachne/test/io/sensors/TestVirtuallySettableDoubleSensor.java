package arachne.test.io.sensors;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arachne.lib.io.sensors.VirtuallySettableDoubleSensor;
import arachne.lib.pipeline.DoublePipe;

public class TestVirtuallySettableDoubleSensor
{
	static final double DELTA = 1e-6;

	VirtuallySettableDoubleSensor sensor;
	DoublePipe valueIn;

	@BeforeEach
	void beforeEach() {
		valueIn = new DoublePipe(0);
	}

	@Test
	void access() {
		sensor = new VirtuallySettableDoubleSensor(valueIn);
		assertEquals(0, sensor.get(), DELTA);

		valueIn.accept(4613);
		assertEquals(4613, sensor.get(), DELTA);

		valueIn.accept(-3.14);
		assertEquals(-3.14, sensor.get(), DELTA);
	}

	@Test
	void mutate() {
		sensor = new VirtuallySettableDoubleSensor(valueIn);

		sensor.accept(4613);
		assertEquals(4613, sensor.get(), DELTA);

		valueIn.accept(-11148);
		assertEquals(4613 - 11148, sensor.get(), DELTA);

		sensor.accept(-3.14);
		assertEquals(-3.14, sensor.get(), DELTA);
	}

	@Test
	void accessWithNonNegativeBounds() {
		sensor = new VirtuallySettableDoubleSensor(valueIn, 0, 360);
		assertEquals(0, sensor.get(), DELTA);

		// In bounds
		valueIn.accept(200);
		assertEquals(200, sensor.get(), DELTA);

		// Slightly above bounds
		valueIn.accept(500);
		assertEquals(500 - 360, sensor.get(), DELTA);

		// Significantly above bounds
		valueIn.accept(4613);
		assertEquals(293, sensor.get(), DELTA);

		// Below bounds
		valueIn.accept(-3.14);
		assertEquals(360 - 3.14, sensor.get(), DELTA);

		// Significantly below bounds
		valueIn.accept(-11148);
		assertEquals(12, sensor.get(), DELTA);
	}

	@Test
	void accessWithAnyBounds() {
		sensor = new VirtuallySettableDoubleSensor(valueIn, -180, 180);
		assertEquals(0, sensor.get(), DELTA);

		// In bounds
		valueIn.accept(-3.14);
		assertEquals(-3.14, sensor.get(), DELTA);

		// Slightly above bounds
		valueIn.accept(200);
		assertEquals(200 - 360, sensor.get(), DELTA);

		// Significantly above bounds
		valueIn.accept(4613);
		assertEquals(-67, sensor.get(), DELTA);

		// Below bounds
		valueIn.accept(-314.15);
		assertEquals(360 - 314.15, sensor.get(), DELTA);

		// Significantly below bounds
		valueIn.accept(-11148);
		assertEquals(12, sensor.get(), DELTA);
	}

	@Test
	void mutateWithBounds() {
		sensor = new VirtuallySettableDoubleSensor(valueIn, -180, 180);

		sensor.accept(4613);
		assertEquals(-67, sensor.get(), DELTA);

		valueIn.accept(-11148);
		assertEquals(-55, sensor.get(), DELTA);

		sensor.accept(-3.14);
		assertEquals(-3.14, sensor.get(), DELTA);
	}

	@Test
	void lowerBoundLessThanUpperBound() {
		assertThrows(IllegalArgumentException.class, () -> new VirtuallySettableDoubleSensor(valueIn, 4613, 0));
		assertThrows(IllegalArgumentException.class, () -> new VirtuallySettableDoubleSensor(valueIn, 4613, 4613));
	}
}
