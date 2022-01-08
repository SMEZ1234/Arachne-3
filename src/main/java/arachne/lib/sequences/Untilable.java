package arachne.lib.sequences;

import arachne.lib.function.BooleanPredicate;
import arachne.lib.io.GettableBoolean;
import arachne.lib.sequences.actions.Action;
import arachne.lib.sequences.actions.HostAction;
import arachne.lib.sequences.conditions.ConditionSupplier;

@FunctionalInterface
public interface Untilable extends Actionable
{
	Action asAction(HostAction host, BooleanPredicate conditionModifier);
	
	@Override
	default Action asAction(HostAction host) {
		return asAction(host, (value) -> value);
	}
	
	default Actionable UNSAFE_UNTIL(GettableBoolean condition) {
		return UNSAFE_UNTIL((value) -> condition.get());
	}
	
	default Actionable UNSAFE_UNTIL(BooleanPredicate conditionModifier) {
		return (host) -> asAction(host, conditionModifier);
	}
	
	default Actionable UNTIL(ConditionSupplier conditionModifierSupplier) {
		return (host) -> asAction(host, conditionModifierSupplier.get());
	}
}
