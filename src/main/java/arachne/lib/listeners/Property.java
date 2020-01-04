package arachne.lib.listeners;

import arachne.lib.io.Settable;

public abstract class Property<T> extends ReadOnlyProperty<T> implements Settable<T>
{
	@Override
	public void accept(T value) {
		fireChange(get(), value);
		_accept(value);
	}
	
	protected abstract void _accept(T value);
}
