package object;

import java.io.IOException;
import javax.imageio.ImageIO;

public class DoorOpened extends SuperObject {

	public DoorOpened(){
		
		name = "door_opened";
		try {
			
			image = ImageIO.read(getClass().getResourceAsStream("/objects/door_open.png"));
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
