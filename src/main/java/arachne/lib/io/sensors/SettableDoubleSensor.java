package arachne.lib.io.sensors;

import arachne.lib.io.GettableDouble;
import arachne.lib.io.SettableDouble;

public interface SettableDoubleSensor extends SettableDouble, ResettableDoubleSensor
{
	public static SettableDoubleSensor create(GettableDouble getter, SettableDouble setter) {
		return create(getter, setter, () -> setter.accept(0));
	}
	
	public static SettableDoubleSensor create(GettableDouble getter, SettableDouble setter, Resettable resetter) {
		return new SettableDoubleSensor() {
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
		};
	}
}
