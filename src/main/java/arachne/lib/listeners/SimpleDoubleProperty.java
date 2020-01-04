package arachne.lib.listeners;

public class SimpleDoubleProperty extends DoubleProperty
{
	protected double value;
	
	public SimpleDoubleProperty() {
		this(0);
	}
	
	public SimpleDoubleProperty(double initialValue) {
		super();
		this.value = initialValue;
	}
	
	@Override
	public double get() {
		return value;
	}

	@Override
	protected void _accept(double value) {
		this.value = value;
	}
}
