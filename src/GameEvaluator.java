
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

public class GameEvaluator
		implements OthelloEvaluator
{

	public int evaluate(AbstractOthelloPosition OthPos1) {
		int eval = 0;

		OthelloPosition OthPos = (OthelloPosition) OthPos1;

		ArrayList<OthelloAction> Possible_moves = OthPos.getMoves();
		int move_num = Possible_moves.size();
		for (int i = 0; i < Possible_moves.size(); i++) {

			//checking corners first
			if ((Possible_moves.get(i).getRow() == 0 || Possible_moves.get(i).getRow() == 7) && (Possible_moves.get(i).getColumn() == 0 || Possible_moves.get(i).getColumn() == 7)) {
				//eval += sign(OthPos)*10  + board_counter(OthPos);
				eval += 15*sign(OthPos);
			}
			// checking risky region8
			else if ((Possible_moves.get(i).getRow() == 1) || (Possible_moves.get(i).getRow() == 6) && (Possible_moves.get(i).getColumn() == 1) || (Possible_moves.get(i).getColumn() == 6)) {
				//eval += sign(OthPos)*(-10  -board_counter(OthPos));
				eval += -5*sign(OthPos);
			}
			// good regions to place a piece
			else if (((Possible_moves.get(i).getRow() == 1) || (Possible_moves.get(i).getRow() == 6)) && ((Possible_moves.get(i).getColumn() == 0) || (Possible_moves.get(i).getColumn() == 7))) {
				//eval +=sign(OthPos)*5 + board_counter(OthPos);
				eval += 5*sign(OthPos);
			} else if (((Possible_moves.get(i).getRow() == 0) || (Possible_moves.get(i).getRow() == 7)) && ((Possible_moves.get(i).getColumn() == 1) || (Possible_moves.get(i).getColumn() == 6))) {
				//eval += sign(OthPos)*5 + board_counter(OthPos);
				eval += 5*sign(OthPos);
			} else if ((Possible_moves.get(i).getRow() == 2) || ((Possible_moves.get(i).getRow() == 5)) && ((Possible_moves.get(i).getColumn() == 0) || (Possible_moves.get(i).getColumn() == 7))) {
				//	eval += sign(OthPos)*5 + board_counter(OthPos);
				eval += 7*sign(OthPos);
			} else if ((Possible_moves.get(i).getRow() == 3) || ((Possible_moves.get(i).getRow() == 4)) && ((Possible_moves.get(i).getColumn() == 0) || (Possible_moves.get(i).getColumn() == 7))) {
				//	eval += sign(OthPos)*5 + board_counter(OthPos);
				eval += 7*sign(OthPos);
			} else if ((Possible_moves.get(i).getRow() == 0) || ((Possible_moves.get(i).getRow() == 7)) && ((Possible_moves.get(i).getColumn() == 2) || (Possible_moves.get(i).getColumn() == 5))) {
				//	eval += sign(OthPos)*5 + board_counter(OthPos);
				eval += 7*sign(OthPos);
			} else if ((Possible_moves.get(i).getRow() == 0) || ((Possible_moves.get(i).getRow() == 7)) && ((Possible_moves.get(i).getColumn() == 3) || (Possible_moves.get(i).getColumn() == 4))) {
				//	eval += sign(OthPos)*5 + board_counter(OthPos);
				eval += 7*sign(OthPos);
			} else
				eval += 1*sign(OthPos);

		}
		return eval;
	}



	public int board_counter(OthelloPosition pos){
		int x =0,y =0;
		for(int i = 1;i<=8;i++){
			for(int j=1;j<=8;j++){
				if(pos.board[i][j] =='W'){
					x++;
				}
				else if(pos.board[i][j]=='B'){
					y++;
				}

			}
		}
		return x-y;
	}

	public int sign(OthelloPosition pos){
		if(!pos.playerToMove)
		{
			//System.out.println(-1);

			return -1;
		}
		else
		{//System.out.println(1);
			return 1;
		}

	}


}