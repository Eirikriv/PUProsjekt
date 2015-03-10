package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;

import core.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class WeekController implements Initializable {
	
	@FXML private GridPane innerWeekGrid;
	@FXML private GridPane outerWeekGrid;
	@FXML private Button backButton;
	@FXML private Button eventButton;
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		for (Event event: SessionData.allEvents) {
			String title = event.getName();
			LocalDate startDate = LocalDate.parse(event.getStart().split(" ")[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			LocalTime startHour = LocalTime.parse(event.getStart().split(" ")[1], DateTimeFormatter.ofPattern("HH:mm"));
			LocalTime endHour = LocalTime.parse(event.getEnd().split(" ")[1], DateTimeFormatter.ofPattern("HH:mm"));
			Calendar calstart = Calendar.getInstance();
			calstart.setTime(SessionData.cal.getTime());
			calstart.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(SessionData.currentWeek));
			while (calstart.get(Calendar.DAY_OF_WEEK) != 2) {
				calstart.add(Calendar.DAY_OF_YEAR, -1);
			}
			calstart.set(Calendar.HOUR_OF_DAY, 7);
			Calendar calend = Calendar.getInstance();
			calend.setTime(calstart.getTime());
			calend.add(Calendar.DAY_OF_YEAR, 6);
			calend.set(Calendar.HOUR, 18);
			
			if (calstart.get(Calendar.DAY_OF_YEAR) <= startDate.getDayOfYear() && calend.get(Calendar.DAY_OF_YEAR) >= startDate.getDayOfYear()) {
				int day = startDate.getDayOfWeek().getValue();
				StackPane sp = new StackPane();
				sp.getChildren().add(new Label(title));
				sp.setStyle("-fx-background-color: green");
				int hours = endHour.getHour() - startHour.getHour();
				GridPane.setRowSpan(sp, hours);
				innerWeekGrid.add(sp, day-1, startHour.getHour()-8);
				
				//innerWeekGrid.setGridLinesVisible(false);
			}
			
			
			
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
