package arachne.lib.io.sensors;

import arachne.lib.io.GettableDouble;
import arachne.lib.io.SettableDouble;

public class SimpleDoubleSensor implements DoubleSensor
{
	protected GettableDouble getter;
	protected SettableDouble setter;
	protected Resettable resetter;
	
	public SimpleDoubleSensor(GettableDouble getter, SettableDouble setter) {
		this(getter, setter, () -> setter.accept(0));
	}
	
	public SimpleDoubleSensor(GettableDouble getter, SettableDouble setter, Resettable resetter) {
		this.getter = getter;
		this.setter = setter;
		this.resetter = resetter;
	}

	@Override
	public double get() {
		return getter.get();
	}

	@Override
	public void accept(double value) {
		setter.accept(value);
	}

	@Override
	public void reset() {
		resetter.reset();
	}
}
