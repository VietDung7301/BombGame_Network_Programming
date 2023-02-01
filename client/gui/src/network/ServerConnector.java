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
	
	
	
	public ServerConnector(){
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
}
