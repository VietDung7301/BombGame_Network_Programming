package model.bomb;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class BombLine {
	private ImageView image[];
	private int WIDTH[] = {20, 28, 38, 28, 20};
	private int HEIGHT[] = {45, 45, 45, 45, 45};
	
	private int currentAnimation = 0;
	private int inRow, inCol;
	private int rotation;
	private long timeKey;
	
	public BombLine(int inRow, int inCol, int rotation, long timeKey) {
		image = new ImageView[5];
		image[0] = new ImageView("file:src/images/boom_1.png");
		image[1] = new ImageView("file:src/images/boom_2.png");
		image[2] = new ImageView("file:src/images/boom_3.png");
		image[3] = new ImageView("file:src/images/boom_2.png");
		image[4] = new ImageView("file:src/images/boom_1.png");
		
		this.inRow = inRow;
		this.inCol = inCol;
		this.rotation = rotation;
		for (int i=0; i<5; i++) {
			image[i].setRotate(rotation);
		}
		this.timeKey = timeKey;
	}
	
	public int getCurrentAnimation() {
		if (System.currentTimeMillis() - timeKey >= 60) {
			currentAnimation = currentAnimation + 1;
			timeKey = System.currentTimeMillis();
			if (currentAnimation == 2) {
				timeKey+= 60;
			}
		}
		return this.currentAnimation;
	}
	public ImageView getImage() {
		return image[getCurrentAnimation()];
	}
	
	public int getInRow() {
		return this.inRow;
	}
	public int getInCol() {
		return this.inCol;
	}
	public int getWidth() {
		return this.WIDTH[getCurrentAnimation()];
	}
	public int getHeight() {
		return this.HEIGHT[getCurrentAnimation()];
	}
	public int getRotation() {
		return this.rotation;
	}
	 
}
