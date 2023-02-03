package controller;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.fxml.FXMLLoader;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.robot.Robot;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.input.InputEvent;
import model.Room;
import model.User;
import netwoork.Connect;
import netwoork.ConnectLoadWaitRoom;

public class ViewRoom {
    private Room room;
    private Button joinbtn;
    private Scene viewWaitRoom;
    public ViewRoom(Room room){
             this.room=room;
            String textBtn="#"+room.getId()+" "+room.getName()+" "+this.room.getUser_List().size()+"/4";
            joinbtn=new Button(textBtn);
            joinbtn.setPrefWidth(363);
            joinbtn.setPrefHeight(69);
            joinbtn.setFont(Font.font("Bold",23));
            joinbtn.setStyle("-fx-background-color: orange;"); 
            joinbtn.setTextFill(Color.WHITE);
            URL url;
        try {
                url = new File("src/view/Scene_6.fxml").toURI().toURL();
                this.viewWaitRoom=new Scene(FXMLLoader.load(url), 771, 551);
                

        } catch (Exception e) {
               
                e.printStackTrace();
        }
           
            if(this.room.getUser_List().size()!=0){
             for(User user:this.room.getUser_List()){
            String id="#player"+user.getIdScene();
            String name="#username"+user.getIdScene();
    	    ImageView playerImg=(ImageView) this.viewWaitRoom.lookup(id);
            Label username=(Label) this.viewWaitRoom.lookup(name);
            username.setText(user.getName());
            Label title=(Label) this.viewWaitRoom.lookup("#title");
            title.setText("#"+room.getId()+" "+room.getName()+" "+user.getIdScene()+"/4");
    	    Image imageX=new Image("file:src/images/player"+user.getIdScene()+".png");
	    playerImg.setImage(imageX);
             }
            }
    }
    
    public void addUser(User user){
            if(!isFull()){
            this.room.getUser_List().add(user);
            user.setIdScene(this.room.getUser_List().size());
            joinbtn.setText("#"+room.getId()+" "+room.getName()+" "+user.getIdScene()+"/4");
            String id="#player"+user.getIdScene();
            String name="#username"+user.getIdScene();
    	    ImageView playerImg=(ImageView) this.viewWaitRoom.lookup(id);
            Label username=(Label) this.viewWaitRoom.lookup(name);
            username.setText(user.getName());
            Label title=(Label) this.viewWaitRoom.lookup("#title");
            title.setText("#"+room.getId()+" "+room.getName()+" "+user.getIdScene()+"/4");
    	    Image imageX=new Image("file:src/images/player"+user.getIdScene()+".png");
	    playerImg.setImage(imageX);
            }    
    }
    public void createUser(User user){
        
            joinbtn.setText("#"+room.getId()+" "+room.getName()+" "+user.getIdScene()+"/4");
            String id="#player"+user.getIdScene();
            String name="#username"+user.getIdScene();
    	    ImageView playerImg=(ImageView) this.viewWaitRoom.lookup(id);
            Label username=(Label) this.viewWaitRoom.lookup(name);
            username.setText(user.getName());
            Label title=(Label) this.viewWaitRoom.lookup("#title");
            title.setText("#"+room.getId()+" "+room.getName()+" "+user.getIdScene()+"/4");
    	    Image imageX=new Image("file:src/images/player"+user.getIdScene()+".png");
	    playerImg.setImage(imageX);
    }
    public boolean isFull(){
        return this.room.getUser_List().size() == 4;
    }
    public void setViewWaitRoom(Stage mainStage,Scene previousScene){
        try {
                
                ImageView btnback=(ImageView) viewWaitRoom.lookup("#back");
                btnback.setCursor(Cursor.HAND);				
                btnback.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
                        public void handle(MouseEvent event) {
                                
                                mainStage.setScene(previousScene);
                        }
                });
                
                ImageView startbtn=(ImageView) viewWaitRoom.lookup("#start");
                startbtn.setCursor(Cursor.HAND);
                startbtn.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
                        public void handle(MouseEvent event) {
                        

                        }
                });
        }catch (Exception e) {
               e.printStackTrace();
        }
   }
   public void loadingWaitroom(Connect connect,Stage mainStage){
        ImageView btnback=(ImageView) viewWaitRoom.lookup("#loadingwaitroom");
        btnback.setCursor(Cursor.HAND);
        String roomId=this.room.getId();
       
        ConnectLoadWaitRoom connectLoadWaitRoom=new ConnectLoadWaitRoom(connect);
        connectLoadWaitRoom.setRoom(roomId);
        Room updateRoom=connectLoadWaitRoom.getRoom();
        this.room=updateRoom;
        ViewRoom room=new ViewRoom(updateRoom);
                        for(User user:room.getRoom().getUser_List()){
                                createUser(user);
                        }
        if(this.room.getStatus()==1 ){
              
                try {
                        URL url = new File("src/view/GameScene.fxml").toURI().toURL();
                        this.viewWaitRoom=new Scene(FXMLLoader.load(url), 710, 710);
                        mainStage.setScene(viewWaitRoom);
                } catch (IOException e) {
                        
                        e.printStackTrace();
                }
                this.room.setCheckforload(0);
                this.room.setStatus(2);
                
        }
  
   }
   public boolean Inlist(User user){
 for(User user2:this.room.getUser_List()){
      if(user2.getName().equals(user.getName())){
        return true;
      }
}
return false;
   }
   
   
   public Room getRoom() {
        return room;
}
public void setRoom(Room room) {
        this.room = room;
}
public void StartGame(){

   }
     
public Button getJoinbtn() {
        return joinbtn;
}
public void setJoinbtn(Button joinbtn) {
        this.joinbtn = joinbtn;
}
public Scene getViewWaitRoom() {
        return viewWaitRoom;
}
public void setViewWaitRoom(Scene viewWaitRoom) {
        this.viewWaitRoom = viewWaitRoom;
}

}
