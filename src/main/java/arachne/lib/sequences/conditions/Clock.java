package arachne.lib.sequences.conditions;

import arachne.lib.io.GettableBoolean;

public class Clock
{
	private Clock() {}
	
	public static GettableBoolean delay(long milliseconds) {
		return new RepeatableCondition() {
			long startTime;
			
			@Override
			protected void initialize() {
				startTime = System.currentTimeMillis();
			}
			
			@Override
			protected boolean condition() {
				return startTime + milliseconds <= System.currentTimeMillis();
			}
		};
	}
}
