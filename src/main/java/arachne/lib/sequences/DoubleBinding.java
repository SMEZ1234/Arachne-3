package arachne.lib.sequences;

import java.util.LinkedHashSet;
import java.util.Set;

import arachne.lib.io.GettableBoolean;
import arachne.lib.io.SettableDouble;
import arachne.lib.pipeline.DoubleSource;
import arachne.lib.scheduler.Schedulable;

public class DoubleBinding implements Actionable
{
	protected DoubleSource source;
	protected Set<SettableDouble> outputs;
	protected Schedulable feedMethod;
	protected GettableBoolean endingCondition;
	
	public DoubleBinding(DoubleSource source, Schedulable feedMethod, GettableBoolean endingCondition, SettableDouble... outputs) {
		this.source = source;
		this.feedMethod = feedMethod;
		this.endingCondition = endingCondition;
		
		this.outputs = new LinkedHashSet<SettableDouble>();
		for(SettableDouble output : outputs) this.outputs.add(output);
	}
	
	@Override
	public Action asAction(HostAction host) {
		return new DoubleBindingAction(host, source, outputs, feedMethod, endingCondition);
	}
}
