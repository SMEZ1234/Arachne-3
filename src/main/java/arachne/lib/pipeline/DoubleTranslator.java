package arachne.lib.pipeline;

import java.util.function.DoubleFunction;

import arachne.lib.io.SettableDouble;

public class DoubleTranslator<R> extends AbstractSource<R> implements SettableDouble
{
	protected R value;
	protected final DoubleFunction<R> translation;
	
	public DoubleTranslator(DoubleFunction<R> translation) {
		this.translation = translation;
	}

	@Override
	protected R getOutputValue() {
		return value;
	}
	
	@Override
	public void accept(double value) {
		this.value = translation.apply(value);
		feedOutputs();
	}
}
