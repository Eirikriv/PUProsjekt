package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;

import core.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class WeekController implements Initializable {
	
	@FXML private GridPane innerWeekGrid;
	@FXML private GridPane outerWeekGrid;
	@FXML private Button backButton;
	@FXML private Button eventButton;
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		StackPane s  = new StackPane();
		Label lb = new Label("Week: " + SessionData.currentWeek);
		s.getChildren().add(lb);
		s.setAlignment(Pos.CENTER);
		outerWeekGrid.add(s, 0, 0);
		for (final Event event: SessionData.allEvents) {
			String title = event.getName();
			LocalDate startDate = LocalDate.parse(event.getStart().split(" ")[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			LocalTime startHour = LocalTime.parse(event.getStart().split(" ")[1], DateTimeFormatter.ofPattern("HH:mm"));
			LocalTime endHour = LocalTime.parse(event.getEnd().split(" ")[1], DateTimeFormatter.ofPattern("HH:mm"));
			Calendar calstart = Calendar.getInstance();
			calstart.setTime(SessionData.cal.getTime());
			calstart.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(SessionData.currentWeek)+1);
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
				final StackPane sp = new StackPane();
				Label l = new Label(title);
				sp.getChildren().add(l);
				sp.setBackground(new Background(new BackgroundFill(Paint.valueOf("0xD1EFFF"), null, null), null));
				sp.setOnMouseEntered(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent event) {
						sp.setBackground(new Background(new BackgroundFill(Color.web("0xDEF3FF"), null, null), null));
					}
				});
				sp.setOnMouseExited(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent event) {
						sp.setBackground(new Background(new BackgroundFill(Color.web("0xD1EFFF"), null, null), null));
					}
				});
				sp.setBorder(new Border(new BorderStroke(Paint.valueOf("0x000000"), BorderStrokeStyle.SOLID, null, null)));
				StackPane.setAlignment(l, Pos.TOP_CENTER);
				int hours = endHour.getHour() - startHour.getHour();
				GridPane.setRowSpan(sp, hours);
				innerWeekGrid.add(sp, day-1, startHour.getHour()-8);
				sp.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						try {
							SessionData.id = event.getEventID();
							SessionData.prevScreen = ScreenNavigator.SCREEN_WEEK;
							ScreenNavigator.loadVista(ScreenNavigator.SCREEN_EVENT);
							
						} catch (Exception exc) {
							exc.printStackTrace();
						}
					}
				});
				
			}
			
			
			
		}
	}
	
	@FXML
	public void back(ActionEvent e) {
		ScreenNavigator.loadVista(ScreenNavigator.SCREEN_CALENDAR);
	}
	
	@FXML
	public void newEvent(ActionEvent e) {
		ScreenNavigator.loadVista(ScreenNavigator.SCREEN_NEW_APPOINTMENT);
	}

}
