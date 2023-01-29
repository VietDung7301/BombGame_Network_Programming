package controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.*;
import model.Character;

public class GameViewManager {
	public static final int MAP_SIZE_X = 17;
	public static final int MAP_SIZE_Y = 17;
	public static final double CELL_SIZE = 710.0/17;
	
	private int numPlayer;
	
	private AnchorPane gameMap;
	private BorderPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	
	private Stage menuStage;
	
	private AnchorPane pauseBtn;
	private Label pauseLabel;
	
	private ImageView santaClause;
	
	Random randItem = new Random();
	long timeKey = 0;
	
	private Character[] character;
	
	private Label[] liveLabel;
	private Label[] powBombLabel;
	private Label[] qtyBombLabel;
	private Label[] speedLabel;
	private Label timeLabel;
	private double timeLeft;
	
	
	private ArrayList<Item> itemList = new ArrayList<Item>();
	private ArrayList<Bomb> bombList = new ArrayList<Bomb>();
	private ArrayList<BombLine> bombLineList = new ArrayList<BombLine>();
	private ArrayList<BombCenter> bombCenterList = new ArrayList<BombCenter>();
	
//	1: Pine tree
//	2: Snowman
//	3: Hat
//	4: Candle
//	5: Socks
//	7: Bomb
//	9: Santa Clause
//	-1: Live item
//	-2: Power item
//	-3: Quantity item
//	-4: Speed item
	private static int map[][] = {
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 0, 0, 3, 3, 4, 1, 0, 1, 0, 2, 0, 0, 0, 0, 0, 1},
		{1, 0, 2, 3, 2, 5, 0, 0, 1, 5, 0, 0, 2, 0, 2, 0, 1},
		{1, 0, 2, 0, 1, 1, 2, 3, 5, 5, 2, 2, 1, 0, 2, 0, 1},
		{1, 0, 0, 0, 0, 0, 2, 2, 1, 1, 2, 0, 0, 0, 0, 0, 1},
		{1,-1, 2, 1, 1, 4, 4, 0, 0, 0, 0, 0, 2, 1, 1, 0, 1},
		{1, 0, 0, 2, 2, 3, 2, 2, 1, 0, 1, 0, 1, 1, 0, 0, 1},
		{1, 2, 0, 0, 1, 4, 3, 9, 9, 9, 4, 0, 1, 0, 0, 2, 1},
		{1, 0, 0, 2, 2, 4, 1, 9, 9, 9, 1, 0, 1, 2, 0, 3, 1},
		{1, 0, 2, 1, 1, 0, 0, 9, 9, 9, 0, 3, 2, 1, 2, 4, 1},
		{1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 0, 5, 0, 0, 0, 1},
		{1, 0, 1, 0, 2, 2, 2, 0, 5, 0, 2, 1, 1, 0, 2, 0, 1},
		{1, 0, 1, 0, 2, 3, 3, 4, 2, 0, 0, 0, 1, 0, 2, 0, 1},
		{1, 0, 0, 0, 0, 0, 1, 0, 2, 5, 2, 0, 0, 0, 0, 0, 1},
		{1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 2, 2, 0, 2, 1, 0, 1},
		{1, 0, 0, 0, 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
	};
				
	AnimationTimer gameTimer;
	
