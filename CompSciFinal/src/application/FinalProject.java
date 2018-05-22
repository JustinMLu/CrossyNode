package application;
	

import java.awt.Paint;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Random;

import com.sun.prism.Image;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class FinalProject extends Application {
	
	public static final double pane_width = 500;
	public static final double pane_height = 500;
	public static final double max_size = 25; //maximum size for every visual node 

	private AnimationTimer timer;
	private Pane root;
	private Rectangle frog;
	private boolean stopCondition = false;
	private ArrayList<Rectangle> carsMovingRight = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> carsMovingLeft = new ArrayList<Rectangle>();

	
	private Rectangle initCar() {
		Rectangle car = new Rectangle(max_size * 2, max_size - 1, Color.DARKRED);

		car.setTranslateY((int) (Math.random() * (pane_height / max_size - 1)) * max_size); //number of rows - last row * height of frog / car
	
		root.getChildren().add(car);
		return car;
	}
	
//	private Rectangle initLog() { 
//		Rectangle log = new Rectangle(max_size * 4, max_size - 1, Color.SADDLEBROWN);
//		
//		root.getChildren().add(log);
//		return log;
//	}
	
	
	private Rectangle initFrog() {
		Rectangle froggo = new Rectangle(max_size - 3, max_size - 3, Color.DARKOLIVEGREEN);
		
		froggo.setStroke(Color.FORESTGREEN);
		froggo.setY((pane_height - max_size) + 1);
		froggo.setX((pane_width / 2) - max_size);
		
		//SETS THE DEFAULT 0,0 POINT TO WHERE THE FROG SPAWNS
		froggo.setTranslateX(0);
		froggo.setTranslateY(0);
		
		return froggo;
	}
	
	
	private void spawnCarsMovingRight() {
		//RANDOMLY SPAWNS CARS
		for (Rectangle car : carsMovingRight) {
			car.setTranslateX(car.getTranslateX() + (Math.random() * 5));
			
			if (car.getBoundsInParent().intersects(frog.getBoundsInParent())) {
				//timer.stop();
				stopCondition = true;
				frog.setFill(Color.DARKRED);
				frog.setStroke(Color.DARKRED);
			}
		}
		
		if ((int) (Math.random() * 100) < 3 && !stopCondition) {
			carsMovingRight.add(initCar());
		}	
	}
	
	//TODO: Make method to spawn LOGS, change IMAGE, and DELETE CARS MOVING
	
	private void spawnLogs() {
		
	}

	
	private Pane generateElements() {
		root = new Pane();
		frog = initFrog();
		
		//add the frog to the root node
		root.getChildren().add(frog);
		
		timer = new AnimationTimer() {
			
			@Override
			public void handle(long now) { //repeatedly initialize and animate cars
				spawnCarsMovingRight();	
			}
		};
		timer.start();
		
		return root;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Scene scene = new Scene(generateElements(), pane_width, pane_height);
		
		primaryStage.setScene(scene);
		
		primaryStage.getScene().setOnKeyPressed(event -> {
			
			switch (event.getCode()) {
			case UP:
				if (!stopCondition && frog.getTranslateY() > -475)
					frog.setTranslateY(frog.getTranslateY() - max_size); // moves the frog 1 frog size up/down/left/right
					break;
				
			case DOWN:
				if (!stopCondition && frog.getTranslateY() < 0)
					frog.setTranslateY(frog.getTranslateY() + max_size);
					break;
				
			case LEFT:
				if (!stopCondition && frog.getTranslateX() > (-pane_width / 2 + max_size))
					frog.setTranslateX(frog.getTranslateX() - max_size);
					break;
				
			case RIGHT:
				if (!stopCondition && frog.getTranslateX() < pane_width / 2)
					frog.setTranslateX(frog.getTranslateX() + max_size);
					break;
				
			default:
				break;
			}			
			//System.out.println("Translate X & Y: " + frog.getTranslateX() + " " + frog.getTranslateY());
		});
		
		primaryStage.setTitle("My JavaFX");
		primaryStage.show();
		
	}
	
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
