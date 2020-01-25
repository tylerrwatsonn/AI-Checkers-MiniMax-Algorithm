import java.util.ArrayList;
import java.util.List;

public class BoardTree {
	
	private List<BoardTree> children;
	private Board board;
	private Player player;	//
	private int heuristicValue;	//heuristic value to judge board state based on black-red pieces
	
	public BoardTree() {
		this.children = new ArrayList<BoardTree>();
		this.board = new Board();
		calculateHeuristicValue();
	}
	
	public BoardTree(Board b, Player p) {
		this.board = b;
		this.player = p;
		calculateHeuristicValue();
	}
	
	private void calculateHeuristicValue() {
		this.heuristicValue = board.getTotalNumBlackPieces() - board.getTotalNumRedPieces();
	}
	
}
