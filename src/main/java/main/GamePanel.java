package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JPanel;
import character.Inventory;
import character.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;
	
	// PICK SCREEN SETTING
	boolean drawFullScreen = true;
	
	// SCREEN SETTINGS
	final int originalTileSize = 16; //16x16 - standard character size
	final int scale = 3; //16*3=48 to scale the characters
	
	public final int tileSize = originalTileSize * scale; // 48*48 tile
	public final int maxScreenCol = 20;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 48 * 20 = 960 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 48 * 12 = 576 pixels

	// WORLD SETTING
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int WorldWidth = tileSize * maxWorldCol;
	public final int WorldHeight = tileSize * maxWorldRow;
	public final int maxMap = 10;
	public int currentMap = 0;
	
	// FOR FULL SCREEN
	public boolean isFullScreen = false;
	int screenWidth2 = screenWidth;
	int screenHeight2 = screenHeight;
	BufferedImage tempScreen;
	Graphics2D g2;

	// FPS
	int FPS = 60;
	
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler(this); //For key controls
	MouseHandler mouseHandler = new MouseHandler(this); //For key controls
	Thread gameThread; //For in-game time management
	public CollisionChecker cheker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public boolean[] ObjectsCreated = new boolean[maxMap];
	public UI ui = new UI(this);
	
	// CHARACTER AND OBJECT
	public Player player = new Player(this, keyH);
	public SuperObject obj[][] = new SuperObject[maxMap][25];
	
	// INVENTORY
	public Inventory inventory = new Inventory();
	
	// GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int tiledMapState = 3;
	
	public int currentMapOption;
	public int currentTileOption = 1;
	
	public double gameTime = 0;
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		this.addMouseListener(mouseHandler);
	}
	
	public void setupGame() {
		
		for(int i = 0; i < maxMap; ++i) {
			ObjectsCreated[i] = false;
		}
		
		aSetter.setObject();
		gameState = titleState;
		
		tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D)tempScreen.getGraphics();
		
		if(drawFullScreen)
			setFullScreen();
	}
	
	public void setFullScreen() {
		
		// GET LOCAL SCREEN DEVICE
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		gd.setFullScreenWindow(Main.window);
		
		// GET SCREEN WIDTH AND HIGHT
		screenWidth2 = Main.window.getWidth();
		screenHeight2 = Main.window.getHeight();
		
		isFullScreen = true;
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		
		double drawInterval = 1000000000 / FPS; // 0.01666 seconds
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		while(gameThread != null) {
			
			update();
			drawToTempScreen(); // draw to buffered image
			drawToScreen();	// draw the buffered image to the screen
			gameTime += 0.01666;
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime /= 1000000; //convert to milliseconds
				
				if(remainingTime < 0)
					remainingTime = 0;

				Thread.sleep((long) remainingTime);
				nextDrawTime += drawInterval;
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void update() {
		
		if(gameState == playState) {
			
			player.update();
		}
		
		if(gameState == pauseState) {
			
			// PAUSE
		}
	}
	
	public void drawToTempScreen() {
		
		// TITLE SCREEN
		if(gameState == titleState) {
			
			try {
				ui.draw(g2, isFullScreen);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		// TILED MAP EDITOR SCREEN
		else if(gameState == tiledMapState) {
			
			try {
				ui.draw(g2, isFullScreen);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			tileM.draw(g2);
		}
		// OTHERS
		else {
			
			// TILE
			tileM.draw(g2);
			
			// OBJECT
			for(int i = 0; i < obj[1].length; ++i) {
				if(obj[currentMap][i] != null) {
					obj[currentMap][i].draw(g2, this);
				}
			}
			
			// PLAYER
			player.draw(g2);
			
			// UI
			try {
				ui.draw(g2, isFullScreen);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void drawToScreen() {
		
		Graphics g = getGraphics();
		g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
		g.dispose();
	}
	
}



