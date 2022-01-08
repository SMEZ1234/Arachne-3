package arachne.tapestry;

import java.util.function.DoubleUnaryOperator;

import arachne.lib.logic.ArachneMath;

public class LogicTapestry
{
	public static DoubleUnaryOperator inBounds(double min, double max) {
		return (value) -> ArachneMath.inBounds(value, min, max);
	}
	
	public static DoubleUnaryOperator wrapAround(double min, double max) {
		return (value) -> ArachneMath.wrapAround(value, min, max);
	}
	
	public static DoubleUnaryOperator signedPow(double exponent) {
		return (value) -> ArachneMath.signedPow(value, exponent);
	}
}
