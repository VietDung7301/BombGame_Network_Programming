package view;

import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import model.Barrier;
import model.Character;

public class GameSceneController {
	private static int MAP_SIZE_X = 17;
	private static int MAP_SIZE_Y = 17;
	
	
	@FXML
	private List<Label> liveList;
	@FXML
	private List<Label> qtyBombList;
	@FXML
	private List<Label> powBombList;
	@FXML
	private List<Label> speedList;
	@FXML
	private AnchorPane gameMap;
	@FXML
	private BorderPane gameScene;
	
	
//	public void initialize() {
//		for (int i=0; i<MAP_SIZE_X; i++) {
//			createBarrier(i, 0, Barrier.PINETREE);
//			createBarrier(i, MAP_SIZE_Y - 1, Barrier.PINETREE);
//		}
//		for (int i=0; i<MAP_SIZE_Y; i++) {
//			createBarrier(0, i, Barrier.PINETREE);
//			createBarrier(MAP_SIZE_X - 1, i, Barrier.PINETREE);
//		}
//		
//		createCharacter(3, 3, Character.BLACK);
//    }
//	
//	private void createBarrier(int x, int y, Barrier barrier) {
//		Image barrierImage = barrier.getImage();
//		ImageView barrierView = new ImageView(barrierImage);
//		barrierView.setLayoutX((double) x*710/17 - (barrierImage.getWidth() - 710/17)/2);
//		barrierView.setLayoutY((double) y*710/17 - barrierImage.getHeight() + barrierImage.getWidth());
//		gameMap.getChildren().add(barrierView);
//		map[x][y] = barrier.getType();
//	}
//	
//	private void createCharacter(int x, int y, Character character) {
//		character.move(Character.DOWN);
//		character.move(Character.LEFT);
//		
//		Image charImage = character.getImage();
//		System.out.println(charImage.getHeight());
//		ImageView charView = new ImageView(charImage);
//		charView.setLayoutX((double) x*710/17 - (charImage.getWidth() - 710/17)/2);
//		charView.setLayoutY((double) y*710/17 - charImage.getHeight() + charImage.getWidth());
//		gameMap.getChildren().add(charView);
//	}
//	
//	private void movePlayer() {
//		
//	}
}
