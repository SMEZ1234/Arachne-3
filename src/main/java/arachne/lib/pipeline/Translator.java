package arachne.lib.pipeline;

import java.util.function.Function;

import arachne.lib.io.Settable;

public class Translator<T, R> extends AbstractSource<R> implements Settable<T>
{
	protected R value;
	protected final Function<T, R> translation;
	
	public Translator(Function<T, R> translation) {
		this.translation = translation;
	}

	@Override
	protected R getOutputValue() {
		return value;
	}
	
	@Override
	public void accept(T value) {
		this.value = translation.apply(value);
		feedOutputs();
	}
}
