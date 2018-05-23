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
	
	private ArrayList<Rectangle> logsMovingRight = new ArrayList<Rectangle>();
	private int environmentCondition = 1;
	private boolean initCondition = true;
	private boolean logCollision = false;
	private double frogLeft = 0, frogRight = 0;
	private boolean frogLeftTrue = false, frogRightTrue = false;
	private boolean logCollide = false;
	private double frogXPos;
	
	
	private Rectangle initCar() {
		Rectangle car = new Rectangle(max_size * 2, max_size, Color.DARKRED);

		car.setTranslateY(((int) (Math.random() * (pane_height / max_size - 1))) * max_size); //number of rows - last row * height of frog / car
	
		root.getChildren().add(car);
		return car;
	}
	
	private Rectangle initLog() { 
		Rectangle log = new Rectangle(max_size * 4, max_size, Color.SADDLEBROWN);
		
		log.setTranslateY(((int) (Math.random() * (pane_height / max_size - 1))) * max_size);
		
		root.getChildren().add(log);
		return log;
	}	
	
	
	private Rectangle initFrog() {
		Rectangle froggo = new Rectangle(max_size - 2, max_size - 2, Color.DARKOLIVEGREEN);
		
		froggo.setStroke(Color.FORESTGREEN);
		froggo.setY((pane_height - max_size) + 1);
		froggo.setX((pane_width / 2) - max_size);
		
		return froggo;
	}
	
	
	private void spawnCarsMovingRight() {
		//RANDOMLY SPAWNS CARS
		for (Rectangle car : carsMovingRight) {
			car.setX(car.getX() + (Math.random() * 5));
			car.setOpacity(0.6);
			
			if (car.getBoundsInParent().intersects(frog.getBoundsInParent())) {
				frog.setTranslateX(car.getX() - 175);
				stopCondition = true;
				frog.setFill(Color.DARKRED);
				frog.setStroke(Color.DARKRED);
			}
		}
		
		if ((int) (Math.random() * 100) < 3 && !stopCondition) {
			carsMovingRight.add(initCar());
		}	
	}
	
	//TODO: Make method to change IMAGE, and DELETE CARS MOVING
	
	private void spawnLogsMovingRight() {
		
		//RANDOMLY SPAWNS LOGS
		for (Rectangle log : logsMovingRight) {
			log.setX(log.getX() + (Math.random() * 3));
			log.setOpacity(0.6);
			frogXPos = frog.getX();

			//COLLISIONS
			if (log.getBoundsInParent().intersects(frog.getBoundsInParent())) {
				logCollide = true;
				frog.setX(log.getX());
				
				System.out.println("FrogLeft: " + frogLeft);
				System.out.println("GetX and Y: " + frog.getX() + " " + frog.getY());
			}
			
			//DESPAWNS LOG
			if (log.getX() >= pane_width) {
				log.setOpacity(0);
				log.setHeight(0);
				log.setWidth(0);
			}		
		}
		
		//ADDS LOG TO ARRAYLISTs
		if ((int) (Math.random() * 100) < 2 && !stopCondition) {
			logsMovingRight.add(initLog());
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
						frogLeft = frog.getX() - max_size; //MAKES THE OFFSET AMOUNT A GLOBAL VARIABLE TO BE USED BY THE LOG
						frog.setX(frogLeft);
					}
					else {
						frog.setX(frog.getX() - max_size);
						frogLeft = 0;
					}
					break;
				}
				
			case RIGHT:
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
