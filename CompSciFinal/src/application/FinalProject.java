package application;
	

import java.awt.Paint;
import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
	public static final double pane_height = 750; //default 500
	public static final double max_size = 25; //maximum size for every visual node 

	private AnimationTimer timer;
	private Pane root;
	private Rectangle frog, rect;
	
	private boolean stopCondition = false;
	private ArrayList<Rectangle> carsMovingRight = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> carsMovingLeft = new ArrayList<Rectangle>();
	
	private ArrayList<Rectangle> logsMovingRight = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> logsMovingLeft = new ArrayList<Rectangle>();

	private double frogLeft = 0;
	private boolean logCollide = false;
	
	//DIFFICULTY FACTORS
	private int carSpeed = 5; //default speed was 5
	private int carAmount = 3; //default random add quantity was 3	
	private int logAmount = 1;
	
	//COUNTERS & LIMITS
	private int leftCarCounter = 0;
	private int rightCarCounter = 0;
	private int leftLimit = 8;
	private int rightLimit = 8;
	
	private int rightSpawnCounter = 0, leftSpawnCounter = 0;
	private int rightSpawnDelay = 60, leftSpawnDelay = 60;

	
	private Rectangle initFrog() {
		Rectangle froggo = new Rectangle(max_size - 2, max_size - 2, Color.DARKOLIVEGREEN);
		
		froggo.setStroke(Color.FORESTGREEN);
		froggo.setY((pane_height - max_size) + 1);
		froggo.setX((pane_width / 2) - max_size);
		
		return froggo;
	}
	
//	private Rectangle initRightMovingLog() { 
//		Rectangle log = new Rectangle(max_size * 4, max_size, Color.SADDLEBROWN);
//		
//		double row = ((int) (Math.random() * (pane_height / max_size - 1)));
//		
//		while (row % 2 == 0) {
//			System.out.println("Row not even: " + row);
//			row = ((int) (Math.random() * (pane_height / max_size - 1)));
//		}
//		
//		log.setY(row * max_size);
//		log.setX(0);
//		
//		root.getChildren().add(log);
//		return log;
//	}
//	
//	private Rectangle initLeftMovingLog() { 
//		Rectangle log = new Rectangle(max_size * 4, max_size, Color.SADDLEBROWN);
//		
//		double row = ((int) (Math.random() * (pane_height / max_size - 1)));
//		
//		while (row % 2 != 0) {
//			System.out.println("Row not even: " + row);
//			row = ((int) (Math.random() * (pane_height / max_size - 1)));
//		}
//		
//		log.setY(row * max_size);
//		log.setX(pane_width);
//		
//		root.getChildren().add(log);
//		return log;
//	}
	
	private Rectangle initRightMovingCar() {
		Rectangle car = new Rectangle(max_size * 2, max_size, Color.DARKRED);

		double row = ((int) (Math.random() * (pane_height / max_size - 1)));
		
		while (row % 2 != 0) {
			row = ((int) (Math.random() * (pane_height / max_size - 1)));
		}
		
		car.setY(row * max_size); //number of rows minus first (19) * height of frog / car (25) 
		car.setX(0);
		
		root.getChildren().add(car);
		return car;
	}
	
	
	private Rectangle initLeftMovingCar() {
		Rectangle car = new Rectangle(max_size * 2, max_size, Color.DARKRED);

		double row = ((int) (Math.random() * (pane_height / max_size - 1)));
		
		while (row % 2 == 0) {
			row = ((int) (Math.random() * (pane_height / max_size - 1)));
		}
		
		car.setY(row * max_size); //number of rows - last row * height of frog / car
		car.setX(pane_width);
		
		root.getChildren().add(car);
		return car;
	}
	
	
	private void spawnCarsMovingRight() {
		
		if (rightCarCounter < rightLimit) {
			rightSpawnCounter++;
		}
		
		//RANDOMLY SPAWNS CARS
		for (Rectangle car : carsMovingRight) {
			car.setX(car.getX() + (Math.random() * carSpeed));
			car.setOpacity(0.6);
			
			if (car.getBoundsInParent().intersects(frog.getBoundsInParent())) {
				stopCondition = true;
				frog.setFill(Color.DARKRED);
				frog.setStroke(Color.DARKRED);
			}
			
			if (car.getX() >= pane_width) {
				car.setX(0);
				car.setX(car.getX() + (Math.random() * carSpeed)); //SETS A NEW RANDOM SPEED
			}	
		}
		
		if ((int) (Math.random() * 100) < carAmount && !stopCondition && rightCarCounter < rightLimit && rightSpawnCounter > rightSpawnDelay) {
			rightSpawnCounter = 0;
			carsMovingRight.add(initRightMovingCar());
			rightCarCounter++;
		}
	}
	
	private void spawnCarsMovingLeft() {
		
		if (leftCarCounter < leftLimit) {
			leftSpawnCounter++;
		}
		
		//RANDOMLY SPAWNS CARS
		for (Rectangle car : carsMovingLeft) {
			car.setX(car.getX() - (Math.random() * carSpeed));
			car.setOpacity(0.6);
			
			if (car.getBoundsInParent().intersects(frog.getBoundsInParent())) {
				stopCondition = true;
				frog.setFill(Color.DARKRED);
				frog.setStroke(Color.DARKRED);
			}
			
			if (car.getX() <= 0) {
				car.setX(pane_width);
				car.setX(car.getX() - (Math.random() * carSpeed)); //SETS A NEW RANDOM SPEED
			}	
		}
		
		if ((int) (Math.random() * 100) < carAmount && !stopCondition && leftCarCounter < leftLimit && leftSpawnCounter > leftSpawnDelay) {
			leftSpawnCounter = 0;
			carsMovingLeft.add(initLeftMovingCar());
			leftCarCounter++;
		}	
	}
	
	//TODO: Make method to change IMAGE, and DELETE CARS MOVING
	
