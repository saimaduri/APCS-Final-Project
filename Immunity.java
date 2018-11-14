import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import java.util.ArrayList;
import javafx.scene.paint.*;

public class Immunity extends Block implements PowerUp {

	private Color color = Color.GREEN;
	private int height, width, counter;

	public Immunity (Canvas canvas) {
		super(canvas);
		height = 10;
		width = 30;
		counter = 0;
	}

	public void setColor(GraphicsContext gc) {
		gc.setFill(color);
	}

}