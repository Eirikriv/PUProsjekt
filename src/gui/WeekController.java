package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import core.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class WeekController implements Initializable {
	
	@FXML private GridPane innerWeekGrid;
	@FXML private GridPane outerWeekGrid;
	@FXML private Button backButton;
	@FXML private Button eventButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		for (Event event: SessionData.allEvents) {
			String title = event.getName();
			LocalDate start = LocalDate.parse(event.getStart().split(" ")[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			Calendar calstart = SessionData.cal;
		}
	}
	
	@FXML
	public void back(ActionEvent e) {
		ScreenNavigator.loadVista(ScreenNavigator.SCREEN_CALENDAR);
	}
	
	@FXML
	public void newEvent(ActionEvent e) {

	}

}
