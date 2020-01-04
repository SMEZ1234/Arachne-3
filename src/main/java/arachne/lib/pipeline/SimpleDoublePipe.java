package arachne.lib.pipeline;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.DoublePredicate;
import java.util.function.DoubleUnaryOperator;

import edu.wpi.first.wpilibj.SpeedController;

public class SimpleDoublePipe extends AbstractDoubleSource implements DoublePipe
{
	protected DoubleUnaryOperator modifier;
	protected Set<DoublePredicate> filters;

	protected double value;
	
	protected boolean hasDefaultValue;
	protected double defaultValue;
	
	public SimpleDoublePipe() {
		super();
		
		this.filters = new LinkedHashSet<DoublePredicate>();		
		this.hasDefaultValue = false;
	}
	
	@Override
	public void accept(double value) {
		boolean passesFilters = true;
		
		for(DoublePredicate filter : filters) {
			if(!filter.test(value)) {
				passesFilters = false;
				break;
			}
		}
		
		this.value = passesFilters ? value : defaultValue;
		
		if(passesFilters || hasDefaultValue) feedOutputs();
	}

	@Override
	protected double getOutputValue() {
		return modifier != null ? modifier.applyAsDouble(value) : value;
	}

	@Override
	public void setModifier(DoubleUnaryOperator modifier) {
		this.modifier = modifier;
	}

	@Override
	public void clearModifier() {
		this.modifier = null;
	}

	@Override
	public boolean addFilter(DoublePredicate predicate) {
		return filters.add(predicate);
	}

	@Override
	public boolean removeFilter(DoublePredicate predicate) {
		return filters.remove(predicate);
	}

	@Override
	public void clearFilters() {
		filters.clear();
	}

	@Override
	public void enableFilteredOutput(double defaultValue) {
		this.hasDefaultValue = true;
		this.defaultValue = defaultValue;
	}

	@Override
	public void disableFilteredOutput() {
		this.hasDefaultValue = false;
	}
	
	public SpeedController asSpeedController() {
		return new SpeedController() {
			private boolean isInverted = false;
			
			@Override
			public double get() {
				return value;
			}
			
			@Override
			public void set(double speed) {
				SimpleDoublePipe.this.accept(isInverted ? -speed : speed);
			}
			
			@Override
			public void pidWrite(double output) {
				SimpleDoublePipe.this.pidWrite(output);
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
				SimpleDoublePipe.this.accept(0);
			}
			
			@Override
			public void stopMotor() {
				disable();
			}
		};
	}
}
