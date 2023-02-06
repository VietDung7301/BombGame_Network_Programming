package network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import model.Room;
import model.User;

public class ConnectLoadingRoom {
    private List<Room> room;
    private ServerConnector connect;
    public ConnectLoadingRoom(ServerConnector connect){
        this.connect=connect;
        this.room= new ArrayList<Room>();
    }
    public void setListRoom() throws IOException{
        
        String[] result=connect.SendAndRecvData("#c003#");
        int length=result.length;
        int loop=(length-4)/4;
        int index=0;
        for(int i=0;i<loop;i++){
            
            Room newRoom=new Room();
            newRoom.setName(result[index+4]);
            newRoom.setId(result[index+5]);
            User owner=new User(result[index+7].split("User")[1],result[index+7]);
            owner.setIdScene(1);
            newRoom.setOwner(owner.getId());
            newRoom.getUser_List().add(owner);
            
            ConnectLoadWaitRoom loadWaitRoom=new ConnectLoadWaitRoom(connect);
            loadWaitRoom.setRoom(result[index+5]);
            Room newRoom2 =loadWaitRoom.getRoom();
          /* for(int j=0;j<Integer.parseInt(result[6])-1;j++){
                User newUser=new User("#check","name");
                newUser.setIdScene(j+2);
                newRoom2.getUser_List().add(newUser); 
            }*/
            this.room.add(newRoom2);

            index+=4;
        }
    }
    public List<Room> getRoom() {
        return room;
    }
    public void setRoom(List<Room> room) {
        this.room = room;
    }
    
}