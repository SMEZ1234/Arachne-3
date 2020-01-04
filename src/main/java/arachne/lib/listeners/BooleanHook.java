package arachne.lib.listeners;

public interface BooleanHook
{
	boolean attach(BooleanChangeHandler changeHandler);
	boolean detach(BooleanChangeHandler changeHandler);
	void detachAll();
}
