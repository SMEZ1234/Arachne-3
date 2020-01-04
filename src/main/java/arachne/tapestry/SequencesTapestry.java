package arachne.tapestry;

import java.util.function.Function;

import arachne.lib.io.Gettable;
import arachne.lib.io.GettableBoolean;
import arachne.lib.io.GettableDouble;
import arachne.lib.io.Settable;
import arachne.lib.io.SettableBoolean;
import arachne.lib.io.SettableDouble;
import arachne.lib.pipeline.BooleanSource;
import arachne.lib.pipeline.DoubleSource;
import arachne.lib.pipeline.Source;
import arachne.lib.requirements.RequirementManager;
import arachne.lib.scheduler.Schedulable;
import arachne.lib.scheduler.ScheduledBooleanSource;
import arachne.lib.scheduler.ScheduledDoubleSource;
import arachne.lib.scheduler.ScheduledSource;
import arachne.lib.sequences.Action;
import arachne.lib.sequences.Actionable;
import arachne.lib.sequences.BinarySelector;
import arachne.lib.sequences.Binding;
import arachne.lib.sequences.BooleanBinding;
import arachne.lib.sequences.DoubleBinding;
import arachne.lib.sequences.Fork;
import arachne.lib.sequences.HostAction;
import arachne.lib.sequences.Sequence;
import arachne.lib.sequences.WithRequirement;

public class SequencesTapestry
{
	// --------------------
	// Basic actionables
	// --------------------
	
	public static Actionable DO_NOTHING() {
		return Action::new;
	}
	
	public static Actionable DO(Schedulable schedulable) {
		return schedulable;
	}
	
	public static Actionable WAIT_UNTIL(GettableBoolean condition) {
		return condition;
	}
	
	public static Actionable SEQUENCE(Actionable... actionables) {
		return new Sequence(actionables);
	}
	
	// --------------------
	// Multi-actions
	// --------------------
	
	public static Split SPLIT(Actionable actionable) {
		return new Split(actionable);
	}
	
	public static final class Split implements Actionable {
		protected final Fork fork;
		
		protected Split(Actionable actionable) {
			this.fork = new Fork(actionable);
		}
		
		public final Split AND(Actionable actionable) {
			fork.add(actionable);
			return this;
		}

		@Override
		public final Action asAction(HostAction host) {
			return fork.asAction(host);
		}
	}
	
	// --------------------
	// Repetition
	// --------------------
	
	public static Repeat REPEAT(Schedulable schedulable) {
		return new Repeat(schedulable);
	}
	
	public static final class Repeat {
		protected final Schedulable schedulable;

		protected Repeat(Schedulable schedulable) {
			this.schedulable = schedulable;
		}
		
		public final Actionable UNTIL(GettableBoolean condition) {
			return (host) -> new Action(host) {
				@Override
				protected void execute() {
					Repeat.this.schedulable.run();
				}
				
				@Override
				protected boolean isFinished() {
					return condition.get();
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
	
	// --------------------
	// Temporary bindings
	// --------------------
	
	public static <T> Bind<T> FEED(Gettable<T> gettable) {
		return BIND(new ScheduledSource<T>(gettable), (source) -> source);
	}
	
	public static BindBoolean FEED(GettableBoolean gettable) {
		return BIND(new ScheduledBooleanSource(gettable), (source) -> source);
	}
	
	public static BindDouble FEED(GettableDouble gettable) {
		return BIND(new ScheduledDoubleSource(gettable), (source) -> source);
	}
	
	public static <T> Bind<T> BIND(Source<T> source) {
		return new Bind<T>(source, null);
	}
	
	public static BindBoolean BIND(BooleanSource source) {
		return new BindBoolean(source, null);
	}
	
	public static BindDouble BIND(DoubleSource source) {
		return new BindDouble(source, null);
	}
	
	public static <T, SourceT extends Source<T>> Bind<T> BIND(SourceT source, Function<SourceT, Schedulable> feedMethodGenerator) {
		return new Bind<T>(source, feedMethodGenerator.apply(source));
	}
	
	public static <SourceT extends BooleanSource> BindBoolean BIND(SourceT source, Function<SourceT, Schedulable> feedMethodGenerator) {
		return new BindBoolean(source, feedMethodGenerator.apply(source));
	}
	
	public static <SourceT extends DoubleSource> BindDouble BIND(SourceT source, Function<SourceT, Schedulable> feedMethodGenerator) {
		return new BindDouble(source, feedMethodGenerator.apply(source));
	}
	
	public static final class Bind<T> {
		protected final Source<T> source;
		protected final Schedulable feedMethod;
		
		protected Bind(Source<T> source, Schedulable feedMethod) {
			this.source = source;
			this.feedMethod = feedMethod;
		}
		
		@SafeVarargs
		public final BindTo<T> TO(Settable<T>... outputs) {
			return new BindTo<T>(source, feedMethod, outputs);
		}
	}
	
	public static final class BindBoolean {
		protected final BooleanSource source;
		protected final Schedulable feedMethod;
		
		protected BindBoolean(BooleanSource source, Schedulable feedMethod) {
			this.source = source;
			this.feedMethod = feedMethod;
		}
		
		public final BindBooleanTo TO(SettableBoolean... outputs) {
			return new BindBooleanTo(source, feedMethod, outputs);
		}
	}
	
	public static final class BindDouble {
		protected final DoubleSource source;
		protected final Schedulable feedMethod;
		
		protected BindDouble(DoubleSource source, Schedulable feedMethod) {
			this.source = source;
			this.feedMethod = feedMethod;
		}
		
		public final BindDoubleTo TO(SettableDouble... outputs) {
			return new BindDoubleTo(source, feedMethod, outputs);
		}
	}
	
	public static final class BindTo<T> {
		protected final Source<T> source;
		protected final Schedulable feedMethod;
		protected final Settable<T>[] outputs;
		
		@SafeVarargs
		protected BindTo(Source<T> source, Schedulable feedMethod, Settable<T>... outputs) {
			this.source = source;
			this.feedMethod = feedMethod;
			this.outputs = outputs;
		}
		
		public final Actionable UNTIL(GettableBoolean condition) {
			return new Binding<T>(source, feedMethod, condition, outputs);
		}
	}
	
	public static final class BindBooleanTo {
		protected final BooleanSource source;
		protected final Schedulable feedMethod;
		protected final SettableBoolean[] outputs;
		
		protected BindBooleanTo(BooleanSource source, Schedulable feedMethod, SettableBoolean... outputs) {
			this.source = source;
			this.feedMethod = feedMethod;
			this.outputs = outputs;
		}
		
		public final Actionable UNTIL(GettableBoolean condition) {
			return new BooleanBinding(source, feedMethod, condition, outputs);
		}
	}
	
	public static final class BindDoubleTo {
		protected final DoubleSource source;
		protected final Schedulable feedMethod;
		protected final SettableDouble[] outputs;
		
		protected BindDoubleTo(DoubleSource source, Schedulable feedMethod, SettableDouble... outputs) {
			this.source = source;
			this.feedMethod = feedMethod;
			this.outputs = outputs;
		}
		
		public final Actionable UNTIL(GettableBoolean condition) {
			return new DoubleBinding(source, feedMethod, condition, outputs);
		}
	}
}
