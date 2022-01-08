package arachne.lib.sequences.bindings;

import java.util.Set;

import arachne.lib.io.GettableBoolean;
import arachne.lib.io.Settable;
import arachne.lib.pipeline.Source;
import arachne.lib.scheduler.Schedulable;
import arachne.lib.sequences.actions.Action;
import arachne.lib.sequences.actions.HostAction;

public class BindingAction<T> extends Action
{
	protected Source<T> source;
	protected Set<Settable<T>> outputs;
	protected Schedulable feedMethod;
	protected GettableBoolean endingCondition;

	public BindingAction(HostAction host, Source<T> source, Set<Settable<T>> outputs, Schedulable feedMethod, GettableBoolean endingCondition) {
		super(host);
		
		this.source = source;
		this.outputs = outputs;
		this.feedMethod = feedMethod;
		this.endingCondition = endingCondition;
	}
	
	@Override
	protected void initialize() {
		for(Settable<T> output : outputs) source.attach(output);
	}
	
	@Override
	protected void execute() {
		if(feedMethod != null) feedMethod.run();
	}
	
	@Override
	protected void end() {
		for(Settable<T> output : outputs) source.detach(output);
	}
	
	@Override
	protected boolean isFinished() {
		return endingCondition.get();
	}
}
