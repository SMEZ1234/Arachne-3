package arachne.lib.io.sensors;

import arachne.lib.io.GettableBoolean;
import arachne.lib.io.SettableBoolean;

public class SimpleBooleanSensor implements BooleanSensor
{
	protected GettableBoolean getter;
	protected SettableBoolean setter;
	protected Resettable resetter;
	
	public SimpleBooleanSensor(GettableBoolean getter, SettableBoolean setter) {
		this(getter, setter, () -> setter.accept(false));
	}
	
	public SimpleBooleanSensor(GettableBoolean getter, SettableBoolean setter, Resettable resetter) {
		this.getter = getter;
		this.setter = setter;
		this.resetter = resetter;
	}

	@Override
	public boolean get() {
		return getter.get();
	}

	@Override
	public void accept(boolean value) {
		setter.accept(value);
	}

	@Override
	public void reset() {
		resetter.reset();
	}
}
