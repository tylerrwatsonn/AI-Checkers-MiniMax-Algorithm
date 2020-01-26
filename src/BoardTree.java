import java.util.ArrayList;
import java.util.List;

public class BoardTree {
	
	private List<BoardTree> children;
	private Board board;
	private Player.Colour colour;	//
	private int heuristicValue;	//heuristic value to judge board state based on black-red pieces
	
	public BoardTree() {
		this.children = new ArrayList<BoardTree>();
		this.board = new Board();
		this.colour = Player.Colour.BLACK;
		calculateHeuristicValue();
	}
	
	public BoardTree(Board b) {
		this.board = b;
		this.children = new ArrayList<BoardTree>();
		this.colour = Player.Colour.BLACK;
		calculateHeuristicValue();
	}
	
	public BoardTree(Board b, Player.Colour c) {
		this.board = b;
		this.colour = c;
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
	
	public List<BoardTree> getChildren() {
		return this.children;
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	public Player.Colour getColour() {
		return this.colour;
	}
	
	public void setColour(Player.Colour colour) {
		this.colour = colour;
	}
}
