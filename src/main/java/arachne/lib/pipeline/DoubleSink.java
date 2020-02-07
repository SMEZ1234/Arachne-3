package arachne.lib.pipeline;

import arachne.lib.io.GettableDouble;

public class DoubleSink extends AbstractDoubleValve implements GettableDouble
{
	protected double value;
	
	@Override
	public double get() {
		return value;
	}

	@Override
	protected void acceptValveValue(double value) {
		this.value = value;
	}
}
