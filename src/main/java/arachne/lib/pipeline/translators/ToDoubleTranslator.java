package arachne.lib.pipeline.translators;

import java.util.function.ToDoubleFunction;

import arachne.lib.io.Settable;
import arachne.lib.pipeline.AbstractDoubleSource;

public class ToDoubleTranslator<R> extends AbstractDoubleSource implements Settable<R>
{
	protected double value;
	protected final ToDoubleFunction<R> translation;
	
	public ToDoubleTranslator(ToDoubleFunction<R> translation) {
		this.translation = translation;
	}

	@Override
	protected double getOutputValue() {
		return value;
	}
	
	@Override
	public void accept(R value) {
		this.value = translation.applyAsDouble(value);
		feedOutputs();
	}
}
