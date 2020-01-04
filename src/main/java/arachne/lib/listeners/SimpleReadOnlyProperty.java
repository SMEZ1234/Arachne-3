package arachne.lib.listeners;

public class SimpleReadOnlyProperty<T> extends ReadOnlyProperty<T>
{
	protected T value;
	
	public SimpleReadOnlyProperty() {
		this(null);
	}
	
	public SimpleReadOnlyProperty(T initialValue) {
		super();
		this.value = initialValue;
	}
	
	@Override
	public T get() {
		return value;
	}
}
