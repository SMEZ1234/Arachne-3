package arachne.lib.pipeline;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public abstract class AbstractValve<T> implements Valve<T>
{
	protected UnaryOperator<T> modifier;
	protected Set<Predicate<T>> filters;
	
	protected boolean hasDefaultValue;
	protected T defaultValue;
	
	public AbstractValve() {
		super();
		
		this.filters = new LinkedHashSet<Predicate<T>>();		
		this.hasDefaultValue = false;
	}
	
	@Override
	public void accept(T value) {
		boolean passesFilters = true;
		
		for(Predicate<T> filter : filters) {
			if(!filter.test(value)) {
				passesFilters = false;
				break;
			}
		}
		
		if(passesFilters || hasDefaultValue) {
			value = modifier != null ? modifier.apply(value) : value;
			acceptValveValue(passesFilters ? value : defaultValue);
		}
	}
	
	protected abstract void acceptValveValue(T value);

	@Override
	public void setModifier(UnaryOperator<T> modifier) {
		this.modifier = modifier;
	}

	@Override
	public void clearModifier() {
		this.modifier = null;
	}

	@Override
	public boolean addFilter(Predicate<T> predicate) {
		return filters.add(predicate);
	}

	@Override
	public boolean removeFilter(Predicate<T> predicate) {
		return filters.remove(predicate);
	}

	@Override
	public void clearFilters() {
		filters.clear();
	}

	@Override
	public void enableFilteredOutput(T defaultValue) {
		this.hasDefaultValue = true;
		this.defaultValue = defaultValue;
	}

	@Override
	public void disableFilteredOutput() {
		this.hasDefaultValue = false;
	}
}
