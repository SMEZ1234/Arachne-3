package arachne.lib.pipeline.filters;

import arachne.lib.io.GettableBoolean;

public class ChangedBooleanFilter implements GettableBoolean
{
	protected GettableBoolean gettable;
	protected boolean lastValue;
	
	public ChangedBooleanFilter(GettableBoolean gettable) {
		this.gettable = gettable;
		this.lastValue = gettable.get();
	}
	
	@Override
	public boolean get() {
		boolean value = gettable.get();
		
		boolean result = value != lastValue;
		this.lastValue = value;
		
		return result;
	}
}
