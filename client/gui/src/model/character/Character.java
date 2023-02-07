package model.character;

import javafx.scene.Group;
import model.common.Direction;

public class Character extends Group {
	protected int lives, qtyBomb, powBomb, speed, currentImage;
	protected double posX, posY;
	protected Direction currentDirection;
	
	public Character(int lives, int qtyBomb, int powBomb, int speed, double posX, double posY) {
		super();
		this.lives = lives;
		this.qtyBomb = qtyBomb;
		this.powBomb = powBomb;
		this.speed = speed;
		this.posX = posX;
		this.posY = posY;
		this.currentImage = 0;
		this.currentDirection = Direction.DOWN;
	}
	public Character(int lives, int qtyBomb, int powBomb, int speed, double posX, double posY, Direction direction, int currentImage) {
		this(lives, qtyBomb, powBomb, speed, posX, posY);
		this.currentImage = currentImage;
		this.currentDirection = direction;
	}

	public int getLives() {
		return lives;
	}

	public int getQtyBomb() {
		return qtyBomb;
	}

	public int getPowBomb() {
		return powBomb;
	}

	public int getSpeed() {
		return speed;
	}

	public Direction getCurrentDirection() {
		return currentDirection;
	}
	
	public void setCurrentDirection(Direction direction) {
		this.currentDirection = direction;
	}
	
	public int getCurrentImage() {
		return currentImage;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public void setQtyBomb(int qtyBomb) {
		this.qtyBomb = qtyBomb;
	}

	public void setPowBomb(int powBomb) {
		this.powBomb = powBomb;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setCurrentImage(int currentImage) {
		this.currentImage = currentImage;
	}

	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}
}
