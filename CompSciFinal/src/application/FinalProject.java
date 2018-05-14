package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;


public class FinalProject extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		
		Button butt = new Button("Mello World!");
		Scene scene = new Scene(butt, 200, 250);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("My JavaFX");
		primaryStage.show();
		
//		try {
//			BorderPane root = new BorderPane();
//			Scene scene = new Scene(root,400,400);
//			
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			primaryStage.setScene(scene);
//			
//			primaryStage.setTitle("Success");
//			primaryStage.show();
//			
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
