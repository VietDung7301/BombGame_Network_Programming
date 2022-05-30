package boomit;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Data {
	private static int a,b;
	public static BufferedImage sprite;
	
	public static Image imageChar1_DOWN_1;
	public static Image imageChar1_DOWN_2;
	public static Image imageChar1_DOWN_3;
	public static Image imageChar1_UP_1;
	public static Image imageChar1_UP_2;
	public static Image imageChar1_UP_3;
	public static Image imageChar1_LEFT_1;
	public static Image imageChar1_LEFT_2;
	public static Image imageChar1_LEFT_3;
	public static Image imageChar1_RIGHT_1;
	public static Image imageChar1_RIGHT_2;
	public static Image imageChar1_RIGHT_3;
	
	public static Image imageChar2_DOWN_1;
	public static Image imageChar2_DOWN_2;
	public static Image imageChar2_DOWN_3;
	public static Image imageChar2_UP_1;
	public static Image imageChar2_UP_2;
	public static Image imageChar2_UP_3;
	public static Image imageChar2_LEFT_1;
	public static Image imageChar2_LEFT_2;
	public static Image imageChar2_LEFT_3;
	public static Image imageChar2_RIGHT_1;
	public static Image imageChar2_RIGHT_2;
	public static Image imageChar2_RIGHT_3;
	
	public static Image imageChar3_DOWN_1;
	public static Image imageChar3_DOWN_2;
	public static Image imageChar3_DOWN_3;
	public static Image imageChar3_UP_1;
	public static Image imageChar3_UP_2;
	public static Image imageChar3_UP_3;
	public static Image imageChar3_LEFT_1;
	public static Image imageChar3_LEFT_2;
	public static Image imageChar3_LEFT_3;
	public static Image imageChar3_RIGHT_1;
	public static Image imageChar3_RIGHT_2;
	public static Image imageChar3_RIGHT_3;
	
	public static Image imageChar4_DOWN_1;
	public static Image imageChar4_DOWN_2;
	public static Image imageChar4_DOWN_3;
	public static Image imageChar4_UP_1;
	public static Image imageChar4_UP_2;
	public static Image imageChar4_UP_3;
	public static Image imageChar4_LEFT_1;
	public static Image imageChar4_LEFT_2;
	public static Image imageChar4_LEFT_3;
	public static Image imageChar4_RIGHT_1;
	public static Image imageChar4_RIGHT_2;
	public static Image imageChar4_RIGHT_3;
	
	public static void loadImage() {
		try {
			sprite = ImageIO.read(new File("res/sprite.png"));
		} catch (IOException e) {
		}
			
		imageChar1_DOWN_1 = sprite.getSubimage(20, 13, 15, 22);
		imageChar1_DOWN_2 = sprite.getSubimage(3, 13, 15, 22);
		imageChar1_DOWN_3 = sprite.getSubimage(36, 13, 15, 22);	
		
		imageChar1_UP_1 = sprite.getSubimage(68, 13, 15, 22);
		imageChar1_UP_2 = sprite.getSubimage(52, 13, 15, 22);
		imageChar1_UP_3 = sprite.getSubimage(84, 13, 15, 22);
			
		imageChar1_LEFT_1 = sprite.getSubimage(19, 37, 16, 22);
		imageChar1_LEFT_2 = sprite.getSubimage(3, 37, 15, 22);
		imageChar1_LEFT_3 = sprite.getSubimage(35, 37, 15, 22);
		
		imageChar1_RIGHT_1 = sprite.getSubimage(67, 37, 16, 22);
		imageChar1_RIGHT_2 = sprite.getSubimage(52, 37, 15, 22);
		imageChar1_RIGHT_3 = sprite.getSubimage(84, 37, 15, 22);
		
		imageChar2_DOWN_1 = sprite.getSubimage(178, 13, 15, 22);
		imageChar2_DOWN_2 = sprite.getSubimage(161, 13, 15, 22);
		imageChar2_DOWN_3 = sprite.getSubimage(194, 13, 15, 22);
		
		imageChar2_UP_1 = sprite.getSubimage(226, 13, 15, 22);
		imageChar2_UP_2 = sprite.getSubimage(210, 13, 15, 22);
		imageChar2_UP_3 = sprite.getSubimage(242, 13, 15, 22);
		
		imageChar2_LEFT_1 = sprite.getSubimage(177, 37, 16, 22);
		imageChar2_LEFT_2 = sprite.getSubimage(161, 37, 15, 22);
		imageChar2_LEFT_3 = sprite.getSubimage(193, 37, 15, 22);
		
		imageChar2_RIGHT_1 = sprite.getSubimage(225, 37, 16, 22);
		imageChar2_RIGHT_2 = sprite.getSubimage(210, 37, 15, 22);
		imageChar2_RIGHT_3 = sprite.getSubimage(242, 37, 15, 22);
		
		
		imageChar3_DOWN_1 = sprite.getSubimage(20, 95 + 13, 15, 22);
		imageChar3_DOWN_2 = sprite.getSubimage(3, 95 + 13, 15, 22);
		imageChar3_DOWN_3 = sprite.getSubimage(36, 95 + 13, 15, 22);
		
		imageChar3_UP_1 = sprite.getSubimage(68, 95 + 13, 15, 22);
		imageChar3_UP_2 = sprite.getSubimage(52, 95 + 13, 15, 22);
		imageChar3_UP_3 = sprite.getSubimage(84, 95 + 13, 15, 22);
		
		imageChar3_LEFT_1 = sprite.getSubimage(19, 95 + 37, 16, 22);
		imageChar3_LEFT_2 = sprite.getSubimage(3, 95 + 37, 15, 22);
		imageChar3_LEFT_3 = sprite.getSubimage(35, 95 + 37, 15, 22);
		
		imageChar3_RIGHT_1 = sprite.getSubimage(67, 95 + 37, 16, 22);
		imageChar3_RIGHT_2 = sprite.getSubimage(52, 95 + 37, 15, 22);
		imageChar3_RIGHT_3 = sprite.getSubimage(84, 95 + 37, 15, 22);
		
			
		imageChar4_DOWN_1 = sprite.getSubimage(158 + 20, 95 + 13, 15, 22);
		imageChar4_DOWN_2 = sprite.getSubimage(158 + 3, 95 + 13, 15, 22);
		imageChar4_DOWN_3 = sprite.getSubimage(158 + 36, 95 + 13, 15, 22);
		
		imageChar4_UP_1 = sprite.getSubimage(158 + 68, 95 + 13, 15, 22);
		imageChar4_UP_2 = sprite.getSubimage(158 + 52, 95 + 13, 15, 22);
		imageChar4_UP_3 = sprite.getSubimage(158 + 84, 95 + 13, 15, 22);
		
		imageChar4_LEFT_1 = sprite.getSubimage(158 + 19, 95 + 37, 16, 22);
		imageChar4_LEFT_2 = sprite.getSubimage(158 + 3, 95 + 37, 15, 22);
		imageChar4_LEFT_3 = sprite.getSubimage(158 + 35, 95 + 37, 15, 22);
		
		imageChar4_RIGHT_1 = sprite.getSubimage(158 + 67, 95 + 37, 16, 22);
		imageChar4_RIGHT_2 = sprite.getSubimage(158 + 52, 95 + 37, 15, 22);
		imageChar4_RIGHT_3 = sprite.getSubimage(158 + 84, 95 + 37, 15, 22);
	} 
	
	
}
