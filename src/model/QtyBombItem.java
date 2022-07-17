package model;

public class QtyBombItem extends Item {
	static private String path = "src/images/qty_bomb_item.png";

	public QtyBombItem(double posX, double posY) {
		super(path, posX, posY);
	}
	public void beEated() {
		this.getChildren().setAll();
	}
}
