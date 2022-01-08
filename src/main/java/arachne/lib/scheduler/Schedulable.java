package arachne.lib.scheduler;

import arachne.lib.function.BooleanPredicate;
import arachne.lib.sequences.Untilable;
import arachne.lib.sequences.actions.Action;
import arachne.lib.sequences.actions.HostAction;

@FunctionalInterface
public interface Schedulable extends Runnable, Untilable
{
	@Override
	default Action asAction(HostAction host, BooleanPredicate conditionModifier) {
		return new Action(host, conditionModifier) {
			@Override
			protected void execute() {
				Schedulable.this.run();
			}
		};
	}
}
