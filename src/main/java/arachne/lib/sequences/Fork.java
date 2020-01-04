package arachne.lib.sequences;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class Fork implements Actionable
{
	protected final Set<Actionable> actionables;
	
	public Fork(Actionable... actionables) {
		this.actionables = new LinkedHashSet<Actionable>(Arrays.asList(actionables));
	}
	
	public void add(Actionable actionable) {
		actionables.add(actionable);
	}
	
	public void clear() {
		actionables.clear();
	}
	
	@Override
	public Action asAction(HostAction host) {
		return new ForkAction(host, actionables);
	}
	
	protected class ForkAction extends HostAction {
		protected final ActionConductor scheduler;
		
		protected boolean isInterruptScheduled = false;
		
		protected ForkAction(HostAction host, Collection<Actionable> actionables) {
			super(host);
			
			this.scheduler = new ActionConductor();			
			for(Actionable actionable : actionables) scheduler.add(actionable, this);
		}

		@Override
		protected void execute() {
			scheduler.run();
		}

		@Override
		protected boolean isFinished() {
			return !scheduler.hasActions();
		}

		@Override
		protected void interruptChildren() {
			scheduler.interrupt();
		}

		@Override
		protected void handleInterruptFromChild(Action interruptSource) { /* Do nothing */ }
	}
}
