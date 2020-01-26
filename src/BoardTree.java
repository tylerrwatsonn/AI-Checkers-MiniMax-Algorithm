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
	
	public BoardTree(Board b) {
		this.board = b;
		this.children = new ArrayList<BoardTree>();
		calculateHeuristicValue();
	}
	
	public BoardTree(Board b, Player p) {
		this.board = b;
		this.player = p;
		this.children = new ArrayList<BoardTree>();
		calculateHeuristicValue();
	}
	
	private void calculateHeuristicValue() {
		this.heuristicValue = board.getTotalNumBlackPieces() - board.getTotalNumRedPieces();
	}
	
	public void addChild(BoardTree bt) {
		this.children.add(bt);
	}
	
	public int getHeuristicValue() {
		return this.heuristicValue;
	}
	
}
