package model.barrier;

import javafx.scene.image.Image;

public enum Barrier {
	PINETREE("file:src/images/pine_tree.png", 2),
	SNOWMAN("file:src/images/snowman.png", 2),
	HAT("file:src/images/hat.png", 1),
	CANDLE("file:src/images/candle.png", 1),
	SOCKS("file:src/images/socks.png", 1);
	
	private Image image;
	private int type;
	
	private Barrier(String path, int type) {
		this.image = new Image(path);
		this.type = type;
	}
	
	public Image getImage() {
		return this.image;
	}
	public int getType() {
		return this.type;
	}
}
