package arachne.lib.scheduler;

import arachne.lib.sequences.Action;
import arachne.lib.sequences.Actionable;
import arachne.lib.sequences.HostAction;

@FunctionalInterface
public interface Schedulable extends Runnable, Actionable
{
	@Override
	default Action asAction(HostAction host) {
		return new Action(host) {
			@Override
			protected void execute() {
				Schedulable.this.run();
			}
		};
	}
}
