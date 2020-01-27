import java.util.Arrays;
import java.util.List;

public class Testing {
	
	public Testing() {
		
	}
	
	/**
	 * Construct children of BoardTree
	 * @param bt
	 */
	static private void constructChildren(BoardTree bt) {
		Board b = bt.getBoard();
		List<Move> possibleMoves = b.generateListOfPossibleMoves(bt.getColour());
		
		for(int k = 0; k < possibleMoves.size(); k++) {
			Board nextBoard = new Board(b.copyBoard());
			nextBoard.move(possibleMoves.get(k), bt.getColour());
			Player.Colour nextColour = bt.getColour() == Player.Colour.BLACK ? Player.Colour.RED : Player.Colour.BLACK;
			BoardTree child = new BoardTree(nextBoard, nextColour);
			bt.addChild(child);
		}
	}
	
	/**
	 * Construct BoardTree for current Board
	 * @param b
	 * @param bt
	 */
	public static BoardTree constructTree(Board b, BoardTree bt) {
		//get children
		if(b == null) {
			b = new Board();
		}
		if(bt == null) {
			bt = new BoardTree(b);
		}	
		constructChildren(bt);
		
		int totalBoards = 0;
	
		b.printBoard();
		for(BoardTree childBT: bt.getChildren()) {
			totalBoards++;
			System.out.println("\tCHILD: HVal = "+childBT.getHeuristicValue());
			childBT.getBoard().printBoard();
			constructChildren(childBT);
			for(BoardTree grandChildBT: childBT.getChildren()) {
				totalBoards++;
				System.out.println("\t\tGRANDCHILD: "+grandChildBT.getHeuristicValue());
				grandChildBT.getBoard().printBoard();
				constructChildren(grandChildBT);
				for(BoardTree greatGrandChildBT: grandChildBT.getChildren()) {
					totalBoards++;
					//System.out.println("\t\t\tGREATGRANDCHILD: "+ greatGrandChildBT.getHeuristicValue());
					greatGrandChildBT.getBoard().printBoard();
				}
				System.out.println("***************************************");
			}
			System.out.println("-------------------------------------------");
		}
		System.out.println("TOTAL BOARDS: "+totalBoards);
		
		return bt;
	}
	
	/**
	 * method creates a king
	 */
	public void kingMoves() {
		Board board = new Board();
		Move move1 = new Move(5,0,4,1);
		board.move(move1, Player.Colour.RED);
		board.printBoard();
		Move move2 = new Move(4,1,3,2);
		board.move(move2, Player.Colour.RED);
		board.printBoard();
		Move move3 = new Move(2,3,4,1);
		board.move(move3, Player.Colour.BLACK);
		board.printBoard();
		Move move4 = new Move(4,1,5,0);
		board.move(move4, Player.Colour.BLACK);
		
		Move move5 = new Move(5,2,4,3);
		board.move(move5, Player.Colour.RED);
		Move move6 = new Move(4,3,3,2);
		board.move(move6, Player.Colour.RED);
		Move move7 = new Move(6,1,5,2);
		board.move(move7, Player.Colour.RED);
		Move move8 = new Move(5,2,4,3);
		board.move(move8, Player.Colour.RED);
		Move move9 = new Move(4,3,3,4);
		board.move(move9, Player.Colour.RED);
		Move move10 = new Move(7,0,6,1);
		board.move(move10, Player.Colour.RED);
		Move move11 = new Move(6,1,5,2);
		board.move(move11, Player.Colour.RED);
		
		Move move12 = new Move(5,0,6,1);
		board.move(move12, Player.Colour.BLACK);
		Move move13 = new Move(6,1,7,0);
		board.move(move13, Player.Colour.BLACK);
		Move move14 = new Move(7,0,6,1);
		board.move(move14, Player.Colour.BLACK);
		board.printBoard();

		Move move15 = new Move(6,1,4,3);
		board.move(move15, Player.Colour.BLACK);
		
		board.printBoard();
		constructTree(board, null);
	}
	
	
	
	/**
	 * Minimax algorithm with alpha-beta pruning
	 * @param bt
	 * @param depth
	 * @param alpha	best score for black (computer)
	 * @param beta	best score for red (player)
	 * @param c
	 * @return
	 */
	public static double minimax(BoardTree bt, int depth, double alpha, double beta, Player.Colour c) {
		if (depth == 0) {
			return bt.getHeuristicValue();
		}
		
		if (c == Player.Colour.BLACK) {	//MAXIMIZING PLAYER based on heuristic calculation
			double maxEval = Double.NEGATIVE_INFINITY;
			for (BoardTree child : bt.getChildren()) {
				double eval = minimax(child, depth - 1, alpha, beta, Player.Colour.RED);
				maxEval = Math.max(maxEval, eval);
				alpha = Math.max(alpha, eval);		//update alpha (black's best possible score
				if (beta <= alpha) break;	//this means that there is a better option for the previous player (red) earlier on in the tree so the rest of the nodes can be pruned
			}
			return maxEval;
		}
		
		else if (c == Player.Colour.RED) { //MINIMIZING PLAYER
			double minEval = Double.POSITIVE_INFINITY;
			for(BoardTree child : bt.getChildren()) {
				double eval = minimax(child, depth - 1, alpha, beta, Player.Colour.BLACK);
				minEval = Math.min(minEval, eval);
				beta = Math.min(eval, beta);	//update beta (red's best possible score)
				if (beta <= alpha) break;	//same idea as before
			}
			return minEval;
		}
		
		return 5.0;
	}
	
	public static void main(String[] args) {
		Board board = new Board();
		board.printBoard();
		System.out.println();
		
		Move move1 = new Move(5,0,4,1);
		board.move(move1, Player.Colour.RED);
		board.printBoard();
		Move move2 = new Move(4,1,3,2);
		board.move(move2, Player.Colour.RED);
		board.printBoard();
		Move move3 = new Move(2,3,4,1);
		board.move(move3, Player.Colour.BLACK);
		board.printBoard();
		Move move4 = new Move(4,1,5,0);
		board.move(move4, Player.Colour.BLACK);
		
		Move move5 = new Move(5,2,4,3);
		board.move(move5, Player.Colour.RED);
		Move move6 = new Move(4,3,3,2);
		board.move(move6, Player.Colour.RED);
		Move move7 = new Move(6,1,5,2);
		board.move(move7, Player.Colour.RED);
		Move move8 = new Move(5,2,4,3);
		board.move(move8, Player.Colour.RED);
		Move move9 = new Move(4,3,3,4);
		board.move(move9, Player.Colour.RED);
		Move move10 = new Move(7,0,6,1);
		board.move(move10, Player.Colour.RED);
		Move move11 = new Move(6,1,5,2);
		board.move(move11, Player.Colour.RED);
		
		Move move12 = new Move(5,0,6,1);
		board.move(move12, Player.Colour.BLACK);
		Move move13 = new Move(6,1,7,0);
		board.move(move13, Player.Colour.BLACK);
		board.printBoard();
//		Move move14 = new Move(7,0,6,1);
//		board.move(move14, Player.Colour.BLACK);
//		board.printBoard();
		
		BoardTree bt = constructTree(board, null);
		double minimaxResult = minimax(bt, 3, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Player.Colour.BLACK);
		System.out.println("MiniMax result: " + minimaxResult);
	}
}
