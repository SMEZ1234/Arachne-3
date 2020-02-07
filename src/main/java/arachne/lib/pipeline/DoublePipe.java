package arachne.lib.pipeline;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.DoublePredicate;
import java.util.function.DoubleUnaryOperator;

import arachne.lib.io.SettableDouble;
import edu.wpi.first.wpilibj.SpeedController;

public class DoublePipe extends AbstractDoubleValve implements DoubleSource
{
	protected Set<SettableDouble> outputs;
	protected double value;
	
	public DoublePipe() {
		super();
		
		this.outputs = new LinkedHashSet<SettableDouble>();
	}

	@Override
	public <SettableT extends SettableDouble> SettableT attachOutput(SettableT settable) {
		outputs.add(settable);
		return settable;
	}

	@Override
	public boolean detachOutput(SettableDouble settable) {
		return outputs.remove(settable);
	}

	@Override
	public void detachAllOutputs() {
		outputs.clear();
	}

	@Override
	public void feedOutputs() {
		for(SettableDouble output : outputs) output.accept(value);
	}

	@Override
	protected void acceptValveValue(double value) {
		this.value = value;
		feedOutputs();
	}
	
	@Override
	public DoublePipe withModifier(DoubleUnaryOperator modifier, DoubleUnaryOperator... additionalModifiers) {
		setModifier(modifier, additionalModifiers);
		return this;
	}
	
	@Override
	public DoublePipe withFilter(DoublePredicate predicate) {
		setFilter(predicate);
		return this;
	}
	
	public SpeedController asSpeedController() {
		return new SpeedController() {
			private boolean isInverted = false;
			
			@Override
			public double get() {
				return DoublePipe.this.value;
			}
			
			@Override
			public void set(double speed) {
				DoublePipe.this.accept(isInverted ? -speed : speed);
			}
			
			@Override
			public void pidWrite(double output) {
				DoublePipe.this.accept(output);
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
				DoublePipe.this.accept(0);
			}
			
			@Override
			public void stopMotor() {
				disable();
			}
		};
	}
}
