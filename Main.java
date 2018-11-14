//Sai Maduri

import javafx.application.Application;
import javafx.stage.Stage;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.media.AudioClip;
import java.net.URL;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import java.util.ArrayList;
import javafx.scene.paint.*;

public class Main extends Application implements EventHandler<InputEvent>{

	int canvasx = 1200;
	int canvasy = 600;
	int score = 0;
	int blockgenerator = 80;
	boolean start = true;
	boolean game = false;
	boolean immunity = false;

	Scene scene;
	GraphicsContext gc;
	Canvas canvas;
	Ball ball;
	Block block;
	ArrayList<Block> blocklist;
	int addblockcounter;
	Platform platform;
	AnimateObjects animate;
	ImmunityPlatform iplatform;
	int counter = 0;
	int count = 0;

	URL powerupsoundurl = getClass().getResource("powerup.mp3");
	AudioClip powerup = new AudioClip(powerupsoundurl.toString());

	URL gameoversoundurl = getClass().getResource("gameover.mp3");
	AudioClip gameover = new AudioClip(gameoversoundurl.toString());

	URL resource = getClass().getResource("backgroundmusic.mp3");
	AudioClip clip = new AudioClip(resource.toString());

	URL damagesoundurl = getClass().getResource("damage.mp3");
	AudioClip damage = new AudioClip(damagesoundurl.toString());

	public static void main(String[] args) {
		launch();
	}

	public void start(Stage stage){
		stage.setTitle("Dodge the Blocks");
		Group root = new Group();
		canvas = new Canvas(canvasx, canvasy);
		root.getChildren().add(canvas);
		scene = new Scene(root);
		stage.setScene(scene);
		scene.addEventHandler(KeyEvent.KEY_PRESSED,this);
		platform = new Platform((int)canvas.getWidth(), (int)canvas.getHeight());
		ball = new Ball(canvas, platform);
		blocklist = new ArrayList<>();
		addblockcounter = 1;
		gc = canvas.getGraphicsContext2D();

		iplatform = new ImmunityPlatform(canvas);
		animate = new AnimateObjects();
		animate.start();

		//clip.play();

		stage.show();
	}

	public void handle(final InputEvent event){
		if (((KeyEvent)event).getCode() == KeyCode.LEFT)
			ball.moveLeft();
		else if (((KeyEvent)event).getCode() == KeyCode.RIGHT)
			ball.moveRight();
		else if (((KeyEvent)event).getCode() == KeyCode.UP)
			ball.jump(platform);
		if (((KeyEvent)event).getCode() == KeyCode.SPACE){
			if (!(game) || start){
				score = 0;
				blockgenerator = 80;
				if (blocklist.size() > 0)
					blocklist.get(0).reset();
				blocklist.clear();
				addblockcounter = 1;
				start = false;
				ball.resetVX();
				ball.resetVY();
				ball.setX((int)(canvas.getWidth())/2);
				ball.setY((int)(platform.getHeight()-(int)(ball.getR())*2));
				gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

				game = true;

			}
		}

	}

	public class AnimateObjects extends AnimationTimer{

