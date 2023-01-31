package application;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

import model.Room;
import netwoork.Connect;
import netwoork.ConnectLoadWaitRoom;
import netwoork.ConnectLoadingRoom;
public class TestConnect  {

    public static void main(String[] args) throws IOException {
        try {
			Connect test=new Connect();
			String[] result=test.SendAndRecvData("#c000#", 5500);
			//result=test.SendAndRecvData("#c004#&nhatsang$$", 5500);
			System.out.println(result[1]);
			for(String str:result) {
				System.out.println(str);
			}
			ConnectLoadingRoom join=new ConnectLoadingRoom(test);
			join.setListRoom();
			List<Room> listroom=join.getRoom();
			for(Room room:listroom){
				room.printRoom();
			}
			String[] result2=test.SendAndRecvData("#c005#&0$$", 5500);
			int i=0;
			for(String str:result2) {
				System.out.println("i "+str);
			}
			//ConnectLoadWaitRoom wait=new ConnectLoadWaitRoom(test);
			String[] result3=test.SendAndRecvData("#c007#&0$$", 5500);
			for(String str:result3) {
				System.out.println("i "+str);
			}

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    
   
}


