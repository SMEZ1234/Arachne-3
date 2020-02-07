package arachne.lib.io;

import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import arachne.lib.logging.ArachneLogger;

@FunctionalInterface
public interface Gettable<T> extends Supplier<T>
{
	default GettableBoolean is(T target) {
		return () -> get() == target;
	}
	
	default GettableBoolean is(Predicate<T> predicate) {
		return () -> predicate.test(get());
	}
	
	default Gettable<T> change(UnaryOperator<T> modifier) {
		return () -> modifier.apply(get());
	}
	
	public static <T> Gettable<T> create(Gettable<T> lambda) {
		return lambda;
	}
	
	public static final class Wrapper<T> implements Gettable<T> {
		protected Gettable<T> gettable;
		
		public Wrapper<T> wrap(Gettable<T> gettable) {
			this.gettable = gettable;
			return this;
		}

		@Override
		public T get() {
			if(gettable != null) return gettable.get();
			
			ArachneLogger.getInstance().error("Tried to get Gettable wrapper with no contained gettable, returning null");
			return null;
		}
	}
}
