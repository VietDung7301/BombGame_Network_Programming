package model.bomb;

public class Boom {
	private int length, inRow, inCol;
	
	public Boom(int inRow, int inCol, int length) {
		this.length = length;
		this.inCol = inCol;
		this.inRow = inRow;
	}
	
	public int getLength() {
		return this.length;
	}
	
	public int getInCol() {
		return this.inCol;
	}
	
	public int getInRow() {
		return this.inRow;
	}
}
