package arachne.lib.function;

import java.util.Objects;

import arachne.lib.io.SettableBoolean;

@FunctionalInterface
public interface BooleanConsumer
{
	void accept(boolean value);
	
	default SettableBoolean andThen(SettableBoolean after) {
        Objects.requireNonNull(after);
        return (value) -> { accept(value); after.accept(value); };
    }
}
