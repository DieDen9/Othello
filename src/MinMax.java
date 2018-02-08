
import java.util.ArrayList;
import java.util.Iterator;


public class MinMax
        implements OthelloAlgorithm {
    protected OthelloPosition position;

    protected int MaxDepth;
    protected static final boolean MaxPlayer = true;
    protected static final boolean MinPlayer = false;
    protected OthelloEvaluator evaluator;
    protected double tLimit;
    protected double tStart;
    protected int CDepth;
    protected static boolean timeFlag = false;
    protected int maxAllowedDepth = 15;

    public MinMax() {
        this.evaluator = new GameEvaluator();
        this.MaxDepth = 12;
    }

    public MinMax(OthelloEvaluator ev) {
        this.evaluator = ev;
        this.MaxDepth = 12;
    }

    public MinMax(OthelloEvaluator ev, int d) {
        this.evaluator = ev;
        this.MaxDepth = d;
    }


    public void setEvaluator(OthelloEvaluator ev) {
        this.evaluator = ev;
    }

    public void setMaxSearchDepth(int d) {
        this.MaxDepth = d;
    }

    public void setTimeLimit(double t) {
        this.tLimit = t * 1000; //set time limit in Milliseconds
    }

    public void setStart(double tS) {
        this.tStart = tS;
    }

    public OthelloAction evaluate(OthelloPosition othPos) {

        OthelloPosition localOthelloPosition = (OthelloPosition) othPos;
        OthelloAction TempAction = new OthelloAction(0, 0, true);

        while (System.currentTimeMillis() - tStart < tLimit) {
            double end = System.currentTimeMillis();
            //System.out.println(end -tStart);
            if (end - tStart < tLimit) {
                if (localOthelloPosition.toMove() == true) {
                    TempAction = getMax(localOthelloPosition, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, System.currentTimeMillis());
                } else
                    TempAction = getMin(localOthelloPosition, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, System.currentTimeMillis());
            }

        }
        return TempAction;

    }


    protected OthelloAction getMax(OthelloPosition pos, int depth, int alpha, int beta, double tEnd) {
        int min = Integer.MIN_VALUE;

        ArrayList<OthelloAction> moves = pos.getMoves();
        Iterator<OthelloAction> iterator = moves.iterator();
        OthelloAction act1;

        // while(tEnd-tStart < tLimit ){
<<<<<<< HEAD
        //System.out.println("depthMax: " + depth);
=======
     //   System.out.println("depthMax: " + depth);
>>>>>>> b15fead9f525d71961a522f382a0c3253376370d


        if (depth > MaxDepth) {
            act1 = new OthelloAction(0, 0);
            act1.value = new GameEvaluator().evaluate(pos);
            return act1;

        }
        if (!iterator.hasNext()) {
            act1 = new OthelloAction(0, 0, true);
            act1.value = new GameEvaluator().evaluate(pos);
            return act1;

        }
        OthelloAction act2 = new OthelloAction(0, 0);
        while (iterator.hasNext() && tEnd - tStart < tLimit) {
            act1 = (OthelloAction) iterator.next();
            OthelloPosition oPos = pos.makeMove(act1);
            OthelloAction act3 = getMin(oPos, depth + 1, alpha, beta, System.currentTimeMillis());
            if (min < act3.value) {
                min = act3.value;
                act1.value = min;
                act2 = act1;
            }
            //beta cut-off
            if (min >= beta) {
                act1.value = min;
                return act1;
            }
            if (min > alpha) {
                alpha = min;
            }
        }
        return act2;
    }
    //}


    protected OthelloAction getMin(OthelloPosition pos, int depth, int alpha, int beta, double tEnd) {
        int max = Integer.MAX_VALUE;

        ArrayList<OthelloAction> moves = pos.getMoves();
        Iterator<OthelloAction> iterator = moves.iterator();
        OthelloAction act1;
        OthelloAction TempAct = new OthelloAction(0, 0);
        //TempAct.value = new GameEvaluator().evaluate(pos);

<<<<<<< HEAD
       // System.out.println("depthMin: " + depth);
=======
        //System.out.println("depthMin: " + depth);
>>>>>>> b15fead9f525d71961a522f382a0c3253376370d

        // if(tEnd-tStart < tLimit ){


        if (depth > MaxDepth) {
            act1 = new OthelloAction(0, 0);
            act1.value = new GameEvaluator().evaluate(pos);
            //System.out.println(act1.value);
            return act1;

        }
        if (!iterator.hasNext()) {
            act1 = new OthelloAction(0, 0, true);
            act1.value = new GameEvaluator().evaluate(pos);
            return act1;
        }
        OthelloAction act2 = new OthelloAction(0, 0);
        moves = pos.getMoves();
        iterator = moves.iterator();
        while (iterator.hasNext() && tEnd - tStart < tLimit) {

            act1 = (OthelloAction) iterator.next();
            OthelloPosition localOthelloPosition = pos.makeMove(act1);
            OthelloAction act3 = getMax(localOthelloPosition, depth + 1, alpha, beta, System.currentTimeMillis());
            //System.out.println(act3.value);
            if (max > act3.value) {
                max = act3.value;
                act1.value = max;
                //act1.print();
                return act1;
            }
            // alpha cut-off
            if (max <= alpha) {
                act1.value = max;
                //act1.print();
                return act1;
            }
            if (max < beta) {
                beta = max;
            }
        }
        //act2.print();
        return act2;
        // }
        //return TempAct;
    }

}
