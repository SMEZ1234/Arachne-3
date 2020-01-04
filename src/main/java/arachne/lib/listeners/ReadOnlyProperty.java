package arachne.lib.listeners;

import java.util.LinkedHashSet;
import java.util.Set;

import arachne.lib.io.Gettable;

public abstract class ReadOnlyProperty<T> implements Gettable<T>, Hook<T>
{
	protected final Set<ChangeHandler<T>> changeHandlers;
	
	public ReadOnlyProperty() {
		this.changeHandlers = new LinkedHashSet<ChangeHandler<T>>();
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
	
	protected void fireChange(T oldValue, T newValue) {
		for(ChangeHandler<T> handler : changeHandlers) handler.onChange(oldValue, newValue);
	}
}
