package boomit;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Character {
	int x, y;
	long t = 0;
	int dir = 1;
	int phase = 1;
	
	public static int STAY = 0;
	public static int GO_UP = -1;
	public static int GO_DOWN = 1;
	public static int GO_LEFT = -2;
	public static int GO_RIGHT = 2;
	int vector;
	
	public Character() {
		x = 1;
		y = 1;
	}
	
	public void setVector(int v) {
		this.vector = v;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public void update() {
		if(System.currentTimeMillis() - t > 150) {
			if(vector == Character.GO_UP) y = y - 2;
			if(vector == Character.GO_DOWN) y = y + 2;
			if(vector == Character.GO_LEFT) x = x - 2;
			if(vector == Character.GO_RIGHT) x = x + 2;
			t = System.currentTimeMillis();
		}
	}
	
	public void drawChar(Graphics g) {
		g.drawImage(CharSprite_1(this.vector,this.dir, this.phase), (x-1)*10, (y-1)*10, 30, 44, null);
	
	}
	
	private Image CharSprite_1(int vector, int dir, int phase) {
		if(dir == Character.GO_UP) {
			if(vector == Character.STAY)
				return Data.imageChar_UP_1;
			else {
				if (phase == 1) {
					PhaseCounter();
					return Data.imageChar_UP_2;
				}
				else if(phase == 2 || phase == 4) {
					PhaseCounter();
					return Data.imageChar_UP_1;
				}
				else{
					PhaseCounter();
					return Data.imageChar_UP_3;
				}
			}
		}
		
		if(dir == Character.GO_DOWN) {
			if(vector == Character.STAY)
				return Data.imageChar_DOWN_1;
			else {
				if (phase == 1) {
					PhaseCounter();
					return Data.imageChar_DOWN_2;
				}
				else if(phase == 2 || phase == 4) {
					PhaseCounter();
					return Data.imageChar_DOWN_1;
				}
				else {
					PhaseCounter();
					return Data.imageChar_DOWN_3;
				}
			}
		}
		
		if(dir == Character.GO_LEFT) {
			if(vector == Character.STAY)
				return Data.imageChar_LEFT_1;
			else {
				if (phase == 1) {
					PhaseCounter();
					return Data.imageChar_LEFT_2;
				}
				else if(phase == 2 || phase == 4) {
					PhaseCounter();
					return Data.imageChar_LEFT_1;
				}
				else {
					PhaseCounter();
					return Data.imageChar_LEFT_3;
				}
				
			}
		}
		
		if(dir == Character.GO_RIGHT) {
			if(vector == Character.STAY)
				return Data.imageChar_RIGHT_1;
			else {
				if (phase == 1) {
					PhaseCounter();
					return Data.imageChar_RIGHT_2;
				}
				else if(phase == 2 || phase == 4) {
					PhaseCounter();
					return Data.imageChar_RIGHT_1;
				}
				else {
					PhaseCounter();
					return Data.imageChar_RIGHT_3;
				}
			}
		}
		return null;
	}
	
	private void PhaseCounter(){
		if(phase == 1 || phase == 2 || phase == 3) phase++;
		else if (phase == 4) phase = 1;
	}
}
