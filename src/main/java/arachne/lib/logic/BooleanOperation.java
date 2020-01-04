package arachne.lib.logic;

import arachne.lib.io.GettableBoolean;
import arachne.lib.sequences.RepeatableCondition;

public class BooleanOperation
{
	public static GettableBoolean and(GettableBoolean... booleans) {
		return () -> {
			for(GettableBoolean bool : booleans) {
				if(!bool.get()) return false;
			}
			
			return true;
		};
	}
	
	public static GettableBoolean or(GettableBoolean... booleans) {
		return () -> {
			for(GettableBoolean bool : booleans) {
				if(bool.get()) return true;
			}
			
			return false;
		};
	}
	
	public static GettableBoolean not(GettableBoolean bool) {
		return () -> !bool.get();
	}
	
	public static GettableBoolean count(int target, GettableBoolean... booleans) {
		return count(target, target, booleans);
	}
	
	public static GettableBoolean count(int min, int max, GettableBoolean... booleans) {
		return () -> {
			int count = 0;
			
			for(GettableBoolean bool : booleans) {
				if(bool.get()) count++;
			}
			
			return count >= min && count <= max;
		};
	}

	public static GettableBoolean once(GettableBoolean... booleans) {
		return once(true, booleans);
	}
	
	public static GettableBoolean once(boolean resetOnCompletion, GettableBoolean... booleans) {
		return new RepeatableCondition(resetOnCompletion) {
			private final boolean[] occurred = new boolean[booleans.length];
			
			@Override
			protected void initialize() {
				for(int i = 0; i < occurred.length; i++) occurred[i] = false;
			}
			
			@Override
			protected boolean condition() {
				for(int i = 0; i < occurred.length; i++) {
					if(!booleans[i].get() && !occurred[i]) return false;
					else occurred[i] = true;
				}
				
				return true;
			}
		};
	}

	public static GettableBoolean inOrder(GettableBoolean... booleans) {
		return inOrder(true, booleans);
	}
	
	/**
	 * Returns a {@link RepeatableCondition} for a sequence of requirements
	 * 
	 * @param resetOnCompletion Whether the RepeatableCondition should reset to an un-initisalised state after completion
	 * @param booleans The sequence of requirements, in order
	 * 
	 * @return A RepeatableCondition for the sequence of requirements
	 */
	public static GettableBoolean inOrder(boolean resetOnCompletion, GettableBoolean... booleans) {
		return new RepeatableCondition(resetOnCompletion) {
			private int index;
			
			@Override
			protected void initialize() {
				index = 0;
			}
			
			@Override
			protected boolean condition() {
				while(index < booleans.length && booleans[index].get()) index++;
				
				return index == booleans.length;
			}
		};
	}
}
