package arachne.lib.listeners;

public class SimpleProperty<T> extends Property<T>
{
	protected T value;
	
	public SimpleProperty() {
		this(null);
	}
	
	public SimpleProperty(T initialValue) {
		super();
		this.value = initialValue;
	}
	
	@Override
	public T get() {
		return value;
	}

	@Override
	protected void _accept(T value) {
		this.value = value;
	}
}
