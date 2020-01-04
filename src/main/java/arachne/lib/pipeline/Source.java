package arachne.lib.pipeline;

import java.util.function.Function;

import arachne.lib.io.Settable;

public interface Source<T>
{
	<SettableT extends Settable<T>> SettableT attachOutput(SettableT settable);
	boolean detachOutput(Settable<T> settable);
	void detachAllOutputs();
	void feedOutputs();
	
	default <U> Source<U> attachTranslator(Function<T, U> translation) {
		Translator<T, U> translator = new Translator<T, U>(translation);
		attachOutput(translator);
		
		return translator;
	}
}
