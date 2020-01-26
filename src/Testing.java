import java.util.Arrays;
import java.util.List;

public class Testing {
	
	public Testing() {
		
	}
	
	public static void constructTree(Board b, BoardTree bt) {
		//get children
		if(b == null) {
			b = new Board();
		}
		if(bt == null) {
			bt = new BoardTree(b);
		}
		
		List<Move> possibleMoves = b.generateListOfPossibleMoves(Player.Colour.BLACK);
		System.out.println("POSSIBLE MOVES SIZE: "+ possibleMoves.size());
		
		for(int i = 0; i < possibleMoves.size(); i++) {
			Board.Piece[][] copy = new Board.Piece[8][8];
			java.lang.System.arraycopy(b.getBoard(), 0, copy, 0, b.getBoard().length);
			Board nextBoard = new Board();
			nextBoard.move(possibleMoves.get(i), Player.Colour.BLACK);
			BoardTree child = new BoardTree(nextBoard);
			bt.addChild(child);
			System.out.println("NEXT BOARD " + i);
			nextBoard.printBoard();
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
