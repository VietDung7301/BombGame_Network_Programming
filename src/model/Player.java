package model;

import javafx.scene.input.KeyCode;

public class Player extends Character {
	private KeyCode up;
	private KeyCode down;
	private KeyCode left;
	private KeyCode right;
	private KeyCode bomb;

	public Player(int direction, double posX, double posY, KeyCode up, KeyCode down, KeyCode left, KeyCode right, KeyCode bomb) {
		super(direction, posX, posY);
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
		this.bomb = bomb;
	}
	
	public KeyCode getUpKey() {
		return this.up;
	}
	public KeyCode getDownKey() {
		return this.down;
	}
	public KeyCode getLeftKey() {
		return this.left;
	}
	public KeyCode getRightKey() {
		return this.right;
	}
	public KeyCode getBombKey() {
		return this.bomb;
	}
}
