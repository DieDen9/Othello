
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


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
    }

    public MinMax(OthelloEvaluator ev) {
        this.evaluator = ev;
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
        this.tLimit = (t * 1000) - 20; //set time limit in Milliseconds
    }

    public void setStart(double tS) {
        this.tStart = tS;
    }

    public OthelloAction evaluate(OthelloPosition othPos) {

        OthelloPosition localOthelloPosition = (OthelloPosition) othPos;
        OthelloAction TempAction = new OthelloAction(0, 0);

        //  while (System.currentTimeMillis() - tStart < tLimit) {
        double tEnd = System.currentTimeMillis();
        //System.out.println(end -tStart);
        if (tEnd - tStart < tLimit) {
            if (localOthelloPosition.toMove() == true) {
                TempAction = getMax(localOthelloPosition, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, System.currentTimeMillis());
            } else
                TempAction = getMin(localOthelloPosition, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, System.currentTimeMillis());
        }


        return TempAction;

    }


    public int alphabeta(OthelloPosition pos, int depth, int alpha, int beta) {

        ArrayList moves = pos.getMoves();
        Iterator iterator = moves.iterator();
        OthelloAction act1;
        int value;
        int tempValue = 0;


        if (pos.toMove()) {
            System.out.println("depthMax" + depth);

            if (depth >= MaxDepth) {
                value = new GameEvaluator().evaluate(pos);
                return value;

            }
            if (System.currentTimeMillis() - tStart <= tLimit) {
            while (iterator.hasNext()) {


                    act1 = (OthelloAction) iterator.next();
                    OthelloPosition oPos = pos.makeMove(act1);
                    int nextDepth = depth + 1;
                    value = alphabeta(oPos, nextDepth, alpha, beta);

                    //beta cut-off
                    if (value > alpha) {
                        alpha = value;
                    }
                    if (beta <= alpha) {
                        break;
                    }
                }

            }
            return alpha;

        } else

        {
             System.out.println("depthMin:"+depth);


            if (depth >= MaxDepth) {

                value = new GameEvaluator().evaluate(pos);
                //System.out.println("value:" + value);
                return value;
            }
            if (System.currentTimeMillis() - tStart <= tLimit) {
                while (iterator.hasNext()) {


                    //System.out.println("min "+depth);

                    act1 = (OthelloAction) iterator.next();
                    OthelloPosition oPos = pos.makeMove(act1);
                    //System.out.println(alpha);
                    int nextDepth = depth + 1;
                    // System.out.println("depthMin:"+depth);

                    value = alphabeta(oPos, nextDepth, alpha, beta);

                    //beta cut-off
                    if (value < beta) {
                        beta = value;
                    }
                    if (beta <= alpha) {
                        break;
                    }
                }


            }
            return beta;
        }
        //System.out.println("value:" + tempValue);

    }

    public OthelloAction algorithm(OthelloPosition pos) {
        ArrayList<OthelloAction> possible_moves = pos.getMoves();
        Iterator<OthelloAction> moveIterator = possible_moves.iterator();
        List<Integer> values = new ArrayList();
        int max = Integer.MIN_VALUE;
        int maxIndex = -1;

        if (!moveIterator.hasNext()) {

            OthelloAction act1 = new OthelloAction(0, 0, true);
            act1.value = new GameEvaluator().evaluate(pos);
            return act1;
        }

        // while(System.currentTimeMillis() - tStart <= tLimit)


        while (moveIterator.hasNext()) {

            OthelloAction act = moveIterator.next();
            OthelloPosition oPos = pos.makeMove(act);
            int val = alphabeta(pos, 0, -10000, 10000);
            values.add(val);

        }
        for (int j = 0; j < values.size(); j++) {
            int item = values.get(j);
            if (item > max)
                max = item;
            maxIndex = j;

        }


      //  System.out.println(maxIndex);
      //  for (int k = 0; k < possible_moves.size(); k++) {
          //  System.out.println(possible_moves.get(k).getRow() + "," + possible_moves.get(k).getColumn());
          //  System.out.println(possible_moves.get(k).getValue());
       // }
        return possible_moves.get(maxIndex);
    }


    protected OthelloAction getMax(OthelloPosition pos, int depth, int alpha, int beta, double tEnd) {
        int min = Integer.MIN_VALUE;

        ArrayList<OthelloAction> moves = pos.getMoves();
        Iterator<OthelloAction> iterator = moves.iterator();
        OthelloAction act1;

        // while(tEnd-tStart < tLimit ){
        //System.out.println("depthMax: " + depth);
        //System.out.println("depthMax: " + depth);


        if (depth >= MaxDepth) {
            act1 = new OthelloAction(0, 0);
            act1.value = new GameEvaluator().evaluate(pos);
            return act1;

        } else if (!iterator.hasNext()) {
            act1 = new OthelloAction(0, 0, true);
            act1.value = new GameEvaluator().evaluate(pos);
            return act1;

        } else {
            OthelloAction act2 = new OthelloAction(0, 0);
            while (iterator.hasNext() && tEnd - tStart < tLimit) {
                act1 = (OthelloAction) iterator.next();
                OthelloPosition oPos = pos.makeMove(act1);
                //System.out.println(alpha);
                int nextDepth = depth + 1;
                OthelloAction act3 = getMin(oPos, nextDepth, alpha, beta, System.currentTimeMillis());
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
    }
    //}


    protected OthelloAction getMin(OthelloPosition pos, int depth, int alpha, int beta, double tEnd) {
        int max = Integer.MAX_VALUE;

        ArrayList<OthelloAction> moves = pos.getMoves();
        Iterator<OthelloAction> iterator = moves.iterator();
        OthelloAction act1;
        // OthelloAction TempAct = new OthelloAction(0, 0);
        //TempAct.value = new GameEvaluator().evaluate(pos);

        // System.out.println("depthMin: " + depth);
        // System.out.println("depthMin: " + depth);

        // if(tEnd-tStart < tLimit ){


        if (depth >= MaxDepth) {
            act1 = new OthelloAction(0, 0);
            act1.value = new GameEvaluator().evaluate(pos);
            //System.out.println(act1.value);
            return act1;

        } else if (!iterator.hasNext()) {
            act1 = new OthelloAction(0, 0, true);
            act1.value = new GameEvaluator().evaluate(pos);
            return act1;
        } else {
            OthelloAction act2 = new OthelloAction(0, 0);
            moves = pos.getMoves();
            iterator = moves.iterator();
            while (iterator.hasNext() && tEnd - tStart < tLimit) {

                act1 = (OthelloAction) iterator.next();
                OthelloPosition localOthelloPosition = pos.makeMove(act1);
                //  System.out.println("beta:"+ beta);
                int nextDepth = depth + 1;

                OthelloAction act3 = getMax(localOthelloPosition, nextDepth, alpha, beta, System.currentTimeMillis());
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
}
