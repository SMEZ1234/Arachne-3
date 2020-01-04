package arachne.lib.scheduler;

import arachne.lib.io.Gettable;
import arachne.lib.io.GettableBoolean;
import arachne.lib.pipeline.SimpleSource;
import arachne.lib.pipeline.filters.ChangedFilter;

public class ScheduledSource<T> extends SimpleSource<T> implements Schedulable
{
	protected GettableBoolean filter;
	
	public ScheduledSource(Gettable<T> gettable) {
		super(gettable);
		
		this.filter = new ChangedFilter<T>(gettable);
	}
	
	public void run() {
		if(filter.get()) feedOutputs();
	}
}
