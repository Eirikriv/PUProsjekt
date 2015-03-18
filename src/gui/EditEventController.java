package gui;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class EditEventController {
	@FXML TextField startTF;
	@FXML TextField endTF;
	@FXML TextField descTF;
	@FXML TextField roomTF;
	@FXML ComboBox<String> roomCB;
	@FXML ComboBox<String> invitedCB;
	@FXML ListView<String> invitedLW;
}
