package model.common;

public class Direction {
	public static final Direction DOWN = new Direction(0, "down");
	public static final Direction UP = new Direction(1, "up");
	public static final Direction LEFT = new Direction(2, "left");
	public static final Direction RIGHT = new Direction(3, "right");
	public static final Direction NONE = new Direction(4, "none");
	
	private int value;
	private String strValue;
	
	private Direction(int direction, String strValue) {
		this.value = direction;
		this.strValue = strValue;
	}
	public int getValue() {
		return this.value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String toString() {
		return this.strValue;
	}
	public static Direction parseDirection(int x) {
		switch (x) {
			case 0: return DOWN;
			case 1: return UP;
			case 2: return LEFT;
			case 3: return RIGHT;
			default: return NONE;
		}
	}
}
