package arachne.lib.hardware;

import java.util.function.DoublePredicate;

import arachne.lib.io.SettableBoolean;
import arachne.lib.listeners.BooleanProperty;
import arachne.lib.listeners.DoubleChangeHandler;
import arachne.lib.logic.ArachneMath;

public class SimpleDifferentialDrivetrain extends DifferentialDrivetrain
{
	protected final BooleanProperty isTankState;
	
	public SimpleDifferentialDrivetrain() {
		this(true, true);
	}
	
	public SimpleDifferentialDrivetrain(boolean isInitiallyTankDrive, boolean squareInputs) {
		this.isTankState = new BooleanProperty(isInitiallyTankDrive);
	}
	
	@Override
	protected void initialize() {
		initializeArcadeBindings();
		initializeTankBindings();
	}
	
	protected void initializeArcadeBindings() {
		DoubleChangeHandler feedArcadeDrive = (oldValue, newValue) -> {
			if(!isTankState.get()) arcadeDrive(arcadeSource.speedInput.get(), arcadeSource.rotationInput.get());
		};

		arcadeSource.speedInput.attach(feedArcadeDrive);
		arcadeSource.rotationInput.attach(feedArcadeDrive);
	}
	
	protected void initializeTankBindings() {
		DoublePredicate tankModeFilter = (value) -> isTankState.get();
		
		tankSource.leftInput.addFilter(tankModeFilter);
		tankSource.leftInput.attachOutput(leftOutput);
		
		tankSource.rightInput.addFilter(tankModeFilter);
		tankSource.rightInput.attachOutput(rightOutput);
	}
	
	protected void arcadeDrive(double speed, double rotation) {
		leftOutput.accept(ArachneMath.inBounds(speed + rotation, -1, 1));
		rightOutput.accept(ArachneMath.inBounds(speed - rotation, -1, 1));
	}
	
	public SettableBoolean getTankStateInput() {
		return isTankState;
	}
}
