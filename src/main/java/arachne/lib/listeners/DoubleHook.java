package arachne.lib.listeners;

public interface DoubleHook
{
	boolean attach(DoubleChangeHandler changeHandler);
	boolean detach(DoubleChangeHandler changeHandler);
	void detachAll();
}
