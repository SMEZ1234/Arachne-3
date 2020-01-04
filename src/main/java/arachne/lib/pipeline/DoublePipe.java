package arachne.lib.pipeline;

import java.util.function.DoublePredicate;
import java.util.function.DoubleUnaryOperator;

import arachne.lib.io.SettableDouble;

public interface DoublePipe extends DoubleSource, SettableDouble
{
	void setModifier(DoubleUnaryOperator modifier);
	void clearModifier();
	
	boolean addFilter(DoublePredicate predicate);
	boolean removeFilter(DoublePredicate predicate);
	void clearFilters();
	
	void enableFilteredOutput(double defaultValue);
	void disableFilteredOutput();
	
	default void setFilter(DoublePredicate predicate) {
		clearFilters();
		addFilter(predicate);
	}
}
