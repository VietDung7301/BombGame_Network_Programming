package controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import exception.InvalidResponseException;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
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
import model.character.Player;
import model.barrier.Barrier;
import model.bomb.*;
import model.character.ControlPlayer;
import model.common.Direction;
import model.item.*;
import service.GameResponse;
import service.GameService;

public class GameViewManager {
	public static final int MAP_SIZE_X = 17;
	public static final int MAP_SIZE_Y = 17;
	public static final double CELL_SIZE = 710.0/17;
	
	public static final KeyCode KEY_UP = KeyCode.UP;
	public static final KeyCode KEY_DOWN = KeyCode.DOWN;
	public static final KeyCode KEY_LEFT = KeyCode.LEFT;
	public static final KeyCode KEY_RIGHT = KeyCode.RIGHT;
	public static final KeyCode KEY_SPACE = KeyCode.SPACE;
	
	public static final Set<Integer> barrierValue = new HashSet<Integer> (
			Arrays.asList(1, 2, 3, 4, 5, 9));
			
	
	private GameService gameService;
	
	private AnchorPane gameMap;
	private BorderPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	
	private Stage menuStage;
	
	
	private ImageView santaClause;
	
	long timeKey = 0;
	
	private int numPlayer;
	private int controlPlayerPosition;
	private Player[] playerList;
	private ControlPlayer controlPlayer;
	
	
	private Label[] liveLabel;
	private Label[] powBombLabel;
	private Label[] qtyBombLabel;
	private Label[] speedLabel;
	private Label timeLabel;
	private double timeLeft;
	private AnchorPane quitBtn;
	
	private ArrayList<BombLine> bombLineList;
	private ArrayList<BombCenter> bombCenterList;
	private ArrayList<Boom> boomList;
	
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
	private int map[][];
				
	AnimationTimer gameTimer;
	
