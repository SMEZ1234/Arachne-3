package arachne.lib.sequences;

import arachne.lib.io.GettableBoolean;

public class SimpleSelector implements Actionable
{
	protected final GettableBoolean condition;
	protected final Actionable actionableOnTrue;
	protected final Actionable actionableOnFalse;
	
	public SimpleSelector(GettableBoolean condition, Actionable actionableOnTrue) {
		this(condition, actionableOnTrue, null);
	}
	
	public SimpleSelector(GettableBoolean condition, Actionable actionableOnTrue, Actionable actionableOnFalse) {
		this.actionableOnTrue = actionableOnTrue;
		this.actionableOnFalse = actionableOnFalse;
		this.condition = condition;
	}

	@Override
	public Action asAction(HostAction host) {
		return new ActionWrapper(host) {
			@Override
			protected Action getNextAction(boolean isFirst) {
				if(!isFirst) return null;
				
				Actionable actionable = SimpleSelector.this.condition.get() ? SimpleSelector.this.actionableOnTrue : SimpleSelector.this.actionableOnFalse;
				return actionable != null ? actionable.asAction(this) : null;
			}
		};
	}
}
