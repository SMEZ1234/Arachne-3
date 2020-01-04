package arachne.lib.pipeline;

import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import arachne.lib.io.Settable;

public interface Pipe<T> extends Source<T>, Settable<T>
{
	void setModifier(UnaryOperator<T> modifier);
	void clearModifier();
	
	boolean addFilter(Predicate<T> predicate);
	boolean removeFilter(Predicate<T> predicate);
	void clearFilters();
	
	void enableFilteredOutput(T defaultValue);
	void disableFilteredOutput();
	
	default void setFilter(Predicate<T> predicate) {
		clearFilters();
		addFilter(predicate);
	}
}
