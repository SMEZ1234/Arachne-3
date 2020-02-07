package arachne.lib.pipeline;

import java.util.function.DoublePredicate;
import java.util.function.DoubleUnaryOperator;

import arachne.lib.io.SettableDouble;

public interface DoubleValve extends SettableDouble
{
	void setModifier(DoubleUnaryOperator modifier);
	void clearModifier();
	
	default void setModifier(DoubleUnaryOperator modifier, DoubleUnaryOperator... additionalModifiers) {
		for(DoubleUnaryOperator additionalModifier : additionalModifiers) modifier = modifier.andThen(additionalModifier);
		setModifier(modifier);
	}
	
	default DoubleValve withModifier(DoubleUnaryOperator modifier, DoubleUnaryOperator... additionalModifiers) {
		setModifier(modifier, additionalModifiers);
		return this;
	}
	
	boolean addFilter(DoublePredicate predicate);
	boolean removeFilter(DoublePredicate predicate);
	void clearFilters();
	
	void enableFilteredOutput(double defaultValue);
	void disableFilteredOutput();
	
	default void setFilter(DoublePredicate predicate) {
		clearFilters();
		addFilter(predicate);
	}
	
	default DoubleValve withFilter(DoublePredicate predicate) {
		setFilter(predicate);
		return this;
	}
}
