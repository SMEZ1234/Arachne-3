package arachne.lib.pipeline.translators;

import arachne.lib.function.BooleanToDoubleFunction;
import arachne.lib.io.SettableBoolean;
import arachne.lib.pipeline.AbstractDoubleSource;

public class BooleanToDoubleTranslator extends AbstractDoubleSource implements SettableBoolean
{
	protected double value;
	protected final BooleanToDoubleFunction translation;
	
	public BooleanToDoubleTranslator(BooleanToDoubleFunction translation) {
		this.translation = translation;
	}

	@Override
	protected double getOutputValue() {
		return value;
	}
	
	@Override
	public void accept(boolean value) {
		this.value = translation.apply(value);
		feedOutputs();
	}
}
