package tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import main.GamePanel;

public class TileManager {
	
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][][];
	File folder = new File("C:\\Programming\\JAVA University Projects\\MyDungeon\\res\\maps");
	public int mapCount = folder.listFiles().length;
	int offset = 50;
	public int currentCol = 0;
	public int currentRow = 0;
	
    
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		
		tile = new Tile[100];
		mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		for(int i = 0; i < mapCount; ++i)
			loadMap("/maps/MAP" + (i + 1) + ".txt", i);
	}
	
	public void getTileImage() {
		
		try {
			setup(0, "black", true);
			setup(1, "wall_center", false);
			setup(2, "floor_plain", false);
			setup(3, "floor_void", false);
			
			
			// OTHER WALLS
			setup(offset + 3, "wall_bottom", false);
			setup(offset + 4, "wall_right", false);
			setup(offset + 5, "wall_left", false);
			setup(offset + 6, "wall_top_right", false);
			setup(offset + 7, "wall_top_left", false);
			setup(offset + 8, "wall_bottom_right", false);
			setup(offset + 9, "wall_bottom_left", false);
			setup(offset + 10, "wall_top_right_in", false);
			setup(offset + 11, "wall_top_left_in", false);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setup(int tileNumber, String name, boolean collisionState) throws IOException {
		tile[tileNumber] = new Tile();
		tile[tileNumber].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + name + ".png"));
		tile[tileNumber].collision = collisionState;
	}
	
	public void loadMap(String filePath, int map) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			int col = 0;
			int row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {

				String line = br.readLine();
				
				while(col < gp.maxWorldCol) {
					
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]); // string to integer
					mapTileNum[map][col][row] = num;
					++col;
				}
				if(col == gp.maxWorldCol) {
					col = 0;
					++row;
				}
			}
			br.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public int chooseWall(int col, int row) {
		
		if(mapTileNum[gp.currentMap][col][row] == 1) {
			
			if(mapTileNum[gp.currentMap][col + 1][row] == 1 && mapTileNum[gp.currentMap][col - 1][row] == 1 && mapTileNum[gp.currentMap][col][row + 1] == 0 && mapTileNum[gp.currentMap][col][row - 1] != 1)
				return offset + 3; // bottom
			if(mapTileNum[gp.currentMap][col + 1][row] == 1 && mapTileNum[gp.currentMap][col - 1][row] == 1 && mapTileNum[gp.currentMap][col][row + 1] != 1 && mapTileNum[gp.currentMap][col][row - 1] != 1)
				return 1; // top
			if(mapTileNum[gp.currentMap][col + 1][row] == 0 && mapTileNum[gp.currentMap][col][row + 1] == 1 && mapTileNum[gp.currentMap][col][row - 1] == 1)
				return offset + 4; // right
			if(mapTileNum[gp.currentMap][col - 1][row] == 0 && mapTileNum[gp.currentMap][col][row + 1] == 1 && mapTileNum[gp.currentMap][col][row - 1] == 1)
				return offset + 5; // left
			if(mapTileNum[gp.currentMap][col + 1][row] == 0 && mapTileNum[gp.currentMap][col - 1][row] == 1 && mapTileNum[gp.currentMap][col][row - 1] == 0)
				return offset + 6; // top right
			if(mapTileNum[gp.currentMap][col + 1][row] == 1 && mapTileNum[gp.currentMap][col - 1][row] == 0 && mapTileNum[gp.currentMap][col][row - 1] == 0)
				return offset + 7; // top left
			if(mapTileNum[gp.currentMap][col + 1][row] == 0 && mapTileNum[gp.currentMap][col - 1][row] == 1 && mapTileNum[gp.currentMap][col][row + 1] == 0)
				return offset + 8; // bottom right
			if(mapTileNum[gp.currentMap][col + 1][row] == 1 && mapTileNum[gp.currentMap][col - 1][row] == 0 && mapTileNum[gp.currentMap][col][row + 1] == 0)
				return offset + 9; // bottom left
			if(mapTileNum[gp.currentMap][col + 1][row] == 1 && mapTileNum[gp.currentMap][col - 1][row] == 2 && mapTileNum[gp.currentMap][col][row + 1] == 2 && mapTileNum[gp.currentMap][col][row - 1] == 1)
				return offset + 10; // top right in
			if(mapTileNum[gp.currentMap][col + 1][row] == 2 && mapTileNum[gp.currentMap][col - 1][row] == 1 && mapTileNum[gp.currentMap][col][row + 1] == 2 && mapTileNum[gp.currentMap][col][row - 1] == 1)
				return offset + 11; // top left in
			if(mapTileNum[gp.currentMap][col + 1][row] == 1 && mapTileNum[gp.currentMap][col - 1][row] == 2 && mapTileNum[gp.currentMap][col][row + 1] == 1 && mapTileNum[gp.currentMap][col][row - 1] == 2)
				return offset + 4; // bottom right in
			if(mapTileNum[gp.currentMap][col + 1][row] == 2 && mapTileNum[gp.currentMap][col - 1][row] == 1 && mapTileNum[gp.currentMap][col][row + 1] == 1 && mapTileNum[gp.currentMap][col][row - 1] == 2)
				return offset + 5; // bottom left in
		}
		else if(mapTileNum[gp.currentMap][col][row + 1] == 0)
			return offset; // floor to void
		return 2; // floor
	}
	
	public void draw(Graphics2D g2) {
				
		if(gp.gameState == gp.tiledMapState) {
			
			g2.setColor(new Color(46, 30, 43));
			g2.fillRect(gp.screenWidth - gp.tileSize * 8, gp.screenHeight / 3 - gp.tileSize, gp.tileSize * 6, gp.tileSize * 6);
			g2.drawImage(tile[gp.currentTileOption].image, gp.screenWidth - gp.tileSize * 7, gp.screenHeight / 3, gp.tileSize * 4, gp.tileSize * 4, null);
			
			int col = currentCol;
			int row = currentRow;
			int x = 28;
			int y = 28;
			
			while (col < currentCol + 20 && row < currentRow + 20) {

				int tileNum = mapTileNum[gp.currentMapOption][col][row];
				g2.drawImage(tile[tileNum].image, x, y, gp.tileSize / 2, gp.tileSize / 2, null);
				++col;
				x += gp.tileSize / 2;
				
				if(col == currentCol + 20) {
					col = currentCol;
					x = 28;
					++row;
					y += gp.tileSize / 2;
				}
				
			}
		}
		else {
			int worldCol = 0;
			int worldRow = 0;
	
			while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
				
				int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];
				
				int worldX = worldCol * gp.tileSize;
				int worldY = worldRow * gp.tileSize;
				int screenX = worldX - gp.player.worldX + gp.player.screenX;
				int screenY = worldY - gp.player.worldY + gp.player.screenY;
				
				// ONLY DRAW WHAT CAN BE SEEN
				if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
					worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
					worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
					worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
	
					if(tileNum == 1 || tileNum == 2)
						tileNum = chooseWall(worldCol, worldRow);

					g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
				}
				++worldCol;
				
				if(worldCol == gp.maxWorldCol) {
					worldCol = 0;
					++worldRow;
				}
			}
		}
	}
	
	public void printMap(int[][][] map, String mapPath) {
        try {
            PrintWriter writer = new PrintWriter(mapPath, "UTF-8");
            for (int i = 0; i < 50; ++i) {
                for (int j = 0; j < 50; ++j) {
                    writer.print(map[gp.currentMapOption][j][i] + " ");
                }
                writer.println();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Error writing matrix to file: " + e.getMessage());
        }
    }
	
	public String chooseFile() {

	    String directory = "C:\\Programming\\JAVA University Projects\\MyDungeon\\res\\maps";
	    JFileChooser fileChooser = new JFileChooser(directory);
	    fileChooser.setDialogTitle("Choose or create a map");

	    // Prompt the user to choose or create a file
	    int option = JOptionPane.showOptionDialog(null, "Do you want to choose an existing map or create a new one?",
	            "Map Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
	            new String[] { "Choose Existing", "Create New" }, "Choose Existing");

	    if (option == 0) {
	        // Choose an existing file
	        int result = fileChooser.showOpenDialog(null);

	        if (result == JFileChooser.APPROVE_OPTION) {
	            // Existing file selected
	            File selectedFile = fileChooser.getSelectedFile();
	            String fileName = selectedFile.getName();

	            if (!fileName.endsWith(".txt")) {
	                System.out.println("Error: selected file is not a text file.");
	                return null;
	            }

	            return fileName;
	        }
	    } else {
	        // Create a new file
	        String fileName = JOptionPane.showInputDialog("Enter the name of the new map file (without extension):");
	        if (fileName == null) {
	            // User cancelled file creation
	            return null;
	        }
	        fileName += ".txt";
	        File newFile = new File(directory, fileName);
	        try {
	            newFile.createNewFile();
	            mapCount = folder.listFiles().length;
	            return fileName;
	        } catch (IOException e) {
	            System.out.println("Error: failed to create new file.");
	            e.printStackTrace();
	            return null;
	        }
	    }

	    return null;
	}
}



