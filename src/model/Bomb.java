package model;

import javafx.scene.image.Image;

public class Bomb {
	public static final Image IMAGE = new Image("file:src/images/bomb.png");
	public static final double WIDTH = IMAGE.getWidth();
	public static final double HEIGHT = IMAGE.getHeight();
	public static final long TIMEBOOM = 2_000;
	private long timeSet;
	private int length, owner, inRow, inCol;
	
	public Bomb(int length, int owner, int inRow, int inCol) {
		this.length = length;
		this.timeSet = System.currentTimeMillis();
		this.owner = owner;
		this.inRow = inRow;
		this.inCol = inCol;
	}
	
	public int getLength() {
		return this.length;
	}
	public int getOwner() {
		return this.owner;
	}
	public int getInRow() {
		return this.inRow;
	}
	public int getInCol() {
		return this.inCol;
	}
	
	public boolean isBoom() {
		if (System.currentTimeMillis() - timeSet >= TIMEBOOM) {
			return true;
		}
		return false;
	}
}
