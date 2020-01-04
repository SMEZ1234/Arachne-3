package arachne.lib.io;

import java.util.function.DoubleConsumer;

import edu.wpi.first.wpilibj.PIDOutput;

@FunctionalInterface
public interface SettableDouble extends DoubleConsumer, PIDOutput
{
	@Override
	default void pidWrite(double output) {
		accept(output);
	}
}
