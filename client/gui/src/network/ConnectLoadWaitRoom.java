package network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Room;
import model.User;

public class ConnectLoadWaitRoom {
    private ServerConnector connet;
    private Room room;
    public ConnectLoadWaitRoom(ServerConnector connect){
        this.connet=connect;
    }
    public void setRoom(String roomId){
             try {
                String[] response=connet.SendAndRecvData("#c007#&"+roomId+"$$");
                Room room=new Room();
                room.setId(roomId);
            
                room.setName(response[3]);
                room.setStatus(Integer.parseInt(response[5]));
               room.setCheckforload(1);
                int numberUser=Integer.parseInt(response[7]);
                String[] ownerid=response[6].split("User");
                User owner=new User(ownerid[1],response[6]);
                owner.setIdScene(1);
                List<User> listuser=new ArrayList<User>();
                listuser.add(owner);
                for(int i=0;i<numberUser-1;i++){
                    User player=new User(response[i+9].split("User")[1],response[i+9]);
                    player.setIdScene(i+2);
                    listuser.add(player);
                }
                room.setOwner(owner.getId());
                room.setUser_List(listuser);
                this.room=room;
                

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
             

    }
    public Room getRoom() {
        return room;
    }
}