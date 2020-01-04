package arachne.lib.sequences;

import java.util.HashSet;
import java.util.Set;

import arachne.lib.requirements.RequirementManager;

public class WithRequirement implements Actionable
{
	protected final Actionable actionable;
	protected Set<RequirementManager<Action>> requirements;
	
	@SafeVarargs
	public WithRequirement(Actionable actionable, RequirementManager<Action>... requirements) {
		this.actionable = actionable;
		
		this.requirements = new HashSet<RequirementManager<Action>>();
		for(RequirementManager<Action> requirement : requirements) this.requirements.add(requirement);
	}
	
	@Override
	public Action asAction(HostAction host) {
		return new ActionWrapper(host) {
			@Override
			protected void initialize() {
				super.initialize();
				for(RequirementManager<Action> requirement : WithRequirement.this.requirements) requirement.acquire(this);
			}
			
			@Override
			protected void end() {
				super.end();
				for(RequirementManager<Action> requirement : WithRequirement.this.requirements) requirement.release(this);
			}
			
			@Override
			protected Action getNextAction(boolean isFirst) {
				return isFirst ? actionable.asAction(this) : null;
			}
		};
	}
}
