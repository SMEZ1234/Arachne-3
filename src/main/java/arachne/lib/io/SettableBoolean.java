package arachne.lib.io;

import arachne.lib.function.BooleanConsumer;

@FunctionalInterface
public interface SettableBoolean extends BooleanConsumer
{
	public static SettableBoolean create(SettableBoolean lambda) {
		return lambda;
	}
}
