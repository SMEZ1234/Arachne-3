package arachne.lib.listeners;

import java.util.LinkedHashSet;
import java.util.Set;

public class Signal implements ReadOnlySignal
{
	protected Set<Runnable> handlers;
	
	public Signal() {
		this.handlers = new LinkedHashSet<Runnable>();
	}
	
	@Override
	public boolean attach(Runnable handler) {
		return handlers.add(handler);
	}

	@Override
	public boolean detach(Runnable handler) {
		return handlers.remove(handler);
	}

	@Override
	public void detachAll() {
		handlers.clear();
	}
	
	public void fire() {
		for(Runnable handler : handlers) handler.run();
	}
}
