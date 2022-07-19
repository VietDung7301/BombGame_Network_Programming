package model;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Item extends Group{
	private ImageView imageView;
	private double height;
	private double width;
	private double posX;
	private double posY;
	private int inCol;
	private int inRow;
	
	public Item(String path, int row, int col, double cellSize) {
		super();
		Image image = new Image("file:" + path);
		this.imageView = new ImageView(image);
		this.posX = cellSize * col + cellSize/2;
		this.posY = cellSize * row + cellSize/2;
		
		this.inCol = col;
		this.inRow = row;
		this.height = image.getHeight();
		this.width = image.getWidth();
		
		this.getChildren().setAll(imageView);
		this.setLayoutX(posX - width/2);
		this.setLayoutY(posY - height/2);
	}
	
	public double getHeight() {
		return this.height;
	}
	public int getInCol() {
		return inCol;
	}
	public int getInRow() {
		return inRow;
	}
	
	public abstract void beAte(Character character);
}
