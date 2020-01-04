package arachne.lib.pipeline;

import arachne.lib.function.BooleanFunction;
import arachne.lib.io.SettableBoolean;

public class BooleanTranslator<R> extends AbstractSource<R> implements SettableBoolean
{
	protected R value;
	protected final BooleanFunction<R> translation;
	
	public BooleanTranslator(BooleanFunction<R> translation) {
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
