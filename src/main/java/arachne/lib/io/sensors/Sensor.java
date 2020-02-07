package arachne.lib.io.sensors;

import arachne.lib.io.Gettable;
import arachne.lib.io.Settable;
import arachne.lib.logging.ArachneLogger;

public interface Sensor<T> extends Gettable<T>, Settable<T>, Resettable
{
	public static final class Wrapper<T> implements Sensor<T> {
		protected Sensor<T> sensor;

		public Wrapper<T> wrap(Sensor<T> sensor) {
			this.sensor = sensor;
			return this;
		}
		
		@Override
		public T get() {
			if(sensor != null) return sensor.get();
			
			ArachneLogger.getInstance().error("Tried to set Sensor wrapper with no contained sensor");
			return null;
		}

		@Override
		public void accept(T value) {
			if(sensor != null) sensor.accept(value);
			else ArachneLogger.getInstance().error("Tried to set Sensor wrapper with no contained sensor");
		}

		@Override
		public void reset() {
			if(sensor != null) sensor.reset();
			else ArachneLogger.getInstance().error("Tried to reset Sensor wrapper with no contained sensor");
		}
	}
}
