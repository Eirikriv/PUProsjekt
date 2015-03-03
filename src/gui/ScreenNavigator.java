package gui;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
 
public class ScreenNavigator {

    public static final String MAIN    = "main.fxml";
    public static final String SCREEN_LOGIN = "LoginScreen.fxml";
    public static final String SCREEN_CALENDAR = "CalendarScreen.fxml";
    public static final String SCREEN_NEW_APPOINTMENT = "CreateAppointment.fxml";
    
    public static final double SCREEN_CALENDAR_HEIGHT = 600;
    public static final double SCREEN_CALENDAR_WIDTH = 600;
    public static final double SCREEN_LOGIN_HEIGHT = 350;
    public static final double SCREEN_LOGIN_WIDTH = 250;
    public static final double SCREEN_NEW_APPOINTMENT_HEIGHT = 400;
    public static final double SCREEN_NEW_APPOINTMENT_WIDTH = 452;

    public static MainController mainController;
 
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
        	} else if (fxml.compareTo(SCREEN_NEW_APPOINTMENT) == 0) {
        		height = SCREEN_NEW_APPOINTMENT_HEIGHT;
        		width = SCREEN_NEW_APPOINTMENT_WIDTH;
        	} else {
        		height = 800;
        		width = 800;
        	}
            mainController.setVista(FXMLLoader.load(ScreenNavigator.class.getResource(fxml)), height, width);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
}