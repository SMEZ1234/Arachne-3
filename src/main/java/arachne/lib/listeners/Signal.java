package arachne.lib.listeners;

public class Signal extends ReadOnlySignal
{
	public void fire() {
		for(Runnable handler : handlers) handler.run();
	}
}
