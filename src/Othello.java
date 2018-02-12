
public class Othello {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		   String str;
		   double Seconds = 3; // default value for time limit
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
				//System.out.println("No time limit was set, Using default time limit: "+Seconds);
		    }
		    OthelloPosition position = new OthelloPosition(str);
		    
		    MinMax mm = new MinMax(new GameEvaluator());
		    int Maxdepth = 12;
		   // System.out.println(depth);
		   mm.setMaxSearchDepth(Maxdepth);
		    mm.setTimeLimit(Seconds);
		    mm.setStart(System.currentTimeMillis());
		    OthelloAction action = mm.algorithm(position);
		   // System.out.println(action);
		    action.print();
		    //position.illustrate();
		  }

	}

