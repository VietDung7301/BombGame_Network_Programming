package model;

import controller.GameViewManager;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

public abstract class Character extends Group{
	private final static String[] path = {
			"file:src/images/WHITE_",
			"file:src/images/GREEN_",
			"file:src/images/BLACK_",
			"file:src/images/RED_",
	};
	private static int count = 0;
	
	public final static double WIDTH = 43;	// width of image that display as character
	public final static double HEIGHT = 62;	// height of image that display as character
	public final static int DOWN = 0;
	public final static int UP = 1;
	public final static int LEFT = 2;
	public final static int RIGHT = 3;
	
	private int lives, qtyBomb, powBomb, speed, direction, currentImage, bombSeted;
	private double posX, posY;
	private long timeKey, lastTimeHit;
	private boolean goUp, goDown, goLeft, goRight, plantingBomb;
	private ImageView image[][];
	
	public Character(int direction, double posX, double posY) {
		super();
		this.image = new ImageView[4][4];
		for (int i=0; i<4; i++) {
			this.image[0][i] = new ImageView(path[count] + "DOWN_" + (i + 1) + ".png");
			this.image[1][i] = new ImageView(path[count] + "UP_" + (i + 1) + ".png");
			this.image[2][i] = new ImageView(path[count] + "LEFT_" + (i + 1) + ".png");
			this.image[3][i] = new ImageView(path[count] + "RIGHT_" + (i + 1) + ".png");
		}
		this.lives = 1;
		this.qtyBomb = 1;
		this.powBomb = 1;
		this.speed = 1;
		this.currentImage = 0;
		this.bombSeted = 0;
		this.direction = direction;
		this.posX = posX;
		this.posY = posY + 4.9;
		this.timeKey = 0;
		this.lastTimeHit = -100000;
		this.goDown = false;
		this.goUp = false;
		this.goLeft = false;
		this.goRight = false;
		this.plantingBomb = false;
		this.setLayoutX(posX - WIDTH/2);
		this.setLayoutY(posY - HEIGHT/1.5);
		this.count++;
		
		this.getChildren().setAll(image[direction][0]);
	}
	
