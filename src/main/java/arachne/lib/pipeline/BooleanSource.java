package arachne.lib.pipeline;

import arachne.lib.function.BooleanFunction;
import arachne.lib.function.BooleanToDoubleFunction;
import arachne.lib.io.SettableBoolean;
import arachne.lib.pipeline.translators.BooleanToDoubleTranslator;
import arachne.lib.pipeline.translators.FromBooleanTranslator;

public interface BooleanSource
{
	<SettableT extends SettableBoolean> SettableT attachOutput(SettableT settable);
	boolean detachOutput(SettableBoolean settable);
	void detachAllOutputs();
	void feedOutputs();
	
	default <U> Source<U> attachTranslator(BooleanFunction<U> translation) {
		FromBooleanTranslator<U> translator = new FromBooleanTranslator<U>(translation);
		return attachOutput(translator);
	}
	
	default DoubleSource attachTranslatorToDouble(BooleanToDoubleFunction translation) {
		BooleanToDoubleTranslator translator = new BooleanToDoubleTranslator(translation);
		return attachOutput(translator);
	}
}
