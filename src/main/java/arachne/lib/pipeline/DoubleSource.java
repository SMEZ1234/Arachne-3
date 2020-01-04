package arachne.lib.pipeline;

import java.util.function.DoubleFunction;
import arachne.lib.io.SettableDouble;

public interface DoubleSource
{
	<SettableT extends SettableDouble> SettableT attachOutput(SettableT settable);
	boolean detachOutput(SettableDouble settable);
	void detachAllOutputs();
	void feedOutputs();
	
	default <U> Source<U> attachTranslator(DoubleFunction<U> translation) {
		DoubleTranslator<U> translator = new DoubleTranslator<U>(translation);
		return attachOutput(translator);
	}
}
