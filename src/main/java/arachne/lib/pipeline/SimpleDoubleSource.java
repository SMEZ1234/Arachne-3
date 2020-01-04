package arachne.lib.pipeline;

import arachne.lib.io.GettableDouble;

public class SimpleDoubleSource extends AbstractDoubleSource
{
	protected final GettableDouble gettable;
	
	public SimpleDoubleSource(GettableDouble gettable) {
		super();
		this.gettable = gettable;
	}

	@Override
	protected double getOutputValue() {
		return gettable.get();
	}
}
