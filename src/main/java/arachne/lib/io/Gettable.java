package arachne.lib.io;

import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@FunctionalInterface
public interface Gettable<T> extends Supplier<T>
{
	default GettableBoolean is(T target) {
		return () -> get() == target;
	}
	
	default GettableBoolean is(Predicate<T> predicate) {
		return () -> predicate.test(get());
	}
	
	default Gettable<T> withModifier(UnaryOperator<T> modifier) {
		return () -> modifier.apply(get());
	}
}
