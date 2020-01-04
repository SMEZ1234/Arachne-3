package arachne.lib.pipeline;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class SimplePipe<T> extends AbstractSource<T> implements Pipe<T>
{
	protected UnaryOperator<T> modifier;
	protected Set<Predicate<T>> filters;
	
	protected T value;
	
	protected boolean hasDefaultValue;
	protected T defaultValue;
	
	public SimplePipe() {
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
		
		this.value = passesFilters ? value : defaultValue;
		
		if(passesFilters || hasDefaultValue) feedOutputs();
	}

	@Override
	protected T getOutputValue() {
		return modifier != null ? modifier.apply(value) : value;
	}

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
