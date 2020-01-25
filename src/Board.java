import java.util.Arrays;

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
