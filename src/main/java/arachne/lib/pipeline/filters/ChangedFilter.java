package arachne.lib.pipeline.filters;

import arachne.lib.io.Gettable;
import arachne.lib.io.GettableBoolean;

public class ChangedFilter<T> implements GettableBoolean
{
	protected Gettable<T> gettable;
	protected T lastValue;
	
	public ChangedFilter(Gettable<T> gettable) {
		this.gettable = gettable;
		this.lastValue = gettable.get();
	}
	
	@Override
	public boolean get() {
		T value = gettable.get();
		
		boolean result = !value.equals(lastValue);
		this.lastValue = value;
		
		return result;
	}
}
