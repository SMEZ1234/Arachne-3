package arachne.lib.states;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import arachne.lib.io.GettableBoolean;
import arachne.lib.listeners.Signal;
import arachne.lib.scheduler.Schedulable;
import arachne.lib.scheduler.ScheduledSignal;
import arachne.lib.sequences.actions.Action;
import arachne.lib.systems.Subsystem;

public abstract class StatefulSubsystem<
	StateT extends Enum<?> & State<StateT, SubsystemT>,
	SubsystemT extends StatefulSubsystem<StateT, SubsystemT>
> extends Subsystem
{
	protected StateT state;
	protected Action currentStateAction;
	
	protected Set<Schedulable> currentStateBindings;
	protected Map<StateT, Set<Schedulable>> stateBindingMap;

	protected final StateT[] allStates;
	
	protected StatefulSubsystem(StateT initialState, Class<StateT> enumClass) {
		this.state = initialState;
		this.stateBindingMap = new HashMap<StateT, Set<Schedulable>>();
		
		this.allStates = enumClass.getEnumConstants();
		
		for(StateT state : allStates) stateBindingMap.put(state, new LinkedHashSet<Schedulable>());
	}
	
	@Override
	public void initialize() {
		for(StateT state : allStates) {
			state.initialize(getSelf());
			
			for(StateTransition<StateT> transition : state.getTransitions(getSelf())) {
				transition.getSignal().attach(() -> {
					if(getState() == state) setState(transition.getToState());
				});
			}
		}
		
		state.constructState(getSelf());
		currentStateBindings = stateBindingMap.get(this.state);
		
		currentStateAction = state.createStateAction(getSelf());
		if(currentStateAction != null) internalActionScheduler.add(currentStateAction);
	}
	
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
		if(currentStateAction != null && !currentStateAction.hasFinished()) currentStateAction.interrupt();
		this.state.deconstructState(getSelf());
		
		this.state = this.state.getTransitionalStateFor(state, getSelf());
		currentStateBindings = stateBindingMap.get(this.state);
		
		currentStateAction = this.state.createStateAction(getSelf());
		if(currentStateAction != null) internalActionScheduler.add(currentStateAction);
		
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
	
	public Signal signalWhen(StateT state, GettableBoolean condition) {
		return addBinding(state, new ScheduledSignal(condition));
	}
}
