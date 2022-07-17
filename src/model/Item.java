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
	
	public Item(String path, double posX, double posY) {
		super();
		Image image = new Image("file:" + path);
		this.imageView = new ImageView(image);
		this.posX = posX;
		this.posY = posY;
		
		this.height = image.getHeight();
		this.width = image.getWidth();
		
		this.getChildren().setAll(imageView);
		this.setLayoutX(posX - (double) width/2);
		this.setLayoutY(posY - (double) height/2);
	}
	
	public double getHeight() {
		return this.height;
	}
	
	abstract void beEated();
}
