package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class EventController implements Initializable {
	@FXML private GridPane grid;
	@FXML private Label createdBy;
	@FXML private Button back;
	@FXML private Label start;
	@FXML private Label end;
	@FXML private Label desc;
	@FXML private Label room;
	@FXML private Label participants;
	@FXML private Label declined;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String eventID = SessionData.id;
		core.Event e = new core.Event(eventID);
		StackPane sp = new StackPane();
		Label title = new Label(e.getName());
		sp.getChildren().add(title);
		grid.add(sp, 1, 0);
	}
	
}
