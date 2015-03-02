package gui;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
 
public class ScreenNavigator {

    public static final String MAIN    = "main.fxml";
    public static final String SCREEN_LOGIN = "LoginScreen.fxml";
    public static final String SCREEN_CALENDAR = "CalendarScreen.fxml";
    public static final double SCREEN_CALENDAR_HEIGHT = 600;
    public static final double SCREEN_CALENDAR_WIDTH = 600;
    public static final double SCREEN_LOGIN_HEIGHT = 350;
    public static final double SCREEN_LOGIN_WIDTH = 250;

    private static MainController mainController;
 
    public static void setMainController(MainController mainController) {
        ScreenNavigator.mainController = mainController;
    }

    public static void loadVista(String fxml) {
        try {
        	double height, width;
        	if (fxml.compareTo(SCREEN_CALENDAR) == 0) {
        		height = SCREEN_CALENDAR_HEIGHT;
        		width = SCREEN_CALENDAR_WIDTH;
        	} else if (fxml.compareTo(SCREEN_LOGIN) == 0) {
        		height = SCREEN_LOGIN_HEIGHT;
        		width = SCREEN_LOGIN_WIDTH;
        	} else {
        		height = 290;
        		width = 224;
        	}
            mainController.setVista(FXMLLoader.load(ScreenNavigator.class.getResource(fxml)), height, width);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
}