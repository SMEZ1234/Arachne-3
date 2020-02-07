package arachne.lib.pipeline;

import arachne.lib.io.Gettable;

public class Sink<T> extends AbstractValve<T> implements Gettable<T>
{
	protected T value;
	
	@Override
	public T get() {
		return value;
	}

	@Override
	protected void acceptValveValue(T value) {
		this.value = value;
	}
}
