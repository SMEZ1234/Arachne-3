package arachne.lib.pipeline;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import arachne.lib.io.Settable;

public class Pipe<T> extends AbstractValve<T> implements Source<T>
{
	protected Set<Settable<T>> outputs;
	protected T value;
	
	public Pipe() {
		super();
		
		this.outputs = new LinkedHashSet<Settable<T>>();
	}

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
		for(Settable<T> output : outputs) output.accept(value);
	}

	@Override
	protected void acceptValveValue(T value) {
		this.value = value;
		feedOutputs();
	}
	
	@Override
	public Pipe<T> withModifier(UnaryOperator<T> modifier) {
		setModifier(modifier);
		return this;
	}
	
	@Override
	public Pipe<T> withModifier(UnaryOperator<T>[] modifiers) {
		setModifier(modifiers);
		return this;
	}
	
	@Override
	public Pipe<T> withFilter(Predicate<T> predicate) {
		setFilter(predicate);
		return this;
	}
}
