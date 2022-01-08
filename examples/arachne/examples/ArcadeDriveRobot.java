package arachne.examples;

import arachne.lib.hardware.DifferentialDrivetrain;
import arachne.lib.hardware.SimpleDifferentialDrivetrain;
import arachne.lib.pipeline.SimpleDoubleSource;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class ArcadeDriveRobot extends RobotBase
{
	public static DifferentialDrivetrain drivetrain = new SimpleDifferentialDrivetrain(false);
	
	@Override
	@SuppressWarnings("resource")
	public void startCompetition() {
		Joystick controller = new Joystick(0);
		
		SpeedController
				leftMotor = new Talon(0),
				rightMotor = new Talon(1);

		new SimpleDoubleSource(controller::getX).attachOutput(drivetrain.arcadeSource.getRotationInput());
		new SimpleDoubleSource(controller::getY).attachOutput(drivetrain.arcadeSource.getSpeedInput());

		drivetrain.getLeftOutput().attachOutput(leftMotor::set);
		drivetrain.getLeftOutput().attachOutput(rightMotor::set);
	}
}
