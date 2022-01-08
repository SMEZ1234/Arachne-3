package arachne.lib.listeners;

import arachne.lib.pipeline.BooleanPipe;
import arachne.lib.pipeline.BooleanSource;
import arachne.lib.pipeline.DoublePipe;
import arachne.lib.pipeline.DoubleSource;
import arachne.lib.pipeline.Pipe;
import arachne.lib.pipeline.Source;

public interface ReadOnlySignal
{
	<RunnableT extends Runnable> RunnableT attach(RunnableT handler);

	boolean detach(Runnable handler);
	void detachAll();
	
	default <T> Source<T> asSource(T value) {
		Pipe<T> pipe = new Pipe<T>(value);
		
		attach(() -> pipe.accept(value));
		
		return pipe;
	}
	
	default BooleanSource asBooleanSource(boolean value) {
		BooleanPipe pipe = new BooleanPipe(value);
		
		attach(() -> pipe.accept(value));
		
		return pipe;
	}
	
	default DoubleSource asDoubleSource(double value) {
		DoublePipe pipe = new DoublePipe(value);
		
		attach(() -> pipe.accept(value));
		
		return pipe;
	}
}
