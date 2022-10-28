package app;

import app.control.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppPuissance extends Application {
	MainController mainController = new MainController();
    private static Stage primaryStage;
    
	@Override
    public void start(Stage stage) {
		mainController.initialise();
		primaryStage = stage;
		
		stage.setTitle("Puissance 4");
		
		Scene scene = new Scene(mainController.getFenetre(), 600, 300);
        stage.setScene(scene);
        stage.show();		
    }
	
	public static Stage getStage() {
		return primaryStage;
	}
	
    public static void main(String[] args) {
        launch();
    }
}
