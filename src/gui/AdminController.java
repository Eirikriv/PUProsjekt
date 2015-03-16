package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import core.Group;
import core.Person;
import core.Room;
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
						core.Program.removeGroup(group.split(":")[0]);
						groupContainer.getChildren().clear();
						ViewController.getAllGroups();
						groupList.setItems(SessionData.allGroups);
					}
				});
			}
		});
		
		roomList.getFocusModel().focusedItemProperty().addListener(new ChangeListener<String>(){
			@Override public void changed(final ObservableValue<? extends String> arg0,
					String arg1, String arg2) {
				Button delete = new Button("delete");
				StackPane sp = wrap(delete);
				roomContainer.getChildren().clear();
				roomContainer.getChildren().add(sp);
				delete.setOnAction(new EventHandler<ActionEvent>(){
					@Override public void handle(ActionEvent argx0) {
						String room = arg0.getValue();
						core.Program.removeRoom(room.split("[")[0].trim());
						roomContainer.getChildren().clear();
						ViewController.getAllRooms();
						roomList.setItems(SessionData.allRooms);
					}
				});
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
				new Person(usernameText.getText(), nameText.getText(), passwordText.getText(), isAnAdmin);
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
	}
	
	@FXML private void newGroup() {
		if (groupIsClicked) {
			groupContainer.getChildren().clear();
			groupContainer.setMinSize(0, 0);
			groupIsClicked = false;
			return;
		}
		groupIsClicked = true;
		groupContainer.getChildren().clear();
		Label name = new Label("Group name:");
		final TextField nameText = new TextField();
		HBox cont = new HBox();
		VBox ccont = new VBox();
		Button members = new Button("Members");
		Button search = new Button("Search people");
		members.setMinSize(60, 20);
		search.setMinSize(100, 20);
		Button add = new Button("<");
		add.setMinSize(27, 20);
		Button delete = new Button(">");
		delete.setMinSize(27, 20);
		ccont.getChildren().addAll(add, delete);
		final ListView<String> lw = new ListView<String>();
		cont.getChildren().addAll(lw, ccont);
		Button createGroup = new Button("Create group");
		
		groupContainer.getChildren().addAll(name, nameText, cont, createGroup);
		groupContainer.setMinSize(100, 200);
		
		
		add.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				try {
					String name = peopleList.getSelectionModel().getSelectedItem();
					if (!groupItems.contains(name)) {
						System.out.println("OK");
						groupItems.add(name);
						lw.setItems(groupItems);
					}
				} catch (Exception e) {
					return;
				}
			}
		});
		
		delete.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				try {
					String name = peopleList.getSelectionModel().getSelectedItem();
					groupItems.remove(name);
					lw.setItems(groupItems);
				} catch (Exception e) {
					return;
				}
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
						VBox.setMargin(userContainer, new Insets(20));
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
		
		Label nameLabel = new Label("Room name:");
		Label capacityLabel = new Label("Capacity:");
		Label descLabel = new Label("Description:");
		final TextField nameText = new TextField();
		final TextField capacityText = new TextField();
		final TextField descText = new TextField();
		Button createRoom = new Button("Create room");
		
		roomContainer.getChildren().addAll(nameLabel, nameText, capacityLabel, capacityText, descLabel, descText, createRoom);
		
		createRoom.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent arg0) {
				Room r = new Room(nameText.getText(), capacityText.getText(), descText.getText());
				System.out.println("added " + r.getPrimaryKey());
				roomContainer.getChildren().clear();
				ViewController.getAllRooms();
				roomList.setItems(SessionData.allRooms);
			}
		});
	}
	
	private StackPane wrap(Node n) {
		StackPane sp = new StackPane();
		sp.getChildren().add(n);
		return sp;
	}

}
