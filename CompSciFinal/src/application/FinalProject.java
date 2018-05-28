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
	private int leftLimit = 6;
	private int rightLimit = 6;

	//Creates frog rectangle object
	private Rectangle initFrog() {
		Rectangle froggo = new Rectangle(max_size - 2, max_size - 2, Color.DARKOLIVEGREEN);
		
		froggo.setStroke(Color.FORESTGREEN); //Sets outer color
		froggo.setY((pane_height - max_size) + 1); //Sets Y location to bottom of screen
		froggo.setX((pane_width / 2) - max_size); //Sets X location to middle
		
		return froggo; //Returns frog node so it can be put into pane 
	}
	
	//FAILED FROGGER LOG CODE
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
	
	//Creates right-moving car node that spawns EVERY EVEN ROW
	private Rectangle initRightMovingCar() {
		Rectangle car = new Rectangle(max_size * 2, max_size, Color.DARKRED); //Sets dimensions of the car

		double row = ((int) (Math.random() * (pane_height / max_size - 1))); //Randomly generates the y pos based on the number of rows there are
		
		while (row % 2 != 0) { //If the row number is odd, regenerate the row until it is even.
			row = ((int) (Math.random() * (pane_height / max_size - 1)));
		}
		
		car.setY(row * max_size); //number of rows minus first (19) * height of frog / car (25) 
		car.setX(-50); //Spawns offscreen so it can transition on-screen
		
		//Add to root node automatically and return object so it can bge used in ArrayList for easier iteration
		root.getChildren().add(car); 
		return car;
	}
	
	//Creates left-moving car that spawns EVERY ODD ROW
	private Rectangle initLeftMovingCar() {
		Rectangle car = new Rectangle(max_size * 2, max_size, Color.DARKRED); //Sets dimensions

		double row = ((int) (Math.random() * (pane_height / max_size - 1))); //Randomly generates y pos row
		
		while (row % 2 == 0) { //If y pos row is even, regenerate until it is odd
			row = ((int) (Math.random() * (pane_height / max_size - 1)));
		}
		
		car.setY(row * max_size); //number of rows - last row * height of frog / car
		car.setX(pane_width);
		
		//Add to root node and return object so it can be used in ArrayList
		root.getChildren().add(car); 
		return car;
	}
	
	//Iterates through all right moving cars and assigns them properties
	private void spawnCarsMovingRight() {
		
		//RANDOMLY SPAWNS CARS
		for (Rectangle car : carsMovingRight) {
			car.setX(car.getX() + (Math.random() * carSpeed)); //Randomly sets the car's speed
			car.setOpacity(0.6);
			
			//If the frog's boundaries intersect the car's boundaries, stop the game and turn the frog bloody red
			if (car.getBoundsInParent().intersects(frog.getBoundsInParent())) {
				stopCondition = true;
				frog.setFill(Color.DARKRED);
				frog.setStroke(Color.DARKRED);
			}
			
			//If the car reaches the end of the screen, reset the car back to its initial pos and re-assign speed randomly
			if (car.getX() >= pane_width) {
				car.setX(-50);
				car.setX(car.getX() + (Math.random() * carSpeed)); //REASSIGNS SPEED OF CAR RANDOMLY
			}	
		}
		
		//Makes sure cars are added at random intervals while stop condition is false, and the limit of cars has not been reached.
		if ((int) (Math.random() * 100) < carAmount && !stopCondition && rightCarCounter < rightLimit) {
			carsMovingRight.add(initRightMovingCar());
			rightCarCounter++; //Add to the limit counter to account for extra added car
		}
	}
	
	private void spawnCarsMovingLeft() { //Literally the exact same thing but for cars moving left
		
		//RANDOMLY SPAWNS CARS
		for (Rectangle car : carsMovingLeft) {
			car.setX(car.getX() - (Math.random() * carSpeed));
			car.setOpacity(0.6);
			
			if (car.getBoundsInParent().intersects(frog.getBoundsInParent())) {
				stopCondition = true;
				frog.setFill(Color.DARKRED);
				frog.setStroke(Color.DARKRED);
			}
			
			if (car.getX() <= -50) {
				car.setX(pane_width);
				car.setX(car.getX() - (Math.random() * carSpeed)); //REASSIGNS SPEED OF CAR RANDOMLY
			}
		}
		
		if ((int) (Math.random() * 100) < carAmount && !stopCondition && leftCarCounter < leftLimit) {
			carsMovingLeft.add(initLeftMovingCar());
			leftCarCounter++;
		}	
	}
	
	private void leftCollisionCheck() {
		for (int i = 0; i < carsMovingLeft.size() - 1; i++) {
			for (int b = i + 1; b < carsMovingLeft.size(); b++) {
				if (carsMovingLeft.get(i).getBoundsInParent().intersects(carsMovingLeft.get(b).getBoundsInParent())) {
					carsMovingLeft.get(b).setOpacity(0);
					carsMovingLeft.remove(b);
				}
			}
		}
	}
	
	private void rightCollisionCheck() {
		for (int i = 0; i < carsMovingRight.size() - 1; i++) {
			for (int b = i + 1; b < carsMovingRight.size(); b++) {
				if (carsMovingRight.get(i).getBoundsInParent().intersects(carsMovingRight.get(b).getBoundsInParent())) {
					carsMovingRight.get(b).setOpacity(0);
					carsMovingRight.remove(b);
				}
			}
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
	
	//Generates a rectangle that will be used as a finish line, and adding it to the root node
	private void generateFinish() {
		rect = new Rectangle(pane_width, max_size, Color.DARKSEAGREEN);
		rect.setY(0);
		rect.setX(0);
		
		root.getChildren().add(rect);
	}
	
	//If the frog intersects with the finish line rectangle, reset the frog and SPEED UP THE CARS
	private void incrementCondition() {
		if (frog.getBoundsInParent().intersects(rect.getBoundsInParent())) {
			frog.setY((pane_height - max_size) + 1);
			frog.setX((pane_width / 2) - max_size);

			carSpeed += 1;
			leftLimit++;
			rightLimit++;
			
			//Increases the car limits so that more cars spawn
			if (leftLimit < 10 || rightLimit < 10) {
				leftLimit = 10;
				rightLimit = 10;
			}
			
			//Limit so the game does not become impossible
			if (carSpeed < 6) {
				carSpeed = 6;
			}
		}
	}
	
	/* Method that creates root container pane and frog, as well as adding all spawn and iterating methods in an infinite AnimationTimer
	
	Originally these things were in the start menu, however in order to modular-ize the code (and possibly call later) I have moved them.
	
	Returns the pane after adding nodes into it */
	
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
				
				leftCollisionCheck();
				rightCollisionCheck();
				
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
				if (!stopCondition && frog.getY() > 1)
					frog.setY(frog.getY() - max_size); // moves the frog 1 frog size up/down/left/right
				break;
				
			case DOWN:
				if (!stopCondition && frog.getY() < 726)
					frog.setY(frog.getY() + max_size);
				break;
				
			case LEFT:
				if (!stopCondition && frog.getX() > 0)
					frog.setX(frog.getX() - max_size);
				break;
				
			case RIGHT:
				if (!stopCondition && frog.getX() < 475)
					frog.setX(frog.getX() + max_size);	
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
