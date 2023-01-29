package model;

public class SpeedItem extends Item {
	static private String path = "src/images/speed_item.png";

	public SpeedItem(int inRow, int inCol, double cellSize) {
		super(path, inRow, inCol, cellSize);
	}
	public void beAte(Character character) {
		this.getChildren().setAll();
		character.increaseSpeed();
	}
}
