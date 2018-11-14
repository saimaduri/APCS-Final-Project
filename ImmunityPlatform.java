import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import java.util.ArrayList;
import javafx.scene.paint.*;

public class ImmunityPlatform {

	private int x, y, height, width;
	private Color color = Color.GREEN;

	public ImmunityPlatform(Canvas canvas) {
		height = 10;
		width = 60;
	}

	public void drawImmunityPlatform(GraphicsContext gc, double x, double y) {
		y-=50;
		x-=20;
		gc.fillRect(x-width/6, y, width, height);
	}

	public Rectangle2D getRectangle(double x, double y) {
		y-=50;
		x-=20;
		return new Rectangle2D(x-width/6, y, width, height);
	}

	public void setColor(GraphicsContext gc) {
		gc.setFill(color);
	}
}