package arachne.lib.pipeline;

import arachne.lib.function.BooleanPredicate;
import arachne.lib.io.SettableBoolean;

public interface BooleanValve extends SettableBoolean
{
	void setModifier(BooleanPredicate modifier);
	void clearModifier();
	
	default void setModifier(BooleanPredicate modifier, BooleanPredicate... additionalModifiers) {
		for(BooleanPredicate additionalModifier : additionalModifiers) modifier = modifier.andThen(additionalModifier);
		setModifier(modifier);
	}
	
	default BooleanValve withModifier(BooleanPredicate modifier, BooleanPredicate... additionalModifiers) {
		setModifier(modifier, additionalModifiers);
		return this;
	}
	
	boolean addFilter(BooleanPredicate predicate);
	boolean removeFilter(BooleanPredicate predicate);
	void clearFilters();
	
	void enableFilteredOutput(boolean defaultValue);
	void disableFilteredOutput();
	
	default void setFilter(BooleanPredicate predicate) {
		clearFilters();
		addFilter(predicate);
	}
	
	default BooleanValve withFilter(BooleanPredicate predicate) {
		setFilter(predicate);
		return this;
	}
}
