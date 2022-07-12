package controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Barrier;
import model.Bomb;
import model.BombCenter;
import model.BombLine;
import model.Character;

public class GameViewManager {
	private static int MAP_SIZE_X = 17;
	private static int MAP_SIZE_Y = 17;
	
	private AnchorPane gameMap;
	private BorderPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	
	private Stage menuStage;
	
	private boolean isLeftKeyPressed = false;
	private boolean isRightKeyPressed = false;
	private boolean isUpKeyPressed = false;
	private boolean isDownKeyPressed = false;
	private boolean isBombSeted[] = new boolean[4];
	
	private ImageView santaClause;
	
	
	long timeKey = 0;
	
	Character character[] = {Character.WHITE, Character.GREEN, Character.BLACK, Character.RED};
	
	Group charView[];
	
	private ArrayList<Bomb> bombList = new ArrayList<Bomb>();
	private ArrayList<BombLine> bombLineList = new ArrayList<BombLine>();
	private ArrayList<BombCenter> bombCenterList = new ArrayList<BombCenter>();
	
	private int map[][] = {
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 0, 0, 3, 3, 4, 1, 0, 1, 0, 2, 0, 0, 0, 0, 0, 1},
		{1, 0, 2, 3, 2, 5, 0, 0, 1, 5, 0, 5, 2, 0, 2, 0, 1},
		{1, 0, 2, 0, 1, 1, 2, 3, 5, 5, 2, 2, 1, 0, 2, 0, 1},
		{1, 0, 0, 0, 0, 0, 2, 2, 1, 1, 2, 0, 0, 0, 0, 0, 1},
		{1, 0, 2, 1, 1, 4, 4, 0, 0, 0, 0, 0, 2, 1, 1, 0, 1},
		{1, 0, 0, 2, 2, 3, 2, 2, 1, 0, 1, 0, 1, 1, 0, 0, 1},
		{1, 2, 0, 0, 1, 4, 3,-1,-1,-1, 4, 0, 1, 0, 0, 2, 1},
		{1, 0, 0, 2, 2, 4, 1,-1,-1,-1, 1, 0, 1, 2, 0, 3, 1},
		{1, 0, 2, 1, 1, 0, 0,-1,-1,-1, 0, 3, 2, 1, 2, 4, 1},
		{1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 0, 5, 0, 0, 0, 1},
		{1, 0, 1, 0, 2, 2, 2, 0, 5, 0, 2, 1, 1, 0, 2, 0, 1},
		{1, 0, 1, 0, 2, 3, 3, 4, 2, 0, 0, 0, 1, 0, 2, 0, 1},
		{1, 0, 0, 0, 0, 0, 1, 0, 2, 5, 2, 0, 0, 0, 0, 0, 1},
		{1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 2, 2, 0, 2, 1, 0, 1},
		{1, 0, 0, 0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
	};
				
		
	
	AnimationTimer gameTimer;
	
	public GameViewManager() {
		initializeStage();
		initializeCharacter();
		createKeyListeners();
	}
	
