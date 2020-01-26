import java.util.List;

public class Main {

	private BoardTree boardTree;
	private Board currBoard;
	
	public void constructTree(Board b, BoardTree bt) {
		//get children
		if(b == null) {
			b = new Board();
		}
		if(bt == null) {
			bt = new BoardTree(b);
		}
		
		List<Move> possibleMoves = b.generateListOfPossibleMoves(Player.Colour.BLACK);
		
		for(int i = 0; i < possibleMoves.size(); i++) {
			Board nextBoard = b;
			nextBoard.move(possibleMoves.get(i), Player.Colour.BLACK);
			BoardTree child = new BoardTree(nextBoard);
			bt.addChild(child);
			System.out.println("NEXT BOARD " + i);
			nextBoard.printBoard();
		}
		
		
		
	}
	
	
	
}
