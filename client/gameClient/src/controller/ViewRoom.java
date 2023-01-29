package controller;


import java.io.File;
import java.net.URL;

import javafx.fxml.FXMLLoader;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Room;
import model.User;

public class ViewRoom {
    private Room room;
    private Button joinbtn;
    private Scene viewWaitRoom;
    public ViewRoom(Room room){
             this.room=room;
            String textBtn="#"+room.getId()+" "+room.getName()+" "+"0/4";
            joinbtn=new Button(textBtn);
            joinbtn.setPrefWidth(363);
            joinbtn.setPrefHeight(69);
            joinbtn.setFont(Font.font("Bold",23));
            joinbtn.setStyle("-fx-background-color: orange;"); 
            joinbtn.setTextFill(Color.WHITE);
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
    public boolean isFull(){
        return this.room.getUser_List().size() == 4;
    }
    public void setViewWaitRoom(Stage mainStage,Scene previousScene){
        try {
                URL url = new File("src/view/Scene_6.fxml").toURI().toURL();
                this.viewWaitRoom=new Scene(FXMLLoader.load(url), 771, 551);
                ImageView btnback=(ImageView) this.viewWaitRoom.lookup("#back");
                btnback.setCursor(Cursor.HAND);				
                btnback.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
                        public void handle(MouseEvent event) {
                                
                                mainStage.setScene(previousScene);
                        }
                });
      } catch (Exception e) {
               e.printStackTrace();
        }
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
