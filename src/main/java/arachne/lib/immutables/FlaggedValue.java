package arachne.lib.immutables;

public class FlaggedValue<T>
{
	protected T value;
	protected boolean flag;
	
	public FlaggedValue(T value, boolean flag) {
		this.value = value;
		this.flag = flag;
	}
	
	public T getValue() {
		return value;
	}
	
	public boolean getFlag() {
		return flag;
	}
}
