import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;

public class Platform {

	int width, height;
	Rectangle2D rectangle;

	public Platform(int width, int height){
		this.width = width;
		this.height = height-100;
		rectangle = new Rectangle2D(0, height, width, 100);
	}

	public void drawPlatform(GraphicsContext gc) {
		gc.setFill(Color.BLUE);
		gc.fillRect(0, height, width, 100);
	}

	public Rectangle2D getRectangle(){
		return rectangle;
	}

	public int getHeight() {
		return height;
	}

}