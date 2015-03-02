package gui;
	
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;


public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			stage.setScene(createScene(loadMainPane()));
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private Pane loadMainPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();
 
        Pane mainPane = (Pane) loader.load(getClass().getResourceAsStream(ScreenNavigator.MAIN));
 
        MainController mainController = loader.getController();
 
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
	
	
}
