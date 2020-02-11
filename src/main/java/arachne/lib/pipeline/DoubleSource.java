package arachne.lib.pipeline;

import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;

import arachne.lib.io.SettableDouble;
import arachne.lib.pipeline.translators.DoubleToBooleanTranslator;
import arachne.lib.pipeline.translators.FromDoubleTranslator;

public interface DoubleSource
{
	<SettableT extends SettableDouble> SettableT attachOutput(SettableT settable);
	boolean detachOutput(SettableDouble settable);
	void detachAllOutputs();
	void feedOutputs();
	
	default <U> Source<U> attachTranslator(DoubleFunction<U> translation) {
		FromDoubleTranslator<U> translator = new FromDoubleTranslator<U>(translation);
		return attachOutput(translator);
	}
	
	default BooleanSource attachTranslatorToBoolean(DoublePredicate translation) {
		DoubleToBooleanTranslator translator = new DoubleToBooleanTranslator(translation);
		return attachOutput(translator);
	}
}
