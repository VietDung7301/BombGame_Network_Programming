package boomit;
import java.awt.Color;
import java.awt.Graphics;


import javax.imageio.ImageIO;
import javax.swing.*;

public class GameScreen extends JPanel implements Runnable{
	int[][] bg = new int [30][30];
	
	Character Player1;
	
	Thread thread;
	public GameScreen() {
		Player1 = new Character();
		Data.loadImage();
		
		thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		while(true) {
			
			Player1.update();
			repaint();
			try {
				Thread.sleep(150);
			} catch (InterruptedException ex) {}
		}
	}
	
	public void paintBg(Graphics g) {
		g.setColor(Color.gray);
		for(int i=0;i<30;i++)
			for(int j=0;j<30;j++) {
				g.fillRect(i*10, j*10, 10, 10);
			}
	}
	
	public void paint(Graphics g) {
		paintBg(g);
		Player1.drawChar(g);
		
	}
}
