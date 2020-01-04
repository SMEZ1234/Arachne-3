package arachne.lib.sequences;

import java.util.Set;

import arachne.lib.io.GettableBoolean;
import arachne.lib.io.SettableDouble;
import arachne.lib.pipeline.DoubleSource;
import arachne.lib.scheduler.Schedulable;

public class DoubleBindingAction extends Action
{
	protected DoubleSource source;
	protected Set<SettableDouble> outputs;
	protected Schedulable feedMethod;
	protected GettableBoolean endingCondition;

	public DoubleBindingAction(HostAction host, DoubleSource source, Set<SettableDouble> outputs, Schedulable feedMethod, GettableBoolean endingCondition) {
		super(host);
		
		this.source = source;
		this.outputs = outputs;
		this.feedMethod = feedMethod;
		this.endingCondition = endingCondition;
	}
	
	@Override
	protected void initialize() {
		for(SettableDouble output : outputs) source.attachOutput(output);
	}
	
	@Override
	protected void execute() {
		if(feedMethod != null) feedMethod.run();
	}
	
	@Override
	protected void end() {
		for(SettableDouble output : outputs) source.detachOutput(output);
	}
	
	@Override
	protected boolean isFinished() {
		return endingCondition.get();
	}
}
