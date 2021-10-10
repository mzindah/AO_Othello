/*
 * Atomic Object 2021 Programming Assessment Othello AI
 * Madeeha Zindah
 */
import java.util.ArrayList;

public class AI {
	
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
	static int lenOfMove; //Used to evaluate length of a move outside Move object
	
	public AI() {
	}

	public int[] computeMove(GameState state) {
		//System.out.println("AI returning canned move for game state - " + state);
		
		//get current player and board
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
					System.out.println("Board: " + i + " " + j);
					move = evaluate(i, j); //returns move w/ longest length associated w/ current piece
					
					if (move != null)
						bestMoves.add(move); //push best move for current piece into bestMoves
				}
			}
		}
		System.out.println("This the list of BestMoves: ");
		printList(bestMoves);

		//Find longest length in bestMoves
		Move best = longestLength(bestMoves);
		bestMove[0] = best.x;
		bestMove[1] = best.y;
		
		return bestMove;
	}
	
	Move evaluate(int row, int col)
	{
		ArrayList<Move> validMoves = new ArrayList<Move>();

		lenOfMove = 0; Move check = null;
		//top
		for (int i = row-1; i >= 0; i--)
		{
			check = checkMoves(i, col);
			if(check != null)
			{
				validMoves.add(check);
				break;
			}
			if(board[i][col] == player || lenOfMove == 0)
				break;
		}
		
		 lenOfMove = 0; check = null;
		 //bottom 
		 for (int i = row+1; i <= 7; i++) 
		 {
			 check = checkMoves(i, col);
				if(check != null)
				{
					validMoves.add(check);
					break;
				}
				if(board[i][col] == player || lenOfMove == 0)
					break;
		 }
		  
		 
		 lenOfMove = 0; check = null; 
		 //left 
		 for (int i = col-1; i >= 0; i--) 
		 { 
			 check = checkMoves(row, i); 
			 if(check != null) 
			 {
				 validMoves.add(check); 
				 break;
			 }
			 if(board[row][i] == player || lenOfMove == 0)
					break;
		 }
		  
		 lenOfMove = 0; check = null; 
		 //right 
		 for (int i = col+1; i <= 7; i++) 
		 { 
			 check = checkMoves(row, i); 
			 if(check != null) 
			 {
				 validMoves.add(check); 
				 break;
			 }
			 if(board[row][i] == player || lenOfMove == 0)
					break;
		 }

		 int a = row-1; int b = col-1; lenOfMove = 0; check = null; 
		 //top-left 
		 while(a >= 0 && b >= 0) 
		 { 
			 check = checkMoves(a, b); 
			 if(check != null) 
			 {
				 validMoves.add(check); 
				 break;
			 }
			 if(board[a][b] == player || lenOfMove == 0)
					break;
			 a--; 
			 b--; 
		  }
		  
		  a = row-1; b = col+1; lenOfMove = 0; check = null; 
		  //top-right 
		  while(a >= 0 && b <= 7) 
		  { 
			 check = checkMoves(a, b); 
			 if(check != null) 
			 {
				 validMoves.add(check); 
				 break;
			 }
			 if(board[a][b] == player || lenOfMove == 0)
					break;
			  a--; 
			  b++; 
		  }
		  
		  a = row+1; b = col-1; lenOfMove = 0; check = null; 
		  //bottom-left 
		  while(a <= 7 && b >= 0) 
		  { 
			  check = checkMoves(a, b); 
			  if(check != null) 
			  {
				 validMoves.add(check); 
				 break;
			  }
			  if(board[a][b] == player || lenOfMove == 0)
				  break;
			  a++; 
			  b--; 
		  }
		  
		  a = row+1; b = col+1; lenOfMove = 0; check = null; 
		  //bottom-right 
		  while(a <= 7 && b <= 7) 
		  { 
			  check = checkMoves(a, b); 
			  if(check != null) 
			  {
				 validMoves.add(check); 
				 break;
			  }
			  if(board[a][b] == player || lenOfMove == 0)
					break;
			  a++; 
			  b++; 
		  }
		
		//CHECK WHICH MOVE HAS THE LONGEST LENGTH AND RETURN AS BEST MOVE
		System.out.println("This is the list of valid moves: ");
		printList(validMoves);
		return longestLength(validMoves);
	}
	
	Move checkMoves(int a, int b) 
	{ 
		if(board[a][b] == 0) //reach empty space 
		{ 	
			if (lenOfMove > 0)
			{
				return new Move(a, b, lenOfMove); 
			}
			return null;
		} 
		else if(board[a][b] == player)
			return null; 
		lenOfMove++; 
		return null; 
	}
	
	Move longestLength(ArrayList<Move> arr)
	{
		if(arr.size() == 0)
			return null;
		Move bestMove = arr.get(0);
		for (int i = 1; i < arr.size(); i++)
	           if(arr.get(i).len > bestMove.len)
	        	   bestMove = arr.get(i);
		System.out.println("This is the best Move: " + bestMove.x + " " + bestMove.y + " " + bestMove.len);
	    return bestMove;
	}
	
	void printList(ArrayList<Move> arr)
	{
		System.out.println("Array Length: " + arr.size());
		for (int i = 0; i < arr.size(); i++)
		{
			System.out.println(arr.get(i).x + " " + arr.get(i).y);
			System.out.println();
		}
	}
	
	public static void main(String[] args)
	{
		GameState state = new GameState();
		int[][] arr = {{0,0,0,0,0,0,0,0},
					   {0,0,0,0,0,0,0,0},
				       {0,0,0,0,0,0,0,0},
				       {0,0,0,1,2,0,0,0},
				       {0,0,0,1,2,0,0,0},
				       {0,0,0,1,2,0,0,0},
				       {0,0,0,0,0,0,0,0},
				       {0,0,0,0,0,0,0,0}};
		state.setBoard(arr);
		state.setPlayer(1);
		state.setMaxTurnTime(15000);
		AI test = new AI();
		int[] move = test.computeMove(state);
		
		for(int i = 0; i < state.getBoard().length; i++)
		{
			for(int j = 0; j < state.getBoard()[i].length; j++)
				System.out.print(state.getBoard()[i][j] + " ");
		System.out.println("");
		}
		arr[move[0]][move[1]] = 1;
		state.setBoard(arr);
		
		System.out.println();
		for(int i = 0; i < state.getBoard().length; i++)
		{
			for(int j = 0; j < state.getBoard()[i].length; j++)
				System.out.print(state.getBoard()[i][j] + " ");
		System.out.println("");
		}
	}
}
