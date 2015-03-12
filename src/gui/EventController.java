package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class EventController implements Initializable {
	@FXML private GridPane grid;
	@FXML private HBox titleBox;
	@FXML private HBox createdBox;
	@FXML private Button back;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String eventID = SessionData.id;
		core.Event e = new core.Event(eventID);
		Label title = new Label(e.getName());
		Label createdBy = new Label(e.getOwner());
		Label start = new Label(e.getStart());
		Label end = new Label(e.getEnd());
		Label desc = new Label(e.getDesc());
		Label room = new Label(e.getRoom());
		String p = "";
		ArrayList<String> ps = e.getParticipants();
		for (int i = 0; i < ps.size(); i++) {
			if (i != 0)
				p += ", ";
			p += ps.get(i);
		}
		Label participants = new Label(p);
		String d = "";
		ArrayList<core.CalendarOwner> ds = e.getDeclined();
		for (int i = 0; i < ds.size(); i++) {
			if (i != 0)
				d += ", ";
			d += ps.get(i);
		}
		Label declined = new Label(d);
		Label[] list = new Label[]{start, end, desc, room, participants, declined};
		
		title.setFont(new Font("Arial", 25));
		titleBox.getChildren().add(title);
		titleBox.setAlignment(Pos.CENTER);
		
		createdBox.getChildren().add(createdBy);
		createdBox.setAlignment(Pos.CENTER);
		
		for (int i = 0; i < list.length; i++) {
			StackPane sp = new StackPane();
			sp.getChildren().add(list[i]);
			StackPane.setAlignment(list[i], Pos.TOP_LEFT);
			grid.add(sp, 1, i);
		}
		
	}
	
}
