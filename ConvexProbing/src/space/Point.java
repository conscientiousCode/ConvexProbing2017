package space;

import org.apache.commons.math3.fraction.Fraction;

public class Point implements RealPoint{

	/*
	 * How many different axis there are in this space
	 * */
	@Override
	public int getDimension() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/*
	 * Two points are equal iff they have the same dimension, and the same value for each axis
	 * */
	@Override
	public boolean equals(RealPoint otherPoint) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * Returns the value of an axis
	 * */
	@Override
	public Fraction getCoordinateValue() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Treat points as vectors and return their dot product
	 * */
	@Override
	public Fraction dot(RealPoint otherPoint) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Treat this point as a vector and return a new point has each axis value scaled by 'scalar'
	 * */
	@Override
	public Fraction scaleBy(Fraction scalar) {
		// TODO Auto-generated method stub
		return null;
	}

}
