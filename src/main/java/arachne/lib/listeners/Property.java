package arachne.lib.listeners;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import arachne.lib.pipeline.AbstractValve;

public class Property<T> extends AbstractValve<T> implements ReadOnlyProperty<T>
{
	protected final Set<ChangeHandler<T>> changeHandlers;
	protected T value;
	
	public Property() {
		this(null);
	}
	
	public Property(T initialValue) {		
		this.changeHandlers = new LinkedHashSet<ChangeHandler<T>>();
		this.value = initialValue;
	}

	@Override
	public T get() {
		return value;
	}
	
	@Override
	public boolean attach(ChangeHandler<T> changeHandler) {
		return changeHandlers.add(changeHandler);
	}

	@Override
	public boolean detach(ChangeHandler<T> changeHandler) {
		return changeHandlers.remove(changeHandler);
	}

	@Override
	public void detachAll() {
		changeHandlers.clear();
	}
	
	@Override
	protected void acceptValveValue(T value) {
		fireChange(this.value, value);
		this.value = value;
	}
	
	protected void fireChange(T oldValue, T newValue) {
		for(ChangeHandler<T> handler : changeHandlers) handler.onChange(oldValue, newValue);
	}
	
	@Override
	public Property<T> withModifier(UnaryOperator<T> modifier) {
		setModifier(modifier);
		return this;
	}
	
	@Override
	public Property<T> withModifier(UnaryOperator<T>[] modifiers) {
		setModifier(modifiers);
		return this;
	}
	
	@Override
	public Property<T> withFilter(Predicate<T> predicate) {
		setFilter(predicate);
		return this;
	}
}
