package gui;
	
import java.io.IOException;

import DB.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;


public class Main extends Application {
	
	@Override
	public void start(Stage stage) {
		try {
			stage.setScene(createScene(loadMainPane(stage)));
			
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private Pane loadMainPane(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
 
        Pane mainPane = (Pane) loader.load(getClass().getResourceAsStream(ScreenNavigator.MAIN));
 
        MainController mainController = loader.getController();
        
        mainController.setStage(stage);
        ScreenNavigator.setMainController(mainController);
        ScreenNavigator.loadVista(ScreenNavigator.SCREEN_LOGIN);
 
        return mainPane;
    }
 
    private Scene createScene(Pane mainPane) {
        return new Scene(mainPane);
    }
 
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static boolean checkUserLogin(String username, String password) {
		//returns true if user exist and password matches the one in the databse.
		
		//example;
		//return DatabaseHandler.checkLogin(username, password);
		
		return true;
	}
}
