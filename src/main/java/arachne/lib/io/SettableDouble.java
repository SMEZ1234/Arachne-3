package arachne.lib.io;

import java.util.function.DoubleConsumer;

@FunctionalInterface
public interface SettableDouble extends DoubleConsumer
{
	public static SettableDouble create(SettableDouble lambda) {
		return lambda;
	}
}
