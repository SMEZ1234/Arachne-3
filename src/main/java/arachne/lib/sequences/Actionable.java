package arachne.lib.sequences;

import arachne.lib.function.BooleanPredicate;
import arachne.lib.io.GettableBoolean;
import arachne.lib.requirements.RequirementManager;
import arachne.lib.scheduler.Schedulable;
import arachne.lib.sequences.actions.Action;
import arachne.lib.sequences.actions.ActionWrapper;
import arachne.lib.sequences.actions.Fork;
import arachne.lib.sequences.actions.HostAction;
import arachne.lib.sequences.conditions.Clock;
import arachne.lib.sequences.conditions.ConditionSupplier;

@FunctionalInterface
public interface Actionable
{
	Action asAction(HostAction host);
	
	// --------------------
	// Basic actionables
	// --------------------
	
	public static Actionable DO_NOTHING() {
		return Action::new;
	}
	
	public static Actionable DO(Schedulable schedulable) {
		return schedulable;
	}
	
	public static Untilable SEQUENCE(Actionable... actionables) {
		return new Sequence(actionables);
	}

	// --------------------
	// Waiting
	// --------------------
	
	public static Untilable WAIT() {
		return (host, conditionModifier) -> new Action(host, conditionModifier) {
			@Override
			protected boolean isFinished() {
				return false;
			};
		};
	}
	
	public static Actionable WAIT(long milliseconds) {
		return ConditionSupplier.create(() -> Clock.delay(milliseconds).toPredicate());
	}
	
	// --------------------
	// Multi-actions
	// --------------------
	
	public static Split SPLIT(Actionable actionable) {
		return new Split(actionable);
	}
	
	public static final class Split implements Untilable {
		protected final Fork fork;
		
		protected Split(Actionable actionable) {
			this.fork = new Fork(actionable);
		}
		
		public final Split AND(Actionable actionable) {
			fork.add(actionable);
			return this;
		}

		@Override
		public final Action asAction(HostAction host, BooleanPredicate conditionModifier) {
			return fork.asAction(host, conditionModifier);
		}
	}
	
	// --------------------
	// Repetition
	// --------------------
	
	public static Repeat REPEAT(Schedulable schedulable) {
		return new Repeat(schedulable);
	}
	
	public static RepeatActionable REPEAT(Actionable actionable) {
		return new RepeatActionable(actionable);
	}
	
	public static final class Repeat implements Untilable {
		protected final Schedulable schedulable;

		protected Repeat(Schedulable schedulable) {
			this.schedulable = schedulable;
		}
		
		@Override
		public Action asAction(HostAction host, BooleanPredicate conditionModifier) {
			return new Action(host, conditionModifier) {
				@Override
				protected void execute() {
					Repeat.this.schedulable.run();
				}
				
				@Override
				protected boolean isFinished() {
					return false;
				}
			};
		}
	}
	
	public static final class RepeatActionable implements Actionable {
		protected final Actionable actionable;
		
		protected RepeatActionable(Actionable actionable) {
			this.actionable = actionable;
		}
		
		@Override
		public Action asAction(HostAction host) {
			return new ActionWrapper(host) {
				@Override
				protected Action getNextAction(boolean isFirst) {
					return RepeatActionable.this.actionable.asAction(this);
				}
			};
		}
	}
	
	// --------------------
	// Selection
	// --------------------
	
	public static If IF(GettableBoolean condition) {
		return new If(condition);
	}
	
	public static final class If {
		protected final BinarySelector selector;
		protected final GettableBoolean condition;
		
		protected If(GettableBoolean condition) {
			this(new BinarySelector(), condition);
		}
		
		protected If(BinarySelector selector, GettableBoolean condition) {
			this.selector = selector;
			this.condition = condition;
		}
		
		public final IfThen THEN(Actionable actionable) {
			return new IfThen(selector.addOption(condition, actionable));
		}
	}
	
	public static final class IfThen implements Actionable {
		protected final BinarySelector selector;
		
		protected IfThen(BinarySelector selector) {
			this.selector = selector;
		}

		@Override
		public final Action asAction(HostAction host) {
			return selector.asAction(host);
		}
		
		public final If ELSE_IF(GettableBoolean condition) {
			return new If(selector, condition);
		}
		
		public final Actionable ELSE(Actionable actionable) {
			return selector.setElse(actionable);
		}
	}
	
	// --------------------
	// Requirements and dependencies
	// --------------------
	
	@SafeVarargs
	public static Require REQUIRE(RequirementManager<Action>... requirements) {
		return new Require(requirements);
	}
	
	public static final class Require {
		protected final RequirementManager<Action>[] requirements;

		protected Require(RequirementManager<Action>[] requirements) {
			this.requirements = requirements;
		}
		
		public final Actionable FOR(Actionable actionable) {
			return new WithRequirement(actionable, requirements);
		}
	}
}
