package main;

import character.Character;

public class CollisionChecker {
	
	GamePanel gp;

	public CollisionChecker(GamePanel gp) {
		
		this.gp = gp;
	}
	
	public void checkTile(Character character) {
		
		int characterLeftWorldX = character.worldX + character.solidArea.x;
		int characterRightWorldX = character.worldX + character.solidArea.x + character.solidArea.width;
		int characterTopWorldY = character.worldY + character.solidArea.y;
		int characterBottomWorldY = character.worldY + character.solidArea.y + character.solidArea.height;
		
		int characterLeftCol = characterLeftWorldX / gp.tileSize;
		int characterRightCol = characterRightWorldX / gp.tileSize;
		int characterTopRow = characterTopWorldY / gp.tileSize;
		int characterBottomRow = characterBottomWorldY / gp.tileSize;
		
		int tileNum1, tileNum2;
		
		
		switch(character.direction) {
		case "up_left":
			characterTopRow = (characterTopWorldY - character.speed * 3) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][characterLeftCol][characterTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][characterRightCol][characterTopRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
				character.collisionOn = true;
			characterLeftCol = (characterLeftWorldX - character.speed * 3) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][characterLeftCol][characterTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][characterLeftCol][characterBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
				character.collisionOn = true;
			break;
		case "up_right":
			characterTopRow = (characterTopWorldY - character.speed * 3) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][characterLeftCol][characterTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][characterRightCol][characterTopRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
				character.collisionOn = true;
			characterRightCol = (characterRightWorldX + character.speed * 3) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][characterRightCol][characterTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][characterRightCol][characterBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
				character.collisionOn = true;
			break;
		case "down_left":
			characterBottomRow = (characterBottomWorldY + character.speed * 3) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][characterLeftCol][characterBottomRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][characterRightCol][characterBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
				character.collisionOn = true;
			characterLeftCol = (characterLeftWorldX - character.speed * 3) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][characterLeftCol][characterTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][characterLeftCol][characterBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
				character.collisionOn = true;
			break;
		case "down_right":
			characterBottomRow = (characterBottomWorldY + character.speed * 3) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][characterLeftCol][characterBottomRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][characterRightCol][characterBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
				character.collisionOn = true;
			characterRightCol = (characterRightWorldX + character.speed * 3) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][characterRightCol][characterTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][characterRightCol][characterBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
				character.collisionOn = true;
			break;
		case "up":
			characterTopRow = (characterTopWorldY - character.speed * 3) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][characterLeftCol][characterTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][characterRightCol][characterTopRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
				character.collisionOn = true;
			break;
		case "down":
			characterBottomRow = (characterBottomWorldY + character.speed * 3) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][characterLeftCol][characterBottomRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][characterRightCol][characterBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
				character.collisionOn = true;
			break;
		case "left":
			characterLeftCol = (characterLeftWorldX - character.speed * 3) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][characterLeftCol][characterTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][characterLeftCol][characterBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
				character.collisionOn = true;
			break;
		case "right":
			characterRightCol = (characterRightWorldX + character.speed * 3) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][characterRightCol][characterTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][characterRightCol][characterBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
				character.collisionOn = true;
			break;
		}
	}
	
	public int checkObject(Character character, Boolean player) {
		
		int index = 999;
		
		for(int i = 0; i < gp.obj[1].length; ++i) {
			
			if(gp.obj[gp.currentMap][i] != null) {
				
				// Get solid area position
				character.solidArea.x += character.worldX;
				character.solidArea.y += character.worldY;
				
				// Get object solid area position
				gp.obj[gp.currentMap][i].solidArea.x += gp.obj[gp.currentMap][i].worldX;
				gp.obj[gp.currentMap][i].solidArea.y += gp.obj[gp.currentMap][i].worldY;
				
				switch(character.direction) {
				case "up_left":
					character.solidArea.y -= character.speed / 1.5;
					character.solidArea.x -= character.speed / 1.5;
					if(character.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {
						if(gp.obj[gp.currentMap][i].collision) {
							character.collisionOn = true;
						}
						if(player) {
							index = i;
						}
					}
					break;
				case "up_right":
					character.solidArea.y -= character.speed / 1.5;
					character.solidArea.x += character.speed / 1.5;
					if(character.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {
						if(gp.obj[gp.currentMap][i].collision) {
							character.collisionOn = true;
						}
						if(player) {
							index = i;
						}
					}
					break;
				case "down_left":
					character.solidArea.y += character.speed / 1.5;
					character.solidArea.x -= character.speed / 1.5;
					if(character.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {
						if(gp.obj[gp.currentMap][i].collision) {
							character.collisionOn = true;
						}
						if(player) {
							index = i;
						}
					}
					break;
				case "down_right":
					character.solidArea.y += character.speed / 1.5;
					character.solidArea.x += character.speed / 1.5;
					if(character.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {
						if(gp.obj[gp.currentMap][i].collision) {
							character.collisionOn = true;
						}
						if(player) {
							index = i;
						}
					}
					break;
				case "up":
					character.solidArea.y -= character.speed;
					if(character.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {
						if(gp.obj[gp.currentMap][i].collision) {
							character.collisionOn = true;
						}
						if(player) {
							index = i;
						}
					}
					break;
				case "down":
					character.solidArea.y += character.speed;
					if(character.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {
						if(gp.obj[gp.currentMap][i].collision) {
							character.collisionOn = true;
						}
						if(player) {
							index = i;
						}
					}
					break;
				case "left":
					character.solidArea.x -= character.speed;
					if(character.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {
						if(gp.obj[gp.currentMap][i].collision) {
							character.collisionOn = true;
						}
						if(player) {
							index = i;
						}
					}
					break;
				case "right":
					character.solidArea.x += character.speed;
					if(character.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {
						if(gp.obj[gp.currentMap][i].collision) {
							character.collisionOn = true;
						}
						if(player) {
							index = i;
						}
					}
					break;
				}
				character.solidArea.x = character.solidAreaDefaultX;
				character.solidArea.y = character.solidAreaDefaultY;
				gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].solidAreaDefaultX;
				gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].solidAreaDefaultY;
			}
		}
		
		return index;
	}
}
