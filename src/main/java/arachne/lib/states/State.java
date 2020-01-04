package arachne.lib.states;

public interface State<
	StateT extends Enum<?> & State<StateT, SubsystemT>,
	SubsystemT extends StatefulSubsystem<StateT, SubsystemT>
>
{
	default StateT getTransitionalStateFor(StateT nextState, SubsystemT subsystem) {
		return nextState;
	}

	default void constructState(SubsystemT subsystem) {}
	default void deconstructState(SubsystemT subsystem) {}
	
	default void run(SubsystemT subsystem) {}
}
