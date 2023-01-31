package controller;

//import java.awt.Color;
import java.io.File;
import java.io.IOException;
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
import netwoork.ConnectLoadWaitRoom;
import netwoork.ConnectLoadingRoom;

public class ViewManager {
	private static int WIDTH = 982;
	private static int HEIGHT = 552;
	private Connect connect;
    private User user;
	private Pane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	private List<ViewRoom> listroommap=new ArrayList<ViewRoom>();
	
	public ViewManager(User user,Connect connect) {
		try {
			this.user=user;
			this.connect=connect;
			initScenes();
			
			createButtonCreate(this.listroommap);
            CreateLoadingbtn(this.listroommap);
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
	private List<ViewRoom> createButtonCreate(List<ViewRoom> listrooMap) {
		AnchorPane createRoomBtn = (AnchorPane) mainScene.lookup("#create-room");
		VBox listRoomFrame=(VBox) mainScene.lookup("#room-list1");
		//List<ViewRoom> listrooMap=new ArrayList<ViewRoom>();
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
					Room newRoom=new Room(""+listrooMap.size(),roomName,user.getId(),listUser);
				    ViewRoom viewRoom=new ViewRoom(newRoom);
                    viewRoom.setViewWaitRoom(mainStage, mainScene);
					listRoomFrame.getChildren().addAll(viewRoom.getJoinbtn());
					VBox.setMargin(viewRoom.getJoinbtn(),new Insets(15, 0, 0, 0));
					listrooMap.add(viewRoom);
					user.setIdScene(1);
					viewRoom.addUser(user);
					mainStage.setScene(viewRoom.getViewWaitRoom());
				  }

				  for(ViewRoom room:listrooMap){
					ConnectLoadWaitRoom connectLoadWaitRoom=new ConnectLoadWaitRoom(connect);
					connectLoadWaitRoom.setRoom(room.getRoom().getId());
					Room updateRoom=connectLoadWaitRoom.getRoom();
					room=new ViewRoom(updateRoom);
				  }
				  for(ViewRoom room:listrooMap){
					// join room
					room.getJoinbtn().setOnMouseClicked(new EventHandler<MouseEvent>(){
                         
						@Override
						public void handle(MouseEvent arg0) {

							try {
								String[] response=connect.SendAndRecvData("#c005#&"+room.getRoom().getId()+"$$", 5500);
								if(response[3].equals("success")){
									
							mainStage.setScene(room.getViewWaitRoom());
							
							user.setIdScene(room.getRoom().getUser_List().size());
							room.addUser(user);
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							
							
							
						}
						
					});
				  }
				
				}
			}catch(Exception e){

			}
		}
	});
	return listrooMap;		
	}

	public void CreateLoadingbtn(List<ViewRoom> listroom){
           ImageView loadingbtn=(ImageView) mainScene.lookup("#loading");
		   VBox listRoomFrame=(VBox) mainScene.lookup("#room-list1");

		   
		   loadingbtn.setOnMouseClicked(new EventHandler<MouseEvent>(){
           
			@Override
			public void handle(MouseEvent arg0) {
				
				try {
					
					ConnectLoadingRoom joinRoomConnect=new ConnectLoadingRoom(connect);
					joinRoomConnect.setListRoom();
					List<Room> ListRoom=joinRoomConnect.getRoom();
					for(Room newRoom: ListRoom){
						
						ViewRoom viewRoom=new ViewRoom(newRoom);
						if(hasRoom(viewRoom)!=null){
						  ViewRoom oldRoom=hasRoom(viewRoom);
                          listRoomFrame.getChildren().remove(oldRoom.getJoinbtn());
						  listroom.remove(oldRoom);
						}
						viewRoom.setViewWaitRoom(mainStage, mainScene);
						listRoomFrame.getChildren().addAll(viewRoom.getJoinbtn());
						VBox.setMargin(viewRoom.getJoinbtn(),new Insets(15, 0, 0, 0));
						listroom.add(viewRoom);
						for(ViewRoom room:listroom){
							ConnectLoadWaitRoom connectLoadWaitRoom=new ConnectLoadWaitRoom(connect);
											connectLoadWaitRoom.setRoom(room.getRoom().getId());
											Room updateRoom=connectLoadWaitRoom.getRoom();
											room=new ViewRoom(updateRoom);
						  }
					    for(ViewRoom room:listroom){
							// join room
							room.getJoinbtn().setOnMouseClicked(new EventHandler<MouseEvent>(){
		
								@Override
								public void handle(MouseEvent arg0) {
									try {
										String[] response=connect.SendAndRecvData("#c005#&"+room.getRoom().getId()+"$$", 5500);
										if(response[3].equals("success")){
									mainStage.setScene(room.getViewWaitRoom());
									//User user=new User("id1","Nhat Sang");
									user.setIdScene(room.getRoom().getUser_List().size());
									room.addUser(user);
										}
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									
								}
								
							});
						  }
						
					}
					
				} catch (IOException e) {

					e.printStackTrace();
				}
				
			}

		   } );
	}

	public ViewRoom hasRoom(ViewRoom room){
      for(ViewRoom view:this.listroommap){
		if(view.getRoom().getName().equals(room.getRoom().getName())){
			return view;
		}
	  }
	  return null;
	}
	
	
	
	
	public Stage getMainStage() {
		return mainStage;
	}
}
