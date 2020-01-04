package arachne.lib.listeners;

import arachne.lib.io.SettableDouble;
import edu.wpi.first.wpilibj.SpeedController;

public abstract class DoubleProperty extends ReadOnlyDoubleProperty implements SettableDouble
{
	@Override
	public void accept(double value) {
		fireChange(get(), value);
		_accept(value);
	}
	
	protected abstract void _accept(double value);
	
	public SpeedController asSpeedController() {
		return new SpeedController() {
			private boolean isInverted = false;
			
			@Override
			public double get() {
				return DoubleProperty.this.get();
			}
			
			@Override
			public void set(double speed) {
				DoubleProperty.this.accept(isInverted ? -speed : speed);
			}
			
			@Override
			public void pidWrite(double output) {
				DoubleProperty.this.pidWrite(output);
			}
			
			@Override
			public boolean getInverted() {
				return isInverted;
			}
			
			@Override
			public void setInverted(boolean isInverted) {
				this.isInverted = isInverted;
			}
			
			@Override
			public void disable() {
				DoubleProperty.this.accept(0);
			}
			
			@Override
			public void stopMotor() {
				disable();
			}
		};
	}
}
