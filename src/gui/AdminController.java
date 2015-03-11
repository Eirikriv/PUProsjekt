package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class AdminController implements Initializable {
	
	@FXML private ListView<String> listPeople;
	@FXML private ListView<String> listGroup;
	@FXML private ListView<String> listRooms;
	@FXML private Button newUser;
	@FXML private Button newGroup;
	@FXML private Button newRoom;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
