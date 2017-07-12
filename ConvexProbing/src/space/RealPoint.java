package space;

import org.apache.commons.math3.fraction.Fraction;

/**
 * A real point is immutable
 *
 */
public interface RealPoint {
	public int getDimension();//Get the dimensions of the space that this point exists in.
	public boolean equals(RealPoint otherPoint);
	public double getAxisValue(int axis);
	public double dot(RealPoint otherPoint);
	public RealPoint scaleBy(double scalar);
	public double getMagnitude();
	public RealPoint negate();
	
}
