package arachne.lib.game;

import edu.wpi.first.hal.HAL;

public enum GameState
{
	DISABLED(HAL::observeUserProgramDisabled),
	AUTO(HAL::observeUserProgramAutonomous),
	TELEOP(HAL::observeUserProgramTeleop),
	TEST(HAL::observeUserProgramTest);
	
	public final Runnable halObserver;
	
	private GameState(Runnable halObserver) {
		this.halObserver = halObserver;
	}
}
