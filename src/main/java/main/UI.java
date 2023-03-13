package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.MouseAdapter;

import object.Banana;
import object.Guard;
import object.Key;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	Font type;
	BufferedImage bananaImage, keyImage, guardImage;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean levelComplete = false;
	public int commandNum = 0;
	public int titleScreenState = 0;
	public int mapOption = 1;
	public int currentCol;
	public int currentRow;
	Point mousePosition;
	Point panelPosition;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		type = new Font("Arial", Font.BOLD, 25);
		Banana banana = new Banana();
		bananaImage = banana.image;
		Key key = new Key();
		keyImage = key.image;
		Guard guard = new Guard();
		guardImage = guard.image;
	}
	
	public void showMessage(String text) {
		
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2, boolean isFullScreen) throws IOException {
		
		this.g2 = g2;
		
		g2.setFont(type);
		g2.setColor(Color.white);
		
		// TITLE STATE
		if(gp.gameState == gp.titleState) {
			drawTitleScreen(isFullScreen);
		}
		
		// PAUSE STATE
		if(gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
		
		// TILED MAP STATE
		if(gp.gameState == gp.tiledMapState) {
			drawTiledMapEditor(isFullScreen);
		}
		
		// PLAY STATE
		if(gp.gameState == gp.playState) {
			
			if(levelComplete) {
				
				g2.setFont(g2.getFont().deriveFont(40F));
				String text = "Congratulations!";
				int x = getXforCenteredText(text);
				int y = gp.screenHeight / 2 - gp.tileSize * 4;
				g2.drawString(text, x, y);
				
				g2.setFont(g2.getFont().deriveFont(60F));
				text = "Level " + (gp.currentMap + 1) + " complete";
				x = getXforCenteredText(text);
				y = gp.screenHeight / 2 - gp.tileSize * 2;
				g2.drawString(text, x, y);

				g2.setFont(g2.getFont().deriveFont(30F));
				text = "TIME: ";
				x = gp.screenWidth - gp.tileSize * 4;
				y = gp.screenHeight / 2 - gp.tileSize * 5;
				g2.drawString(text, x, y);
				g2.drawString(Math.round(gp.gameTime) + "", gp.screenWidth - gp.tileSize * 2, gp.screenHeight / 2 - gp.tileSize * 5);

				
				++messageCounter;
				if(messageCounter > 120) {
					messageCounter = 0;
					gp.ObjectsCreated[gp.currentMap] = false;
					++gp.currentMap;
					if(gp.currentMap >= gp.tileM.mapCount)
						gp.currentMap = 0;
					gp.player.setDefaultValues();
					gp.aSetter.setObject();
					levelComplete = false;
				}
			}
			else {
				g2.setFont(g2.getFont().deriveFont(35F));
				String text = "Level " + (gp.currentMap + 1);
				int x = getXforCenteredText(text);
				int y = gp.screenHeight / 2 - gp.tileSize * 5;
				g2.drawString(text, x, y);
				
				g2.setFont(g2.getFont().deriveFont(30F));
				text = "TIME: ";
				x = gp.screenWidth - gp.tileSize * 4;
				y = gp.screenHeight / 2 - gp.tileSize * 5;
				g2.drawString(text, x, y);
				g2.drawString(Math.round(gp.gameTime) + "", gp.screenWidth - gp.tileSize * 2, gp.screenHeight / 2 - gp.tileSize * 5);

				
				
				g2.drawImage(bananaImage, 15, 10, gp.tileSize, gp.tileSize, null);
				g2.drawString("   x  " + gp.inventory.bananaCount, 50, 50);
				g2.drawImage(keyImage, 15, 55, gp.tileSize, gp.tileSize, null);
				g2.drawString("   x  " + gp.inventory.keyCount, 50, 85);
				
				// MESSAGE
				if(messageOn) {
					g2.drawImage(guardImage, 15, gp.tileSize * 9 + 20, gp.tileSize * 3, gp.tileSize * 3, null);
					g2.setFont(g2.getFont().deriveFont(35F));
					g2.drawString(message, gp.tileSize * 4, gp.tileSize * 12 - 20);
					}
					++messageCounter;
					if(messageCounter > 120) {
						messageCounter = 0;
						messageOn = false;
				}
			}
		}
	}
	
	public void drawTitleScreen(boolean isFullScreen) throws IOException {
	
	gp.gameTime = 0;
		
	gp.inventory.bananaCount = 0;
	gp.inventory.keyCount = 0;
			
	g2.setColor(new Color(66, 40, 53));
	g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
	g2.setColor(Color.white);
		
		// TITLE NAME
		g2.setFont(g2.getFont().deriveFont(80F));
		String text = "Monkey Dungeon";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight / 5;
		// SHADOW
		g2.setColor(Color.black);
		g2.drawString(text, x + 5, y + 5);
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		// TITLE IMAGES
		BufferedImage guard = ImageIO.read(getClass().getResourceAsStream("/objects/guard.png"));
		BufferedImage banana = ImageIO.read(getClass().getResourceAsStream("/objects/banana.png"));
		g2.drawImage(guard, gp.screenWidth / 2 + gp.tileSize * 5, y + gp.tileSize * 5, gp.tileSize * 4, gp.tileSize * 4, null);
		g2.drawImage(gp.player.down[0], gp.screenWidth / 2 - 50, y + gp.tileSize * 2 + 20, gp.tileSize * 7, gp.tileSize * 7, null);
		for(int j = 0; j < 30; j += 10)
			for(int i = 0; i < 17; i += 2) {
			g2.drawImage(banana, gp.tileSize * i + j * 5, gp.tileSize * 11 + j - 20, gp.tileSize + 20, gp.tileSize + 20, null);
			banana = flipHorizontal(banana);
			}
	
		// MENU
		g2.setFont(g2.getFont().deriveFont(35F));		
		Point mousePosition = MouseInfo.getPointerInfo().getLocation();
		Point panelPosition = gp.getLocationOnScreen();
		if(isFullScreen) {
			mousePosition.y = (int) Math.round(mousePosition.y * 0.54);
			panelPosition.y = (int) Math.round(panelPosition.y - gp.tileSize * 1.2);
		}
		
		// MAIN TITLE SCREEN 
		if(titleScreenState == 0) {
			
			if(mousePosition.y > y + panelPosition.y + gp.tileSize * 5.2)
				commandNum = 3;
			else if(mousePosition.y > y + panelPosition.y + gp.tileSize * 4.2)
				commandNum = 2;
			else if(mousePosition.y > y + panelPosition.y + gp.tileSize * 3.2)
				commandNum = 1;
			else
				commandNum = 0;
			
			text = "PLAY";
			x = gp.tileSize * 2;
			y += gp.tileSize * 3;
			g2.drawString(text, x, y);
			if(commandNum == 0) {
				g2.drawImage(banana, x - gp.tileSize - 5, y - gp.tileSize + 5, gp.tileSize, gp.tileSize, null);
			}
			
			text = "LOAD MAP";
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 1) {
				g2.drawImage(banana, x - gp.tileSize - 5, y - gp.tileSize + 5, gp.tileSize, gp.tileSize, null);
			}
			
			text = "CREATE MAP";
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 2) {
				g2.drawImage(banana, x - gp.tileSize - 5, y - gp.tileSize + 5, gp.tileSize, gp.tileSize, null);
			}
			
			text = "QUIT";
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 3) {
				g2.drawImage(banana, x - gp.tileSize - 5, y - gp.tileSize + 5, gp.tileSize, gp.tileSize, null);
			}
	
		
			gp.addMouseListener(new MouseAdapter() {
			    @Override
			    public void mouseClicked(MouseEvent e) {
			        // MOUSE CLICKED
			        if(commandNum == 0) {
			        	gp.currentMap = 0;
			    		//gp.inventory.bananaCount = 0;
			    		//gp.inventory.keyCount = 0;
			        	gp.player.setDefaultValues();
			        	gp.gameState = gp.playState;
			        }
			        if(commandNum == 1)
			        	titleScreenState = 1;
			        if(commandNum == 2) {
			        	gp.gameState = gp.tiledMapState;
			        }
			       	if(commandNum == 3)
			       		System.exit(0);
			    }
			});
		}
		
		// MAP OPTION SCREEN
		else if(titleScreenState == 1) { 
			
			if(mousePosition.y > y + panelPosition.y + 250)
				commandNum = 7;
			else if(mousePosition.y > y + panelPosition.y + 200)
				commandNum = 6;
			else if(mousePosition.y > y + panelPosition.y + 155)
				commandNum = 5;
			else
				commandNum = 4;
			
			text = "MAP " + mapOption;
			x = gp.tileSize * 2;
			y += gp.tileSize * 3;
			g2.drawString(text, x, y);
			if(commandNum == 4) {
				g2.drawImage(banana, x - gp.tileSize - 5, y - gp.tileSize + 5, gp.tileSize, gp.tileSize, null);
			}
			
			text = "NEXT";
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 5) {
				g2.drawImage(banana, x - gp.tileSize - 5, y - gp.tileSize + 5, gp.tileSize, gp.tileSize, null);
			}
			
			text = "PREVIOUS";
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 6) {
				g2.drawImage(banana, x - gp.tileSize - 5, y - gp.tileSize + 5, gp.tileSize, gp.tileSize, null);
			}
			
			text = "BACK";
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 7) {
				g2.drawImage(banana, x - gp.tileSize - 5, y - gp.tileSize + 5, gp.tileSize, gp.tileSize, null);
			}
			gp.addMouseListener(new MouseAdapter() {
			    @Override
			    public void mouseClicked(MouseEvent e) {
			        if(commandNum == 4) { 
			        	gp.currentMap = (mapOption - 1);
			        	gp.player.setDefaultValues();
			        	gp.gameState = gp.playState;
			        }
			        if(commandNum == 5) {
			        	++mapOption;
			        	if(mapOption > gp.tileM.mapCount)
			        		mapOption = 1;
			        	gp.currentMap = (mapOption - 1);
				       	gp.aSetter.setObject();
				       	commandNum = 8; // for rendering one click
			        }
			        if(commandNum == 6) {
				       	--mapOption;
				       	if(mapOption < 1)
				       		mapOption = gp.tileM.mapCount;
				       	gp.currentMap = (mapOption - 1);
				       	gp.aSetter.setObject();
				       	commandNum = 8; // for rendering one click
			        }
			       	if(commandNum == 7)
			       		titleScreenState = 0;
			    }
			});
		}
	}
	
	public void drawTiledMapEditor(boolean isFullScreen) {
		
		g2.setColor(new Color(66, 40, 53));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		g2.setColor(Color.white);
		
		int rows = 20;
		int cols = 20;
		int yRow = 24;
		int xCol = 24;

		Point mouse = MouseInfo.getPointerInfo().getLocation();
		Point screen = gp.getLocationOnScreen();
		
		if(isFullScreen) {
			mouse.x = (int) Math.round(mouse.x * 0.63);
			mouse.y = (int) Math.round(mouse.y * 0.67);
		}
		
		int x = 29 + screen.x;
		int y = 29 + screen.y;
		if (mouse.x >= x && mouse.x <= x + xCol * cols && mouse.y >= y && mouse.y <= y + yRow * rows) {
			currentCol = (mouse.x - x) / xCol + 1 + gp.tileM.currentCol;
			currentRow = (mouse.y - y) / yRow + 1 + gp.tileM.currentRow;
		}
		else {
			currentCol = 0;
			currentRow = 0;
		}
		
		g2.drawString("Column: " + currentCol, 30, 550);
		g2.drawString("Row: " + currentRow, 250, 550);
		
		if(gp.mouseHandler.clicked && gp.ui.currentCol > 0 && gp.ui.currentRow > 0)
			gp.tileM.mapTileNum[gp.currentMapOption][gp.ui.currentCol - 1][gp.ui.currentRow - 1] = gp.currentTileOption;
	}
	
	public void drawPauseScreen() {
		
		g2.setColor(new Color(28, 17, 23));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		g2.setColor(Color.white);
		
		g2.setFont(g2.getFont().deriveFont(60F));
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight / 2;

		g2.drawString(text, x, y);
	}
	
	public int getXforCenteredText(String text) {
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		return gp.screenWidth / 2 - length / 2;
	}
	
	public static BufferedImage flipHorizontal(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage flipped = new BufferedImage(width, height, image.getType());
        Graphics2D g = flipped.createGraphics();
        AffineTransform flip = AffineTransform.getScaleInstance(-1, 1);
        flip.translate(-width, 0);
        g.transform(flip);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return flipped;
    }
	
}
