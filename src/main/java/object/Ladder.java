package object;

import java.io.IOException;
import javax.imageio.ImageIO;

public class Ladder extends SuperObject {

	public Ladder() {
		
		name = "ladder";
		try {
			
			image = ImageIO.read(getClass().getResourceAsStream("/objects/floor_ladder.png"));
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
