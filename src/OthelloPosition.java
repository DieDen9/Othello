
import java.io.PrintStream;
import java.util.ArrayList;

public class OthelloPosition
  extends AbstractOthelloPosition
{
  protected static final int BOARD_SIZE = 8;
  protected boolean playerToMove;
  protected char[][] board;
  
  public OthelloPosition()
  {
    this.board = new char[10][10];
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        this.board[i][j] = 69;
      }
    }
  }
  
  public OthelloPosition(String str)
  {
    int i;
    int j;
    if (str.length() != 65)
    {
      this.board = new char[10][10];
      for (i = 0; i < 10; i++) {
        for (j = 0; j < 10; j++) {
          this.board[i][j] = 69;
        }
      }
    }
    else
    {
      this.board = new char[10][10];
      if (str.charAt(0) == 'W') {
        this.playerToMove = true;
      } else {
        this.playerToMove = false;
      }
      for (i = 1; i <= 64; i++)
      {
    	  char c;
        if (str.charAt(i) == 'E') {
          c= 'E';
        } else if (str.charAt(i) == 'O') {
          c = 'W';
        } else {
          c = 'B';
        }
        int k = (i - 1) % 8 + 1;
        int m = (i - 1) / 8 + 1;
        this.board[m][k] = c;
      }
    }
  }
  
  public void initialize()
  {
    this.board[4][4] = (this.board[5][5] = 'W');
    this.board[4][5] = (this.board[5][4] = 'B');
    this.playerToMove = true;
  }
  
  public ArrayList<OthelloAction> getMoves()
  {
    boolean[][] arrayOfBoolean = new boolean[8][8];
    ArrayList<OthelloAction> possible_moves = new ArrayList<OthelloAction>();
    int j;
    for (int i = 0; i < 8; i++) {
      for (j = 0; j < 8; j++) {
        arrayOfBoolean[i][j] = possibleMove(i + 1, j + 1);
      }
    }
    for (int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        if ((possibleMove(x+1,y+1) && 
          (validMove(x + 1, y + 1)))) {
          possible_moves.add(new OthelloAction(x+ 1, y + 1));
        }
      }
    }
    return possible_moves;
  }
  
  private boolean validMove(int r, int c)
  {
    if ((checkUp(r, c)) || (checkDown(r, c)) || (checkRight(r, c)) || (checkLeft(r, c)) || 
    	(checkUpRight(r, c)) || (checkUpLeft(r, c)) || (checkDownLeft(r, c)) || (checkDownRight(r, c)) ){
    	return true;
    }
    return false;
  }
  
  private boolean checkUp(int r, int c)
  {
    if (!checkEnemy(r - 1, c)) {
      return false;
    }
    for (int i = r - 2; i > 0; i--)
    {
      if (isEmpty(i, c)) {
        return false;
      }
      if (checkFrndly(i, c)) {
        return true;
      }
    }
    return false;
  }
  
  private boolean checkRight(int r, int c)
  {
    if (!checkEnemy(r, c + 1)) {
      return false;
    }
    for (int i = c + 2; i <= 8; i++)
    {
      if (isEmpty(r, i)) {
        return false;
      }
      if (checkFrndly(r, i)) {
        return true;
      }
    }
    return false;
  }
  
  private boolean checkDown(int r, int c)
  {
    if (!checkEnemy(r + 1, c)) {
      return false;
    }
    for (int i = r + 2; i <= 8; i++)
    {
      if (isEmpty(i, c)) {
        return false;
      }
      if (checkFrndly(i, c)) {
        return true;
      }
    }
    return false;
  }
  
  private boolean checkLeft(int r, int c)
  {
    if (!checkEnemy(r, c - 1)) {
      return false;
    }
    for (int i = c - 2; i > 0; i--)
    {
      if (isEmpty(r, i)) {
        return false;
      }
      if (checkFrndly(r, i)) {
        return true;
      }
    }
    return false;
  }
  
  private boolean checkUpRight(int r, int c)
  {
    if (!checkEnemy(r - 1, c + 1)) {
      return false;
    }
    for (int i = 2; (r - i > 0) && (c + i <= 8); i++)
    {
      if (isEmpty(r - i, c + i)) {
        return false;
      }
      if (checkFrndly(r - i, c + i)) {
        return true;
      }
    }
    return false;
  }
  private boolean checkUpLeft(int r, int c)
  {
    if (!checkEnemy(r - 1, c - 1)) {
      return false;
    }
    for (int i = 2; (r - i > 0) && (c - i > 0); i++)
    {
      if (isEmpty(r - i, c - i)) {
        return false;
      }
      if (checkFrndly(r - i, c - i)) {
        return true;
      }
    }
    return false;
  }
  
  private boolean checkDownRight(int r, int c)
  {
    if (!checkEnemy(r + 1, c + 1)) {
      return false;
    }
    for (int i = 2; (r + i <= 8) && (c + i <= 8); i++)
    {
      if (isEmpty(r + i, c + i)) {
        return false;
      }
      if (checkFrndly(r + i, c + i)) {
        return true;
      }
    }
    return false;
  }
  
  private boolean checkDownLeft(int r, int c)
  {
    if (!checkEnemy(r + 1, c - 1)) {
      return false;
    }
    for (int i = 2; (r + i <= 8) && (c - i > 0); i++)
    {
      if (isEmpty(r + i, c - i)) {
        return false;
      }
      if (checkFrndly(r + i, c - i)) {
        return true;
      }
    }
    return false;
  }
  

  
  private boolean checkEnemy(int r, int c)
  {
    if ((this.playerToMove) && (this.board[r][c] == 'B')) {
      return true;
    }
    if ((!this.playerToMove) && (this.board[r][c] == 'W')) {
      return true;
    }
    return false;
  }
  
  private boolean checkFrndly(int r, int c)
  {
    if ((!this.playerToMove) && (this.board[r][c] == 'B')) {
      return true;
    }
    if ((this.playerToMove) && (this.board[r][c] == 'W')) {
      return true;
    }
    return false;
  }
  
  private boolean possibleMove(int r, int c)
  {
    if (!isEmpty(r, c)) {
      return false;
    }
    if (checkNeighbor(r, c)) {
      return true;
    }
    return false;
  }
  
  private boolean checkNeighbor(int r, int c)
  {
    if ((!isEmpty(r - 1, c)) || (!isEmpty(r - 1, c + 1)) ||(!isEmpty(r , c + 1)) || (!isEmpty(r + 1, c + 1)) || (!isEmpty(r + 1, c))
    		|| (!isEmpty(r + 1, c - 1)) || (!isEmpty(r, c - 1)) || (!isEmpty(r - 1, c - 1)))
    		{
      return true;
    }
    
    return false;
  }
  
  private boolean isEmpty(int r, int c)
  {
    if (this.board[r][c] == 'E') {
      return true;
    }
    return false;
  }
  
  public boolean toMove()
  {
    return this.playerToMove;
  }
  
  public OthelloPosition makeMove(OthelloAction action)
  {
    OthelloPosition localOthelloPosition = clone();
    OthelloAction localOthelloAction = action;
    int i = localOthelloAction.row;
    int j = localOthelloAction.column;
    if (action.isPassMove())
    {
      localOthelloPosition.playerToMove = (!this.playerToMove);
      return localOthelloPosition;
    }
    if ((i == 0) || (j == 0)) {
      System.out.println("error!");
    }
    if (this.playerToMove) {
      localOthelloPosition.board[localOthelloAction.row][localOthelloAction.column] = 'W';
    } else {
      localOthelloPosition.board[localOthelloAction.row][localOthelloAction.column] = 'B';
    }
    int k;
    if (checkUp(i, j))
    {
      k = i - 1;
      while (checkEnemy(k, j))
      {
        localOthelloPosition.flip(k, j);
        k--;
      }
    }
    if (checkRight(i, j))
    {
      k = j + 1;
      while (checkEnemy(i, k))
      {
        localOthelloPosition.flip(i, k);
        k++;
      }
    }
    if (checkDown(i, j))
    {
      k = i + 1;
      while (checkEnemy(k, j))
      {
        localOthelloPosition.flip(k, j);
        k++;
      }
    }
    if (checkLeft(i, j))
    {
      k = j - 1;
      while (checkEnemy(i, k))
      {
        localOthelloPosition.flip(i, k);
        k--;
      }
    }
    int m;
    if (checkUpRight(i, j))
    {
      k = i - 1;
      m = j + 1;
      while (checkEnemy(k, m))
      {
        localOthelloPosition.flip(k, m);
        k--;
        m++;
      }
    }
    if (checkDownRight(i, j))
    {
      k = i + 1;
      m = j + 1;
      while (checkEnemy(k, m))
      {
        localOthelloPosition.flip(k, m);
        k++;
        m++;
      }
    }
    if (checkDownLeft(i, j))
    {
      k = i + 1;
      m = j - 1;
      while (checkEnemy(k, m))
      {
        localOthelloPosition.flip(k, m);
        k++;
        m--;
      }
    }
    if (checkUpLeft(i, j))
    {
      k = i - 1;
      m = j - 1;
      while (checkEnemy(k, m))
      {
        localOthelloPosition.flip(k, m);
        k--;
        m--;
      }
    }
    localOthelloPosition.playerToMove = (!this.playerToMove);
    return localOthelloPosition;
  }
  
  private void flip(int r, int c)
  {
    if (this.playerToMove) {
      this.board[r][c] = 'W';
    } else {
      this.board[r][c] = 'B';
    }
  }
  
  protected OthelloPosition clone()
  {
    OthelloPosition localOthelloPosition = new OthelloPosition();
    localOthelloPosition.playerToMove = this.playerToMove;
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        localOthelloPosition.board[i][j] = this.board[i][j];
      }
    }
    return localOthelloPosition;
  }
  
  public int evaluate()
  {
    int i = 0;
    int j = 0;
    for (int k = 1; k <= 8; k++) {
      for (int m = 1; m <= 8; m++)
      {
        if (this.board[k][m] == 'W') {
          j++;
        }
        if (this.board[k][m] == 'B') {
          i++;
        }
      }
    }
    return j - i;
  }
  
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
  
  public String toString()
  {
    String str = "";
    if (this.playerToMove) {
      str = str + "W";
    } else {
      str = str + "B";
    }
    for (int j = 1; j <= 8; j++) {
      for (int k = 1; k <= 8; k++)
      {
        int i = this.board[j][k];
        char c;
        if (i == 87) {
          c = 'O';
        } else if (i == 66) {
          c = 'X';
        } else {
          c = 'E';
        }
        str = str + c;
      }
    }
    return str;
  }
}

