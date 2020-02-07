package arachne.lib.io.sensors;

import arachne.lib.io.Gettable;
import arachne.lib.io.Settable;

public class SimpleSensor<T> implements Sensor<T>
{
	protected Gettable<T> getter;
	protected Settable<T> setter;
	protected Resettable resetter;
	
	public SimpleSensor(Gettable<T> getter, Settable<T> setter) {
		this(getter, setter, () -> setter.accept(null));
	}
	
	public SimpleSensor(Gettable<T> getter, Settable<T> setter, Resettable resetter) {
		this.getter = getter;
		this.setter = setter;
		this.resetter = resetter;
	}

	@Override
	public T get() {
		return getter.get();
	}

	@Override
	public void accept(T value) {
		setter.accept(value);
	}

	@Override
	public void reset() {
		resetter.reset();
	}
}
