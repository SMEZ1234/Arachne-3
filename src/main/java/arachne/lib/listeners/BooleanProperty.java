package arachne.lib.listeners;

import java.util.LinkedHashSet;
import java.util.Set;

import arachne.lib.function.BooleanPredicate;
import arachne.lib.pipeline.AbstractBooleanValve;

public class BooleanProperty extends AbstractBooleanValve implements ReadOnlyBooleanProperty
{
	protected final Set<BooleanChangeHandler> changeHandlers;
	protected boolean value;
	
	public BooleanProperty() {
		this(false);
	}
	
	public BooleanProperty(boolean initialValue) {		
		this.changeHandlers = new LinkedHashSet<BooleanChangeHandler>();
		this.value = initialValue;
	}

	@Override
	public boolean get() {
		return value;
	}
	
	@Override
	public boolean attach(BooleanChangeHandler changeHandler) {
		return changeHandlers.add(changeHandler);
	}

	@Override
	public boolean detach(BooleanChangeHandler changeHandler) {
		return changeHandlers.remove(changeHandler);
	}

	@Override
	public void detachAll() {
		changeHandlers.clear();
	}
	
	@Override
	protected void acceptValveValue(boolean value) {
		fireChange(this.value, value);
		this.value = value;
	}
	
	protected void fireChange(boolean oldValue, boolean newValue) {
		for(BooleanChangeHandler handler : changeHandlers) handler.onChange(oldValue, newValue);
	}
	
	@Override
	public BooleanProperty withModifier(BooleanPredicate modifier, BooleanPredicate... additionalModifiers) {
		setModifier(modifier, additionalModifiers);
		return this;
	}
	
	@Override
	public BooleanProperty withFilter(BooleanPredicate predicate) {
		setFilter(predicate);
		return this;
	}
}
