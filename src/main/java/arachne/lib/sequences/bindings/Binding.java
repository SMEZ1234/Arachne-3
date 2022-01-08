package arachne.lib.sequences.bindings;

import java.util.LinkedHashSet;
import java.util.Set;

import arachne.lib.io.GettableBoolean;
import arachne.lib.io.Settable;
import arachne.lib.pipeline.Source;
import arachne.lib.scheduler.Schedulable;
import arachne.lib.sequences.Actionable;
import arachne.lib.sequences.actions.Action;
import arachne.lib.sequences.actions.HostAction;

public class Binding<T> implements Actionable
{
	protected Source<T> source;
	protected Set<Settable<T>> outputs;
	protected Schedulable feedMethod;
	protected GettableBoolean endingCondition;
	
	@SafeVarargs
	public Binding(Source<T> source, Schedulable feedMethod, GettableBoolean endingCondition, Settable<T>... outputs) {
		this.source = source;
		this.feedMethod = feedMethod;
		this.endingCondition = endingCondition;
		
		this.outputs = new LinkedHashSet<Settable<T>>();
		for(Settable<T> output : outputs) this.outputs.add(output);
	}
	
	@Override
	public Action asAction(HostAction host) {
		return new BindingAction<T>(host, source, outputs, feedMethod, endingCondition);
	}
}
