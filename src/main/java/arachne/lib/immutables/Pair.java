package arachne.lib.immutables;

public class Pair<FirstT, SecondT>
{
	protected FirstT first;
	protected SecondT second;
	
	public Pair(FirstT first, SecondT second) {
		this.first = first;
		this.second = second;
	}
	
	public FirstT getFirst() {
		return first;
	}
	
	public SecondT getSecond() {
		return second;
	}
}
