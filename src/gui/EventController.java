package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class EventController implements Initializable {
	@FXML private GridPane grid;
	@FXML private HBox titleBox;
	@FXML private Button back;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String eventID = SessionData.id;
		core.Event e = new core.Event(eventID);
		Label title = new Label(e.getName());
		title.setFont(new Font("Arial", 25));
		titleBox.getChildren().add(title);
		titleBox.setAlignment(Pos.CENTER);
	}
	
}
