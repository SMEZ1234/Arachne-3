package arachne.lib.logic;

import java.util.function.DoubleFunction;
import java.util.function.DoubleUnaryOperator;

import arachne.lib.immutables.FlaggedDoubleValue;

// TODO Refactor to Tapestry
public class LinearMap
{
	protected final double min, max;
	protected final boolean isBounded;
	
	protected LinearMap(double min, double max, boolean isBounded) {
		this.min = min;
		this.max = max;
		this.isBounded = isBounded;
	}
	
	public static LinearMap map(double min, double max) {
		return new LinearMap(min, max, false);
	}
	
	public Until to(double start, double finish) {
		return new Until((d) -> {
			if(isBounded) d = ArachneMath.inBounds(d, this.min, this.max);
			
			return (d - this.min) * (finish - start) / (this.max - this.min) + start;
		});
	}
	
	public static final int
			REACHES_MIN = 1,
			REACHES_MAX = 1 << 1;
	
	public class Until implements DoubleUnaryOperator {
		protected final DoubleUnaryOperator mapping;
		
		protected Until(DoubleUnaryOperator mapping) {
			this.mapping = mapping;
		}
		
		@Override
		public double applyAsDouble(double operand) {
			return mapping.applyAsDouble(operand);
		}
		
		public DoubleFunction<FlaggedDoubleValue> until(int conditions) {
			return (value) -> {
				boolean flag = false;
				
				if((conditions & REACHES_MIN) > 0 && value <= LinearMap.this.min) {
					value = LinearMap.this.min;
					flag = true;
				}
				
				if((conditions & REACHES_MAX) > 0 && value >= LinearMap.this.max) {
					value = LinearMap.this.max;
					flag = true;
				}
				
				return new FlaggedDoubleValue(applyAsDouble(value), flag);
			};
		}
	}
}
