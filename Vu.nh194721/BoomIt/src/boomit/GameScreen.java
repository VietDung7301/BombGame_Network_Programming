package boomit;
import java.awt.Color;
import java.awt.Graphics;


import javax.swing.*;

public class GameScreen extends JPanel implements Runnable{
	public static final int block = 10;
	public static final int length = 60;
	public static int[][] bg = new int [length][length];
	
	Character Player1 = new Character(1,1,1);
	Character Player2 = new Character(length - 2,length - 2,2);
	Character Player3 = new Character(1,length - 2 ,3);
	Character Player4 = new Character(length - 2,1,4);
	
	public void isObstacle(int a, int b) {	//a,b: 0->19
		bg[3 * a + 1][3 * b + 1] = 1;
	}
	
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
		/*
		for(int i=0;i<length;i++)
			for(int j=0;j<length;j++) {
				if((i+j)%2==0)
					g.setColor(Color.cyan);
				else
					g.setColor(Color.yellow);
				g.fillRect(i*block, j*block, block, block);
			}
		*/
		
		for(int i=0;i<length;i++)
			for(int j=0;j<length;j++) {
				if(i % 3 == 0 && j % 3 ==0) {
					if((i+j)%2==0)
						g.setColor(Color.cyan);
					else
						g.setColor(Color.yellow);
					g.fillRect(i*block, j*block, block * 3, block *3);
				}
			}
		
		for(int i=1;i<length - 1;i += 3)
			for(int j=1;j<length - 1;j += 3) {
				if(bg[i][j] == 1){
					g.setColor(Color.black);
					g.fillRect((i-1)*block, (j -1)*block, block * 3, block *3);
				}
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
