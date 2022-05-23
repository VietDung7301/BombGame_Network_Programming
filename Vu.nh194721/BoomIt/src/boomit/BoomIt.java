package boomit;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class BoomIt extends JFrame {
	GameScreen game;

	public BoomIt() {
		setSize(300,300);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		game = new GameScreen();
		add(game);
		this.addKeyListener(new handler());
		
		setVisible(true);
		
	}
	public static void main(String[] args) {
		BoomIt f = new BoomIt();
	}
	
	private class handler implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_UP) {
				game.Player1.setVector(Character.GO_UP);
				game.Player1.setDir(Character.GO_UP);
			}
			if(e.getKeyCode()==KeyEvent.VK_DOWN) {
				game.Player1.setVector(Character.GO_DOWN);
				game.Player1.setDir(Character.GO_DOWN);
			}
			if(e.getKeyCode()==KeyEvent.VK_LEFT) {
				game.Player1.setVector(Character.GO_LEFT);
				game.Player1.setDir(Character.GO_LEFT);
			}
			if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
				game.Player1.setVector(Character.GO_RIGHT);
				game.Player1.setDir(Character.GO_RIGHT);
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_UP) {
				game.Player1.setVector(Character.STAY);
			}
			if(e.getKeyCode()==KeyEvent.VK_DOWN) {
				game.Player1.setVector(Character.STAY);
			}
			if(e.getKeyCode()==KeyEvent.VK_LEFT) {
				game.Player1.setVector(Character.STAY);
			}
			if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
				game.Player1.setVector(Character.STAY);
			}
			
		}
		
	}
}
