package character;

import main.KeyHandler;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;


public class Player extends Character {

	GamePanel gp;
	KeyHandler keyH;
	public final int screenX;
	public final int screenY;

	public Player(GamePanel gp, KeyHandler keyH) {
		
		this.gp = gp;
		this.keyH = keyH;
		
		// Find the screen center
		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
		
		//solidArea = new Rectangle(10, 32, 28, 16); // 8 32 16 32
		solidArea = new Rectangle(0, 0, 48, 90); // 8 32 16 32
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
				
		setDefaultValues();
		initializeImages();
		getPlayerImage();
	}
	
	
	public void setDefaultValues() {
		
		// DEFAULT STATS
		worldX = gp.tileSize * 24;
		worldY = gp.tileSize * 24;
		speed = 3;
		direction = "down";
	}
	
	public void initializeImages() {
		
	    up = new BufferedImage[4];
	    up_right = new BufferedImage[4];
	    right = new BufferedImage[4];
	    down_right = new BufferedImage[4];
	    down = new BufferedImage[4];
	    down_left = new BufferedImage[4];
	    left = new BufferedImage[4];
	    up_left = new BufferedImage[4];
	}
	
	public void getPlayerImage() {
		
	    try {
	    	for (int i = 0; i < 4; ++i)
	            up[i] = removeBackground(ImageIO.read(new File("res/player/up (" + (i + 1) + ").png")));
	    	for (int i = 0; i < 4; ++i)
	            up_right[i] = removeBackground(ImageIO.read(new File("res/player/up_right (" + (i + 1) + ").png")));
	    	for (int i = 0; i < 4; ++i)
	            up_left[i] = removeBackground(ImageIO.read(new File("res/player/up_left (" + (i + 1) + ").png")));
	    	for (int i = 0; i < 4; ++i)
	            down[i] = removeBackground(ImageIO.read(new File("res/player/down (" + (i + 1) + ").png")));
	    	for (int i = 0; i < 4; ++i)
	            down_right[i] = removeBackground(ImageIO.read(new File("res/player/down_right (" + (i + 1) + ").png")));
	        for (int i = 0; i < 4; ++i)
	            down_left[i] = removeBackground(ImageIO.read(new File("res/player/down_left (" + (i + 1) + ").png")));
	        for (int i = 0; i < 4; ++i)
	            left[i] = removeBackground(ImageIO.read(new File("res/player/left (" + (i + 1) + ").png")));
	        for (int i = 0; i < 4; ++i)
	        	right[i] = removeBackground(ImageIO.read(new File("res/player/right (" + (i + 1) + ").png")));
	    }
	    catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	private BufferedImage removeBackground(BufferedImage image) {
	    Color green = new Color(48, 104, 80);
	    BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
	    for (int x = 0; x < image.getWidth(); x++) {
	        for (int y = 0; y < image.getHeight(); y++) {
	            int pixel = image.getRGB(x, y);
	            Color c = new Color(pixel);
	            if (!c.equals(green)) {
	                newImage.setRGB(x, y, pixel);
	            }
	        }
	    }
	    return newImage;
	}
	
	public void update() {

		if(keyH.upPressed && keyH.leftPressed)
			direction = "up_left";
		
		else if(keyH.upPressed && keyH.rightPressed)
			direction = "up_right";
		
		else if(keyH.downPressed && keyH.leftPressed)
			direction = "down_left";

		else if(keyH.downPressed && keyH.rightPressed)
			direction = "down_right";
			
		else if(keyH.upPressed)
			direction = "up";
		
		else if(keyH.downPressed) 
			direction = "down";
	
		else if(keyH.leftPressed)
			direction = "left";
		
		else if(keyH.rightPressed) 
			direction = "right";

		
		
		// CHECK COLLISION
		collisionOn = false;
		gp.cheker.checkTile(this);
		
		int objIndex = gp.cheker.checkObject(this, true);
		pickUpObject(objIndex);
		
		// IF COLLISION IS FALSE, PLAYER CAN'T MOVE
		if(collisionOn == false && (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed)) {
			switch(direction) {
			case "up_left":
				worldY -= speed / 1.5;
				worldX -= speed / 1.5;
				break;
			case "up_right":
				worldY -= speed / 1.5;
				worldX += speed / 1.5;
				break;
			case "down_left":
				worldY += speed / 1.5;
				worldX -= speed / 1.5;
				break;
			case "down_right":
				worldY += speed / 1.5;
				worldX += speed / 1.5;
				break;
			case "up":
				worldY -= speed;
				break;
			case "down":
				worldY += speed;
				break;
			case "left":
				worldX -= speed;
				break;
			case "right":
				worldX += speed;
				break;
			}
		}
			
		
		++spriteCounter;
		if(spriteCounter > 8) {
			++spriteNumber;
			spriteNumber %= 4;
			spriteCounter = 0;
		}
	}
	
	public void pickUpObject(int i) {
		
		if(i != 999 && keyH.spacePressed) { // if object and player collided
			int bananaNeed = gp.currentMap + 2;
			String objectName = gp.obj[gp.currentMap][i].name;
			switch(objectName) {
			case "banana":
				++gp.inventory.bananaCount;
				gp.obj[gp.currentMap][i] = null;
				gp.ui.showMessage("You got a banana!");
				break;
			case "guard":
				if(bananaNeed > gp.inventory.bananaCount) {
					gp.ui.showMessage("Find me " + bananaNeed + " bananas for a key");
				}
				else {
					gp.ui.showMessage("Good job, now take the key");
					gp.inventory.bananaCount -= bananaNeed;
					++gp.inventory.keyCount;
					gp.obj[gp.currentMap][i] = null;
				}
				break;
			case "door_closed":
				if(gp.inventory.keyCount != 0) {
					--gp.inventory.keyCount;
					gp.obj[gp.currentMap][i] = null;
				}
				else
					gp.ui.showMessage("You need a key!");
				break;
			case "door_opened":
				gp.ui.levelComplete = true;
				break;
			case "ladder":
				//NEXT LEVEL?
				break;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		if(!keyH.upPressed && !keyH.downPressed && !keyH.leftPressed && !keyH.rightPressed)
			spriteNumber = 0;
		
		switch(direction) {
		case "up_left":
			image = up_left[spriteNumber];
			break;
		case "up_right":
			image = up_right[spriteNumber];
			break;
		case "down_left":
			image = down_left[spriteNumber];
			break;
		case "down_right":
			image = down_right[spriteNumber];
			break;
		case "up":
			image = up[spriteNumber];
			break;
		case "down":
			image = down[spriteNumber];
			break;
		case "left":
			image = left[spriteNumber];
			break;
		case "right":
			image = right[spriteNumber];
			break;
		}
		
		// WEIRD PLAYER COLLISION BOX
		//g2.setColor(Color.RED);
	    //g2.fillRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
	    
	    
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	}
}




