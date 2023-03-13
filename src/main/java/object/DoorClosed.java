package object;

import java.io.IOException;
import javax.imageio.ImageIO;

public class DoorClosed extends SuperObject {

	public DoorClosed(){
		
		name = "door_closed";
		try {
			
			image = ImageIO.read(getClass().getResourceAsStream("/objects/door_closed.png"));
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
