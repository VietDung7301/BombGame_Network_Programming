package model;

public class PowBombItem extends Item{
	static private String path = "src/images/pow_bomb_item.png";

	public PowBombItem(double posX, double posY) {
		super(path, posX, posY);
	}
	public void beEated() {
		this.getChildren().setAll();
	}
}