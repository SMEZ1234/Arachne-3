package arachne.lib;

import arachne.lib.game.GameState;
import arachne.lib.logging.ArachneLogger;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.NotifierJNI;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArachneRobot extends RobotBase
{
	public static final double DEFAULT_PERIOD = 0.02;

	protected final double loopPeriod;
	protected final Watchdog watchdog;
	
	// The C pointer to the notifier object. We don't use it directly, it is
	// just passed to the JNI bindings.
	protected final int notifierHandle = NotifierJNI.initializeNotifier();

	protected GameState gameState;
	
	public ArachneRobot() {
		this(DEFAULT_PERIOD);
	}

	public ArachneRobot(double loopPeriod) {
		this.loopPeriod = loopPeriod;
	    this.watchdog = new Watchdog(loopPeriod, this::handleLoopOverrun);
	}

	@Override
	public void startCompetition() {
		initialize();

		// Tell the DS that the robot is ready to be enabled
		HAL.observeUserProgramStarting();

		double nextLoopTime = RobotController.getFPGATime() * 1e-6 + loopPeriod;
		updateAlarm(nextLoopTime);

		// Loop forever, calling Arachne's execution and state change functions
		while(true) {
			try {
				if(NotifierJNI.waitForNotifierAlarm(notifierHandle) == 0) break;

				nextLoopTime += loopPeriod;
				updateAlarm(nextLoopTime);

				loopFunc();
			}
			catch(Exception e) {
				errorFallback(e);
			}
		}
	}
	
	@Override
	public void endCompetition() {
		NotifierJNI.stopNotifier(notifierHandle);
		end();
	}

	/**
	 * Update the alarm hardware to reflect the next alarm.
	 */
	protected void updateAlarm(double nextLoopTime) {
		NotifierJNI.updateNotifierAlarm(notifierHandle, (long) (nextLoopTime * 1e6));
	}

	protected void loopFunc() {
		watchdog.reset();
		
		// Determine current state
		GameState newState;
		if(isDisabled()) newState = GameState.DISABLED;
		else if(isAutonomous()) newState = GameState.AUTO;
		else if(isOperatorControl()) newState = GameState.TELEOP;
		else newState = GameState.TELEOP;
		
		// Handle state change
		if(gameState != newState) {
			// Only enable live window and actuator widgets in test mode
			if(newState == GameState.TEST) {
				LiveWindow.setEnabled(true);
				Shuffleboard.enableActuatorWidgets();
			}
			else {
				LiveWindow.setEnabled(false);
				Shuffleboard.disableActuatorWidgets();
			}
			
			// Handle mode-specific state changes
			onStateChange(gameState, newState);
			watchdog.addEpoch("onStateChange: " + gameState + " -> " + newState);
		}
		
		// Execute periodic code for state
		gameState = newState;
		gameState.halObserver.run();
		
		execute(gameState);
		watchdog.addEpoch("execute: " + gameState);
		
		watchdog.disable();
		
		// Update displayed values
		SmartDashboard.updateValues();
		LiveWindow.updateValues();
		Shuffleboard.update();

		// Warn on loop time overruns
		if(watchdog.isExpired()) watchdog.printEpochs();
	}

	protected void initialize() {}
	protected void execute(GameState state) {}
	protected void onStateChange(GameState oldState, GameState newState) {}
	protected void end() {}
	
	protected void errorFallback(Exception exception) {
		ArachneLogger.getInstance().critical("Error from inside robot's loopFunc(): " + exception.getMessage());
		DriverStation.reportError(exception.getMessage(), exception.getStackTrace());
	}

	protected void handleLoopOverrun() {
		DriverStation.reportWarning("Loop time of " + loopPeriod + "s overrun\n", false);
	}
}
