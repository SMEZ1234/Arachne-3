package arachne.dev.test;

import java.util.ArrayList;
import java.util.List;

public class CallableStub
{
	private List<Object[]> calls = new ArrayList<Object[]>();
	
	public void call(Object... objects) {
		calls.add(objects);
	}
	
	public int getNumberOfCalls() {
		return calls.size();
	}
	
	public Object[] getArgs(int call) {
		return calls.get(call);
	}
}
