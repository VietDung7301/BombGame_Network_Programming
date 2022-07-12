package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public enum Character {
	RED("file:src/images/RED_", 1),
	WHITE("file:src/images/WHITE_", 0),
	GREEN("file:src/images/GREEN_", 0),
	BLACK("file:src/images/BLACK_", 1);
	
	public static double WIDTH = 43;	// width of image that display as character
	public static double HEIGHT = 62;	// height of image that display as character
	public final static int DOWN = 0;
	public final static int UP = 1;
	public final static int LEFT = 2;
	public final static int RIGHT = 3;
	
	private int lives, qtyBomb, powBomb, speed, direction, currentImage, bombSeted;
	double posX, posY;
	private ImageView image[][];
	
	private Character(String path, int direction) {
		this.image = new ImageView[4][4];
		for (int i=0; i<4; i++) {
			this.image[0][i] = new ImageView(path + "DOWN_" + (i + 1) + ".png");
			this.image[1][i] = new ImageView(path + "UP_" + (i + 1) + ".png");
			this.image[2][i] = new ImageView(path + "LEFT_" + (i + 1) + ".png");
			this.image[3][i] = new ImageView(path + "RIGHT_" + (i + 1) + ".png");
		}
		this.lives = 1;
		this.qtyBomb = 1;
		this.powBomb = 5;
		this.speed = 1;
		this.currentImage = 0;
		this.bombSeted = 0;
		this.direction = direction;
	}
	
	public void increaseLives() {
		if (this.lives < 10)
			this.lives++;
	}
	public void decreaseLives() {
		this.lives--;
	}
	public void increaseQtyBomb() {
		if (this.qtyBomb < 10)
			this.qtyBomb++;
	}
	public void increasePowBomb() {
		if (this.powBomb < 10)
			this.powBomb++;
	}
	public void increaseSpeed() {
		if (this.speed < 10)
			this.speed++;
	}
	public void setBomb() {
		if (isCanSetBomb()) {
			bombSeted++;
		}
	}
	public void restoreBomb() {
		bombSeted--;
	}
	public boolean isCanSetBomb() {
		if (qtyBomb > bombSeted) {
			return true;
		}
		return false;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public void setPosX(double x) {
		this.posX = x;
	}
	public void setPosY(double y) {
		this.posY = y;
	}
	public void resetCurrentImage() {
		this.currentImage = 0;
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
	

	public int getDirection() {
		return direction;
	}
	public double getPosX() {
		return this.posX;
	}
	public double getPosY() {
		return this.posY;
	}
	public double getDisMove() {
		return 2.0 + speed*0.5;
	}

	public ImageView getImage() {
		return image[direction][currentImage];
	}
	
	public void move(int direction, boolean movable, boolean moveAnimation) {
		if (moveAnimation) {
			this.direction = direction;
			this.currentImage = (currentImage + 1) % 4;
		}
		if (movable) {
			switch (direction) {
			case UP: posY-= getDisMove();
				break;
			case DOWN: posY+= getDisMove();
				break;
			case LEFT: posX-= getDisMove();
				break;
			case RIGHT: posX+= getDisMove();
				break;
			default:
			}
		}
	}
}