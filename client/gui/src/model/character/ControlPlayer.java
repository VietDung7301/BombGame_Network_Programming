package model.character;

import javafx.scene.input.KeyCode;
import model.common.Direction;

public class ControlPlayer extends Player {
	private KeyCode up;
	private KeyCode down;
	private KeyCode left;
	private KeyCode right;
	private KeyCode bomb;
	
	Direction movingDirection;
	private boolean plantingBomb;

	public ControlPlayer(Character character, KeyCode up, KeyCode down, KeyCode left, KeyCode right, KeyCode bomb) {
		super(character);
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
		this.bomb = bomb;
		this.movingDirection = Direction.NONE;
		this.plantingBomb = false;
	}
	
	public void setMovingDirection(Direction direction) {
		this.movingDirection = direction;
	}
	public Direction getMovingDirection() {
		return this.movingDirection;
	}
	public boolean isPlantingBomb() {
		return this.plantingBomb;
	}
	public void setPlantingBomb(boolean bomb) {
		this.plantingBomb = bomb;
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
