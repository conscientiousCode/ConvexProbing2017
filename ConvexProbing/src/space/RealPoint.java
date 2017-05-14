package space;

import org.apache.commons.math3.fraction.Fraction;

public interface RealPoint {
	public int getDimension();//Get the dimensions of the space that this point exists in.
	public boolean equals(RealPoint otherPoint);
	public Fraction getCoordinateValue();
	public Fraction dot(RealPoint otherPoint);
	public Fraction scaleBy(Fraction scalar);
	
}
