package arachne.lib.pipeline;

import arachne.lib.function.BooleanPredicate;
import arachne.lib.io.SettableBoolean;

public interface BooleanPipe extends BooleanSource, SettableBoolean
{
	void setModifier(BooleanPredicate modifier);
	void clearModifier();
	
	boolean addFilter(BooleanPredicate predicate);
	boolean removeFilter(BooleanPredicate predicate);
	void clearFilters();
	
	void enableFilteredOutput(boolean defaultValue);
	void disableFilteredOutput();
	
	default void setFilter(BooleanPredicate predicate) {
		clearFilters();
		addFilter(predicate);
	}
}
