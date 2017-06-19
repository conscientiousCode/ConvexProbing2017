package probing_algorithms;
import space.*;

public class Two_Dimensional_Probe {
	
	public static void main(String[] args){
		ConvexShape2D X = new ConvexShape2D(5);
		System.out.println(X.oracle(new Point(new double[]{1.0, 1.0})));
	}
	
	public static EdgeSet findConvexShape(ConvexShape2D X){
		X.oracle(new Point(new double[]{1.0, 1.0}));
		
		return null;
	}
}
