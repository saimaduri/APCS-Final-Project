import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import java.util.ArrayList;
import javafx.scene.paint.*;

public class Nuke extends Block implements PowerUp {

	private Color color = Color.MAGENTA;

	public Nuke(Canvas canvas){
		super(canvas);
	}

	public void action(ArrayList<Block> blocklist){
		blocklist.clear();
	}

	public void setColor(GraphicsContext gc) {
		gc.setFill(color);
	}
}