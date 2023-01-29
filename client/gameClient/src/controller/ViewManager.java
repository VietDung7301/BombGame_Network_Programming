package controller;

//import java.awt.Color;
import java.io.File;

import java.net.URL;

import java.util.ArrayList;

import java.util.List;

import java.util.Optional;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Room;
import model.User;
import netwoork.Connect;

public class ViewManager {
	private static int WIDTH = 982;
	private static int HEIGHT = 552;
	private Connect connect;
    private User user;
	private Pane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	
	
	public ViewManager(User user,Connect connect) {
		try {
			this.user=user;
			this.connect=connect;
			initScenes();
			createButtonCreate();
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
			URL url = new File("src/view/Scene_5.fxml").toURI().toURL();
			mainPane = FXMLLoader.load(url);
			mainScene = new Scene(mainPane, WIDTH, HEIGHT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// tao button 
	private void createButtonCreate() {
		AnchorPane createRoomBtn = (AnchorPane) mainScene.lookup("#create-room");
		VBox listRoomFrame=(VBox) mainScene.lookup("#room-list1");
		List<ViewRoom> listrooMap=new ArrayList<ViewRoom>();
		createRoomBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			int roomnumber=1;
			@Override
			public void handle(MouseEvent event) {
				try {
				
				//Create a dialog to input room's name
				TextInputDialog dialog = new TextInputDialog();
			    dialog.setTitle("Input Dialog");
			    dialog.setHeaderText("Enter room's name:");
			    dialog.setContentText("Name: ");
			    Image image = new Image("file:src/images/bomb.png");
			    ImageView imageView = new ImageView(image);
			    dialog.setGraphic(imageView);
			    Optional<String> result = dialog.showAndWait();
			    if (result.isPresent()) {
			      String roomName=result.get();
			      String inputString = "#c004#&"+roomName+"$$";
				  //send and receive data from client to server
				  String[] resul=connect.SendAndRecvData(inputString, 5500);
				  
				  if(!resul[1].equals("serr")){
					//add room
					List<User> listUser=new ArrayList<User>();
					Room newRoom=new Room(roomnumber+"IT",roomName,user.getId(),listUser);
				    ViewRoom viewRoom=new ViewRoom(newRoom);
                    viewRoom.setViewWaitRoom(mainStage, mainScene);
					listRoomFrame.getChildren().addAll(viewRoom.getJoinbtn());
					VBox.setMargin(viewRoom.getJoinbtn(),new Insets(15, 0, 0, 0));
					listrooMap.add(viewRoom);
					viewRoom.addUser(user);
					mainStage.setScene(viewRoom.getViewWaitRoom());
				  }

				  for(ViewRoom room:listrooMap){
					// join room
					room.getJoinbtn().setOnMouseClicked(new EventHandler<MouseEvent>(){

						@Override
						public void handle(MouseEvent arg0) {
							mainStage.setScene(room.getViewWaitRoom());
							//User user=new User("id1","Nhat Sang");
							room.addUser(user);
							
							
						}
						
					});
				  }
				}
			}catch(Exception e){

			}
		}
	});		
	}

	public void CreateLoadingbtn(){
           
	}
	
	
	
	
	public Stage getMainStage() {
		return mainStage;
	}
}
