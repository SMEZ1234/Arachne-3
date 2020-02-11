package arachne.lib.pipeline;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

import arachne.lib.io.Settable;
import arachne.lib.pipeline.translators.ToBooleanTranslator;
import arachne.lib.pipeline.translators.ToDoubleTranslator;
import arachne.lib.pipeline.translators.Translator;

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
	
	default BooleanSource attachTranslatorToBoolean(Predicate<T> translation) {
		ToBooleanTranslator<T> translator = new ToBooleanTranslator<T>(translation);
		attachOutput(translator);
		
		return translator;
	}
	
	default DoubleSource attachTranslatorToDouble(ToDoubleFunction<T> translation) {
		ToDoubleTranslator<T> translator = new ToDoubleTranslator<T>(translation);
		attachOutput(translator);
		
		return translator;
	}
}
