package arachne.lib.listeners;

@FunctionalInterface
public interface ChangeHandler<T>
{
	void onChange(T oldValue, T newValue);
}
