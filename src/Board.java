import java.io.Console;
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
    
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    
    
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
    	System.out.println(" | 0 1 2 3 4 5 6 7");
    	System.out.println("-----------------");
    	for(int i = 0; i<SIZE; i++) {
    		String row = "" + i + "|";
    		for(int j = 0; j<SIZE; j++) {
    			row += " " + printBoard[i][j];
    		}
    		System.out.println(row);
    	}
    	System.out.println();
    }
    
    
    public void printBoard(Move move) {
    	Board.Piece[][] bd = this.copyBoard();
    	Board b = new Board(bd);
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
    	System.out.println(" | 0 1 2 3 4 5 6 7");
    	System.out.println("-----------------");
    	for(int i = 0; i<SIZE; i++) {
    		String row = "" + i + "|";
    		for(int j = 0; j<SIZE; j++) {
    			if(i== move.getStartRow() && j==move.getStartCol()) printBoard[i][j] = "S";
    			else if(i== move.getEndRow() && j==move.getEndCol()) printBoard[i][j] = "E";
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
    		this.board[endRow][endCol] = this.board[startRow][startCol];
    		this.board[startRow][startCol] = Piece.EMPTY;
    		checkForKingship(endRow, endCol);
    		
    		//take out jumped piece
    		if(Math.abs(startCol - endCol) == 2) {
    			this.board[(startRow + endRow)/2][(startCol + endCol)/2] = Piece.EMPTY;
    			this.updatePieceCount();
    		}
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
    public ArrayList<Move> generateListOfPossibleMoves(Player.Colour c) {
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
    	int rowOffset = c == Player.Colour.BLACK ? 1 : -1;	//To dictate allowed direction
    	
    	
    	if ((piece == Piece.BLACK && c == Player.Colour.BLACK) || (piece == Piece.RED && c == Player.Colour.RED)) {
    		if(!(row + rowOffset == 8 || row + rowOffset == -1)) {	//check that the desired location is within range
	    		if(col > 0 && getPiece(row + rowOffset, col - 1) == Piece.EMPTY) {
	    			validMoves.add(new Move(row, col, row + rowOffset, col - 1));
	    		}
	    		
	    		if (col < SIZE - 1 ) {
	    			if(getPiece(row + rowOffset, col + 1) == Piece.EMPTY) {
	    				validMoves.add(new Move(row, col, row + rowOffset, col + 1));
	    			}
	    		}
	    		
	    		//-----------------JUMPING-----------
	    		
	    		
	    		if(!(row + 2*rowOffset == 8 || row + 2*rowOffset == -1)) {
	    			//Jump to right
	    			if(col <= 5 && getPiece(row + rowOffset, col + 1) != Piece.EMPTY && getColour(getPiece(row + rowOffset, col + 1)) != c && getPiece(row + 2*rowOffset, col + 2) == Piece.EMPTY) {
		    			validMoves.add(new Move(row, col, row + 2*rowOffset, col + 2));
		    		}
		    		//Jump to left
		    		if(col >= 2 && getPiece(row + rowOffset, col - 1) != Piece.EMPTY && getColour(getPiece(row + rowOffset, col - 1)) != c && getPiece(row + 2*rowOffset, col - 2) == Piece.EMPTY) {
		    			validMoves.add(new Move(row, col, row + 2*rowOffset, col - 2));
		    		}
	    		}
    		}
    	}
    	
    	//KING MOVES
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
	    		
	    		//------------------------JUMPING--------
	    		
	    			//Jump down
	    			if(col <= 5 && getPiece(row + 1, col + 1) != Piece.EMPTY && getColour(getPiece(row + 1, col + 1)) != c && getPiece(row + 2, col + 2) == Piece.EMPTY) {
		    			validMoves.add(new Move(row, col, row + 2, col + 2));
		    		}
		    		if(col >= 2 && getPiece(row + 1, col - 1) != Piece.EMPTY && getColour(getPiece(row + 1, col - 1)) != c && getPiece(row + 2, col - 2) == Piece.EMPTY) {
		    			validMoves.add(new Move(row, col, row + 2, col - 2));
		    		}
		    		
		    		//Jump up
	    			if(col <= 5 && getPiece(row - 1, col + 1) != Piece.EMPTY && getColour(getPiece(row - 1, col + 1)) != c && getPiece(row - 2, col + 2) == Piece.EMPTY) {
		    			validMoves.add(new Move(row, col, row - 2, col + 2));
		    		}
		    		if(col >= 2 && getPiece(row - 1, col - 1) != Piece.EMPTY && getColour(getPiece(row  - 1, col - 1)) != c && getPiece(row - 2, col - 2) == Piece.EMPTY) {
		    			validMoves.add(new Move(row, col, row - 2, col - 2));
		    		}
	    		
    		
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
    
    public int getNumBlackPieces() {
    	return this.numBlackPieces;
    }
    
    public int getNumBlackKingPieces() {
    	return this.numBlackKingPieces;
    }
    
    public int getNumRedPieces() {
    	return this.numRedPieces;
    }
    
    public int getNumRedKingPieces() {
    	return this.numRedKingPieces;
    }
    
    public Piece getPiece(int row, int col) {
    	if(row < 0 || row > 7 || col < 0 || col > 7) return null;
    	return this.board[row][col];
    }
    
    public Piece[][] getBoard() {
    	return this.board;
    }
    
    /**
     * Alternative to cloning to avoid changing original board with move function
     * @return
     */
    public Piece[][] copyBoard() {
    	Piece[][] copy = new Piece[SIZE][SIZE];
    	for(int i=0; i<SIZE; i++) {
    		copy[i] = Arrays.copyOf(this.board[i], this.board[i].length);
    	}
    	return copy;
    }
    
    static public Player.Colour getColour(Piece piece) {
    	if (piece == Piece.BLACK || piece == Piece.BLACK_KING) return Player.Colour.BLACK;
    	else if (piece == Piece.RED || piece == Piece.RED_KING) return Player.Colour.RED;
    	return null;
    }
    
    /**
     * Update Piece count after a jump
     */
    public void updatePieceCount() {
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
    
    private void checkForKingship(int row, int col) {
    	if((row == 0 && this.board[row][col] == Piece.RED)) {
			this.board[row][col] = Piece.RED_KING; 
		}
    	else if(row == 7 && this.board[row][col] == Piece.BLACK) {
    		this.board[row][col] = Piece.BLACK_KING;
    	}
    }
}
