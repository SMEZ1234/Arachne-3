package arachne.lib.logic;

public class ArachneMath
{
	public static double inBounds(double value, double min, double max) {
		return Math.min(Math.max(value, min), max);
	}
	
	public static double wrapAround(double value, double min, double max) {
		double diff = max - min;
		
		while(value < min) value += diff;
		while(value > max) value -= diff;
		
		return value;
	}
	
	public static double signedPow(double value, double exponent) {
		if(value < 0) return -Math.pow(-value, exponent);
		return Math.pow(value, exponent);
	}
}
