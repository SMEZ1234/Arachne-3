package arachne.lib.hardware;

import java.util.function.DoubleUnaryOperator;

import arachne.lib.io.SettableDouble;
import arachne.lib.listeners.DoubleProperty;
import arachne.lib.pipeline.DoublePipe;
import arachne.lib.pipeline.DoubleSource;
import arachne.lib.systems.Subsystem;

public abstract class DifferentialDrivetrain extends Subsystem
{
	protected final DoublePipe leftOutput, rightOutput;
	
	public final TankSource tankSource;
	public final ArcadeSource arcadeSource;
	
	public DifferentialDrivetrain() {
		this(null);
	}
	
	public DifferentialDrivetrain(DoubleUnaryOperator outputModifier) {
		tankSource = new TankSource();
		arcadeSource = new ArcadeSource();
		
		leftOutput = new DoublePipe();
		leftOutput.setModifier(outputModifier);
		
		rightOutput = new DoublePipe();
		rightOutput.setModifier(outputModifier);
	}

	public DoubleSource getLeftOutput() {
		return leftOutput;
	}
	
	public DoubleSource getRightOutput() {
		return rightOutput;
	}
	
	public class TankSource {
		protected final DoublePipe leftInput, rightInput;
		
		protected TankSource() {
			leftInput = new DoublePipe();
			rightInput = new DoublePipe();
		}
		
		public SettableDouble getLeftInput() {
			return leftInput;
		}
		
		public SettableDouble getRightInput() {
			return rightInput;
		}
	}
	
	public class ArcadeSource {
		protected final DoubleProperty speedInput, rotationInput;
		
		protected ArcadeSource() {
			speedInput = new DoubleProperty();
			rotationInput = new DoubleProperty();
		}
		
		public SettableDouble getSpeedInput() {
			return speedInput;
		}
		
		public SettableDouble getRotationInput() {
			return rotationInput;
		}
	}
}
