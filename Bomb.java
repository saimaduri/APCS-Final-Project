import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import java.util.ArrayList;
import javafx.scene.paint.*;

public class Bomb extends Block implements PowerUp {

	private Color color = Color.RED;
	int points = -50;

	public Bomb(Canvas canvas){
			super(canvas);
		}

	public int action(){
		return points;
	}

	public void setColor(GraphicsContext gc) {
			gc.setFill(color);
	}
}