package gui;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;
 
public class ScreenNavigator {

    public static final String MAIN    = "main.fxml";
    public static final String SCREEN_LOGIN = "LoginScreen.fxml";
    public static final String SCREEN_CALENDAR = "CalendarScreen.fxml";
    public static final String SCREEN_NEW_APPOINTMENT = "CreateAppointment.fxml";
    public static final String SCREEN_WEEK = "WeekScreen.fxml";
    public static final String SCREEN_EVENT = "EventScreen.fxml";
    public static final String SCREEN_ADMIN = "AdminScreen.fxml";
    
    public static final double SCREEN_CALENDAR_HEIGHT = 600;
    public static final double SCREEN_CALENDAR_WIDTH = 750;
    public static final double SCREEN_LOGIN_HEIGHT = 350;
    public static final double SCREEN_LOGIN_WIDTH = 250;
    public static final double SCREEN_NEW_APPOINTMENT_HEIGHT = 600;
    public static final double SCREEN_NEW_APPOINTMENT_WIDTH = 600;
    public static final double SCREEN_WEEK_HEIGHT = 600;
    public static final double SCREEN_WEEK_WIDTH = 850;
    public static final double SCREEN_EVENT_HEIGHT = 500;
    public static final double SCREEN_EVENT_WIDTH = 700;

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
        	}else if (fxml.compareTo(SCREEN_WEEK) == 0) {
        		height = SCREEN_WEEK_HEIGHT;
        		width = SCREEN_WEEK_WIDTH;
        	} else if (fxml.compareTo(SCREEN_EVENT) == 0) {
        		height = SCREEN_EVENT_HEIGHT;
        		width = SCREEN_EVENT_WIDTH;
        	} else {
        		height = 800;
        		width = 800;
        	}
            mainController.setVista((Node)FXMLLoader.load(ScreenNavigator.class.getResource(fxml)), height, width);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
}