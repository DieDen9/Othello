import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;

public abstract class AbstractOthelloPosition
{
  protected static final int BOARD_SIZE = 8;
  protected boolean playerToMove;
  protected char[][] board;
  
  public void initialize()
  {
    this.board = new char[10][10];
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        this.board[i][j] = 69;
      }
    }
    this.board[4][4] = (this.board[5][5] = 87);
    this.board[4][5] = (this.board[5][4] = 66);
    this.playerToMove = true;
  }
  
  public abstract ArrayList<OthelloAction> getMoves();
  
  public boolean toMove()
  {
    return this.playerToMove;
  }
  
  public abstract AbstractOthelloPosition makeMove(OthelloAction paramOthelloAction)
    throws IllegalMoveException;
  
  public void illustrate()
  {
    System.out.print("   ");
    for (int i = 1; i <= 8; i++) {
      System.out.print("| " + i + " ");
    }
    System.out.println("|");
    printHorizontalBorder();
    for (int i = 1; i <= 8; i++)
    {
      System.out.print(" " + i + " ");
      for (int j = 1; j <= 8; j++) {
        if (this.board[i][j] == 'W') {
          System.out.print("| 0 ");
        } else if (this.board[i][j] == 'B') {
          System.out.print("| X ");
        } else {
          System.out.print("|   ");
        }
      }
      System.out.println("| " + i + " ");
      printHorizontalBorder();
    }
    System.out.print("   ");
    for (int i = 1; i <= 8; i++) {
      System.out.print("| " + i + " ");
    }
    System.out.println("|\n");
  }
  
  private void printHorizontalBorder()
  {
    System.out.print("---");
    for (int i = 1; i <= 8; i++) {
      System.out.print("|---");
    }
    System.out.println("|---");
  }
}
