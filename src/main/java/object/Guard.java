package object;

import java.io.IOException;
import javax.imageio.ImageIO;

public class Guard extends SuperObject {

	public Guard() {
		
		name = "guard";
		try {
			
			image = ImageIO.read(getClass().getResourceAsStream("/objects/guard.png"));
		}
		catch(IOException e){
			e.printStackTrace();
		}
		//collision = true;
	}
}