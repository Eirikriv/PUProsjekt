package GUI;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
 
/**
 * Utility class for controlling navigation between vistas.
 *
 * All methods on the navigator are static to facilitate
 * simple access from anywhere in the application.
 */
public class ScreenNavigator {

    public static final String MAIN    = "main.fxml";
    public static final String SCREEN_LOGIN = "LoginScreen.fxml";
    public static final String SCREEN_CALENDAR = "CalendarScreen.fxml";

    private static MainController mainController;
 
    public static void setMainController(MainController mainController) {
        ScreenNavigator.mainController = mainController;
    }

    public static void loadVista(String fxml) {
        try {
            mainController.setVista(FXMLLoader.load(ScreenNavigator.class.getResource(fxml)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
}