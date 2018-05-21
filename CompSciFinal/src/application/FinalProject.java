package application;
	

import java.util.ArrayList;
import java.util.Random;

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
	public static final double frog_size = 25;

	private AnimationTimer timer;
	private Pane root;
	private Rectangle frog;
	private Rectangle car;
	private ArrayList<Rectangle> cars = new ArrayList<Rectangle>();
	
	private Rectangle initCar() {
		Rectangle car = new Rectangle(50, 25, Color.DARKRED);
		
		car.setStroke(Color.RED);
		car.setTranslateY((int) (Math.random() * (pane_height / frog_size - 1)) * frog_size); //number of rows - 1 * size of frog (courtesy of tutorial guy)
	
		return car;
	}
	
	private Rectangle initFrog(int moveAmount) {
		
		Rectangle froggo = new Rectangle(frog_size, frog_size, Color.DARKOLIVEGREEN);
		
		froggo.setStroke(Color.FORESTGREEN);
		froggo.setY(pane_height - frog_size);
		froggo.setX((pane_width / 2) - frog_size);
		
		return froggo;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		root = new Pane();
		frog = initFrog(20);
		car = initCar();
		
		//add the frog to the root node
		root.getChildren().add(frog);
		root.getChildren().add(car);
		
		//WHY DOESNT THIS WORK
		timer = new AnimationTimer() {
			@Override
			public void handle(long now) { //repeatedly initialize and animate cars
				car.setTranslateX(car.getTranslateX() + (Math.random() * 10));
			}
		};
		
		Scene scene = new Scene(root, pane_width, pane_height);
		
		primaryStage.setScene(scene);
		
		primaryStage.getScene().setOnKeyPressed(event -> {
			switch (event.getCode()) {
			case UP:
				frog.setTranslateY(frog.getTranslateY() - frog_size); // moves the frog 1 frog size up/down/left/right
				break;
				
			case DOWN:
				frog.setTranslateY(frog.getTranslateY() + frog_size);
				break;
				
			case LEFT:
				frog.setTranslateX(frog.getTranslateX() - frog_size);
				break;
				
			case RIGHT:
				frog.setTranslateX(frog.getTranslateX() + frog_size);
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
