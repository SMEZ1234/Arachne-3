package arachne.lib.io;

import java.util.function.Consumer;

@FunctionalInterface
public interface Settable<T> extends Consumer<T>
{
	public static <T> Settable<T> create(Settable<T> lambda) {
		return lambda;
	}
}
