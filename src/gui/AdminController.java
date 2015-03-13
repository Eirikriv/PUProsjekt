package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import core.Group;
import core.Person;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
	@FXML private VBox groupContainer;
	@FXML private VBox roomContainer;
	
	private boolean userIsClicked = false;
	private boolean groupIsClicked = false;
	private boolean roomIsClicked = false;
	
	ArrayList<String> groupPeople = new ArrayList<String>();
	ObservableList<String> groupItems = FXCollections.observableArrayList(groupPeople);
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		peopleList.setItems(SessionData.allMembers);
		groupList.setItems(SessionData.allGroups);
		roomList.setItems(SessionData.allRooms);
		
		peopleList.getFocusModel().focusedItemProperty().addListener(new ChangeListener<String>(){
			@Override public void changed(final ObservableValue<? extends String> arg0,
					String arg1, String arg2) {
				Button delete = new Button("delete");
				StackPane sp = wrap(delete);
				userContainer.getChildren().clear();
				userContainer.getChildren().add(sp);
				delete.setOnAction(new EventHandler<ActionEvent>(){
					@Override public void handle(ActionEvent argx0) {
						String user = arg0.getValue();
						core.Program.removePerson(user.split("<")[0]);
						userContainer.getChildren().clear();
						ViewController.getAllPeople();
						peopleList.setItems(SessionData.allMembers);
					}
					
				});
			}
		});
		
		groupList.getFocusModel().focusedItemProperty().addListener(new ChangeListener<String>(){
			@Override public void changed(final ObservableValue<? extends String> arg0,
					String arg1, String arg2) {
				Button delete = new Button("delete");
				StackPane sp = wrap(delete);
				groupContainer.getChildren().clear();
				groupContainer.getChildren().add(sp);
				delete.setOnAction(new EventHandler<ActionEvent>(){
					@Override public void handle(ActionEvent argx0) {
						String group = arg0.getValue();
						//core.Program.removeGroup();
					}
				});
			}
		});
		
		roomList.getFocusModel().focusedItemProperty().addListener(new ChangeListener<String>(){
			@Override public void changed(ObservableValue<? extends String> arg0,
					String arg1, String arg2) {
				System.out.println(arg0.getValue());
			}
		});
	}
	
	@FXML private void newUser() {
		if (userIsClicked) {
			userContainer.getChildren().clear();
			userIsClicked = false;
			return;
		}
		userIsClicked = true;
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
				new Person(nameText.getText(), usernameText.getText(), passwordText.getText(), isAnAdmin);
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
		userContainer.autosize();
	}
	
	@FXML private void newGroup() {
		if (groupIsClicked) {
			groupContainer.getChildren().clear();
			groupIsClicked = false;
			return;
		}
		groupIsClicked = true;
		groupContainer.getChildren().clear();
		Label name = new Label("Group name:");
		final TextField nameText = new TextField();
		HBox cont = new HBox();
		VBox ccont = new VBox();
		Button add = new Button("<");
		add.setMinSize(25, 20);
		Button delete = new Button(">");
		delete.setMinSize(25, 20);
		ccont.getChildren().addAll(add, delete);
		final ListView<String> lw = new ListView<String>();
		cont.getChildren().addAll(lw, ccont);
		Button createGroup = new Button("Create group");
		
		groupContainer.getChildren().addAll(name, nameText, cont, createGroup);
		groupContainer.setMinSize(100, 200);
		
		
		add.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent arg0) {
				String name = peopleList.getSelectionModel().getSelectedItem();
				if (!groupItems.contains(name)) {
					groupItems.add(name);
					lw.setItems(groupItems);
				}
			}
		});
		
		delete.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent arg0) {
				String name = lw.getSelectionModel().getSelectedItem();
				lw.getItems().remove(name);
				lw.setItems(groupItems);
			}
		});
		
		createGroup.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				if (nameText.getText().length() != 0 && lw.getItems().size() != 0) {
					Group grp = new Group(null, nameText.getText());
					for (String s: lw.getItems()) {
						String name = s.split("<")[1];
						name = name.split(">")[0];
						grp.addMember(name);
						groupContainer.getChildren().clear();
						ViewController.getAllGroups();
						groupList.setItems(SessionData.allGroups);
					}
				}
			}
		});
		
	}
	
	@FXML private void newRoom() {
		if (roomIsClicked) {
			roomContainer.getChildren().clear();
			roomIsClicked = false;
			return;
		}
		roomIsClicked = true;
		roomContainer.getChildren().clear();
		
		
		
		
		
		
	}
	
	private StackPane wrap(Node n) {
		StackPane sp = new StackPane();
		sp.getChildren().add(n);
		return sp;
	}

}
