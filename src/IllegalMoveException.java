

@SuppressWarnings("serial")
public class IllegalMoveException
extends Exception
{
private OthelloAction action;

public IllegalMoveException(OthelloAction o)
{
  this.action = o;
}

public OthelloAction getAction()
{
  return this.action;
}
}
