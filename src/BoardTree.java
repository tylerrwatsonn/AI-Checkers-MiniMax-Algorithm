import java.util.ArrayList;
import java.util.List;

public class BoardTree {
	
	private List<BoardTree> children;
	private Board board;
	private Player.Colour colour;	//
	private double heuristicValue;	//heuristic value to judge board state based on black-red pieces
	private int[][] positionWorth = {{0,4,0,5,0,5,0,3},{2,0,1,0,1,0,1,0},{0,1,0,2,0,2,0,1},{1,0,3,0,3,0,2,0}
										,{0,2,0,3,0,3,0,1},{1,0,2,0,2,0,1,0},{0,1,0,1,0,1,0,2},{2,0,3,0,3,0,2,0}};
	
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
		this.heuristicValue = calculatePositionHeuristicValue()*0.01 + calculateNumPiecesHeuristic();
	}
	
	private double calculatePositionHeuristicValue() {
		double positionHeuristicValue = 0;
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				if(this.board.getBoard()[i][j] == Board.Piece.BLACK || this.board.getBoard()[i][j] == Board.Piece.BLACK_KING) {
					positionHeuristicValue += positionWorth[i][j];
				}
			}
		}
		return positionHeuristicValue;
	}
	
	private double calculateNumPiecesHeuristic() {
		double kingWeight = 1.3;
		return board.getNumBlackKingPieces()*kingWeight + board.getNumBlackPieces()
		 - board.getNumRedKingPieces()*kingWeight - board.getNumRedPieces();
	}
	
	public void addChild(BoardTree bt) {
		this.children.add(bt);
	}
	
	public double getHeuristicValue() {
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
