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
			System.out.println("NEXT BOARD " + k);
			nextBoard.printBoard();
		}
		System.out.println("FOR THIS BOARD ");
		b.printBoard();
		System.out.println("CHILDREN SIZE: "+bt.getChildren().size());
		System.out.println();
	}
	
	/**
	 * Construct BoardTree for current Board
	 * @param b
	 * @param bt
	 */
	public static void constructTree(Board b, BoardTree bt) {
		//get children
		if(b == null) {
			b = new Board();
		}
		if(bt == null) {
			bt = new BoardTree(b);
		}
			
		constructChildren(bt);
	
		
		for(BoardTree childBT: bt.getChildren()) {
			constructChildren(childBT);
			for(BoardTree grandChildBT: childBT.getChildren()) {
				constructChildren(grandChildBT);
			}
		}
		
		
		
		
		
		
	}
	
	public static void main(String[] args) {
		Board board = new Board();
//		board.printBoard();
//		Move move1 = new Move(5,0,4,1);
//		board.move(move1, Player.Colour.RED);
//		board.printBoard();
//		Move move2 = new Move(5,2,4,3);
//		board.move(move2, Player.Colour.RED);
		board.printBoard();
		System.out.println();
		constructTree(board, null);
	}
}
