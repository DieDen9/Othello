
public class DepthGenerator {
	
	
	public int CalculateDepth(double seconds){
		int bfact = 20; //assuming the branching factor = 20
		int calib_depth = 9; 
		double nodesNum = Math.pow(bfact, (3*calib_depth)/4);
		//Total Time = AccessTime per node * Number of nodes to be visited
		double NodeAccessTime = 7 /nodesNum;
		double value = logofBase(20, seconds/NodeAccessTime); // logarithm with base of branching factor (20)
		double depth = (4*value)/3;
		return (int) Math.floor(depth);	
		
		
	}
	public double logofBase(int base, double n){
		return Math.log(n)/Math.log(base);
	}
}