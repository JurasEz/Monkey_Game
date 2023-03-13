package main;

import java.util.Random;

import object.Banana;
import object.DoorClosed;
import object.DoorOpened;
import object.Guard;

public class AssetSetter {

	GamePanel gp;

	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		
		int mapNum = gp.currentMap;
		if(gp.ObjectsCreated[mapNum] == false) {
			gp.ObjectsCreated[mapNum] = true;
			int i = 0;
			placeObjects(mapNum, i);
		}

//		gp.obj[4] = new Ladder();
//		gp.obj[4].worldX = 23 * gp.tileSize;
//		gp.obj[4].worldY = 23 * gp.tileSize;
	}
	
	public void placeObjects(int mapNum, int objectNum) {
		
		int x = 0;
		int y = 0;
		int bananasPlaced = 0;
		int guardsPlaced = 0;
		int doorsPlaced = 0;
		
		Random rand = new Random();
        int randomNumber;
		
		while(bananasPlaced < mapNum + 3 || guardsPlaced == 0 || doorsPlaced == 0) {
			if(gp.tileM.mapTileNum[mapNum][x][y] == 2) {
				randomNumber = rand.nextInt(100);
				if(randomNumber == 1 && gp.obj[mapNum][objectNum] == null) {
					gp.obj[mapNum][objectNum] = new Banana();
					gp.obj[mapNum][objectNum].worldX = x * gp.tileSize;
					gp.obj[mapNum][objectNum].worldY = y * gp.tileSize;
					++bananasPlaced;
					++objectNum;
				}
				else if(randomNumber == 2 && guardsPlaced == 0) {
					gp.obj[mapNum][objectNum] = new Guard();
					gp.obj[mapNum][objectNum].worldX = x * gp.tileSize;
					gp.obj[mapNum][objectNum].worldY = y * gp.tileSize;
					++objectNum;
					++guardsPlaced;
				}
				// Doors are at the top, because there is no random number involved
				else if(doorsPlaced == 0 && gp.tileM.mapTileNum[mapNum][x][y - 1] == 1 && gp.tileM.mapTileNum[mapNum][x][y - 2] == 0) {
					gp.obj[mapNum][objectNum] = new DoorOpened();
					gp.obj[mapNum][objectNum].worldX = x * gp.tileSize;
					gp.obj[mapNum][objectNum].worldY = (y - 1) * gp.tileSize;
					++objectNum;
					
					gp.obj[mapNum][objectNum] = new DoorClosed();
					gp.obj[mapNum][objectNum].worldX = x * gp.tileSize;
					gp.obj[mapNum][objectNum].worldY = (y - 1) * gp.tileSize;
					++objectNum;
					++doorsPlaced;
				}
			}
			++x;
			if(x >= 50) {
				x = 0;
				++y;
			}
			if(y >= 50) {
				y = 0;
			}
		}
	}
}
