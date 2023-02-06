package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class ServerConnector {
	private static final String SERVER_ADDRESS = "172.17.119.224";
	private static final int SERVER_PORT = 8000;
	
	private InetAddress ip;
	private int port;
	private DatagramSocket socket;
	
	private static ServerConnector CONNECTOR;
	
	public static ServerConnector getConn() {
		if (CONNECTOR == null) {
			CONNECTOR = new ServerConnector();
		}
		return CONNECTOR;
	}
	
	private ServerConnector(){
		try {
			this.socket = new DatagramSocket();
			this.ip = InetAddress.getByName(SERVER_ADDRESS);
			this.port = SERVER_PORT;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String sendData(String req) {
		byte[] buff = req.getBytes();
		byte[] recv = new byte[800];
		DatagramPacket dataSend = new DatagramPacket(buff, buff.length, ip, port);
		DatagramPacket dataRecv = new DatagramPacket(recv, recv.length);
		try {
			socket.send(dataSend);
			System.out.println("Data sent: " + req);
			socket.receive(dataRecv);
			String res = new String(recv, 0, dataRecv.getLength());
			System.out.println("Data recv: " + res);
			return res;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String[] SendAndRecvData(String data) throws IOException {
		DatagramPacket dataSend =new DatagramPacket(data.getBytes(),data.getBytes().length,ip,port);
		socket.send(dataSend);
		byte[] buffer = new byte[1024];
		DatagramPacket response=new DatagramPacket(buffer,buffer.length);
		socket.receive(response);
		String result1=new String(buffer, 0, response.getLength());
		// tach ra lay tung phan cua mang response
		String[] result2=result1.split("[#$&]");
		return result2;  
	  }
}
