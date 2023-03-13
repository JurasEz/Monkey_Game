package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	GamePanel gp;
	public boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed;
	String edit;

	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.titleState;
			gp.ui.titleScreenState = 0;
		}
		if(gp.gameState == gp.tiledMapState) {
			if(code == KeyEvent.VK_SPACE) {
				if(gp.currentTileOption < 2)
					++gp.currentTileOption;
				else
					gp.currentTileOption = 0;
			}
			if(code == KeyEvent.VK_DOWN && gp.tileM.currentRow < 30) {
				++gp.tileM.currentRow;
			}
			if(code == KeyEvent.VK_UP && gp.tileM.currentRow > 0) {
				--gp.tileM.currentRow;
			}
			if(code == KeyEvent.VK_RIGHT && gp.tileM.currentCol < 30) {
				++gp.tileM.currentCol;
			}
			if(code == KeyEvent.VK_LEFT && gp.tileM.currentCol > 0) {
				--gp.tileM.currentCol;
			}
			if(code == KeyEvent.VK_ENTER) {
				edit = "/map/" + gp.tileM.chooseFile();
				gp.currentMapOption = Integer.parseInt(edit.substring(8, 9)) - 1;
				gp.tileM.loadMap(edit, gp.currentMapOption);
			}
			if(code == KeyEvent.VK_S) {
				gp.tileM.printMap(gp.tileM.mapTileNum, "C:\\Programming\\JAVA University Projects\\MyDungeon\\res\\maps\\MAP" + (gp.currentMapOption + 1)+ ".txt");
			}
		}
		else {
			if(code == KeyEvent.VK_DOWN) {
				downPressed = true;
			}
			if(code == KeyEvent.VK_UP) {
				upPressed = true;
			}
			if(code == KeyEvent.VK_RIGHT) {
				rightPressed = true;
			}
			if(code == KeyEvent.VK_LEFT) {
				leftPressed = true;
			}
			if(code == KeyEvent.VK_SPACE) {
				spacePressed = true;
			}
			if(code == KeyEvent.VK_ENTER) {
				if(gp.gameState == gp.playState) {
					gp.gameState = gp.pauseState;
				}
				else if(gp.gameState == gp.pauseState) {
					gp.gameState = gp.playState;
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_DOWN) {
			downPressed = false;
		}
		if(code == KeyEvent.VK_UP) {
			upPressed = false;
		}
		if(code == KeyEvent.VK_RIGHT) {
			rightPressed = false;
		}
		if(code == KeyEvent.VK_LEFT) {
			leftPressed = false;
		}
		if(code == KeyEvent.VK_SPACE) {
			spacePressed = false;
		}
	}
	
}
