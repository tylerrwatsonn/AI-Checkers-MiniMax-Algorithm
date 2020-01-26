
public class Move {
	
	private int startRow;
	private int startCol;
	private int endRow;
	private int endCol;
	
	public Move(int startRow, int startCol, int endRow, int endCol) {
		this.startRow = startRow;
		this.startCol = startCol;
		this.endRow = endRow;
		this.endCol = endCol;
	}
	
	public void printMove() {
		System.out.println("NEXT MOVE");
		System.out.println("SR " + getStartRow());
		System.out.println("SC "+getStartCol());
		System.out.println("ER "+getEndRow());
		System.out.println("EC "+getEndCol());
		System.out.println();
	}
	
	
	//--------GETTERS AND SETTERS
	
	public int getStartRow() {
		return this.startRow;
	}
	
	public int getEndRow() {
		return this.endRow;
	}
	
	public int getStartCol() {
		return this.startCol;
	}
	
	public int getEndCol() {
		return this.endCol;
	}
}