		public void handle(long now) {
			for (int i = 0; i < blocklist.size(); i++) {
				if ((blocklist.get(i).getRectangle().intersects(ball.getRectangle()) && !(blocklist.get(i) instanceof PowerUp)))
					game = false;
			}

			if (score % 25 == 0 && blockgenerator > 7){
				blockgenerator-=2;
				score++;
			}

			if (blocklist.size() > 0 && score % 100 == 0){
				blocklist.get(0).updateVY();
				blocklist.get(0).updateR();
				score++;
			}

			if (start) {
				Image startgamebackground = new Image("startgamebackground.jpg");
				gc.drawImage(startgamebackground, 0, 0, canvas.getWidth(), canvas.getHeight());
			} else if (game) {
				//clear screen and draw ball
				gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				gc.fillRect(0, canvas.getHeight()-100, canvas.getWidth(), 100);
				ball.drawBall(gc, platform);
				ball.perFrame(canvas, platform);
				//add intersects
				platform.drawPlatform(gc);

				if (addblockcounter % blockgenerator == 0){
					blocklist.add(new Block(canvas));
				}
				if (addblockcounter % 45 == 0){
					score++;
				}
				if (addblockcounter % 500 == 0){
					blocklist.add((Block)(new Nuke(canvas)));
				}
				if (addblockcounter % 350 == 0){
					blocklist.add((Block)(new Coin(canvas)));
				}

				if (addblockcounter % 450 == 0){
					blocklist.add((Block)(new Immunity(canvas)));
				}

				if (addblockcounter % 250 == 0){
					blocklist.add((Block)(new Bomb(canvas)));
				}

				if (!(clip.isPlaying()))
					clip.play();

				for (int i = 0; i < blocklist.size(); i++){
					blocklist.get(i).setColor(gc);
					blocklist.get(i).setColor(gc);
					blocklist.get(i).drawBlock(gc);
					blocklist.get(i).updateBlock();
					if ((blocklist.get(i).getRectangle().intersects(ball.getRectangle())) && (blocklist.get(i) instanceof PowerUp)) {
						if(powerup.isPlaying())
							powerup.stop();
						if (blocklist.get(i) instanceof Nuke){
							blocklist.get(i).action(blocklist);
							powerup.play();
						}
						else if (blocklist.get(i) instanceof Coin){
							int counter = blocklist.get(i).action();
							blocklist.remove(i);
							powerup.play();
							for (int j = 0; j < counter; j++){
								score++;
								if (score % 25 == 0 && blockgenerator > 7){
									blockgenerator-=2;
								}
								if (blocklist.size() > 0 && score % 100 == 0){
									blocklist.get(0).updateVY();
									blocklist.get(0).updateR();
									score++;
								}
							}
						}
						else if (blocklist.get(i) instanceof Bomb){
							if (score >= 50)
								score += blocklist.get(i).action();
							else
								score = 0;
							blocklist.remove(i);
							damage.play();
						}
						else if (blocklist.get(i) instanceof Immunity){
							immunity = true;
							blocklist.remove(i);
							powerup.play();
						}
					}

					if (immunity && blocklist.get(i).getRectangle().intersects(iplatform.getRectangle(ball.getX(), ball.getY())) && !(blocklist.get(i) instanceof PowerUp)) {
						blocklist.remove(i);
					}

					if (counter < 2000 && immunity) {
						iplatform.setColor(gc);
						iplatform.drawImmunityPlatform(gc, ball.getX(), ball.getY());
					} else {
						counter = 0;
						immunity = false;
					}
					counter++;

					for (int j = i+1; j < blocklist.size(); j++){
						if ((blocklist.get(i).getRectangle()).intersects(blocklist.get(j).getRectangle()))
							blocklist.remove(j);
					}
					if (blocklist.get(i).getY() > platform.getHeight() + blocklist.get(i).getR())
						blocklist.remove(i);

					//score
					gc.setFill(Color.YELLOW);
					gc.setStroke(Color.BLACK);
					gc.setLineWidth(1);
					Font font = Font.font("Calibri", FontWeight.NORMAL, 48);
					gc.setFont(font);
					gc.fillText(Integer.toString(score), canvas.getWidth()-100, 50);
					gc.strokeText(Integer.toString(score), canvas.getWidth()-100, 50);
				}

				addblockcounter++;
			} else {
				clip.stop();
				while(count < 1){
					gameover.play();
					count++;
				}
				Image gameoverbackground = new Image("gameoverbackground.jpg");
				gc.drawImage(gameoverbackground, 0, 0, canvas.getWidth(), canvas.getHeight());
				gc.setFill(Color.YELLOW);
				gc.setStroke(Color.BLACK);
				gc.setLineWidth(1);
				gc.fillText("Your score was " + score + "!", canvas.getWidth()/3, 50);
				gc.strokeText("Your score was " + score + "!", canvas.getWidth()/3, 50);
				gc.fillText("GAME OVER!!", canvas.getWidth()/3 + 50, 250);
				gc.strokeText("GAME OVER!!", canvas.getWidth()/3 + 50, 250);
				gc.fillText("Press SPACE to play again!", canvas.getWidth()/4, 450);
				gc.strokeText("Press SPACE to play again!", canvas.getWidth()/4, 450);

			}



		}
	}
}