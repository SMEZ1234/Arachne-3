package arachne.lib.io.sensors;

import arachne.lib.io.GettableDouble;
import arachne.lib.io.SettableDouble;
import arachne.lib.logging.ArachneLogger;

public interface DoubleSensor extends GettableDouble, SettableDouble, Resettable
{
	public static final class Wrapper implements DoubleSensor {
		protected DoubleSensor sensor;

		public Wrapper wrap(DoubleSensor sensor) {
			this.sensor = sensor;
			return this;
		}
		
		@Override
		public double get() {
			if(sensor != null) return sensor.get();
			
			ArachneLogger.getInstance().error("Tried to get DoubleSensor wrapper with no contained sensor, returning 0");
			return 0;
		}

		@Override
		public void accept(double value) {
			if(sensor != null) sensor.accept(value);
			else ArachneLogger.getInstance().error("Tried to set DoubleSensor wrapper with no contained sensor");
		}

		@Override
		public void reset() {
			if(sensor != null) sensor.reset();
			else ArachneLogger.getInstance().error("Tried to reset DoubleSensor wrapper with no contained sensor");
		}
	}
}
