package arachne.lib.sequences.bindings;

import java.util.LinkedHashSet;
import java.util.Set;

import arachne.lib.io.GettableBoolean;
import arachne.lib.io.SettableDouble;
import arachne.lib.pipeline.DoubleSource;
import arachne.lib.scheduler.Schedulable;
import arachne.lib.sequences.Actionable;
import arachne.lib.sequences.actions.Action;
import arachne.lib.sequences.actions.HostAction;

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
