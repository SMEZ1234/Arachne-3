package arachne.lib.io;

import java.util.function.Consumer;

@FunctionalInterface
public interface Settable<T> extends Consumer<T>
{
	// Empty, exists for future features
}
