package gui;

import java.net.URL;
import java.util.ResourceBundle;

import core.Person;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class AdminController implements Initializable {
	
	@FXML private ListView<String> peopleList;
	@FXML private ListView<String> groupList;
	@FXML private ListView<String> roomList;
	@FXML private Button newUser;
	@FXML private Button newGroup;
	@FXML private Button newRoom;
	@FXML private VBox userContainer;
	
	private boolean isClicked = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		peopleList.setItems(SessionData.allMembers);
		groupList.setItems(SessionData.allGroups);
		roomList.setItems(SessionData.allRooms);
		
		
	}
	
	@FXML private void newUser() {
		if (isClicked) {
			userContainer.getChildren().clear();
			isClicked = false;
			return;
		}
		isClicked = true;
		userContainer.getChildren().clear();
		Label name = new Label("Name:");
		final TextField nameText = new TextField();
		
		Label username = new Label("Username:");
		final TextField usernameText = new TextField();
		
		Label password = new Label("Password:");
		final PasswordField passwordText = new PasswordField();
		
		HBox bottom = new HBox();
		HBox.setMargin(bottom, new Insets(15));
		Label admin = new Label("admin");
		final CheckBox isAdmin = new CheckBox();
		Button createUser = new Button("Create user");
		
		createUser.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent arg0) {
				String isAnAdmin = "";
				if (isAdmin.selectedProperty().getValue()) {
					isAnAdmin = "1";
				} else {
					isAnAdmin = "0";
				}
				Person p = new Person(nameText.getText(), usernameText.getText(), passwordText.getText(), isAnAdmin);
				ViewController.getAllPeople();
				peopleList.setItems(SessionData.allMembers);
				userContainer.getChildren().clear();
			}
		});
		
		StackPane adminCont = wrap(admin);
		StackPane isAdminCont = wrap(isAdmin);
		StackPane createUserCont = wrap(createUser);
		bottom.getChildren().addAll(adminCont, isAdminCont, createUserCont);
		
		
		
		userContainer.getChildren().addAll(name, nameText, username, usernameText, password, passwordText, bottom);
		//userContainer.autosize();
	}
	
	@FXML private void newGroup() {
		
	}
	
	@FXML private void newRoom() {
		
	}
	
	private StackPane wrap(Node n) {
		StackPane sp = new StackPane();
		sp.getChildren().add(n);
		return sp;
	}

}
