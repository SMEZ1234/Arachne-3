package arachne.lib.io.sensors;

@FunctionalInterface
public interface Input<T>
{
	void populate(T input);
}
