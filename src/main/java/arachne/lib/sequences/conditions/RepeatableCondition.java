package arachne.lib.sequences.conditions;

import arachne.lib.io.GettableBoolean;

public abstract class RepeatableCondition implements GettableBoolean
{
	protected boolean shouldRepeat, isInitialized;
	
	public RepeatableCondition() {
		this(true);
	}
	
	public RepeatableCondition(boolean shouldRepeat) {
		this.shouldRepeat = shouldRepeat;
		this.isInitialized = false;
	}
	
	@Override
	public boolean get() {
		if(!isInitialized) {
			initialize();
			isInitialized = true;
		}
		
		if(condition()) {
			if(shouldRepeat) {
				reset();
				isInitialized = false;
			}
			return true;
		}
		
		return false;
	}

	protected void initialize() {}
	protected void reset() {}
	
	protected abstract boolean condition();
}
