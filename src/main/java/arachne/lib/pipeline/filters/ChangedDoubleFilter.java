package arachne.lib.pipeline.filters;

import arachne.lib.io.GettableBoolean;
import arachne.lib.io.GettableDouble;

public class ChangedDoubleFilter implements GettableBoolean
{
	protected GettableDouble gettable;
	protected double lastValue;
	
	public ChangedDoubleFilter(GettableDouble gettable) {
		this.gettable = gettable;
		this.lastValue = gettable.get();
	}
	
	@Override
	public boolean get() {
		double value = gettable.get();
		
		boolean result = value != lastValue;
		this.lastValue = value;
		
		return result;
	}
}
