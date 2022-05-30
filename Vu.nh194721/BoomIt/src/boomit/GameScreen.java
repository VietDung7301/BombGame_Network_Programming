package boomit;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;

public class GameScreen extends JPanel implements Runnable{
	public static final int block = 10;
	public static final int length = 59;
	int[][] bg = new int [length][length];
	
	Character Player1 = new Character(1,1,1);
	Character Player2 = new Character(length - 2,length - 4,2);
	Character Player3 = new Character(1,length - 4,3);
	Character Player4 = new Character(length - 2,1,4);
	
	Thread thread;
	public GameScreen() {
		Data.loadImage();

		thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		while(true) {
			
			Player1.update();
			Player2.update();
			Player3.update();
			Player4.update();
			repaint();
			try {
				Thread.sleep(120);
			} catch (InterruptedException ex) {}
		}
	}
	
	public void paintBg(Graphics g) {
		g.setColor(Color.gray);
		for(int i=0;i<length;i++)
			for(int j=0;j<length;j++) {
				g.fillRect(i*block, j*block, block, block);
			}
	}
	
	public void paint(Graphics g) {
		paintBg(g);
		Player1.drawChar(g);
		Player2.drawChar(g);
		Player3.drawChar(g);
		Player4.drawChar(g);
	}
}
