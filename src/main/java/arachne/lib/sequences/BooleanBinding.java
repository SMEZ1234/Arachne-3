package arachne.lib.sequences;

import java.util.LinkedHashSet;
import java.util.Set;

import arachne.lib.io.GettableBoolean;
import arachne.lib.io.SettableBoolean;
import arachne.lib.pipeline.BooleanSource;
import arachne.lib.scheduler.Schedulable;

public class BooleanBinding implements Actionable
{
	protected BooleanSource source;
	protected Set<SettableBoolean> outputs;
	protected Schedulable feedMethod;
	protected GettableBoolean endingCondition;
	
	public BooleanBinding(BooleanSource source, Schedulable feedMethod, GettableBoolean endingCondition, SettableBoolean... outputs) {
		this.source = source;
		this.feedMethod = feedMethod;
		this.endingCondition = endingCondition;
		
		this.outputs = new LinkedHashSet<SettableBoolean>();
		for(SettableBoolean output : outputs) this.outputs.add(output);
	}
	
	@Override
	public Action asAction(HostAction host) {
		return new BooleanBindingAction(host, source, outputs, feedMethod, endingCondition);
	}
}
