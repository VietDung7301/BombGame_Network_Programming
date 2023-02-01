package controller;

import java.util.List;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class StartRoom {
     private List<ViewRoom> listroom;
     private String owner;
     public StartRoom(List<ViewRoom> listroom,String owner){
          this.listroom=listroom;
          this.owner=owner;
     }
     public void start(){
          for(ViewRoom viewRoom:listroom){
              System.out.println("Room id: "+viewRoom.getRoom().getId()+".Number of user: "+viewRoom.getRoom().getUser_List().size());
          }
     }
     
}
