package arachne.lib.logic;

import java.util.function.DoublePredicate;

@FunctionalInterface
public interface DoubleComparison
{
	public boolean compare(double a, double b);
	
	public static DoublePredicate greaterThan(double val) {
		return d -> d > val;
	}
	
	public static DoublePredicate lessThan(double val) {
		return d -> d < val;
	}
	
	public static DoublePredicate greaterThanOrEqualTo(double val) {
		return d -> d >= val;
	}
	
	public static DoublePredicate lessThanOrEqualTo(double val) {
		return d -> d <= val;
	}
	
	public static DoublePredicate equal(double val) {
		return d -> d == val;
	}
	
	public static DoublePredicate between(double min, double max) {
		return d -> d >= min && d <= max;
	}
	
	public static Within within(double tolerance) {
		return new Within(tolerance);
	}
	
	public static class Within implements DoubleComparison
	{
		protected final double tolerance;
		
		protected Within(double tolerance) {
			this.tolerance = tolerance;
		}
		
		public boolean compare(double a, double b) {
			return Math.abs(a - b) <= tolerance;
		}
		
		public DoublePredicate of(double val) {
			return d -> compare(d, val);
		}
	}
}