	private void createKeyListeners() {
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch(event.getCode()) {
					case LEFT:
						isLeftKeyPressed = true;
						isRightKeyPressed = false;
						isUpKeyPressed = false;
						isDownKeyPressed = false;
						break;
					case RIGHT:
						isLeftKeyPressed = false;
						isRightKeyPressed = true;
						isUpKeyPressed = false;
						isDownKeyPressed = false;
						break;
					case UP:
						isLeftKeyPressed = false;
						isRightKeyPressed = false;
						isUpKeyPressed = true;
						isDownKeyPressed = false;
						break;
					case DOWN:
						isLeftKeyPressed = false;
						isRightKeyPressed = false;
						isUpKeyPressed = false;
						isDownKeyPressed = true;
						break;
					case SPACE:
						isBombSeted[0] = true;
					default: break;
				}
			}
		});
		
		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch(event.getCode()) {
				case LEFT:
					isLeftKeyPressed = false;
					break;
				case RIGHT:
					isRightKeyPressed = false;
					break;
				case UP:
					isUpKeyPressed = false;
					break;
				case DOWN:
					isDownKeyPressed = false;
					break;
				case SPACE:
					isBombSeted[0] = false;
					break;
				default: break;
				}
			}
		});
	}
	
	
	private void initializeStage() {
		try {
			URL url = new File("src\\view\\GameScene.fxml").toURI().toURL();
			gamePane = FXMLLoader.load(url);
			gameScene = new Scene(gamePane);
			gameStage = new Stage();
			gameStage.setTitle("BomIT");
			gameStage.setScene(gameScene);
			
			gameMap = (AnchorPane) gamePane.lookup("#GameMap");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		santaClause = new ImageView("file:src/images/santa_clause.png");
		santaClause.setLayoutX(291.0);
		santaClause.setLayoutY(257.0);
	}
	
	private void initializeCharacter() {
		charView = new Group[4];
		character[0].setPosX((double) 1*710/17 - (Character.WIDTH - 710/17)/2 + 1);
		character[0].setPosY((double) 1*710/17 - Character.HEIGHT + 0.8*Character.WIDTH + 1);
		
		character[1].setPosX((double) 15*710/17 - (Character.WIDTH - 710/17)/2 + 1);
		character[1].setPosY((double) 1*710/17 - Character.HEIGHT + 0.8*Character.WIDTH + 1);
		
		character[2].setPosX((double) 1*710/17 - (Character.WIDTH - 710/17)/2 + 1);
		character[2].setPosY((double) 15*710/17 - Character.HEIGHT + 0.8*Character.WIDTH + 1);
		
		character[3].setPosX((double) 15*710/17 - (Character.WIDTH - 710/17)/2 + 1);
		character[3].setPosY((double) 15*710/17 - Character.HEIGHT + 0.8*Character.WIDTH + 1);
		
		for (int i=0; i<4; i++) {
			charView[i] = new Group(character[i].getImage());
		}
	}
	
	
	public void createNewGame(Stage menuStage) {
		this.menuStage = menuStage;
		this.menuStage.hide();
		
		createGameElement();
		createGameLoop();
		gameStage.show();
	}
	
	private void createGameLoop() {
		gameTimer = new AnimationTimer() {
			private long lastUpdate = 0;
			
			@Override
			public void handle(long now) {
				if (now - lastUpdate >= 30_000_000) {
					moveCharacter();
					setBomb();
					bombBoom();
					createGameElement();
					createBoomAnimation();
				}
			}
		};
		gameTimer.start();
	}
	
	private void createGameElement() {
		gameMap.getChildren().clear();
		for (int j=0; j<MAP_SIZE_Y; j++) {
			for (int c = 0; c<4; c++) {
				if (getCharacterRow(character[c].getPosY() + 5) == j) {
					if (character[c].getLives() > 0)
						createCharacter(charView[c], character[c]);
				}
			}
			if (j == 7) {
				gameMap.getChildren().add(santaClause);
			}
			for (int i=0; i<MAP_SIZE_X; i++) {
				switch(map[j][i]) {
				case 1:
					createBarrier(i, j, Barrier.PINETREE);
					break;
				case 2:
					createBarrier(i, j, Barrier.SNOWMAN);
					break;
				case 3:
					createBarrier(i, j, Barrier.HAT);
					break;
				case 4:
					createBarrier(i, j, Barrier.SOCKS);
					break;
				case 5:
					createBarrier(i, j, Barrier.CANDLE);
					break;
				case 7:
					createBomb(i, j);
				}
			}
		}
	}
	
	private void moveCharacter() {
		boolean moveAnimation = false;
		boolean movable = true;
		
		if (System.currentTimeMillis() - timeKey >= 100) {
			timeKey = System.currentTimeMillis();
			moveAnimation = true;
		}
		
		int currentCol = getCharacterCol(character[0].getPosX() + 20);
		int currentRow = getCharacterRow(character[0].getPosY() + 15);
		
		if (isDownKeyPressed) {
			int row = getCharacterRow(character[0].getPosY() + 41 + character[0].getDisMove());
			int colL = getCharacterCol(character[0].getPosX() + 5);
			int colR = getCharacterCol(character[0].getPosX() + 36);
			
			if ((map[row][colL] != 0 || map[row][colR] != 0)) {
				movable = false;
				if (map[currentRow][currentCol] == 7 && (currentRow == row) && (currentCol == colL || currentCol == colR))
					movable = true;
			}
			character[0].move(Character.DOWN, movable, moveAnimation);
		} else if (isUpKeyPressed) {
			int row = getCharacterRow(character[0].getPosY() - character[0].getDisMove());
			int colL = getCharacterCol(character[0].getPosX() + 5);
			int colR = getCharacterCol(character[0].getPosX() + 36);
			
			if (map[row][colL] != 0 || map[row][colR] != 0) {
				movable = false;
				if (map[currentRow][currentCol] == 7 && (currentRow == row) && (currentCol == colL || currentCol == colR))
					movable = true;
			}
			character[0].move(Character.UP, movable, moveAnimation);
		} else if (isLeftKeyPressed) {
			int col = getCharacterCol(character[0].getPosX() - character[0].getDisMove());
			int rowL = getCharacterRow(character[0].getPosY() + 5);
			int rowR = getCharacterRow(character[0].getPosY() + 36);
			
			if (map[rowL][col] != 0 || map[rowR][col] != 0) {
				movable = false;
				if (map[currentRow][currentCol] == 7 && (currentCol == col) && (currentRow == rowL || currentRow == rowR))
					movable = true;
			}
			character[0].move(Character.LEFT, movable, moveAnimation);
		} else if (isRightKeyPressed) {
			int col = getCharacterCol(character[0].getPosX() + 41 + character[0].getDisMove());
			int rowL = getCharacterRow(character[0].getPosY() + 5);
			int rowR = getCharacterRow(character[0].getPosY() + 36);
			
			if (map[rowL][col] != 0 || map[rowR][col] != 0) {
				movable = false;
				if (map[currentRow][currentCol] == 7 && (currentCol == col) && (currentRow == rowL || currentRow == rowR))
					movable = true;
			}
			character[0].move(Character.RIGHT, movable, moveAnimation);
		}
		
		if (isLeftKeyPressed == false
			&& isRightKeyPressed == false
			&& isUpKeyPressed == false
			&& isDownKeyPressed == false ) {
				character[0].resetCurrentImage();
			}
		
		charView[0].getChildren().setAll(character[0].getImage());
		charView[0].setLayoutX(character[0].getPosX());
		charView[0].setLayoutY(character[0].getPosY());
	}
	
	private void setBomb() {
		for (int i=0; i<4; i++) {
			int col = getCharacterCol(character[i].getPosX() + 20);
			int row = getCharacterRow(character[i].getPosY() + 15);

			if (isBombSeted[i]) {
				if (map[row][col] == 0 && character[i].isCanSetBomb() == true) {
					character[i].setBomb();
					map[row][col] = 7;
					bombList.add(new Bomb(character[i].getPowBomb(), i, row, col));
				}
				
			}
		}
	}
	private void bombBoom() {
		Iterator<Bomb> iter = bombList.iterator();
		int row, col;
		while (iter.hasNext()){
			Bomb bomb = iter.next();
			if (bomb.isBoom()) {
				row = bomb.getInRow();
				col = bomb.getInCol();
				map[row][col] = 0;
				character[bomb.getOwner()].restoreBomb();
				
				long timeBoom = System.currentTimeMillis();
				
				BombCenter bombCenter = new BombCenter(row, col, timeBoom);
				bombCenterList.add(bombCenter);
				createBombCenter(row, col, bombCenter);
				
				
				for (int i = 1; i<=bomb.getLength(); i++) {
					if (map[row-i][col] == 1 || map[row-i][col] == 2 || map[row-i][col] == -1)
						break;
					if (map[row-i][col] == 3 || map[row-i][col] == 4 || map[row-i][col] == 5) {
						destroyBarrier(row-i, col);
						break;
					}
					BombLine bombLine = new BombLine(row-i, col, 0, timeBoom);
					bombLineList.add(bombLine);
					createBombLine(row-i, col, bombLine);
				}
				
				for (int i = 1; i<=bomb.getLength(); i++) {
					if (map[row+i][col] == 1 || map[row+i][col] == 2 || map[row+i][col] == -1)
						break;
					if (map[row+i][col] == 3 || map[row+i][col] == 4 || map[row+i][col] == 5) {
						destroyBarrier(row+i, col);
						break;
					}
					BombLine bombLine = new BombLine(row+i, col, 0, timeBoom);
					bombLineList.add(bombLine);
					createBombLine(row+i, col, bombLine);
				}
				
				for (int i = 1; i<=bomb.getLength(); i++) {
					System.out.println("run here" + map[row][col + 1]);
					if (map[row][col+i] == 1 || map[row][col+i] == 2 || map[row][col+i] == -1)
						break;
					if (map[row][col+i] == 3 || map[row][col+i] == 4 || map[row][col+i] == 5) {
						destroyBarrier(row, col+i);
						break;
					}
					
					BombLine bombLine = new BombLine(row, col+i, 90, timeBoom);
					bombLineList.add(bombLine);
					createBombLine(row, col+i, bombLine);
				}
				
				for (int i = 1; i<=bomb.getLength(); i++) {
					System.out.println("run here" + map[row][col + 1]);
					if (map[row][col-i] == 1 || map[row][col-i] == 2 || map[row][col-i] == -1)
						break;
					if (map[row][col-i] == 3 || map[row][col-i] == 4 || map[row][col-i] == 5) {
						destroyBarrier(row, col-i);
						break;
					}
					
					BombLine bombLine = new BombLine(row, col-i, 90, timeBoom);
					bombLineList.add(bombLine);
					createBombLine(row, col-i, bombLine);
				}
				
				iter.remove();
			}
		}
	}
	
	private void createBoomAnimation() {
		Iterator<BombLine> iter = bombLineList.iterator();
		Iterator<BombCenter> iter2 = bombCenterList.iterator();
		while (iter.hasNext()) {
			BombLine bombLine = iter.next();
			if (bombLine.getCurrentAnimation() == 5) {
				iter.remove();
				continue;
			}
			for (int i=0; i<4; i++) {
				if (bombLine.getInCol() == getCharacterCol(character[i].getPosX() + 20)
					&& bombLine.getInRow() == getCharacterRow(character[i].getPosY() + 20) ) {
						character[i].decreaseLives();
					}
						
			}
			createBombLine(bombLine.getInCol(), bombLine.getInRow(), bombLine);
		}
		
		while (iter2.hasNext()) {
			BombCenter bombCenter = iter2.next();
			if (bombCenter.getCurrentAnimation() == 5) {
				iter2.remove();
				continue;
			}
			for (int i=0; i<4; i++) {
				if (bombCenter.getInCol() == getCharacterCol(character[i].getPosX() + 20)
					&& bombCenter.getInRow() == getCharacterRow(character[i].getPosY() + 20) ) {
						character[i].decreaseLives();
					}
						
			}
			createBombCenter(bombCenter.getInCol(), bombCenter.getInRow(), bombCenter);
		}
	}
	
	private void createBarrier(int x, int y, Barrier barrier) {
		Image barrierImage = barrier.getImage();
		ImageView barrierView = new ImageView(barrierImage);
		barrierView.setLayoutX((double) x*710/17 - (barrierImage.getWidth() - 710/17)/2);
		barrierView.setLayoutY((double) y*710/17 - barrierImage.getHeight() + barrierImage.getWidth());
		gameMap.getChildren().add(barrierView);
	}
	
	private void destroyBarrier(int row, int col) {
		map[row][col] = 0;
	}
	
	private void createBombLine(int x, int y, BombLine bombLine) {
		bombLine.getImage().setLayoutX((double) x * 710/17 + (710.0/17 - bombLine.getWidth()) / 2);
		bombLine.getImage().setLayoutY((double) y * 710/17 + (710.0/17 - bombLine.getHeight()) / 2);
		gameMap.getChildren().add(bombLine.getImage());
	}
	
	private void createBombCenter(int x, int y, BombCenter bombCenter) {
		bombCenter.getImage().setLayoutX((double) x * 710/17 + (710.0/17 - bombCenter.getWidth()) / 2);
		bombCenter.getImage().setLayoutY((double) y * 710/17 + (710.0/17 - bombCenter.getHeight()) / 2);
		gameMap.getChildren().add(bombCenter.getImage());
	}
	
	private void createCharacter(Group charView, Character character) {
		charView.setLayoutX(character.getPosX());
		charView.setLayoutY(character.getPosY());
		gameMap.getChildren().add(charView);
	}
	
	private void createBomb(int x, int y) {
		ImageView bombView = new ImageView(Bomb.IMAGE);
		bombView.setLayoutX((double) x*710/17 - (Bomb.WIDTH - 710/17)/2);
		bombView.setLayoutY((double) y*710/17 - Bomb.HEIGHT + Bomb.WIDTH);
		gameMap.getChildren().add(bombView);
	}
	
	private int getCharacterRow(double posY) {
		double result = (posY + Character.HEIGHT - 0.8*Character.WIDTH) * 17 / 710 ;
		return (int) result;
	}
	private int getCharacterCol(double posX) {
		double result = (posX + (Character.WIDTH - 710/17)/2 ) * 17 / 710 ;
		return (int) result;
	}
}