//	private void spawnLogsMovingRight() {
//		
//		//RANDOMLY SPAWNS LOGS
//		for (Rectangle log : logsMovingRight) {
//			log.setX(log.getX() + (Math.random() * 3));
//			log.setOpacity(0.6);
//
//			//COLLISIONS
//			if (log.getBoundsInParent().intersects(frog.getBoundsInParent())) {
//				logCollide = true;
//				frog.setX(log.getX());
//				
//				System.out.println("FrogLeft: " + frogLeft);
//				System.out.println("GetX and Y: " + frog.getX() + " " + frog.getY());
//			}
//			else if (!log.getBoundsInParent().intersects(frog.getBoundsInParent())) {
//				logCollide = false;
//			}
//			
//			//DESPAWNS LOG
//			if (log.getX() >= pane_width) {
//				log.setOpacity(0);
//				log.setHeight(0);
//				log.setWidth(0);
//			}		
//		}
//		
//		//TODO: ADD METHOD FOR LEFT MOVING LOGS
//		
//		//ADDS LOG TO ARRAYLISTs
//		if ((int) (Math.random() * 100) < logAmount && !stopCondition) {
//			logsMovingRight.add(initRightMovingLog());
//		}	
//	}
	
	private void generateFinish() {
		rect = new Rectangle(pane_width, max_size, Color.TRANSPARENT);
		rect.setY(0);
		rect.setX(0);
		
		root.getChildren().add(rect);
	}
	
	private void incrementCondition() {
		if (frog.getBoundsInParent().intersects(rect.getBoundsInParent())) {
			frog.setY((pane_height - max_size) + 1);
			frog.setX((pane_width / 2) - max_size);

			carSpeed += 2;
		}
	}
	
	private Pane generateElements() {
		root = new Pane();
		frog = initFrog();
		
		//add the frog to the root node
		root.getChildren().add(frog);
		
		timer = new AnimationTimer() {
			
			@Override
			public void handle(long now) { //repeatedly initialize and animate cars
				spawnCarsMovingLeft();
				spawnCarsMovingRight();
				generateFinish();
				incrementCondition();
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
				if (!stopCondition)
					frog.setY(frog.getY() - max_size); // moves the frog 1 frog size up/down/left/right
				break;
				
			case DOWN:
				if (!stopCondition)
					frog.setY(frog.getY() + max_size);
				break;
				
			case LEFT:
				if (!stopCondition) {
					if (logCollide) {
						frogLeft = frog.getX() - (max_size + 25); //MAKES THE OFFSET AMOUNT A GLOBAL VARIABLE TO BE USED BY THE LOG
						frog.setX(frogLeft);
					}
					
					else {
						frog.setX(frog.getX() - max_size);
						frogLeft = 0;
					}
				}
				break;
				
			case RIGHT:
				if (!stopCondition)
					frog.setTranslateX(frog.getTranslateX() + max_size);	
				break;
				
			default:
				break;
			}
		});
		
		primaryStage.setTitle("My JavaFX");
		primaryStage.show();		
	}
	
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
