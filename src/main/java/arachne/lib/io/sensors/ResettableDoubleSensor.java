package arachne.lib.io.sensors;

import arachne.lib.io.GettableDouble;

public interface ResettableDoubleSensor extends GettableDouble, Resettable
{
	public static ResettableDoubleSensor create(GettableDouble getter, Resettable resetter) {
		return new ResettableDoubleSensor() {
			@Override
			public void reset() {
				resetter.reset();
			}
			
			@Override
			public double get() {
				return getter.get();
			}
		};
	}
}
