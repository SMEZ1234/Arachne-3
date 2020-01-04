package arachne.lib.sequences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import arachne.lib.io.GettableBoolean;

public class BinarySelector implements Actionable
{
	protected final List<BinaryOption> options;
	protected Actionable actionableAsElse;
	
	public BinarySelector() {
		this(null);
	}
	
	public BinarySelector(Actionable actionableAsElse, BinaryOption... options) {
		this.actionableAsElse = actionableAsElse;
		this.options = new ArrayList<BinaryOption>(Arrays.asList(options));
	}
	
	public BinarySelector addOption(GettableBoolean condition, Actionable actionable) {
		options.add(new BinaryOption(condition, actionable));
		return this;
	}
	
	public BinarySelector setElse(Actionable actionable) {
		this.actionableAsElse = actionable;
		return this;
	}

	@Override
	public Action asAction(HostAction host) {
		return new ActionWrapper(host) {
			@Override
			protected Action getNextAction(boolean isFirst) {
				if(!isFirst) return null;
				
				for(BinaryOption option : options) {
					if(option.get()) return option.asAction(this);
				}
				
				return actionableAsElse != null ? actionableAsElse.asAction(this) : null;
			}
		};
	}
	
	public static class BinaryOption implements Actionable, GettableBoolean {
		protected final GettableBoolean condition;
		protected final Actionable actionable;
		
		public BinaryOption(GettableBoolean condition, Actionable actionable) {
			this.condition = condition;
			this.actionable = actionable;
		}

		@Override
		public boolean get() {
			return condition.get();
		}

		@Override
		public Action asAction(HostAction host) {
			return actionable.asAction(host);
		}
	}
}
