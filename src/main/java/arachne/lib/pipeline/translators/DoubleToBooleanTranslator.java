package arachne.lib.pipeline.translators;

import java.util.function.DoublePredicate;

import arachne.lib.io.SettableDouble;
import arachne.lib.pipeline.AbstractBooleanSource;

public class DoubleToBooleanTranslator extends AbstractBooleanSource implements SettableDouble
{
	protected boolean value;
	protected final DoublePredicate translation;
	
	public DoubleToBooleanTranslator(DoublePredicate translation) {
		this.translation = translation;
	}

	@Override
	protected boolean getOutputValue() {
		return value;
	}
	
	@Override
	public void accept(double value) {
		this.value = translation.test(value);
		feedOutputs();
	}
}
