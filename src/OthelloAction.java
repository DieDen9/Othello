import java.io.PrintStream;

public class OthelloAction {
    protected int row = -1;
    protected int column = -1;
    protected int value = 0;
    protected boolean pass = false;

    public OthelloAction(int n, int n2) {
        this.row = n;
        this.column = n2;
        this.value = 0;
    }

    public OthelloAction(int n, int n2, boolean bl) {
        this.row = n;
        this.column = n2;
        this.value = 0;
        this.pass = bl;
    }

    public OthelloAction(String string) {
        if (string.equals("pass")) {
            this.row = 0;
            this.column = 0;
            this.value = 0;
            this.pass = true;
        } else {
            this.row = new Integer(new Character(string.charAt(1)).toString());
            this.column = new Integer(new Character(string.charAt(3)).toString());
            this.value = 0;
        }
    }

    public void setValue(int n) {
        this.value = n;
    }

    public int getValue() {
        return this.value;
    }

    public void setColumn(int n) {
        this.column = n;
    }

    public int getColumn() {
        return this.column;
    }

    public void setRow(int n) {
        this.row = n;
    }

    public int getRow() {
        return this.row;
    }

    public void setPassMove(boolean bl) {
        this.pass = bl;
    }

    public boolean isPassMove() {
        return this.pass;
    }

    public void print() {
        if (this.pass) {
            System.out.println("pass");
        } else {
            System.out.println("(" + this.row + "," + this.column + ")");
        }
    }
}