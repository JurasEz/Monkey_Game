package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener{

	GamePanel gp;
	public boolean clicked = false;
	
	public MouseHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(gp.gameState == gp.tiledMapState && gp.ui.currentCol > 0 && gp.ui.currentRow > 0)
			clicked = false;
//		if(clicked || gp.gameState == gp.tiledMapState && gp.ui.currentCol > 0 && gp.ui.currentRow > 0) {
//
//			gp.tileM.mapTileNum[gp.currentMapOption][gp.ui.currentCol - 1][gp.ui.currentRow - 1] = gp.currentTileOption;
//		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(gp.gameState == gp.tiledMapState && gp.ui.currentCol > 0 && gp.ui.currentRow > 0)
			clicked = true;
//		if(gp.gameState == gp.tiledMapState && gp.ui.currentCol > 0 && gp.ui.currentRow > 0) {
//
//			gp.tileM.mapTileNum[gp.currentMapOption][gp.ui.currentCol - 1][gp.ui.currentRow - 1] = gp.currentTileOption;
//		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(gp.gameState == gp.tiledMapState && gp.ui.currentCol > 0 && gp.ui.currentRow > 0)
			clicked = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
}
