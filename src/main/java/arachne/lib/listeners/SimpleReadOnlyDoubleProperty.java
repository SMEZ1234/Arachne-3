package arachne.lib.listeners;

public class SimpleReadOnlyDoubleProperty extends ReadOnlyDoubleProperty
{
	protected double value;
	
	public SimpleReadOnlyDoubleProperty() {
		this(0);
	}
	
	public SimpleReadOnlyDoubleProperty(double initialValue) {
		super();
		this.value = initialValue;
	}
	
	@Override
	public double get() {
		return value;
	}
}
