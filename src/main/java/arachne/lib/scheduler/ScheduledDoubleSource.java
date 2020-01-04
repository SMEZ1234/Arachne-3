package arachne.lib.scheduler;

import arachne.lib.io.GettableBoolean;
import arachne.lib.io.GettableDouble;
import arachne.lib.pipeline.SimpleDoubleSource;
import arachne.lib.pipeline.filters.ChangedDoubleFilter;

public class ScheduledDoubleSource extends SimpleDoubleSource implements Schedulable
{
	protected GettableBoolean filter;
	
	public ScheduledDoubleSource(GettableDouble gettable) {
		super(gettable);
		
		this.filter = new ChangedDoubleFilter(gettable);
	}
	
	public void run() {
		if(filter.get()) feedOutputs();
	}
}
