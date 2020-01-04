package arachne.lib.hardware.adaptors;

import arachne.lib.io.GettableBoolean;
import arachne.lib.io.SettableBoolean;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class DoubleSolenoidBooleanAdaptor implements GettableBoolean, SettableBoolean
{
	protected DoubleSolenoid solenoid;

	public DoubleSolenoidBooleanAdaptor(DoubleSolenoid solenoid) {
		this.solenoid = solenoid;
	}

	public boolean get() {
		return solenoid.get() == Value.kForward;
	}

	@Override
	public void accept(boolean value) {
		solenoid.set(value ? Value.kForward : Value.kReverse);
	}
}
