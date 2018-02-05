

public abstract interface OthelloAlgorithm
{
  public abstract void setEvaluator(OthelloEvaluator ev);
  
  public abstract OthelloAction evaluate(OthelloPosition o);
  
  public abstract void setSearchDepth(int d);
}
