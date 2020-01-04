package arachne.lib.listeners;

public class SimpleReadOnlyBooleanProperty extends ReadOnlyBooleanProperty
{
	protected boolean value;
	
	public SimpleReadOnlyBooleanProperty() {
		this(false);
	}
	
	public SimpleReadOnlyBooleanProperty(boolean initialValue) {
		super();
		this.value = initialValue;
	}
	
	@Override
	public boolean get() {
		return value;
	}
}
