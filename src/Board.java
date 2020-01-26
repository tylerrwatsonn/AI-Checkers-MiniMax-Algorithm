import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
	
	
	private Piece[][] board;
    public final int SIZE = 8;

    private int numBlackPieces;
    private int numRedPieces;
    private int numBlackKingPieces;
    private int numRedKingPieces;
    
    
    public enum Piece { 
    	EMPTY, BLACK, RED, BLACK_KING, RED_KING
    }
    
    public enum Result {
    	COMPLETED, INVALID_POSITION, INVALID_PIECE, ANOTHER_TURN, GAME_OVER
    }
    
    /**
     * New empty board
     */
    public Board() {
    	getInitialBoard();
    }
    
    /**
     * Initialize board with 2D array of pieces
     * @param b
     */
    public Board(Piece[][] b) {
    	this.board = b;
    	this.numBlackKingPieces = 0;
    	this.numBlackPieces = 0;
    	this.numRedKingPieces = 0;
    	this.numRedPieces = 0;
    	
    	for(int i = 0; i<SIZE; i++) {
	    	for(int j = 0; j<SIZE; j++) {
				if(this.board[i][j] == Piece.BLACK) this.numBlackPieces++;
				else if(this.board[i][j] == Piece.RED) this.numRedPieces++;
				else if(this.board[i][j] == Piece.BLACK_KING) this.numBlackKingPieces++;
				else if(this.board[i][j] == Piece.RED_KING) this.numRedKingPieces++;
			}
    	}
    }
    
    /**
     * Initialize board
     */
    public void getInitialBoard() {
    	
    	this.numBlackPieces = 12;
    	this.numRedPieces = 12;
    	this.numBlackKingPieces = 0;
    	this.numRedKingPieces = 0;
    	
    	this.board = new Piece[SIZE][SIZE];
    	
    	for(int i = 0; i < 3; i++) {
    		for(int j=0; j<SIZE; j++) {
	    		if(j % 2 == i % 2) this.board[i][j] = Piece.EMPTY;
	    		else this.board[i][j] = Piece.BLACK;
    		}
    	}
    	for(int i = 3; i < 5; i++) {
    		for(int j=0; j<SIZE; j++) {
    			this.board[i][j] = Piece.EMPTY;
    		}
    	}
    	
    	for(int i = 5; i < SIZE; i++) {
    		for(int j=0; j<SIZE; j++) {
	    		if(j % 2 != i % 2) this.board[i][j] = Piece.RED;
	    		else this.board[i][j] = Piece.EMPTY;
    		}
    	}
    		
    }
    
    
    
    /**
     * Print a formatted board to the console
     */
    public void printBoard() {
    	String[][] printBoard = new String[SIZE][SIZE];
    	for(int i = 0; i<SIZE; i++) {
    		for(int j = 0; j<SIZE; j++) {
    			if(this.board[i][j] == Piece.EMPTY) printBoard[i][j] = "-";
    			else if(this.board[i][j] == Piece.BLACK) printBoard[i][j] = "b";
    			else if(this.board[i][j] == Piece.RED) printBoard[i][j] = "r";
    			else if(this.board[i][j] == Piece.BLACK_KING) printBoard[i][j] = "B";
    			else if(this.board[i][j] == Piece.RED_KING) printBoard[i][j] = "R";
    		}
    	}
    	for(int i = 0; i<SIZE; i++) {
    		String row = "";
    		for(int j = 0; j<SIZE; j++) {
    			row += " " + printBoard[i][j];
    		}
    		System.out.println(row);
    	}
    	System.out.println();
    }
    
    /**
     * Check validity of certain move
     * @param move
     * @param c
     * @return
     */
    public Result move(Move move, Player.Colour c) {
    	if(move == null) {
    		return Result.GAME_OVER;
    	}
    	
    	//Get move attributes
    	int startRow = move.getStartRow();
    	int startCol = move.getStartCol();
    	int endRow = move.getEndRow();
    	int endCol = move.getEndCol();
    	
    	if(!isMovingOwnPiece(startRow, startCol, c) || getPiece(startRow, startCol) == Piece.EMPTY) {
    		return Result.INVALID_PIECE;
    	}
    	
    	List<Move> possibleMoves = generateListOfPossibleMoves(c);
    	
    	if(contains(possibleMoves, move)) {
    		Piece temp = this.board[startRow][startCol];
    		this.board[startRow][startCol] = this.board[endRow][endCol];
    		this.board[endRow][endCol] = temp;
    	}
    	
    	
    	
    	return Result.COMPLETED;
    	
    }
    
    /**
     * Check if piece is valid
     * @param row
     * @param col
     * @param c
     * @return
     */
    private boolean isMovingOwnPiece(int row, int col, Player.Colour c) {
    	Piece piece = getPiece(row, col);
    	if (c == Player.Colour.BLACK) {
    		return (piece == Piece.BLACK || piece == Piece.BLACK_KING);
    	}
    	else if (c == Player.Colour.RED) {
    		return (piece == Piece.RED || piece == Piece.RED_KING);
    	}
    	return false;
    }
    
    /**
     * Generate list of possible moves from colour
     * @param c
     * @return
     */
    private ArrayList<Move> generateListOfPossibleMoves(Player.Colour c) {
    	List<Move> possibleMoves = new ArrayList<Move>();
    	
    	for(int i = 0; i<SIZE; i++) {
    		for(int j=0; j<SIZE; j++) {
    			
    			if(getPiece(i, j) != Piece.EMPTY) possibleMoves.addAll(getValidMoves(i, j, c));
    		}
    	}
    	return (ArrayList<Move>) possibleMoves;
    	
    }
    
    /**
     * Get validMoves from position and colour
     * @param row
     * @param col
     * @param c
     * @return
     */
    private List<Move> getValidMoves(int row, int col, Player.Colour c) {
    	ArrayList<Move> validMoves = new ArrayList<Move>();
    	Piece piece = getPiece(row, col);
    	if(piece == Piece.BLACK) {
    		if(col > 0 && getPiece(row + 1, col - 1) == Piece.EMPTY) {
    			validMoves.add(new Move(row, col, row + 1, col -1));
    		}
    		
    		if (col < SIZE - 1 ) {
    			if(getPiece(row + 1, col + 1) == Piece.EMPTY) {
    				validMoves.add(new Move(row, col, row+1, col+1));
    			}
    		}
    	}
    	
    	else if(piece == Piece.BLACK_KING || piece == Piece.RED_KING) {
    		if(col > 0) {
    			if(getPiece(row + 1, col - 1) == Piece.EMPTY) {
    				validMoves.add(new Move(row, col, row + 1, col - 1));
    			}
    			if(getPiece(row - 1, col - 1) == Piece.EMPTY) {
    				validMoves.add(new Move(row, col, row - 1, col - 1));
    			}
    		}
    		else if(col < SIZE - 1) {
    			if(getPiece(row + 1, col + 1) == Piece.EMPTY) {
    				validMoves.add(new Move(row, col, row + 1, col + 1));
    			}
    			if(getPiece(row - 1, col + 1) == Piece.EMPTY) {
    				validMoves.add(new Move(row, col, row - 1, col + 1));
    			}
    		}
    	}
    	for(int i=0; i < validMoves.size(); i++) {
    		System.out.println("MOVE " + i);
    		System.out.println("SR" + validMoves.get(i).getStartRow());
    		System.out.println("SC"+validMoves.get(i).getStartCol());
    		System.out.println("ER"+validMoves.get(i).getEndRow());
    		System.out.println("EC"+validMoves.get(i).getEndCol());
    		System.out.println();
    	}
    	return validMoves;
    }
    
    private boolean contains(List<Move> list, Move m) {
    	for(int i=0; i<list.size(); i++) {
    		if(list.get(i).getStartCol() == m.getStartCol() && list.get(i).getStartRow() == m.getStartRow()
    				&& list.get(i).getEndCol() == m.getEndCol() && list.get(i).getEndRow() == m.getEndRow()) {
    			return true;
    		}
    	}
    	return false;
    }
    
    //-----GETTERS AND SETTERS------//
    
    public int getTotalNumBlackPieces() {
    	return this.numBlackPieces + this.numBlackKingPieces;
    }
    
    public int getTotalNumRedPieces() {
    	return this.numRedPieces + this.numRedKingPieces;
    }
    
    public Piece getPiece(int row, int col) {
    	return this.board[row][col];
    }
    
    
}
