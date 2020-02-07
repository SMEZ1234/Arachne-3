package arachne.lib.io;

import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleUnaryOperator;

import arachne.lib.logging.ArachneLogger;

@FunctionalInterface
public interface GettableDouble extends DoubleSupplier
{
	double get();
	
	@Override
	default double getAsDouble() {
		return get();
	}
	
	default GettableBoolean is(double target) {
		return () -> get() == target;
	}
	
	default GettableBoolean is(DoublePredicate predicate) {
		return () -> predicate.test(get());
	}
	
	default GettableDouble change(DoubleUnaryOperator modifier) {
		return () -> modifier.applyAsDouble(get());
	}
	
	public static GettableDouble create(GettableDouble lambda) {
		return lambda;
	}
	
	public static final class Wrapper implements GettableDouble {
		protected GettableDouble gettable;
		
		public Wrapper wrap(GettableDouble gettable) {
			this.gettable = gettable;
			return this;
		}

		@Override
		public double get() {
			if(gettable != null) return gettable.get();
			
			ArachneLogger.getInstance().error("Tried to get GettableDouble wrapper with no contained gettable, returning 0");
			return 0;
		}
	}
}
