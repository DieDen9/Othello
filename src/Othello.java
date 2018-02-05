
public class Othello {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		   String str;
		   double Seconds = 6; // default value for time limit
		   //System.out.println(args.length+"here");
		    if (args.length > 0) {
		      str = args[0];
		      try{
		      Seconds = Double.parseDouble(args[1]);
		      } catch (IllegalArgumentException e){
		    	 System.out.println("Seconds must be number");
		      }
		    }		      
		     else {
		      str = "WEEEEEEEEEEEEEEEEEEEEEEEEEEEOXEEEEEEXOEEEEEEEEEEEEEEEEEEEEEEEEEEE";
		      System.out.println("No time limit was set, Using default time limit: "+Seconds);		      
		    }
		    OthelloPosition position = new OthelloPosition(str);
		    
		    MinMax mm = new MinMax(new GameEvaluator());
		    int depth = new DepthGenerator().CalculateDepth(Seconds);
		   // System.out.println(depth);
		   // mm.setSearchDepth(depth);
		    mm.setTimeLimit(Seconds);
		    mm.setStart(System.currentTimeMillis());
		    OthelloAction action = mm.evaluate(position);
		    
		    action.print();
		    //position.illustrate();
		  }

	}

