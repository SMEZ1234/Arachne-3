package arachne.lib.states;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import arachne.lib.scheduler.Schedulable;
import arachne.lib.systems.Subsystem;

public abstract class StatefulSubsystem<
	StateT extends Enum<?> & State<StateT, SubsystemT>,
	SubsystemT extends StatefulSubsystem<StateT, SubsystemT>
> extends Subsystem
{
	protected StateT state;
	
	protected Set<Schedulable> currentStateBindings;
	protected Map<StateT, Set<Schedulable>> stateBindingMap;
	
	protected StatefulSubsystem(StateT initialState, Class<StateT> enumClass) {
		this.state = initialState;
		this.stateBindingMap = new HashMap<StateT, Set<Schedulable>>();
		
		for(StateT state : enumClass.getEnumConstants()) {
			stateBindingMap.put(state, new LinkedHashSet<Schedulable>());
		}
	}
	
	@Override
	protected void initialize() {
		state.constructState(getSelf());
		currentStateBindings = stateBindingMap.get(this.state);
		_initialize();
	}
	
	protected abstract void _initialize();
	
	@Override
	public void run() {
		super.run();
		state.run(getSelf());
	}
	
	@Override
	public void runBindings() {
		super.runBindings();
		
		if(currentStateBindings != null) {
			for(Schedulable binding : currentStateBindings) binding.run();
		}
	}
	
	protected abstract SubsystemT getSelf();
	
	public StateT getState() {
		return state;
	}
	
	public void setState(StateT state) {
		this.state.deconstructState(getSelf());
		this.state = this.state.getTransitionalStateFor(state, getSelf());
		currentStateBindings = stateBindingMap.get(this.state);
		this.state.constructState(getSelf());
	}
	
	public <BindingT extends Schedulable> BindingT addBinding(StateT state, BindingT binding) {
		stateBindingMap.get(state).add(binding);
		return binding;
	}
	
	public void removeBinding(Schedulable binding) {
		super.removeBinding(binding);
		
		for(Set<Schedulable> stateBindings : stateBindingMap.values()) stateBindings.remove(binding);
	}
}
