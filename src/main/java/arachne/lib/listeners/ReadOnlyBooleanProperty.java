package arachne.lib.listeners;

import java.util.LinkedHashSet;
import java.util.Set;

import arachne.lib.io.GettableBoolean;

public abstract class ReadOnlyBooleanProperty implements GettableBoolean, BooleanHook
{
	protected final Set<BooleanChangeHandler> changeHandlers;
	
	public ReadOnlyBooleanProperty() {
		this.changeHandlers = new LinkedHashSet<BooleanChangeHandler>();
	}
	
	@Override
	public boolean attach(BooleanChangeHandler changeHandler) {
		return changeHandlers.add(changeHandler);
	}

	@Override
	public boolean detach(BooleanChangeHandler changeHandler) {
		return changeHandlers.remove(changeHandler);
	}

	@Override
	public void detachAll() {
		changeHandlers.clear();
	}
	
	protected void fireChange(boolean oldValue, boolean newValue) {
		for(BooleanChangeHandler handler : changeHandlers) handler.onChange(oldValue, newValue);
	}
}
