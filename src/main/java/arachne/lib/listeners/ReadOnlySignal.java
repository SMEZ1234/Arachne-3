package arachne.lib.listeners;

import java.util.LinkedHashSet;
import java.util.Set;

public class ReadOnlySignal
{
	protected Set<Runnable> handlers;
	
	public ReadOnlySignal() {
		this.handlers = new LinkedHashSet<Runnable>();
	}
	
	public boolean attach(Runnable handler) {
		return handlers.add(handler);
	}

	public boolean detach(Runnable handler) {
		return handlers.remove(handler);
	}

	public void detachAll() {
		handlers.clear();
	}
}
