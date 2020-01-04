package arachne.lib.listeners;

import java.util.LinkedHashSet;
import java.util.Set;

import arachne.lib.io.GettableDouble;

public abstract class ReadOnlyDoubleProperty implements GettableDouble, DoubleHook
{
	protected final Set<DoubleChangeHandler> changeHandlers;
	
	public ReadOnlyDoubleProperty() {
		this.changeHandlers = new LinkedHashSet<DoubleChangeHandler>();
	}
	
	@Override
	public boolean attach(DoubleChangeHandler changeHandler) {
		return changeHandlers.add(changeHandler);
	}

	@Override
	public boolean detach(DoubleChangeHandler changeHandler) {
		return changeHandlers.remove(changeHandler);
	}

	@Override
	public void detachAll() {
		changeHandlers.clear();
	}
	
	protected void fireChange(double oldValue, double newValue) {
		for(DoubleChangeHandler handler : changeHandlers) handler.onChange(oldValue, newValue);
	}
}
