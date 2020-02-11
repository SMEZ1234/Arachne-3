package arachne.lib.pipeline.translators;

import java.util.function.DoubleFunction;

import arachne.lib.io.SettableDouble;
import arachne.lib.pipeline.AbstractSource;

public class FromDoubleTranslator<R> extends AbstractSource<R> implements SettableDouble
{
	protected R value;
	protected final DoubleFunction<R> translation;
	
	public FromDoubleTranslator(DoubleFunction<R> translation) {
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
