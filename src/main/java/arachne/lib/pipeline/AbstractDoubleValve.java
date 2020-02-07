package arachne.lib.pipeline;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.DoublePredicate;
import java.util.function.DoubleUnaryOperator;

public abstract class AbstractDoubleValve implements DoubleValve
{
	protected DoubleUnaryOperator modifier;
	protected Set<DoublePredicate> filters;
	
	protected boolean hasDefaultValue;
	protected double defaultValue;
	
	public AbstractDoubleValve() {
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
		
		if(passesFilters || hasDefaultValue) {
			value = modifier != null ? modifier.applyAsDouble(value) : value;
			acceptValveValue(passesFilters ? value : defaultValue);
		}
	}
	
	protected abstract void acceptValveValue(double value);

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
}
