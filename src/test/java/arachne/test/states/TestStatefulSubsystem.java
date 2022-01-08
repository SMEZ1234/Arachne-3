package arachne.test.states;

import org.junit.jupiter.api.Test;

import arachne.lib.sequences.actions.Action;
import arachne.lib.states.State;
import arachne.lib.states.StatefulSubsystem;

public class TestStatefulSubsystem
{
	static class TestActionableStates {
		static class TestSubsystem extends StatefulSubsystem<TestStates, TestSubsystem> {
			protected TestSubsystem(TestStates initialState) {
				super(initialState, TestStates.class);
			}

			@Override
			protected TestSubsystem getSelf() {
				return this;
			}
		}
		
		static enum TestStates implements State<TestStates, TestSubsystem> {
			ACTIONABLE_STATE {
				@Override
				public Action createStateAction(TestSubsystem subsystem) {
					return new Action(null) {
						@Override
						protected void initialize() {
							System.out.println("Initalizing");
						}
						
						@Override
						protected void execute() {
							System.out.println("Executing");
						}
						
						@Override
						protected void end() {
							System.out.println("Ending");
						}
					};
				}
			}
		}
		
		@Test
		void testActionableStates() {
			
		}
	}
}
