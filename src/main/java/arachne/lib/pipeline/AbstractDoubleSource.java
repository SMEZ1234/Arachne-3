package arachne.lib.pipeline;

import java.util.LinkedHashSet;
import java.util.Set;

import arachne.lib.io.SettableDouble;

public abstract class AbstractDoubleSource implements DoubleSource
{
	protected Set<SettableDouble> outputs;
	
	public AbstractDoubleSource() {
		this.outputs = new LinkedHashSet<SettableDouble>();
	}
	
	protected abstract double getOutputValue();
	
	@Override
	public <SettableT extends SettableDouble> SettableT attachOutput(SettableT settable) {
		outputs.add(settable);
		return settable;
	}

	@Override
	public boolean detachOutput(SettableDouble settable) {
		return outputs.remove(settable);
	}

	@Override
	public void detachAllOutputs() {
		outputs.clear();
	}

	@Override
	public void feedOutputs() {
		double value = getOutputValue();
		
		for(SettableDouble output : outputs) output.accept(value);
	}
}
