import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import java.util.ArrayList;
import javafx.scene.paint.*;

public class Block {

	private double x, y;
	private static double vy = 2;
	private static double r = 30;
	Rectangle2D rectangle;
	private Color color = Color.BLUE;

	public Block(Canvas canvas){
		x = (Math.random()*canvas.getWidth()-r)+r;
		y = -5;
		rectangle = new Rectangle2D(x, y, r, r);
	}

	public void drawBlock(GraphicsContext gc) {
		gc.fillRect(x, y, r, r);
	}

	public void updateBlock() {
		y+=vy;
	}

	public void updateR() {
		r+=2;
	}

	public void reset() {
		r = 30;
		vy = 2;
	}

	public void updateVY() {
		vy++;
	}

	public Rectangle2D getRectangle() {
		return new Rectangle2D(x, y, r, r);
	}

	public void setColor(GraphicsContext gc) {
		gc.setFill(color);
	}

	public double getY() {
		return y;
	}

	public double getX() {
		return x;
	}

	public double getR() {
		return r;
	}

	public void action(ArrayList<Block> blocklist){}

	public int action(){
		return 0;
	}

}