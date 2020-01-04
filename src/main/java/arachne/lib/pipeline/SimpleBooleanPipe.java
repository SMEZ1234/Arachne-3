package arachne.lib.pipeline;

import java.util.LinkedHashSet;
import java.util.Set;

import arachne.lib.function.BooleanPredicate;

public class SimpleBooleanPipe extends AbstractBooleanSource implements BooleanPipe
{
	protected BooleanPredicate modifier;
	protected Set<BooleanPredicate> filters;

	protected boolean value;
	
	protected boolean hasDefaultValue;
	protected boolean defaultValue;
	
	public SimpleBooleanPipe() {
		super();
		
		this.filters = new LinkedHashSet<BooleanPredicate>();		
		this.hasDefaultValue = false;
	}
	
	@Override
	public void accept(boolean value) {
		boolean passesFilters = true;
		
		for(BooleanPredicate filter : filters) {
			if(!filter.test(value)) {
				passesFilters = false;
				break;
			}
		}
		
		this.value = passesFilters ? value : defaultValue;
		
		if(passesFilters || hasDefaultValue) feedOutputs();
	}

	@Override
	protected boolean getOutputValue() {
		return modifier != null ? modifier.test(value) : value;
	}

	@Override
	public void setModifier(BooleanPredicate modifier) {
		this.modifier = modifier;
	}

	@Override
	public void clearModifier() {
		this.modifier = null;
	}

	@Override
	public boolean addFilter(BooleanPredicate predicate) {
		return filters.add(predicate);
	}

	@Override
	public boolean removeFilter(BooleanPredicate predicate) {
		return filters.remove(predicate);
	}

	@Override
	public void clearFilters() {
		filters.clear();
	}

	@Override
	public void enableFilteredOutput(boolean defaultValue) {
		this.hasDefaultValue = true;
		this.defaultValue = defaultValue;
	}

	@Override
	public void disableFilteredOutput() {
		this.hasDefaultValue = false;
	}
}
