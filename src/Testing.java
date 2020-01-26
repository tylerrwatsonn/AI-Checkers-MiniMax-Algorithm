
public class Testing {
	
	public Testing() {
		
	}
	
	public static void main(String[] args) {
		Board board = new Board();
		board.printBoard();
		Move move = new Move(2,7,3,6);
		board.move(move, Player.Colour.BLACK);
		board.printBoard();
	}
}
