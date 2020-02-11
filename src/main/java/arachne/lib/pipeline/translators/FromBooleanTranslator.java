package arachne.lib.pipeline.translators;

import arachne.lib.function.BooleanFunction;
import arachne.lib.io.SettableBoolean;
import arachne.lib.pipeline.AbstractSource;

public class FromBooleanTranslator<R> extends AbstractSource<R> implements SettableBoolean
{
	protected R value;
	protected final BooleanFunction<R> translation;
	
	public FromBooleanTranslator(BooleanFunction<R> translation) {
		this.translation = translation;
	}

	@Override
	protected R getOutputValue() {
		return value;
	}
	
	@Override
	public void accept(boolean value) {
		this.value = translation.apply(value);
		feedOutputs();
	}
}
