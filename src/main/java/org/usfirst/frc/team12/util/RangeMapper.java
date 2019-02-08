package org.usfirst.frc.team12.util;

/**
 * Mapping number from a origin range to a target range
 */
public final class RangeMapper {
	private double origA,origB,targA,targB;
	/**
	 * Create RangeMapper instace. 
	 * @param oLower origin range lower bound
	 * @param oUpper origin range upper bound
	 * @param tLower target range lower bound
	 * @param tUpper target range upper bound
	 */
	public RangeMapper(double oLower, double oUpper, double tLower, double tUpper){
		origA = oLower; origB = oUpper; targA = tLower; targB = tUpper;
	}

	/**
	 * Create RangeMapper instance with default origin range from -1.0 to 1.0
	 * @param tLower target range lower bound
	 * @param tUpper target range upper bound
	 */
	public RangeMapper(double tLower, double tUpper) {
		this(-1.0,1.0,tLower,tUpper);
	}

	/**
	 * Map number to target bound
	 * @param s number to map
	 * @return mapped number in target bound
	 * @throws IllegalArgumentException if {@code s} out of origin range
	 */
	public double mapRange(double s) throws IllegalArgumentException{
		if( !inRange(s,origA,origB) ) {
			throw new IllegalArgumentException("s not in available range");
		}
		return targA + ((s - origA)*(targB - targA))/(origB - origA);
	}
	
	/**
	 * Check if value is in destinated range
	 * @param s number to check
	 * @param rA range upper bound
	 * @param rB range lower bound
	 * @return is number in range
	 */
	private static boolean inRange(double s, double rA, double rB) {
		double a = Math.min(rA, rB), b = Math.max(rA, rB);
		return ( s >= a && s <= b );
	}
}
