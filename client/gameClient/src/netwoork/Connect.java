package netwoork;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Connect {
  private DatagramSocket ds;
  private DatagramPacket dataSend;
  //private DatagramPacket dataRecv;
  private InetAddress ip;
  public Connect() throws SocketException, UnknownHostException {
	  ds=new DatagramSocket();
	  //khoi tao IP ( van dang su dung localhost)
	  ip = InetAddress.getByName("127.0.0.1");
	  
  }
  public String[] SendAndRecvData(String data,int port) throws IOException {
	  dataSend=new DatagramPacket(data.getBytes(),data.getBytes().length,ip,port);
	  ds.send(dataSend);
	  byte[] buffer = new byte[1024];
	  DatagramPacket response=new DatagramPacket(buffer,buffer.length);
	  ds.receive(response);
	  String result1=new String(buffer, 0, response.getLength());
	  // tach ra lay tung phan cua mang response
	  String[] result2=result1.split("[#$&]");
	  return result2;
	  
  }
  public void Close(){
	ds.close();
  }
}
