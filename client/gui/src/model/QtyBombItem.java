package model;

public class QtyBombItem extends Item {
	static private String path = "src/images/qty_bomb_item.png";

	public QtyBombItem(int inRow, int inCol, double cellSize) {
		super(path, inRow, inCol, cellSize);
	}
	public void beAte(Character character) {
		this.getChildren().setAll();
		character.increaseQtyBomb();
	}
}
