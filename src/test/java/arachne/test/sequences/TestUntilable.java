package arachne.test.sequences;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arachne.lib.function.BooleanPredicate;
import arachne.lib.sequences.Actionable;
import arachne.lib.sequences.Untilable;
import arachne.lib.sequences.actions.Action;
import arachne.lib.sequences.actions.Action.ActionState;

import static org.junit.jupiter.api.Assertions.*;

public class TestUntilable
{
	boolean internalCondition, modifiedCondition;
	Untilable untilable;
	
	@BeforeEach
	void beforeEach() {
		internalCondition = false;
		modifiedCondition = false;
		
		untilable = (host, conditionModifier) -> new Action(host, conditionModifier) {
			@Override
			protected boolean isFinished() {
				return internalCondition;
			}
		};
	}
	
	@Test
	void withoutUntil() {
		Action action = untilable.asAction(null);
		assertEquals(action.act(), ActionState.RUNNING);
		
		internalCondition = true;
		assertEquals(action.act(), ActionState.FINISHED);
	}
	
	@Test
	void condition() {
		Actionable actionable = untilable.UNSAFE_UNTIL(() -> modifiedCondition);

		Action action = actionable.asAction(null);
		assertEquals(action.act(), ActionState.RUNNING);
		
		internalCondition = true;
		assertEquals(action.act(), ActionState.RUNNING);

		internalCondition = false;
		modifiedCondition = true;
		assertEquals(action.act(), ActionState.FINISHED);
	}
	
	@Test
	void modifier() {
		Actionable actionable = untilable.UNSAFE_UNTIL((internal) -> modifiedCondition);

		Action action = actionable.asAction(null);
		assertEquals(action.act(), ActionState.RUNNING);
		
		internalCondition = true;
		assertEquals(action.act(), ActionState.RUNNING);

		internalCondition = false;
		modifiedCondition = true;
		assertEquals(action.act(), ActionState.FINISHED);
	}
	
	@Test
	void modifierReceivesInternalCondition() {
		Actionable actionable = untilable.UNSAFE_UNTIL((internal) -> {
			assertEquals(internal, internalCondition);
			return internal;
		});

		Action action = actionable.asAction(null);
		assertEquals(action.act(), ActionState.RUNNING);
		
		internalCondition = true;
		assertEquals(action.act(), ActionState.FINISHED);
	}
	
	@Test
	void supplier() {
		Actionable actionable = untilable.UNTIL(() -> (internal) -> modifiedCondition);

		Action action = actionable.asAction(null);
		assertEquals(action.act(), ActionState.RUNNING);
		
		internalCondition = true;
		assertEquals(action.act(), ActionState.RUNNING);

		internalCondition = false;
		modifiedCondition = true;
		assertEquals(action.act(), ActionState.FINISHED);
	}
	
	@Test
	void supplierReceivesInternalCondition() {
		Actionable actionable = untilable.UNTIL(() -> (internal) -> {
			assertEquals(internal, internalCondition);
			return internal;
		});

		Action action = actionable.asAction(null);
		assertEquals(action.act(), ActionState.RUNNING);
		
		internalCondition = true;
		assertEquals(action.act(), ActionState.FINISHED);
	}
	
	@Test
	void supplierMakesMultipleInstances() {
		Actionable actionable = untilable.UNTIL(() -> new BooleanPredicate() {
			boolean firstCall = true;
			
			@Override
			public boolean test(boolean value) {
				if(firstCall) {
					firstCall = false;
					return false;
				}
				
				return true;
			}
		});

		Action firstAction = actionable.asAction(null);
		assertEquals(firstAction.act(), ActionState.RUNNING);
		assertEquals(firstAction.act(), ActionState.FINISHED);
		
		Action secondAction = actionable.asAction(null);
		assertEquals(secondAction.act(), ActionState.RUNNING);
		assertEquals(secondAction.act(), ActionState.FINISHED);
	}
}
