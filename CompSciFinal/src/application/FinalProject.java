package application;
	

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;


public class FinalProject extends Application {
	
	public static final double pane_width = 500;
	public static final double pane_height = 500;
	
	private Rectangle[] initCars(int num, double width, double height) {
		
		Rectangle[] cars = new Rectangle[num];
		
		for (int i = 0; i < cars.length; i++) {
			cars[i] = new Rectangle(width, height, Color.DARKRED);
			cars[i].setStroke(Color.RED);
			cars[i].setY(i * height + i * 1);	
		}
		
		return cars;
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Rectangle rect = new Rectangle(50, 25, Color.RED);
		
		Pane root = new Pane();
		
		Rectangle[] vehicles = initCars(5, 50, 25);
		
		for (int i = 0; i < vehicles.length; i++) {
			root.getChildren().add(vehicles[i]);
		}
		
		Scene scene = new Scene(root, pane_width, pane_height);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("My JavaFX");
		primaryStage.show();
		
	}
	
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
