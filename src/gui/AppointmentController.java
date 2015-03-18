package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import database.EventDatabaseHandler;
import database.GroupDatabaseHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class AppointmentController implements Initializable {
	
	@FXML private GridPane appointmentContainer;
	@FXML private TextField titleField;
	@FXML private TextArea descriptionText;
	@FXML private DatePicker dateField;
	@FXML private TextField startField;
	@FXML private TextField endField;
	@FXML private ListView<String> listMembersField;
	
	
	TextField tf = new TextField();
	private ObservableList<String> listViewList = FXCollections.observableArrayList();
	private ObservableList<String> roomList = FXCollections.observableArrayList();
	private FilterComboBox members = new FilterComboBox(SessionData.allMembers);
	private FilterComboBox groups = new FilterComboBox(SessionData.allGroups);
	private FilterComboBox rooms = new FilterComboBox(roomList);
	EventDatabaseHandler edb = new EventDatabaseHandler();
	GroupDatabaseHandler gdb = new GroupDatabaseHandler();
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listMembersField.setItems(listViewList);
		listMembersField.setFocusTraversable(false);
		descriptionText.setFocusTraversable(false);
		members.setFocusTraversable(true);
		groups.setFocusTraversable(true);
		rooms.setFocusTraversable(true);
		startField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0,
					String arg1, String arg2) {
				fillRoomBox();
			}
		});
		endField.textProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue<? extends String> arg0,
					String arg1, String arg2) {
				fillRoomBox();
			}
			
		});
		dateField.valueProperty().addListener(new ChangeListener<LocalDate>() {
			@Override public void changed(
					ObservableValue<? extends LocalDate> observable,
					LocalDate oldValue, LocalDate newValue) {
				fillRoomBox();
			}
		});
		tf.textProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				fillRoomBox();
			}
			
		});
		
		members.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
					addToListView(newValue);
				}
		});
		
		groups.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
					ArrayList<String> members = gdb.getGroupMembers(newValue);
					for (String name: members) {
						addToListView(name);
					}
				}
		});
		
		appointmentContainer.add(members, 1, 5);
		appointmentContainer.add(groups, 1, 6);
		
		StackPane hb = new StackPane();
		HBox sp = new HBox();
		
		
		sp.getChildren().addAll(tf, rooms);
		hb.getChildren().add(sp);
		sp.setAlignment(Pos.CENTER);
		appointmentContainer.add(hb, 1, 7);
		
		Button addEvent = new Button("Create Event");
		addEvent.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent arg0) {
				createEvent(arg0);
			}
		});
		appointmentContainer.add(addEvent, 2, 8);
		
	}
	
	@FXML
	public void navigateBack(MouseEvent e) {
		ScreenNavigator.loadVista(ScreenNavigator.SCREEN_CALENDAR);
	}
	
	
	@FXML
	public void fillRoomBox() {
		if (dateField.getValue() == null) {
			roomList.clear();
			rooms.setItems(roomList);
			return;
		}
		
		if (!startField.getText().matches("[0-9]{2}:[0-9]{2}")) {
			roomList.clear();
			rooms.setItems(roomList);
			return;
		}
		if (!endField.getText().matches("[0-9]{2}:[0-9]{2}")) {
			roomList.clear();
			rooms.setItems(roomList);
			return;
		}
		if (tf.getText().length() == 0) {
			roomList.clear();
			rooms.setItems(roomList);
			return;
		}
		
		LocalDate date = dateField.getValue();
		String sDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		roomList = SessionData.availableRooms(sDate + " " +startField.getText(), sDate + " " +endField.getText(), tf.getText());
		rooms.setItems(roomList);
		
	}
	
	
	@FXML
	public void keyStroke(KeyEvent e) {
		System.out.println(e.getCharacter());
	}
	
	public void addToListView(String item) {
		if (!listViewList.contains(item)) {
			this.listViewList.add(item);
		}
	}
	
	public void removeFromListView(String item) {
		if (listViewList.contains(item)) {
			listViewList.remove(item);
		}
	}
	
	@FXML
	public void createEvent(ActionEvent e) {
		EventDatabaseHandler edb = new EventDatabaseHandler();
		
		String title = titleField.getText();
		if (title.length() < 1) {
			return;
		}
		
		LocalDate date = dateField.getValue();
		String sDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		String startTime = startField.getText();
		String endTime = endField.getText();
		assert startTime.matches("[0-9]{2}:[0-9]{2}");
		assert endTime.matches("[0-9]{2}:[0-9]{2}");
		if (startTime.compareTo(endTime) == 1) {
			return;
		}
		
		String start = sDate + " " +  startTime;
		String end = sDate + " " + endTime;
		String description = descriptionText.getText();
		if (description.length() == 0) {
			description = "";
		}
		
		String roomId = rooms.getValue();
		if (roomId.length() == 0) {
			return;
		}
		String owner = SessionData.username;
		String[] data = {title, owner, start, end, description, roomId};
		String eventID = edb.add(data);
		
		for (String username: listViewList) {
			username = username.split("<")[0];
			if (edb.addPerson(eventID, username)) {
				System.out.println("added " + username + " to event:" + eventID);
			} else {
				System.out.println(username + "could not be added to event:" + eventID);
			}
			
			
		}
		
		SessionData.message = "New appointment created!";
		ScreenNavigator.loadVista(ScreenNavigator.SCREEN_CALENDAR);
	}

}
