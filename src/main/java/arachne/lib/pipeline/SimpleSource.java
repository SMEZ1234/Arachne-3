package arachne.lib.pipeline;

import arachne.lib.io.Gettable;

public class SimpleSource<T> extends AbstractSource<T>
{
	protected final Gettable<T> gettable;
	
	public SimpleSource(Gettable<T> gettable) {
		super();
		this.gettable = gettable;
	}
	
	@Override
	protected T getOutputValue() {
		return gettable.get();
	}
}
