package arachne.lib.pipeline;

import arachne.lib.function.BooleanFunction;
import arachne.lib.io.SettableBoolean;

public interface BooleanSource
{
	<SettableT extends SettableBoolean> SettableT attachOutput(SettableT settable);
	boolean detachOutput(SettableBoolean settable);
	void detachAllOutputs();
	void feedOutputs();
	
	default <U> Source<U> attachTranslator(BooleanFunction<U> translation) {
		BooleanTranslator<U> translator = new BooleanTranslator<U>(translation);
		return attachOutput(translator);
	}
}
