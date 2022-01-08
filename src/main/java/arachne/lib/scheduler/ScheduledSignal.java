package arachne.lib.scheduler;

import arachne.lib.function.BooleanPredicate;
import arachne.lib.io.GettableBoolean;
import arachne.lib.listeners.Signal;
import arachne.lib.pipeline.filters.ChangedBooleanFilter;

public class ScheduledSignal extends Signal implements Schedulable
{
	protected final GettableBoolean trigger;
	protected BooleanPredicate changedFilter;
	
	public ScheduledSignal(GettableBoolean trigger) {
		this.trigger = trigger;
		this.changedFilter = new ChangedBooleanFilter();
	}

	@Override
	public void run() {
		boolean triggerValue = trigger.get();
		
		if(changedFilter.test(triggerValue) && triggerValue) fire();
	}
}
