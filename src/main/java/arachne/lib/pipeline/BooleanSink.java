package arachne.lib.pipeline;

import arachne.lib.io.GettableBoolean;

public class BooleanSink extends AbstractBooleanValve implements GettableBoolean
{
	protected boolean value;
	
	@Override
	public boolean get() {
		return value;
	}

	@Override
	protected void acceptValveValue(boolean value) {
		this.value = value;
	}
}
