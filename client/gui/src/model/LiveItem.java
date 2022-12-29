package model;

public class LiveItem extends Item {
	static String path = "src/images/live_item.png";

	public LiveItem(int inRow, int inCol, double cellSize) {
		super(path, inRow, inCol, cellSize);
	}
	public void beAte(Character character) {
		this.getChildren().setAll();
		character.increaseLives();
	}
}