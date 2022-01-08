package arachne.lib.immutables;

public class Triple<FirstT, SecondT, ThirdT>
{
	protected FirstT first;
	protected SecondT second;
	protected ThirdT third;
	
	public Triple(FirstT first, SecondT second, ThirdT third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}
	
	public FirstT getFirst() {
		return first;
	}
	
	public SecondT getSecond() {
		return second;
	}
	
	public ThirdT getThird() {
		return third;
	}
}
