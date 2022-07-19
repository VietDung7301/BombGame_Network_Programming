package model;

public class PowBombItem extends Item{
	static private String path = "src/images/pow_bomb_item.png";

	public PowBombItem(int inRow, int inCol, double cellSize) {
		super(path, inRow, inCol, cellSize);
	}
	public void beAte(Character character) {
		this.getChildren().setAll();
		character.increasePowBomb();
	}
}