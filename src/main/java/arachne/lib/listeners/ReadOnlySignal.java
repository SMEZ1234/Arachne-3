package arachne.lib.listeners;

public interface ReadOnlySignal
{
	boolean attach(Runnable handler);
	boolean detach(Runnable handler);
	void detachAll();
}
