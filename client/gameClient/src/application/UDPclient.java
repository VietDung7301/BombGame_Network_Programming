package application;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
  
public class UDPclient
{
    public static void main(String args[]) throws IOException
    {
        Scanner sc = new Scanner(System.in);
  
        // Step 1:Create the socket object for
        // carrying the data.
        DatagramSocket ds = new DatagramSocket();
  
        InetAddress ip = InetAddress.getLocalHost();
        byte buf[] = null;
  
        // loop while user not enters "bye"
        while (true)
        {
            String inp = sc.nextLine();
  
            // convert the String input into the byte array.
            buf = inp.getBytes();
  
            // Step 2 : Create the datagramPacket for sending
            // the data.
            DatagramPacket DpSend =
                  new DatagramPacket(buf, buf.length, ip, 5500);
  
            // Step 3 : invoke the send call to actually send
            // the data.
            ds.send(DpSend);
            byte[] buffer = new byte[1024];
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            ds.receive(response);
            String quote = new String(buffer, 0, response.getLength());
            System.out.println("Server sent :"+ quote);
            // break the loop if user enters "bye"
            if (inp.equals("bye"))
                break;
        }
        ds.close();
        sc.close();
    }
}