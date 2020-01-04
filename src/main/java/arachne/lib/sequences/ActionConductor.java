package arachne.lib.sequences;

import java.util.LinkedHashSet;
import java.util.Set;

import arachne.lib.scheduler.Schedulable;
import arachne.lib.sequences.Action.ActionState;

public class ActionConductor implements Schedulable
{
	protected Set<Action> actions;
	
	public ActionConductor() {
		this.actions = new LinkedHashSet<Action>();
	}
	
	@Override
	public void run() {
		actions.removeIf((action) -> {
			ActionState state = action.act();
			return state == ActionState.INTERRUPTED || state == ActionState.FINISHED;
		});
	}
	
	public Action add(Action action) {
		actions.add(action);
		return action;
	}
	
	public Action add(Actionable actionable) {
		return add(actionable, null);
	}
	
	public Action add(Actionable actionable, HostAction host) {
		return add(actionable.asAction(host));
	}
	
	public void clear() {
		actions.clear();
	}
	
	public boolean hasActions() {
		return !actions.isEmpty();
	}
	
	public void interrupt() {
		for(Action action : actions) action.interrupt();
	}
}
