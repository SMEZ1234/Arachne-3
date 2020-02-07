package arachne.lib.io.sensors;

import arachne.lib.io.GettableBoolean;
import arachne.lib.io.SettableBoolean;
import arachne.lib.logging.ArachneLogger;

public interface BooleanSensor extends GettableBoolean, SettableBoolean, Resettable
{
	public static final class Wrapper implements BooleanSensor {
		protected BooleanSensor sensor;

		public Wrapper wrap(BooleanSensor sensor) {
			this.sensor = sensor;
			return this;
		}
		
		@Override
		public boolean get() {
			if(sensor != null) return sensor.get();
			
			ArachneLogger.getInstance().error("Tried to get BooleanSensor wrapper with no contained sensor, returning 0");
			return false;
		}

		@Override
		public void accept(boolean value) {
			if(sensor != null) sensor.accept(value);
			else ArachneLogger.getInstance().error("Tried to set BooleanSensor wrapper with no contained sensor");
		}

		@Override
		public void reset() {
			if(sensor != null) sensor.reset();
			else ArachneLogger.getInstance().error("Tried to reset BooleanSensor wrapper with no contained sensor");
		}
	}
}
