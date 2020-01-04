package arachne.lib.pipeline;

import arachne.lib.io.GettableBoolean;

public class SimpleBooleanSource extends AbstractBooleanSource
{
	protected final GettableBoolean gettable;
	
	public SimpleBooleanSource(GettableBoolean gettable) {
		super();
		this.gettable = gettable;
	}

	@Override
	protected boolean getOutputValue() {
		return gettable.get();
	}
}
