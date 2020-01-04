package arachne.lib.listeners;

@FunctionalInterface
public interface DoubleChangeHandler
{
	void onChange(double oldValue, double newValue);
}
