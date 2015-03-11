package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class AdminController implements Initializable {
	
	@FXML private ListView<String> peopleList;
	@FXML private ListView<String> groupList;
	@FXML private ListView<String> roomList;
	@FXML private Button newUser;
	@FXML private Button newGroup;
	@FXML private Button newRoom;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		peopleList.setItems(SessionData.allMembers);
		groupList.setItems(SessionData.allGroups);
		roomList.setItems(SessionData.allRooms);
	}
	
	@FXML private void newUser() {
		
	}
	
	@FXML private void newGroup() {
		
	}
	
	@FXML private void newRoom() {
		
	}

}
