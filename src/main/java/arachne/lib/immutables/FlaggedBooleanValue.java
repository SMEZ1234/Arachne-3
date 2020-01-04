package arachne.lib.immutables;

public class FlaggedBooleanValue
{
	protected boolean value;
	protected boolean flag;
	
	public FlaggedBooleanValue(boolean value, boolean flag) {
		this.value = value;
		this.flag = flag;
	}
	
	public boolean getValue() {
		return value;
	}
	
	public boolean getFlag() {
		return flag;
	}
}
