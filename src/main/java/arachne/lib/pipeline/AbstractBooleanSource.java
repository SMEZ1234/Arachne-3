package arachne.lib.pipeline;

import java.util.LinkedHashSet;
import java.util.Set;

import arachne.lib.io.SettableBoolean;

public abstract class AbstractBooleanSource implements BooleanSource
{
	protected Set<SettableBoolean> outputs;
	
	public AbstractBooleanSource() {
		this.outputs = new LinkedHashSet<SettableBoolean>();
	}
	
	protected abstract boolean getOutputValue();
	
	@Override
	public <SettableT extends SettableBoolean> SettableT attachOutput(SettableT settable) {
		outputs.add(settable);
		return settable;
	}

	@Override
	public boolean detachOutput(SettableBoolean settable) {
		return outputs.remove(settable);
	}

	@Override
	public void detachAllOutputs() {
		outputs.clear();
	}

	@Override
	public void feedOutputs() {
		boolean value = getOutputValue();
		
		for(SettableBoolean output : outputs) output.accept(value);
	}
}
