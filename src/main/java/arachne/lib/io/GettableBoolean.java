package arachne.lib.io;

import java.util.function.BooleanSupplier;

import arachne.lib.function.BooleanPredicate;
import arachne.lib.sequences.Action;
import arachne.lib.sequences.Actionable;
import arachne.lib.sequences.HostAction;

@FunctionalInterface
public interface GettableBoolean extends BooleanSupplier, Actionable
{
	GettableBoolean TRUE = () -> true, FALSE = () -> false;
	
	boolean get();
	
	@Override
	default boolean getAsBoolean() {
		return get();
	}
	
	@Override
	default Action asAction(HostAction host) {
		return new Action(host) {
			@Override
			protected boolean isFinished() {
				return GettableBoolean.this.get();
			}
		};
	}
	
	default GettableBoolean is(boolean target) {
		return () -> get() == target;
	}
	
	default GettableBoolean is(BooleanPredicate predicate) {
		return () -> predicate.test(get());
	}
	
	default GettableBoolean and(GettableBoolean other) {
		return () -> this.get() && other.get();
	}
	
	default GettableBoolean or(GettableBoolean other) {
		return () -> this.get() || other.get();
	}
	
	default GettableBoolean xor(GettableBoolean other) {
		return () -> this.get() != other.get();
	}
}
