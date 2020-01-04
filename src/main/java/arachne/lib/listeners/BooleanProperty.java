package arachne.lib.listeners;

import arachne.lib.io.SettableBoolean;

public abstract class BooleanProperty extends ReadOnlyBooleanProperty implements SettableBoolean
{
	@Override
	public void accept(boolean value) {
		fireChange(get(), value);
		_accept(value);
	}
	
	protected abstract void _accept(boolean value);
}
