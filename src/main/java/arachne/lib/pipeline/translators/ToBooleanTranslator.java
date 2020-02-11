package arachne.lib.pipeline.translators;

import java.util.function.Predicate;

import arachne.lib.io.Settable;
import arachne.lib.pipeline.AbstractBooleanSource;

public class ToBooleanTranslator<R> extends AbstractBooleanSource implements Settable<R>
{
	protected boolean value;
	protected final Predicate<R> translation;
	
	public ToBooleanTranslator(Predicate<R> translation) {
		this.translation = translation;
	}

	@Override
	protected boolean getOutputValue() {
		return value;
	}
	
	@Override
	public void accept(R value) {
		this.value = translation.test(value);
		feedOutputs();
	}
}
