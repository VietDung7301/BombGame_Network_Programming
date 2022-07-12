package controller;

import java.io.File;
import java.net.URL;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ViewManager {
	private static int WIDTH = 982;
	private static int HEIGHT = 725;
	private BorderPane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	public ViewManager() {
		mainPane = new BorderPane();
		mainScene = new Scene(mainPane, WIDTH, HEIGHT);
		mainStage = new Stage();
		mainStage.setScene(mainScene);
		
		mainScene.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				GameViewManager gameManager = new GameViewManager();
				gameManager.createNewGame(mainStage);
			}
		});
	}
	
	public Stage getMainStage() {
		return mainStage;
	}
}
