package model.character;

import javafx.scene.image.ImageView;
import model.common.Direction;

public class Player extends Character{
	private final static String[] path = {
			"file:src/images/WHITE_",
			"file:src/images/GREEN_",
			"file:src/images/BLACK_",
			"file:src/images/RED_",
	};
	
	public final static double WIDTH = 43;	// width of image that display as character
	public final static double HEIGHT = 62;	// height of image that display as character
	
	public static int count = 0;
	
	private long timeKey;
	private ImageView image[][];
	
	public Player() {
		this(Direction.NONE, 0, 0, 0, 0, 0, 0);
	}
	
	public Player(Direction direction, int lives, int qtyBomb, int powBomb, int speed, double posX, double posY) {
		super(lives, qtyBomb, powBomb, speed, posX, posY);
		this.image = new ImageView[4][4];
		for (int i=0; i<4; i++) {
			this.image[0][i] = new ImageView(path[count] + "DOWN_" + (i + 1) + ".png");
			this.image[1][i] = new ImageView(path[count] + "UP_" + (i + 1) + ".png");
			this.image[2][i] = new ImageView(path[count] + "LEFT_" + (i + 1) + ".png");
			this.image[3][i] = new ImageView(path[count] + "RIGHT_" + (i + 1) + ".png");
		}
		this.timeKey = 0;
		this.setLayoutX(posX - WIDTH/2);
		this.setLayoutY(posY - HEIGHT/1.5);
		this.count++;
		this.getChildren().setAll(image[getCurrentDirection().getValue()][0]);
	}
	
	public Player(Character character) {
		this (
			character.currentDirection,
			character.lives,
			character.qtyBomb,
			character.powBomb,
			character.speed,
			character.posX,
			character.posY
		);
	}
	
	public void setBasicInfor(Character character) {
		this.lives = character.lives;
		this.qtyBomb = character.qtyBomb;
		this.powBomb = character.powBomb;
		this.speed = character.speed;
		this.currentDirection = character.currentDirection;
		this.currentImage = character.currentImage;
		this.posX = character.posX;
		this.posY = character.posY;
		this.setLayoutX(posX - WIDTH/2);
		this.setLayoutY(posY - HEIGHT/1.5);
	}
}