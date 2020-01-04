package arachne.lib.listeners;

@FunctionalInterface
public interface BooleanChangeHandler
{
	void onChange(boolean oldValue, boolean newValue);
}
