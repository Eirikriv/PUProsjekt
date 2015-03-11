package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class AdminController implements Initializable {
	
	@FXML private ListView<String> peopleList;
	@FXML private ListView<String> groupList;
	@FXML private ListView<String> roomList;
	@FXML private Button newUser;
	@FXML private Button newGroup;
	@FXML private Button newRoom;
	@FXML private StackPane userContainer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		peopleList.setItems(SessionData.allMembers);
		groupList.setItems(SessionData.allGroups);
		roomList.setItems(SessionData.allRooms);
	}
	
	@FXML private void newUser() {
		Label username = new Label("Username:");
		TextField usernameText = new TextField();
		userContainer.getChildren().addAll(username, usernameText);
	}
	
	@FXML private void newGroup() {
		
	}
	
	@FXML private void newRoom() {
		
	}

}
