package object;

import java.io.IOException;
import javax.imageio.ImageIO;

public class Banana extends SuperObject {

	public Banana(){
		
		name = "banana";
		try {
			
			image = ImageIO.read(getClass().getResourceAsStream("/objects/banana.png"));
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