	public GameViewManager(int numPlayer, int controlPlayerPos) {
		System.out.println("num: " + numPlayer + ' ' + controlPlayerPos);
		this.numPlayer = numPlayer;
		this.controlPlayerPosition = controlPlayerPos;
		this.gameService = new GameService();
		initializeStage();
		initializeCharacter();
		initializeLabel();
		initializeButton();
		initializeGameElement();
		initializeKeyListeners();
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
		playerList = new Player[numPlayer];
		for (int i=0; i < numPlayer; i++) {
			if (i != controlPlayerPosition)
				playerList[i] = new Player();
			else {
				controlPlayer = new ControlPlayer(KEY_UP,KEY_DOWN,KEY_LEFT,KEY_RIGHT,KEY_SPACE);
				playerList[i] = controlPlayer;
			}
		}
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
		quitBtn = (AnchorPane) gamePane.lookup("#pause-btn");
		quitBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				gameService.quitGame();
			}
		});
	}
	
	private void initializeGameElement() {
		bombLineList = new ArrayList<BombLine>();
		bombCenterList = new ArrayList<BombCenter>();
		boomList = new ArrayList<Boom>();
	}
	
	private void initializeKeyListeners() {
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == controlPlayer.getUpKey()) {
					controlPlayer.setMovingDirection(Direction.UP);
				} else if (event.getCode() == controlPlayer.getDownKey()) {
					controlPlayer.setMovingDirection(Direction.DOWN);
				} else if (event.getCode() == controlPlayer.getLeftKey()) {
					controlPlayer.setMovingDirection(Direction.LEFT);
				} else if (event.getCode() == controlPlayer.getRightKey()) {
					controlPlayer.setMovingDirection(Direction.RIGHT);
				} else if (event.getCode() == controlPlayer.getBombKey()) {
					controlPlayer.setPlantingBomb(true);
				}
			}
		});
		
		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == controlPlayer.getUpKey()) {
					if (controlPlayer.getMovingDirection() == Direction.UP) {
						controlPlayer.setMovingDirection(Direction.NONE);
					}
				} else if (event.getCode() == controlPlayer.getDownKey()) {
					if (controlPlayer.getMovingDirection() == Direction.DOWN) {
						controlPlayer.setMovingDirection(Direction.NONE);
					}
				} else if (event.getCode() == controlPlayer.getLeftKey()) {
					if (controlPlayer.getMovingDirection() == Direction.LEFT) {
						controlPlayer.setMovingDirection(Direction.NONE);
					}
				} else if (event.getCode() == controlPlayer.getRightKey()) {
					if (controlPlayer.getMovingDirection() == Direction.RIGHT) {
						controlPlayer.setMovingDirection(Direction.NONE);
					}
				} else if (event.getCode() == controlPlayer.getBombKey()) {
					controlPlayer.setPlantingBomb(false);
				}
			}
		});
	}
	
	
	
	public void createNewGame(Stage menuStage) {
		this.menuStage = menuStage;
		menuStage.hide();
		createGameLoop();
		gameStage.show();
	}
	
	private void createGameLoop() {
		gameTimer = new AnimationTimer() {
			long lastUpdate = 0;
			long lastGetGameStatus = 0; 
			
			@Override
			public void handle(long now) {
				if (now - lastUpdate >= 30_000_000L) {
					lastUpdate = now;
					if (now - lastGetGameStatus >= 30_000_000L) {
						lastGetGameStatus = now;
						updateGame();
					}
					bombBoom();
					createGameElement();
					updateLabel();
					createBoomAnimation();
				}
			}
		};
		gameTimer.start();
	}
	
	private void updateGame() {
		try {
			GameResponse newGameStatus = 
					gameService.sendPlayerAction(
							numPlayer, 
							controlPlayer.isPlantingBomb(), 
							controlPlayer.getMovingDirection()
					);
			
			// Update Map
			this.map = newGameStatus.getMap();
			
			// Update player infor
			for (int i=0; i < numPlayer; i++) {
				playerList[i].setBasicInfor(newGameStatus.getPlayerList()[i]);
			}
			
			// Update timeLeft;
			this.timeLeft = newGameStatus.getTimeLeft();
			timeLabel.setText("" + ((int) this.timeLeft));
			
			// Update boom list
			this.boomList = newGameStatus.getBoomList();
		} catch (InvalidResponseException e) {
			e.printStackTrace();
		}
	}
	
	private void createGameElement() {
		gameMap.getChildren().clear();
		for (int j=0; j<MAP_SIZE_Y; j++) {
			for (int c = 0; c<numPlayer; c++) {
				if (playerList[c].getLives() > 0 && getRowByPosY(playerList[c].getPosY()) == j) {
						createPlayer(playerList[c]);
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
	
	private void bombBoom() {
		Iterator<Boom> iter = boomList.iterator();
		int row, col;
		while (iter.hasNext()){
			Boom boom = iter.next();
			
			row = boom.getInRow();
			col = boom.getInCol();
			
			long timeBoom = System.currentTimeMillis();
			
			BombCenter bombCenter = new BombCenter(row, col, timeBoom);
			bombCenterList.add(bombCenter);
			createBombCenter(row, col, bombCenter);
			
			
			int[] ar = {-1, 0, 1, 0};
			int[] ac = {0, -1, 0, 1};
			boolean[] isBlocked = {false, false, false, false};
			
			for (int i = 1; i <= boom.getLength(); i++) {
				for (int j=0; j<4; j++) {
					if (!isBlocked[j]) {
						if (barrierValue.contains(map[row + ar[j]*i][col + ac[j]*i]) ) {
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
	
	private void createBoomAnimation() {
		Iterator<BombLine> iter = bombLineList.iterator();
		Iterator<BombCenter> iter2 = bombCenterList.iterator();
		while (iter.hasNext()) {
			BombLine bombLine = iter.next();
			if (bombLine.getCurrentAnimation() == 5) {
				iter.remove();
				continue;
			}

			createBombLine(bombLine.getInCol(), bombLine.getInRow(), bombLine);
		}
		
		while (iter2.hasNext()) {
			BombCenter bombCenter = iter2.next();
			if (bombCenter.getCurrentAnimation() == 5) {
				iter2.remove();
				continue;
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
		gameMap.getChildren().add(item);
	}
	
	private void updateLabel() {
		for (int i=0; i < numPlayer; i++) {
			liveLabel[i].setText("" + playerList[i].getLives());
			qtyBombLabel[i].setText("" + playerList[i].getQtyBomb());
			powBombLabel[i].setText("" + playerList[i].getPowBomb());
			speedLabel[i].setText("" + playerList[i].getSpeed());
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
	
	private void createPlayer(Player player) {
		gameMap.getChildren().add(player);
	}
	
	private void createBomb(int x, int y) {
		ImageView bombView = new ImageView(Bomb.IMAGE);
		bombView.setLayoutX((double) x*CELL_SIZE - (Bomb.WIDTH - CELL_SIZE)/2);
		bombView.setLayoutY((double) y*CELL_SIZE - Bomb.HEIGHT + Bomb.WIDTH);
		gameMap.getChildren().add(bombView);
	}
	
	private int getRowByPosY(double posY) {
		return (int) (posY/CELL_SIZE);
	}
	
	private int getColByPosX(double posX) {
		return (int) (posX/CELL_SIZE);
	}
}
