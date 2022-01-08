package arachne.lib.types;

public enum TriState
{
	POSITIVE,
	ZERO,
	NEGATIVE;
	
	public static TriState fromBooleans(boolean positive, boolean negative) {
		if(positive) {
			if(negative) return ZERO;
			else return POSITIVE;
		}
		else if(negative) return NEGATIVE;
		
		return ZERO;
	}
	
	public static TriState fromDouble(Double value) {
		if(value > 0) return POSITIVE;
		else if(value < 0) return NEGATIVE;
		
		return ZERO;
	}
}
