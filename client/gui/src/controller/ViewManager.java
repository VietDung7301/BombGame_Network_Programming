package controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ViewManager {
	private static int WIDTH = 982;
	private static int HEIGHT = 552;
	
	private Scene modeScene;
	private Scene guideScene;
	
	private Pane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	public ViewManager() {
		try {
			initScenes();
			createButtons();
			
			mainStage = new Stage();
			mainStage.setScene(mainScene);
			mainStage.setTitle("BoomIT 7");
			mainStage.getIcons().add(new Image("file:src/images/bomb.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initScenes() {
		try {
			URL url = new File("src/view/Scene_1.fxml").toURI().toURL();
			mainPane = FXMLLoader.load(url);
			mainScene = new Scene(mainPane, WIDTH, HEIGHT);

			url = new File("src/view/Scene_2.fxml").toURI().toURL();		
			modeScene = new Scene(FXMLLoader.load(url), WIDTH, HEIGHT);

			url = new File("src/view/Scene_3.fxml").toURI().toURL();
			guideScene = new Scene(FXMLLoader.load(url), WIDTH, HEIGHT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createButtons() {
		AnchorPane startBtn = (AnchorPane) mainScene.lookup("#start-btn");
		AnchorPane guideBtn = (AnchorPane) mainScene.lookup("#guide-btn");
		AnchorPane rankingBtn = (AnchorPane) mainScene.lookup("#ranking-btn");
		ImageView backBtn1 = (ImageView) modeScene.lookup("#back-btn");
		AnchorPane backBtn2 = (AnchorPane) guideScene.lookup("#back-btn");
		AnchorPane _1pBtn = (AnchorPane) modeScene.lookup("#one-player");
		AnchorPane _2pBtn = (AnchorPane) modeScene.lookup("#two-player");
		
		startBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mainStage.setScene(modeScene);
			}
		});
		
		guideBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mainStage.setScene(guideScene);
			}
		});
		
		rankingBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
			}
		});
		
		backBtn1.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mainStage.setScene(mainScene);
			}
		});
		
		backBtn2.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mainStage.setScene(mainScene);
			}
		});
		
		_1pBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				GameViewManager gameManager = new GameViewManager(1, 0);
				gameManager.createNewGame(mainStage);
			}
		});
		
		_2pBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				GameViewManager gameManager = new GameViewManager(2, 0);
				gameManager.createNewGame(mainStage);
			}
		});
	}
	
	
	public Stage getMainStage() {
		return mainStage;
	}
}
