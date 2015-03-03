package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class appointmentController implements Initializable {
	
	@FXML private GridPane appointmentContainer;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		appointmentContainer.add(new FilterComboBox(SessionData.allMembers), 1, 3);
		appointmentContainer.add(new FilterComboBox(SessionData.allGroups), 1, 4);
		
		StackPane hb = new StackPane();
		HBox sp = new HBox();
		TextField tf = new TextField();
		FilterComboBox fcb = new FilterComboBox(SessionData.allRooms);
		sp.getChildren().addAll(tf, fcb);
		hb.getChildren().add(sp);
		sp.setAlignment(Pos.CENTER);
		appointmentContainer.add(hb, 1, 5);
		
	}
	
	public void navigateBack(MouseEvent e) {
		ScreenNavigator.loadVista(ScreenNavigator.SCREEN_CALENDAR);
	}

}
