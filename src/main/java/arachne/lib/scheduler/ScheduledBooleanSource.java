package arachne.lib.scheduler;

import arachne.lib.io.GettableBoolean;
import arachne.lib.pipeline.SimpleBooleanSource;
import arachne.lib.pipeline.filters.ChangedBooleanFilter;

public class ScheduledBooleanSource extends SimpleBooleanSource implements Schedulable
{
	protected GettableBoolean filter;
	
	public ScheduledBooleanSource(GettableBoolean gettable) {
		super(gettable);
		
		this.filter = new ChangedBooleanFilter(gettable);
	}
	
	public void run() {
		if(filter.get()) feedOutputs();
	}
}
