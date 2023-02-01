package netwoork;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Room;
import model.User;

public class ConnectLoadWaitRoom {
    private Connect connet;
    private Room room;
    public ConnectLoadWaitRoom(Connect connect){
        this.connet=connect;
    }
    public void setRoom(String roomId){
             try {
                String[] response=connet.SendAndRecvData("#c007#&"+roomId+"$$", 5500);
                Room room=new Room();
                room.setId(roomId);
                room.setName(response[3]);
                int numberUser=Integer.parseInt(response[6]);
                String[] id=response[5].split("User");
                User owner=new User(id[1],response[5]);
                owner.setIdScene(1);
                List<User> listuser=new ArrayList<User>();
                listuser.add(owner);
                for(int i=0;i<numberUser-1;i++){
                    User player=new User("av",response[i+8]);
                    player.setIdScene(i+2);
                    listuser.add(player);
                }
                room.setOwner(owner.getId());

                room.setUser_List(listuser);
              //  System.out.println("Number of user: "+room.getUser_List().size());
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
