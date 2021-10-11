/*
 * Atomic Object 2021 Programming Assessment Othello AI
 * Madeeha Zindah
 */
import java.util.ArrayList;

public class AI {
	
	/*Inner class created for the purpose of defining a "move" more easily.
	 * Length implies the distance between current piece and the piece empty
	 * (x, y) slot being visited. */
	public class Move 
    {
        int x;
        int y;
        int len;
        
        public Move(int xCoor, int yCoor, int length)
        {
        	x = xCoor;
        	y = yCoor;
        	len = length;
        }
    }
	
	int[] bestMove = new int[2];
	int player;
	int[][] board;
	static int lenOfMove = 0; //IMPORTANT: Used to evaluate length of a move outside a Move object
	static int numOfMoves = 0;
	static ArrayList<Move> validMoves = null;
	
	public AI() {
	}

	public int[] computeMove(GameState state) {
		System.out.println("AI returning canned move for game state - " + state);
		
		//Get current player and board and initialize vars
		player = state.getPlayer();
		board = state.getBoard();
		ArrayList<Move> bestMoves = new ArrayList<Move>();
		Move move = null;
		
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				if (board[i][j] != 0 && board[i][j] == player)
				{
					//Returns move w/ longest length associated w/ current piece
					move = evaluate(i, j);
					
					if (move != null)
						bestMoves.add(move); //Add best move for current piece into bestMoves
				}
			}
		}
		
		Move best;
		if (numOfMoves < 8) 
        {
            ++numOfMoves;
            best = shortestLength(bestMoves);
        }
		else
			best = longestLength(bestMoves);
		bestMove[0] = best.x;
		bestMove[1] = best.y;
		
		return bestMove;
	}
	
	//Evaluate all possible combinations of validMoves.
	Move evaluate(int row, int col)
	{
		validMoves = new ArrayList<Move>();
		int checks = 0;
		
		lenOfMove = 0;
		//Top: checking directly above current piece for valid move
		for (int i = row-1; i >= 0; i--)
		{
			checks = checks(i, col);
			if(checks == 1)
				break;
		}
		
		 lenOfMove = 0;
		 //Bottom: checking directly below current piece for valid move
		 for (int i = row+1; i <= 7; i++) 
		 {
			 checks = checks(i, col);
				if(checks == 1)
					break;
		 }
		  
		 
		 lenOfMove = 0;
		 //Left: Checking to the left of current piece for valid move
		 for (int i = col-1; i >= 0; i--) 
		 { 
			 checks = checks(row, i);
				if(checks == 1)
					break;
		 }
		  
		 lenOfMove = 0;
		 //Right: checking to the right of current piece for valid move
		 for (int i = col+1; i <= 7; i++) 
		 { 
			 checks = checks(row, i);
				if(checks == 1)
					break;
		 }

		 //Checking ALL diagonals for valid moves
		 
		 int a = row-1; int b = col-1; lenOfMove = 0;
		 //Top-Left
		 while(a >= 0 && b >= 0) 
		 { 
			 checks = checks(a, b);
				if(checks == 1)
					break;
			 a--; 
			 b--; 
		  }
		  
		  a = row-1; b = col+1; lenOfMove = 0; 
		  //Top-Right
		  while(a >= 0 && b <= 7) 
		  { 
			  checks = checks(a, b);
				if(checks == 1)
					break;
			  a--; 
			  b++; 
		  }
		  
		  a = row+1; b = col-1; lenOfMove = 0;
		  //Bottom-Left
		  while(a <= 7 && b >= 0) 
		  { 
			  checks = checks(a, b);
				if(checks == 1)
					break;
			  a++; 
			  b--; 
		  }
		  
		  a = row+1; b = col+1; lenOfMove = 0;
		  //Bottom-Right
		  while(a <= 7 && b <= 7) 
		  { 
			  checks = checks(a, b);
				if(checks == 1)
					break;
			  a++; 
			  b++; 
		  }
		
		//Check which move has the longest length and return as best move
		return longestLength(validMoves);
	}
	
	Move checkMoves(int a, int b) 
	{ 
		if(board[a][b] == 0) //If slot is empty
		{ 	
			if (lenOfMove > 0) //If there's only opposing players pieces between the move and current piece
				return new Move(a, b, lenOfMove); 
			return null;
		} 
		else if(board[a][b] == player) //If current player's piece is reached.
			return null;
		
		// If corner or edge piece, weigh higher than other pieces.
		if((a == 0 && b == 0) || (a == 0 && b==7) || (a == 7 && b == 0) || (a == 7 && b == 7))
			lenOfMove = lenOfMove + 4;
		else if((a == 0) || (b == 0) || (a == 7) || (b == 7))
			lenOfMove = lenOfMove + 2;
		else
			lenOfMove++; 
		return null; 
	}
	
	//Checks if moves are valid and adds them to list of valid moves.
	int checks (int row, int col)
	{
		Move check = checkMoves(row, col);
		if(check != null)
		{
			validMoves.add(check);
			return 1;
		}
		if(board[row][col] == player || lenOfMove == 0)
			return 1;
		return 0;
	}
	
	//Finds the valid move with the longest length between it and current piece
	Move longestLength(ArrayList<Move> arr)
	{
		if(arr.size() == 0)
			return null;
		Move bestMove = arr.get(0);
		for (int i = 1; i < arr.size(); i++)
	           if(arr.get(i).len > bestMove.len)
	        	   bestMove = arr.get(i);
	    return bestMove;
	}
	
	//Finds the valid move with the shortest length between it and current piece
	Move shortestLength(ArrayList<Move> arr)
	{
		if(arr.size() == 0)
			return null;
		Move bestMove = arr.get(0);
		for (int i = 1; i < arr.size(); i++)
	           if(arr.get(i).len < bestMove.len)
	        	   bestMove = arr.get(i);
	    return bestMove;
	}
}