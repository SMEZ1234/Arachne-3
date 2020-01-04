package arachne.lib.pipeline;

import java.util.LinkedHashSet;
import java.util.Set;

import arachne.lib.io.Settable;

public abstract class AbstractSource<T> implements Source<T>
{
	protected Set<Settable<T>> outputs;
	
	public AbstractSource() {
		this.outputs = new LinkedHashSet<Settable<T>>();
	}
	
	protected abstract T getOutputValue();
	
	@Override
	public <SettableT extends Settable<T>> SettableT attachOutput(SettableT settable) {
		outputs.add(settable);
		return settable;
	}

	@Override
	public boolean detachOutput(Settable<T> settable) {
		return outputs.remove(settable);
	}

	@Override
	public void detachAllOutputs() {
		outputs.clear();
	}

	@Override
	public void feedOutputs() {
		T value = getOutputValue();
		
		for(Settable<T> output : outputs) output.accept(value);
	}
}
