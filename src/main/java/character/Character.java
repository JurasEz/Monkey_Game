package character;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Character {

	public int worldX, worldY;
	public int speed;
	
	public BufferedImage[] up, up_right, right, down_right, down, down_left, left, up_left;
	public String direction;
	
	public int spriteCounter = 0;
	public int spriteNumber = 0;
	public Rectangle solidArea;
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
}
