package arachne.lib.listeners;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.DoublePredicate;
import java.util.function.DoubleUnaryOperator;

import arachne.lib.pipeline.AbstractDoubleValve;
import edu.wpi.first.wpilibj.SpeedController;

public class DoubleProperty extends AbstractDoubleValve implements ReadOnlyDoubleProperty
{
	protected final Set<DoubleChangeHandler> changeHandlers;
	protected double value;
	
	public DoubleProperty() {
		this(0);
	}
	
	public DoubleProperty(double initialValue) {		
		this.changeHandlers = new LinkedHashSet<DoubleChangeHandler>();
		this.value = initialValue;
	}

	@Override
	public double get() {
		return value;
	}
	
	@Override
	public boolean attach(DoubleChangeHandler changeHandler) {
		return changeHandlers.add(changeHandler);
	}

	@Override
	public boolean detach(DoubleChangeHandler changeHandler) {
		return changeHandlers.remove(changeHandler);
	}

	@Override
	public void detachAll() {
		changeHandlers.clear();
	}
	
	@Override
	protected void acceptValveValue(double value) {
		fireChange(this.value, value);
		this.value = value;
	}
	
	protected void fireChange(double oldValue, double newValue) {
		for(DoubleChangeHandler handler : changeHandlers) handler.onChange(oldValue, newValue);
	}
	
	@Override
	public DoubleProperty withModifier(DoubleUnaryOperator modifier, DoubleUnaryOperator... additionalModifiers) {
		setModifier(modifier, additionalModifiers);
		return this;
	}
	
	@Override
	public DoubleProperty withFilter(DoublePredicate predicate) {
		setFilter(predicate);
		return this;
	}
	
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
				DoubleProperty.this.accept(output);
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
