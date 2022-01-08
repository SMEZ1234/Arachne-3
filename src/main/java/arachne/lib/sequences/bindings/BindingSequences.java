package arachne.lib.sequences.bindings;

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
import arachne.lib.scheduler.Schedulable;
import arachne.lib.scheduler.ScheduledBooleanSource;
import arachne.lib.scheduler.ScheduledDoubleSource;
import arachne.lib.scheduler.ScheduledSource;
import arachne.lib.sequences.Actionable;

@Deprecated
public class BindingSequences
{
	// --------------------
	// Temporary bindings
	// TODO Make Untilable
	// --------------------
	
	public static <T> Bind<T> FEED(T initialValue, Gettable<T> gettable) {
		return BIND(new ScheduledSource<T>(initialValue, gettable), (source) -> source);
	}
	
	public static BindBoolean FEED(boolean initialValue, GettableBoolean gettable) {
		return BIND(new ScheduledBooleanSource(initialValue, gettable), (source) -> source);
	}
	
	public static BindDouble FEED(double initialValue, GettableDouble gettable) {
		return BIND(new ScheduledDoubleSource(initialValue, gettable), (source) -> source);
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
