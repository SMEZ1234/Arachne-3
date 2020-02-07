package arachne.lib.pipeline;

import java.util.LinkedHashSet;
import java.util.Set;

import arachne.lib.function.BooleanPredicate;
import arachne.lib.io.SettableBoolean;

public class BooleanPipe extends AbstractBooleanValve implements BooleanSource
{
	protected Set<SettableBoolean> outputs;
	protected boolean value;
	
	public BooleanPipe() {
		super();
		
		this.outputs = new LinkedHashSet<SettableBoolean>();
	}

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
		for(SettableBoolean output : outputs) output.accept(value);
	}

	@Override
	protected void acceptValveValue(boolean value) {
		this.value = value;
		feedOutputs();
	}
	
	@Override
	public BooleanPipe withModifier(BooleanPredicate modifier, BooleanPredicate... additionalModifiers) {
		setModifier(modifier, additionalModifiers);
		return this;
	}
	
	@Override
	public BooleanPipe withFilter(BooleanPredicate predicate) {
		setFilter(predicate);
		return this;
	}
}
