package model;

public class LiveItem extends Item {
	static String path = "src/images/live_item.png";

	public LiveItem(double posX, double posY) {
		super(path, posX, posY);
	}
	public void beEated() {
		this.getChildren().setAll();
	}
}
