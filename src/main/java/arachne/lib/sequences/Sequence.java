package arachne.lib.sequences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sequence implements Actionable
{
	protected List<Actionable> actionables;
	
	public Sequence(Actionable... actionables) {
		this.actionables = new ArrayList<Actionable>(Arrays.asList(actionables));
	}
	
	@Override
	public Action asAction(HostAction host) {
		return new ActionWrapper(host) {
			int counter;
			
			@Override
			protected Action getNextAction(boolean isFirst) {
				if(isFirst) counter = 0;
				else counter++;
				
				if(counter < Sequence.this.actionables.size()) return Sequence.this.actionables.get(counter).asAction(this);
				return null;
			}
		};
	}
}
