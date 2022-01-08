package arachne.lib.sequences.conditions;

import java.util.function.Supplier;

import arachne.lib.function.BooleanPredicate;
import arachne.lib.io.GettableBoolean;
import arachne.lib.sequences.Actionable;
import arachne.lib.sequences.actions.Action;
import arachne.lib.sequences.actions.HostAction;

@FunctionalInterface
public interface ConditionSupplier extends Actionable, Supplier<BooleanPredicate>
{
	default Action asAction(HostAction host) {
		BooleanPredicate predicate = get();
		
		return GettableBoolean.create(() -> predicate.test(false)).asAction(host);
	}
	
	public static ConditionSupplier create(ConditionSupplier lambda) {
		return lambda;
	}
}
