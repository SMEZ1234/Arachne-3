package arachne.lib.immutables;

public class FlaggedDoubleValue
{
	protected double value;
	protected boolean flag;
	
	public FlaggedDoubleValue(double value, boolean flag) {
		this.value = value;
		this.flag = flag;
	}
	
	public double getValue() {
		return value;
	}
	
	public boolean getFlag() {
		return flag;
	}
}
