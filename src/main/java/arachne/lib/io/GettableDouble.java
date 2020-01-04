package arachne.lib.io;

import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleUnaryOperator;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

@FunctionalInterface
public interface GettableDouble extends DoubleSupplier
{
	double get();
	
	@Override
	default double getAsDouble() {
		return get();
	}
	
	default GettableBoolean is(double target) {
		return () -> get() == target;
	}
	
	default GettableBoolean is(DoublePredicate predicate) {
		return () -> predicate.test(get());
	}
	
	default GettableDouble withModifier(DoubleUnaryOperator modifier) {
		return () -> modifier.applyAsDouble(get());
	}
	
	default PIDSource asPIDSource(PIDSourceType pidSource) {
		return new PIDSource() {
			private PIDSourceType type = pidSource;
			
			@Override
			public double pidGet() {
				return GettableDouble.this.get();
			}
			
			@Override
			public PIDSourceType getPIDSourceType() {
				return type;
			}
			
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				this.type = pidSource;
			}
		};
	}
}
