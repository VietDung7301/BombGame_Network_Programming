package model;

public class SpeedItem extends Item {
	static private String path = "src/images/speed_item.png";

	public SpeedItem(double posX, double posY) {
		super(path, posX, posY);
	}
	public void beEated() {
		this.getChildren().setAll();
	}
}
