package model;

import javafx.scene.image.ImageView;

public class BombCenter {
	private ImageView image[];
	private int WIDTH[] = {24, 34, 59, 59, 59};
	private int HEIGHT[] = {24, 34, 59, 59, 59};
	
	private int currentAnimation = 0;
	private int inRow, inCol;
	private long timeKey;
	
	public BombCenter(int inRow, int inCol, long timeKey) {
		image = new ImageView[5];
		image[0] = new ImageView("file:src/images/center_1.png");
		image[1] = new ImageView("file:src/images/center_2.png");
		image[2] = new ImageView("file:src/images/center_3.png");
		image[3] = new ImageView("file:src/images/center_3.png");
		image[4] = new ImageView("file:src/images/center_4.png");
		
		this.inRow = inRow;
		this.inCol = inCol;
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
	 
}
