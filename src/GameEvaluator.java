

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

public class GameEvaluator
implements OthelloEvaluator
{
	
public int evaluate(AbstractOthelloPosition OthPos1)
{
	int eval =0;
	
	OthelloPosition OthPos = (OthelloPosition) OthPos1;
	
	ArrayList<OthelloAction> Possible_moves = OthPos.getMoves();
	int move_num = Possible_moves.size();
	for(int i=0; i<Possible_moves.size();i++){
			
	//checking corners first
		if((Possible_moves.get(i).getRow() == 1) && (Possible_moves.get(i).getColumn() == 1) || (Possible_moves.get(i).getRow() == 1) &&(Possible_moves.get(i).getColumn() == 8) 
				|| (Possible_moves.get(i).getRow() == 8) &&(Possible_moves.get(i).getColumn() == 1) || (Possible_moves.get(i).getRow() == 8) &&(Possible_moves.get(i).getColumn() == 8)){
			eval += sign(OthPos)*10  + board_counter(OthPos);
		}
		// checking risky region8
		else if((Possible_moves.get(i).getRow() == 2) || (Possible_moves.get(i).getRow() == 7) || (Possible_moves.get(i).getColumn() == 2) || (Possible_moves.get(i).getColumn() == 7)){
			eval += sign(OthPos)*(-10  -board_counter(OthPos));
		}
		// good regions to place a piece
		else if((Possible_moves.get(i).getRow() == 1) && ((Possible_moves.get(i).getColumn() == 3) || (Possible_moves.get(i).getColumn() == 4) || (Possible_moves.get(i).getColumn() == 5) || (Possible_moves.get(i).getColumn() == 6))){
			eval +=sign(OthPos)*5 + board_counter(OthPos);
		}
		else if((Possible_moves.get(i).getRow() == 8) && ((Possible_moves.get(i).getColumn() == 3) || (Possible_moves.get(i).getColumn() == 4) || (Possible_moves.get(i).getColumn() == 5) || (Possible_moves.get(i).getColumn() == 6))){
			eval += sign(OthPos)*5 + board_counter(OthPos);
		}
		else if((Possible_moves.get(i).getColumn() == 1) && ((Possible_moves.get(i).getRow() == 3) || (Possible_moves.get(i).getRow() == 4) || (Possible_moves.get(i).getRow() == 5) || (Possible_moves.get(i).getRow() == 6))){
			eval += sign(OthPos)*5 + board_counter(OthPos);
		}
		else if((Possible_moves.get(i).getColumn() == 8) && ((Possible_moves.get(i).getRow() == 3) || (Possible_moves.get(i).getRow() == 4) || (Possible_moves.get(i).getRow() == 5) || (Possible_moves.get(i).getRow() == 6))){
			eval += sign(OthPos)*5 + board_counter(OthPos);
		}
		else		
			eval += sign(OthPos)*board_counter(OthPos);
					
				}
	return eval;
	}

	/*
		
public int evaluate(AbstractOthelloPosition OthPos1)
{
	int wScore =0;
	int bScore = 0;
	OthelloPosition OthPos = (OthelloPosition) OthPos1;
	
	ArrayList<OthelloAction> Possible_moves = OthPos.getMoves();
	int num_moves = Possible_moves.size();
	//System.out.println(num_moves);
	//while(Possible_moves.size()>0){
	for(int i=1; i<=8;i++){
		for(int j=1; j<=8;j++){
			
		if(OthPos.board[i][j] == 'W'){
			
	//checking corners first
		if(i==1 && j == 1 || i == 1 && j == 8 
				|| i == 8 && j == 1 || i == 8 && j == 8){
			wScore += 10;
		}
		// checking risky regions
		else if(i == 2 || i == 7 ||  j== 2 || j == 7){
			wScore += -5;
		}
		// good regions to place a piece
		else if(i == 1  && (j == 3 || j == 4 || j == 5 || j == 6)){
			wScore +=8;
		}
		else if(i == 8  && (j == 3 || j == 4 || j == 5 || j == 6)){
			wScore += 8;
		}
		else if(j == 1 && (i == 3 || i== 4 || i == 5 || i == 6)){
			wScore += 8;
		}
		else if(j == 8 && (i == 3 || i == 4 || i == 5 || i == 6)){
			wScore += 8;
		}
		else		
			wScore += 3;
					
				}
		else if(OthPos.board[i][j]=='B'){
			if(i==1 && j == 1 || i == 1 && j == 8 
					|| i == 8 && j == 1 || i == 8 && j == 8){
				bScore += 10;
			}
			// checking risky regions
			else if(i == 2 || i == 7 ||  j== 2 || j == 7){
				bScore += -5;
			}
			// good regions to place a piece
			else if(i == 1  && (j == 3 || j == 4 || j == 5 || j == 6)){
				bScore +=8;
			}
			else if(i == 8  && (j == 3 || j == 4 || j == 5 || j == 6)){
				bScore += 8;
			}
			else if(j == 1 && (i == 3 || i== 4 || i == 5 || i == 6)){
				bScore += 8;
			}
			else if(j == 8 && (i == 3 || i == 4 || i == 5 || i == 6)){
				bScore += 8;
			}
			else		
				bScore += 3;
						
					}
	
			
			
		}
	}
		
	if(!OthPos.toMove())
		return (wScore - bScore) + -1*(board_counter(OthPos))+num_moves;
		//return sign(OthPos)*bScore;
	else
		return bScore-wScore + board_counter(OthPos) +num_moves;
		//return sign(OthPos)*wScore;

	}

*/


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
