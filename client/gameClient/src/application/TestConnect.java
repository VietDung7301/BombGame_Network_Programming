package application;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import netwoork.Connect;
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
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    
   
}


