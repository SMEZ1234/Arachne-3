/*
 * This file was auto-generated by Arachne's Template Manager.
 * Edits made to this file will be overridden on regeneration.
 *
 * Template file: Translator.jgn
 * Variable definitions: Translator.json
 * Label used: DoubleToBoolean
 */
package arachne.lib.pipeline.translators;

import java.util.function.DoublePredicate;

import arachne.lib.io.SettableBoolean;
import arachne.lib.io.SettableDouble;
import arachne.lib.pipeline.BooleanListener;
import arachne.lib.pipeline.BooleanSource;
import arachne.lib.pipeline.BooleanPipe;

public class DoubleToBooleanTranslator implements SettableDouble, BooleanSource
{
	protected final BooleanPipe pipe;
	protected final DoublePredicate translation;

	public DoubleToBooleanTranslator(double initialInputValue, DoublePredicate translation) {
		this.pipe = new BooleanPipe(translation.test(initialInputValue));
		this.translation = translation;
	}

	@Override
	public void accept(double value) {
		pipe.accept(translation.test(value));
	}

	@Override
	public boolean get() {
		return pipe.get();
	}

	@Override
	public <SettableT extends SettableBoolean> SettableT attach(SettableT settable) {
		return pipe.attach(settable);
	}

	@Override
	public <ListenerT extends BooleanListener> ListenerT attachListener(ListenerT listener) {
		return pipe.attachListener(listener);
	}

	@Override
	public boolean detach(SettableBoolean settable) {
		return pipe.detach(settable);
	}

	@Override
	public boolean detachListener(BooleanListener listener) {
		return pipe.detachListener(listener);
	}

	@Override
	public void detachAll() {
		pipe.detachAll();
	}
}