	public Bomb setBomb(int row, int col) {
		if (lives <= 0) return null;
		Bomb bomb = null;
		if (isCanSetBomb()) {
			bomb = new Bomb(this.powBomb, this, row, col);
			bombSeted++;
		}
		return bomb;
	}
	public void restoreBomb() {
		bombSeted--;
	}
	public void increaseLives() {
		if (lives == 1) lives++;
	}
	public void decreaseLive() {
		if (System.currentTimeMillis() - lastTimeHit > 3000) {
			lives--;
			if (lives == 0) {
				this.getChildren().setAll();
			}
			lastTimeHit = System.currentTimeMillis();
		}
	}
	public void increasePowBomb() {
		powBomb++;
	}
	public void increaseQtyBomb() {
		qtyBomb++;
	}
	public void increaseSpeed() {
		speed++;
	}
	public void setGoUp(boolean up) {
		this.goUp = up;
	}
	public void setGoDown(boolean down) {
		this.goDown = down;
	}
	public void setGoLeft(boolean left) {
		this.goLeft = left;
	}
	public void setGoRight(boolean right) {
		this.goRight = right;
	}
	public void setPlantingBomb(boolean bomb) {
		this.plantingBomb = bomb;
	}
	public boolean isCanSetBomb() {
		if (lives <= 0) return false;
		if (qtyBomb > bombSeted) {
			return true;
		}
		return false;
	}
	public void setDirection(int direction) {
		this.direction = direction;
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
	public boolean isGoUP() {
		return goUp;
	}
	public boolean isGoDown() {
		return goDown;
	}
	public boolean isGoLeft() {
		return goLeft;
	}
	public boolean isGoRight() {
		return goRight;
	}
	public boolean isPlantingBomb() {
		return plantingBomb;
	}
	public double getPosX() {
		return this.posX;
	}
	public double getPosY() {
		return this.posY;
	}
	public double getDisMove() {
		return 2.0 + speed*0.25;
	}
	
	public void move(int direction, int[][] map, double cellSize) {
		if (lives <= 0) return;
		
		boolean moveAnimation = false;
		boolean movable = true;
		
		int currentCol = getCharacterCol(getLayoutX() + 20);
		int currentRow = getCharacterRow(getLayoutY() + 20);
		
		if (System.currentTimeMillis() - timeKey >= 100) {
			timeKey = System.currentTimeMillis();
			moveAnimation = true;
		}
		
		if (direction == DOWN) {
			int row = getCharacterRow(getLayoutY() + 41 + getDisMove());
			int colL = getCharacterCol(getLayoutX() + 5);
			int colR = getCharacterCol(getLayoutX() + 36);
			
			if ((map[row][colL] > 0 || map[row][colR] > 0)) {
				movable = false;
				if (map[currentRow][currentCol] == 7 && (currentRow == row) && (currentCol == colL || currentCol == colR))
					movable = true;
			}
		} else if (direction == UP) {
			int row = getCharacterRow(getLayoutY() - getDisMove());
			int colL = getCharacterCol(getLayoutX() + 5);
			int colR = getCharacterCol(getLayoutX() + 36);
			
			if (map[row][colL] > 0 || map[row][colR] > 0) {
				movable = false;
				if (map[currentRow][currentCol] == 7 && (currentRow == row) && (currentCol == colL || currentCol == colR))
					movable = true;
			}
		} else if (direction == LEFT) {
			int col = getCharacterCol(getLayoutX() - getDisMove());
			int rowL = getCharacterRow(getLayoutY() + 10);
			int rowR = getCharacterRow(getLayoutY() + 31);
			
			if (map[rowL][col] > 0 || map[rowR][col] > 0) {
				movable = false;
				if (map[currentRow][currentCol] == 7 && (currentCol == col) && (currentRow == rowL || currentRow == rowR))
					movable = true;
			}
		} else if (direction == RIGHT) {
			int col = getCharacterCol(getLayoutX() + 41 + getDisMove());
			int rowL = getCharacterRow(getLayoutY() + 10);
			int rowR = getCharacterRow(getLayoutY() + 31);
			
			if (map[rowL][col] > 0 || map[rowR][col] > 0) {
				movable = false;
				if (map[currentRow][currentCol] == 7 && (currentCol == col) && (currentRow == rowL || currentRow == rowR))
					movable = true;
			}
		}
		
		if (moveAnimation) {
			this.direction = direction;
			this.currentImage = (currentImage + 1) % 4;
			this.getChildren().setAll(image[direction][currentImage]);
		}
		if (movable) {
			switch (direction) {
			case UP: posY-= getDisMove();
					this.setLayoutY(getLayoutY() - getDisMove());
				break;
			case DOWN: posY+= getDisMove();
					this.setLayoutY(getLayoutY() + getDisMove());
				break;
			case LEFT: posX-= getDisMove();
					this.setLayoutX(getLayoutX() - getDisMove());
				break;
			case RIGHT: posX+= getDisMove();
					this.setLayoutX(getLayoutX() + getDisMove());
				break;
			default:
			}
		}
	}
	
	public void stop() {
		this.currentImage = 0;
		this.getChildren().setAll(image[direction][currentImage]);
	}
	
	public static int getCharacterRow(double posY) {
		double cellSize = GameViewManager.CELL_SIZE;
		double result = (posY + HEIGHT - 0.8*Character.WIDTH) / cellSize;
		return (int) result;
	}
	public static int getCharacterCol(double posX) {
		double cellSize = GameViewManager.CELL_SIZE;
		double result = (posX + (WIDTH - cellSize)/2 ) / cellSize ;
		return (int) result;
	}
}