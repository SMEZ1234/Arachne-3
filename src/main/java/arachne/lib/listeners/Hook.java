package arachne.lib.listeners;

public interface Hook<T>
{
	boolean attach(ChangeHandler<T> changeHandler);
	boolean detach(ChangeHandler<T> changeHandler);
	void detachAll();
}
