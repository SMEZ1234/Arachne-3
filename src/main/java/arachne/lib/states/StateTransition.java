package arachne.lib.states;

import arachne.lib.listeners.Signal;

public class StateTransition<StateT extends Enum<?> & State<StateT, ?>>
{
	protected final Signal signal;

	protected final StateT toState;
	
	public StateTransition(Signal signal, StateT toState) {
		this.signal = signal;
		this.toState = toState;
	}
	
	public Signal getSignal() {
		return signal;
	}

	public StateT getToState() {
		return toState;
	}
}
