package arachne.lib.listeners;

public class SimpleBooleanProperty extends BooleanProperty
{
	protected boolean value;
	
	public SimpleBooleanProperty() {
		this(false);
	}
	
	public SimpleBooleanProperty(boolean initialValue) {
		super();
		this.value = initialValue;
	}
	
	@Override
	public boolean get() {
		return value;
	}

	@Override
	protected void _accept(boolean value) {
		this.value = value;
	}
}
