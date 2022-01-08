package arachne.lib.io.sensors;

import arachne.lib.io.GettableDouble;

public class VirtuallySettableDoubleSensor implements SettableDoubleSensor
{
	protected final GettableDouble getter;

	protected final boolean bounded;
	protected final double lowerBound, upperBound;

	protected double offset = 0;

	public VirtuallySettableDoubleSensor(GettableDouble getter) {
		this(getter, false, Double.NaN, Double.NaN);
	}

	public VirtuallySettableDoubleSensor(GettableDouble getter, double lowerBound, double upperBound) {
		this(getter, true, lowerBound, upperBound);
	}

	private VirtuallySettableDoubleSensor(GettableDouble getter, boolean bounded, double lowerBound, double upperBound) {
		if(bounded && lowerBound >= upperBound) {
			throw new IllegalArgumentException("Lower bound (" + lowerBound + ") must be less than upper bound (" + upperBound + ")");
		}

		this.getter = getter;

		this.bounded = bounded;
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}

	@Override
	public double get() {
		double value = getter.get() + offset;

		if(bounded) {
			while(value < lowerBound) value += upperBound - lowerBound;
			while(value >= upperBound) value -= upperBound - lowerBound;
		}

		return value;
	}

	@Override
	public void accept(double value) {
		offset = value - getter.get();
	}

	@Override
	public void reset() {
		accept(0);
	}
}
