package arachne.lib.sequences;

import java.util.Set;

import arachne.lib.io.GettableBoolean;
import arachne.lib.io.SettableBoolean;
import arachne.lib.pipeline.BooleanSource;
import arachne.lib.scheduler.Schedulable;

public class BooleanBindingAction extends Action
{
	protected BooleanSource source;
	protected Set<SettableBoolean> outputs;
	protected Schedulable feedMethod;
	protected GettableBoolean endingCondition;

	public BooleanBindingAction(HostAction host, BooleanSource source, Set<SettableBoolean> outputs, Schedulable feedMethod, GettableBoolean endingCondition) {
		super(host);
		
		this.source = source;
		this.outputs = outputs;
		this.feedMethod = feedMethod;
		this.endingCondition = endingCondition;
	}
	
	@Override
	protected void initialize() {
		for(SettableBoolean output : outputs) source.attachOutput(output);
	}
	
	@Override
	protected void execute() {
		if(feedMethod != null) feedMethod.run();
	}
	
	@Override
	protected void end() {
		for(SettableBoolean output : outputs) source.detachOutput(output);
	}
	
	@Override
	protected boolean isFinished() {
		return endingCondition.get();
	}
}
