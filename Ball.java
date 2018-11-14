import javafx.scene.shape.Circle;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.*;

public class Ball {

	private Circle circle;
	private Rectangle2D rectangle;
	private double x;
	private double y;
	private int r = 16;
	private double vx = 0;
	private double vy = 0;
	final double g = 13;
	final double t = 0.4;
	final double friction = 0.98;
	final double energyloss = .8;

	public Ball(Canvas canvas, Platform p) {
		x = canvas.getWidth()/2;
		y = p.getHeight()-r*2;
		circle = new Circle(x, y, r);
		rectangle = new Rectangle2D(x, y, r*2, r*2);
	}

	public Circle getCircle() {
		return new Circle(x, y, r*2);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getR() {
		return r;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void resetVX() {
		vx = 0;
	}

	public void resetVY() {
		vy = 0;
	}

	public void moveLeft(){
		if (Math.abs(vx) < 10)
			vx--;
	}

	public void moveRight(){
		if (Math.abs(vx) < 10)
			vx++;
	}

	public void jump(Platform p){
		if (y == p.getHeight())
			vy = 80;
	}

	public void perFrame(Canvas canvas, Platform p) {
		if (x + vx > (int)canvas.getWidth() - r){
			x = (int)canvas.getWidth()-r;
			vx = -vx;
		}
		else if (x + vx < r){
			x = r;
			vx = -vx;
		}
		else {
				x+=vx;
		}

		if (y <= p.getHeight()){
			vx*=friction;
		}

		if (y > p.getHeight()){
			y = p.getHeight();
			vy *= energyloss;
			vy =- vy;
		}
		else {
			vy+=g*t;
			y+=vy*t + (1/2)*(g*t*t);
		}
	}

	public void drawBall(GraphicsContext gc, Platform p) {
		gc.setFill(Color.RED);
		gc.fillOval(x-r, y-r*2, r*2, r*2);
	}

	public Rectangle2D getRectangle() {
		return new Rectangle2D(x, y, r, r);
	}

}