	public GameViewManager(int numPlayer) {
		this.numPlayer = numPlayer;
		this.timeLeft = 150;
		initializeStage();
		initializeLabel();
		initializeButton();
		initializeCharacter();
		createKeyListeners();
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
	
	private void initializeLabel() {
		liveLabel = new Label[4];
		qtyBombLabel = new Label[4];
		powBombLabel = new Label[4];
		speedLabel = new Label[4];
		for (int i=0; i<4; i++) {
			liveLabel[i] = (Label) gamePane.lookup("#live-label-" + (i+1));
			qtyBombLabel[i] = (Label) gamePane.lookup("#qtybomb-label-" + (i+1));
			powBombLabel[i] = (Label) gamePane.lookup("#powbomb-label-" + (i+1));
			speedLabel[i] = (Label) gamePane.lookup("#speed-label-" + (i+1));
		}
		
		timeLabel = (Label) gamePane.lookup("#time-label");
	}
	
	private void initializeButton() {
		pauseBtn = (AnchorPane) gamePane.lookup("#pause-btn");
		pauseLabel = (Label) pauseBtn.lookup("#pause-label");
		pauseBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if (pauseLabel.getText().equals("Pause")) {
					gameTimer.stop();
					pauseLabel.setText("Resume");
				} else {
					gameTimer.start();
					pauseLabel.setText("Pause");
				}
				
			}
		});
	}
	
	private void createKeyListeners() {
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				for (int i=0; i<4; i++) {
					if (character[i] instanceof Player) {
						Player player = (Player) character[i];
						if (event.getCode() == player.getUpKey()) {
							player.setGoUp(true);
							player.setGoDown(false);
							player.setGoLeft(false);
							player.setGoRight(false);
						} else if (event.getCode() == player.getDownKey()) {
							player.setGoUp(false);
							player.setGoDown(true);
							player.setGoLeft(false);
							player.setGoRight(false);
						} else if (event.getCode() == player.getLeftKey()) {
							player.setGoUp(false);
							player.setGoDown(false);
							player.setGoLeft(true);
							player.setGoRight(false);
						} else if (event.getCode() == player.getRightKey()) {
							player.setGoUp(false);
							player.setGoDown(false);
							player.setGoLeft(false);
							player.setGoRight(true);
						} else if (event.getCode() == player.getBombKey()) {
							player.setPlantingBomb(true);
						}
					}
				}
			}
		});
		
		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				for (int i=0; i<4; i++) {
					if (character[i] instanceof Player) {
						Player player = (Player) character[i];
						if (event.getCode() == player.getUpKey()) {
							player.setGoUp(false);
						} else if (event.getCode() == player.getDownKey()) {
							player.setGoDown(false);
						} else if (event.getCode() == player.getLeftKey()) {
							player.setGoLeft(false);
						} else if (event.getCode() == player.getRightKey()) {
							player.setGoRight(false);
						} else if (event.getCode() == player.getBombKey()) {
							player.setPlantingBomb(false);
						}
					}
				}
			}
		});
	}
	
	
	/* tao nguoi choi*/
	private void initializeCharacter() {
		Bot bot;
		character = new Character[4];
		if (numPlayer == 1) {
			character[0] = new Player(0, 1.5*CELL_SIZE, 1.5*CELL_SIZE, KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SPACE);
			character[1] = new Bot(0, (MAP_SIZE_X-1.5) * CELL_SIZE, 1.5*CELL_SIZE);
			bot = (Bot) character[1];
			bot.autoPlay(character, map);
		} else if (numPlayer == 2) {
			character[0] = new Player(0, 1.5*CELL_SIZE, 1.5*CELL_SIZE, KeyCode.W, KeyCode.S, KeyCode.A, KeyCode.D, KeyCode.SPACE);
			character[1] = new Player(0, (MAP_SIZE_X-1.5) * CELL_SIZE, 1.5*CELL_SIZE, KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.ENTER);
		}
		character[2] = new Bot(1, 1.5*CELL_SIZE, (MAP_SIZE_Y-1.5) * CELL_SIZE);
		bot = (Bot) character[2];
		bot.autoPlay(character, map);
		character[3] = new Bot(1, (MAP_SIZE_X-1.5) * CELL_SIZE, (MAP_SIZE_Y-1.5) * CELL_SIZE);
		bot = (Bot) character[3];
		bot.autoPlay(character, map);
	}
	
	
	public void createNewGame(Stage menuStage) {
		this.menuStage = menuStage;
		this.menuStage.hide();
		
		createGameLoop();
		gameStage.show();
	}
	
	private void createGameLoop() {
		gameTimer = new AnimationTimer() {
			private long lastUpdate = 0;
			
			@Override
			public void handle(long now) {
				if (now - lastUpdate >= 30_000_000) {
					lastUpdate = now;
					updateTimeLeft();
					moveCharacter();
					setBomb();
					bombBoom();
					createGameElement();
					eatItems();
					updateLabel();
					createBoomAnimation();
				}
			}
		};
		gameTimer.start();
	}
	
	private void updateTimeLeft() {
		this.timeLeft-= 0.03;
		timeLabel.setText("" + ((int) timeLeft));
	}
	
	private void createGameElement() {
		itemList.clear();
		gameMap.getChildren().clear();
		for (int j=0; j<MAP_SIZE_Y; j++) {
			for (int c = 0; c<4; c++) {
				if (Character.getCharacterRow(character[c].getLayoutY() + 20) == j) {
					if (character[c].getLives() > 0)
						createCharacter(character[c]);
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
					break;
				case -1:
				case -2:
				case -3:
				case -4:
					createItem(j, i, map[j][i]);
				}
			}
		}
	}
	
	private void moveCharacter() {
		for (int i=0; i<4; i++) {
			if (character[i].isGoDown()) {
				character[i].move(Character.DOWN, map, CELL_SIZE);
			} else if (character[i].isGoUP()) {
				character[i].move(Character.UP, map, CELL_SIZE);
			} else if (character[i].isGoLeft()) {
				character[i].move(Character.LEFT, map, CELL_SIZE);
			} else if (character[i].isGoRight()) {
				character[i].move(Character.RIGHT, map, CELL_SIZE);
			}
			
			if (character[i].isGoDown() == false
				&& character[i].isGoUP() == false
				&& character[i].isGoLeft() == false
				&& character[i].isGoRight() == false ) {
					character[i].stop();
				}
		}
		
	}
	
	private void setBomb() {
		for (int i=0; i<4; i++) {
			int col = Character.getCharacterCol(character[i].getLayoutX() + 20);
			int row = Character.getCharacterRow(character[i].getLayoutY() + 15);

			if (character[i].isPlantingBomb()) {
				if (map[row][col] == 0 && character[i].isCanSetBomb() == true) {
					Bomb bomb = character[i].setBomb(row, col);
					map[row][col] = 7;
					bombList.add(bomb);
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
				bomb.getOwner().restoreBomb();
				
				long timeBoom = System.currentTimeMillis();
				
				BombCenter bombCenter = new BombCenter(row, col, timeBoom);
				bombCenterList.add(bombCenter);
				createBombCenter(row, col, bombCenter);
				
				
				int[] ar = {-1, 0, 1, 0};
				int[] ac = {0, -1, 0, 1};
				boolean[] isBlocked = {false, false, false, false};
				
				for (int i = 1; i<=bomb.getLength(); i++) {
					for (int j=0; j<4; j++) {
						if (!isBlocked[j]) {
							if (map[row + ar[j]*i][col + ac[j]*i] == 1 || map[row + ar[j]*i][col + ac[j]*i] == 2 || map[row + ar[j]*i][col + ac[j]*i] == 9) {
								isBlocked[j] = true;
								continue;
							}
							if (map[row + ar[j]*i][col + ac[j]*i] == 3 || map[row + ar[j]*i][col + ac[j]*i] == 4 || map[row + ar[j]*i][col + ac[j]*i] == 5) {
								destroyBarrier(row + ar[j]*i, col + ac[j]*i);
								isBlocked[j] = true;
								continue;
							}
							BombLine bombLine = new BombLine(row + ar[j]*i, col + ac[j]*i, Math.abs(ac[j] * 90), timeBoom);
							bombLineList.add(bombLine);
							createBombLine(row + ar[j]*i, col + ac[j]*i, bombLine);						
						}						
					}
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
				if (bombLine.getInCol() == Character.getCharacterCol(character[i].getLayoutX() + 20)
					&& bombLine.getInRow() == Character.getCharacterRow(character[i].getLayoutY() + 20) ) {
						character[i].decreaseLive();
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
				if (bombCenter.getInCol() == Character.getCharacterCol(character[i].getLayoutX() + 20)
					&& bombCenter.getInRow() == Character.getCharacterRow(character[i].getLayoutY() + 20) ) {
						character[i].decreaseLive();
					}
						
			}
			createBombCenter(bombCenter.getInCol(), bombCenter.getInRow(), bombCenter);
		}
	}
	
	private void createBarrier(int x, int y, Barrier barrier) {
		Image barrierImage = barrier.getImage();
		ImageView barrierView = new ImageView(barrierImage);
		barrierView.setLayoutX((double) x*CELL_SIZE - (barrierImage.getWidth() - CELL_SIZE)/2);
		barrierView.setLayoutY((double) y*CELL_SIZE - barrierImage.getHeight() + barrierImage.getWidth());
		gameMap.getChildren().add(barrierView);
	}
	
	private void destroyBarrier(int row, int col) {
		map[row][col] = -(randItem.nextInt(4) + 1);
	}
	
	private void createItem(int row, int col, int type) {
		Item item = null;
		switch (type) {
		case -1: 
			item = new LiveItem(row, col, CELL_SIZE);
			break;
		case -2:
			item = new PowBombItem(row, col, CELL_SIZE);
			break;
		case -3:
			item = new QtyBombItem(row, col, CELL_SIZE);
			break;
		case -4:
			item = new SpeedItem(row, col, CELL_SIZE);
			break;
		default:
			break;
		}
		itemList.add(item);
		gameMap.getChildren().add(item);
	}
	
	private void eatItems() {
		Iterator<Item> iter = itemList.iterator();
		while (iter.hasNext()) {
			Item item = iter.next();
			for (int i=0; i<4; i++) {
				if (item.getInCol() == Character.getCharacterCol(character[i].getLayoutX() + 20)
				&& item.getInRow() == Character.getCharacterRow(character[i].getLayoutY() + 20) ) {
					item.beAte(character[i]);
					map[item.getInRow()][item.getInCol()] = 0;
					iter.remove();
					break;
				}
			}
		}
	}
	
	private void updateLabel() {
		for (int i=0; i<4; i++) {
			liveLabel[i].setText("" + character[i].getLives());
			qtyBombLabel[i].setText("" + character[i].getQtyBomb());
			powBombLabel[i].setText("" + character[i].getPowBomb());
			speedLabel[i].setText("" + character[i].getSpeed());
		}
	}
	
	private void createBombLine(int x, int y, BombLine bombLine) {
		bombLine.getImage().setLayoutX((double) x * CELL_SIZE + (CELL_SIZE - bombLine.getWidth()) / 2);
		bombLine.getImage().setLayoutY((double) y * CELL_SIZE + (CELL_SIZE - bombLine.getHeight()) / 2);
		gameMap.getChildren().add(bombLine.getImage());
	}
	
	private void createBombCenter(int x, int y, BombCenter bombCenter) {
		bombCenter.getImage().setLayoutX((double) x * CELL_SIZE + (CELL_SIZE - bombCenter.getWidth()) / 2);
		bombCenter.getImage().setLayoutY((double) y * CELL_SIZE + (CELL_SIZE - bombCenter.getHeight()) / 2);
		gameMap.getChildren().add(bombCenter.getImage());
	}
	
	private void createCharacter(Character character) {
		gameMap.getChildren().add(character);
	}
	
	private void createBomb(int x, int y) {
		ImageView bombView = new ImageView(Bomb.IMAGE);
		bombView.setLayoutX((double) x*CELL_SIZE - (Bomb.WIDTH - CELL_SIZE)/2);
		bombView.setLayoutY((double) y*CELL_SIZE - Bomb.HEIGHT + Bomb.WIDTH);
		gameMap.getChildren().add(bombView);
	}
}
