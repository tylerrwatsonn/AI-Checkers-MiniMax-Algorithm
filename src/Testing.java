import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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
	public static BoardTree constructTree(Board b, BoardTree bt, int depth) {
		//get children
		if(b == null) {
			b = new Board();
		}
		if(bt == null) {
			bt = new BoardTree(b);
		}	
		constructChildren(bt);
		
		int totalBoards = 0;
	
//		b.printBoard();
//		BoardTree temp = bt;
//		for(int i = 0; i < depth; i++) {
//			constructChildren(temp);
//			for(BoardTree child: temp.getChildren()) {
//				constructChildren(child);
//			}
//			
//		}
		
		for(BoardTree childBT: bt.getChildren()) {
			totalBoards++;
			System.out.println("\tCHILD: HVal = "+childBT.getHeuristicValue() + "\tCOLOUR "+ childBT.getColour());
			//childBT.getBoard().printBoard();
			constructChildren(childBT);
			for(BoardTree grandChildBT: childBT.getChildren()) {
				totalBoards++;
				System.out.println("\t\tGRANDCHILD: "+grandChildBT.getHeuristicValue() + "\tCOLOUR "+ grandChildBT.getColour());
				//grandChildBT.getBoard().printBoard();
				constructChildren(grandChildBT);
				for(BoardTree greatGrandChildBT: grandChildBT.getChildren()) {
					totalBoards++;
					constructChildren(greatGrandChildBT);
					System.out.println("\t\t\tGREATGRANDCHILD: "+ greatGrandChildBT.getHeuristicValue() + "\tCOLOUR "+ greatGrandChildBT.getColour());
					//greatGrandChildBT.getBoard().printBoard();
					for(BoardTree greatGreatGrandChildBT: greatGrandChildBT.getChildren()) {
						constructChildren(greatGreatGrandChildBT);
						System.out.println("\t\t\t\tGREATGREATGRANDCHILD: "+ greatGreatGrandChildBT.getHeuristicValue() + "\tCOLOUR "+ greatGreatGrandChildBT.getColour());

					}
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
	public static Board kingMoves() {
		Board board = new Board();
		Move move1 = new Move(5,0,4,1);
		board.move(move1, Player.Colour.RED);
		Move move2 = new Move(4,1,3,2);
		board.move(move2, Player.Colour.RED);
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
		constructTree(board, null, 3);
		return board;
	}
	
	/**
	 * 
	 * @param board
	 * @param depth
	 * @param c
	 * @param maximizingPlayer
	 * @return best move based on minimax
	 */
	public static Move findBestMiniMaxMove(Board board, int depth, Player.Colour c, boolean maximizingPlayer) {
		//constructTree
		BoardTree bt = constructTree(board, null, depth);
		
		ArrayList<Double> heuristics = new ArrayList<Double>();
		
		//Iterate through children to populate heuristics list
		for(BoardTree child: bt.getChildren()) {
			double minimaxResult = minimaxPruning(child, depth-1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, maximizingPlayer);
			System.out.println("MINIMAX RESULT: " + minimaxResult);
			heuristics.add(minimaxResult);
		}
		
		ArrayList<Move> possibleMoves = bt.getBoard().generateListOfPossibleMoves(c);	//moves to choose from
		
		double maxHeuristic = Double.NEGATIVE_INFINITY;
		Random rand = new Random();
		
		//find max heuristic
		for(int i = 0; i < heuristics.size(); i++) {
			maxHeuristic = Math.max(maxHeuristic, heuristics.get(i));
			System.out.println("Heuristic: " + heuristics.get(i));
			bt.getChildren().get(i).getBoard().printBoard(possibleMoves.get(i));
		}
		System.out.println();
		System.out.println("Max heuristic: "+ maxHeuristic);
		
		//weed out any child with heuristic lower than the maximum
		for(int i = 0; i < heuristics.size(); i++) {
			if (heuristics.get(i) < maxHeuristic) {
				heuristics.remove(i);
				possibleMoves.remove(i);
				i--;
			}
		}
		
		int randInt = rand.nextInt(possibleMoves.size());
		if(randInt == 0) return null;
		return possibleMoves.get(randInt);
		
	}
	
	/**
	 * Minimax algorithm with alpha-beta pruning
	 * @param bt
	 * @param depth
	 * @param alpha	best score for black (computer)
	 * @param beta	best score for red (player)
	 * @param maximizingPlayer 
	 *
	 * @return best heuristic (double)
	 */
	public static double minimaxPruning(BoardTree bt, int depth, double alpha, double beta, boolean maximizingPlayer) {
		if (depth == 0) {
			return bt.getHeuristicValue();
		}
		
		if (maximizingPlayer) {	//MAXIMIZING PLAYER based on heuristic calculation
			double maxEval = Double.NEGATIVE_INFINITY;
			for (BoardTree child : bt.getChildren()) {
				double eval = minimaxPruning(child, depth - 1, alpha, beta, false);
				maxEval = Math.max(maxEval, eval);
				//PRUNING
				alpha = Math.max(alpha, eval);		//update alpha (black's best possible score
				if (beta <= alpha) break;	//this means that there is a better option for the previous player (red) earlier on in the tree so the rest of the nodes can be pruned
			}
			return maxEval;
		}
		
		else { //MINIMIZING PLAYER
			double minEval = Double.POSITIVE_INFINITY;
			for(BoardTree child : bt.getChildren()) {
				double eval = minimaxPruning(child, depth - 1, alpha, beta, true);
				//PRUNING
				minEval = Math.min(minEval, eval);
				beta = Math.min(eval, beta);	//update beta (red's best possible score)
				if (beta <= alpha) break;	//same idea as before
			}
			return minEval;
		}
	}
	
	public static double minimax(BoardTree bt, int depth, boolean maximizingPlayer) {
		if (depth == 0) {
			return bt.getHeuristicValue();
		}
		
		if (maximizingPlayer) {	//MAXIMIZING PLAYER based on heuristic calculation
			double maxEval = Double.NEGATIVE_INFINITY;
			for (BoardTree child : bt.getChildren()) {
				double eval = minimax(child, depth - 1, false);
				maxEval = Math.max(maxEval, eval);
			}
			return maxEval;
		}
		
		else { //MINIMIZING PLAYER
			double minEval = Double.POSITIVE_INFINITY;
			for(BoardTree child : bt.getChildren()) {
				double eval = minimax(child, depth - 1, true);
				//PRUNING
				minEval = Math.min(minEval, eval);
			}
			return minEval;
		}
	}
	
	/**
	 * Compare time between pruning and no pruning
	 * @param bt
	 */
	public static void comparePruningTime(BoardTree bt) {
		long start = Calendar.getInstance().getTimeInMillis();
		double minimaxPruningResult = minimaxPruning(bt, 3, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true);
		long end = Calendar.getInstance().getTimeInMillis();

		System.out.println("MiniMaxPruning result: " + minimaxPruningResult);
		System.out.println("MINIMAX TOOK: " + (end - start) + " ms");
		System.out.println();
		
		start = Calendar.getInstance().getTimeInMillis();
		double minimaxResult = minimax(bt, 3, true);
		end = Calendar.getInstance().getTimeInMillis();

		System.out.println("MiniMax result: " + minimaxResult);
		System.out.println("MINIMAX TOOK: " + (end - start) + " ms");
	}
	
	
	public static void main(String[] args) {
		int startRow, startCol, endRow, endCol;
		Board board = new Board();
//		BoardTree bt = new BoardTree();
		Scanner input = new Scanner(System.in);
//		Board board = kingMoves();
//		//constructTree(board, null, 3);
		boolean blackTurn = true;
//		Move move = findBestMiniMaxMove(board, 5, Player.Colour.BLACK, true);
//		board.printBoard();
//		board.move(move, Player.Colour.BLACK);
//		board.printBoard(move);
//		Move move2 = new Move(5,6,4,7);
//		board.move(move2, Player.Colour.RED);
//		board.printBoard(move2);
//		Move move3 = findBestMiniMaxMove(board, 5, Player.Colour.BLACK, true);
//		board.printBoard(move3);
//		board.move(move3, Player.Colour.BLACK);
//		board.printBoard();
		while(true) {
			while(blackTurn) {
				Move moveBlack = findBestMiniMaxMove(board, 5, Player.Colour.BLACK, true);
				board.printBoard();
				board.move(moveBlack, Player.Colour.BLACK);
				board.printBoard();
				System.out.println("\nStart Row: ");
				startRow = input.nextInt();
				System.out.println("\nStart Col: ");
				startCol = input.nextInt();
				System.out.println("\nEnd Row: ");
				endRow = input.nextInt();
				System.out.println("\nEnd Col: ");
				endCol = input.nextInt();
				Move moveRed = new Move(startRow, startCol, endRow, endCol);
				board.move(moveRed, Player.Colour.RED);
			}
		}
	}
}
