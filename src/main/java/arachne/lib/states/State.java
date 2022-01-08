package arachne.lib.states;

import java.util.ArrayList;
import java.util.List;

import arachne.lib.sequences.Actionable;
import arachne.lib.sequences.actions.Action;

public interface State<
	StateT extends Enum<?> & State<StateT, SubsystemT>,
	SubsystemT extends StatefulSubsystem<StateT, SubsystemT>
>
{
	default StateT getTransitionalStateFor(StateT nextState, SubsystemT subsystem) {
		return nextState;
	}
	
	default void initialize(SubsystemT subsystem) {}
	default void constructState(SubsystemT subsystem) {}
	default void deconstructState(SubsystemT subsystem) {}
	
	default void run(SubsystemT subsystem) {}
	
	default Action createStateAction(SubsystemT subsystem) {
		return Actionable.DO_NOTHING().asAction(null);
	}
	
	default List<StateTransition<StateT>> getTransitions(SubsystemT subsystem) {
		return new ArrayList<StateTransition<StateT>>();
	}
}
