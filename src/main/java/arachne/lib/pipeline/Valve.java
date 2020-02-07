package arachne.lib.pipeline;

import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import arachne.lib.io.Settable;

public interface Valve<T> extends Settable<T>
{
	void setModifier(UnaryOperator<T> modifier);
	void clearModifier();
	
	default void setModifier(UnaryOperator<T>[] modifiers) {
		final UnaryOperator<T>[] modifierChain = modifiers.clone();
		
		setModifier((value) -> {
			for(UnaryOperator<T> modifier : modifierChain) value = modifier.apply(value);
			return value;
		});
	}
	
	default Valve<T> withModifier(UnaryOperator<T> modifier) {
		setModifier(modifier);
		return this;
	}
	
	default Valve<T> withModifier(UnaryOperator<T>[] modifiers) {
		setModifier(modifiers);
		return this;
	}
	
	boolean addFilter(Predicate<T> predicate);
	boolean removeFilter(Predicate<T> predicate);
	void clearFilters();
	
	void enableFilteredOutput(T defaultValue);
	void disableFilteredOutput();
	
	default void setFilter(Predicate<T> predicate) {
		clearFilters();
		addFilter(predicate);
	}
	
	default Valve<T> withFilter(Predicate<T> predicate) {
		setFilter(predicate);
		return this;
	}
}
