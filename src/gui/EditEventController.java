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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class EditEventController implements Initializable{
	@FXML DatePicker dateDP;
	@FXML TextField startTF;
	@FXML TextField endTF;
	@FXML TextField descTF;
	@FXML TextField roomTF;
	@FXML ListView<String> invitedLW;
	@FXML HBox roomHbox;
	@FXML GridPane grid;
	@FXML Button backButton;
	@FXML Button update;
	@FXML TextField titleTF;
	@FXML HBox lwContainer;
	
	private ObservableList<String> listViewList = FXCollections.observableArrayList();
	private ObservableList<String> roomList = FXCollections.observableArrayList();
	private FilterComboBox members = new FilterComboBox(SessionData.allMembers);
	private FilterComboBox rooms = new FilterComboBox(roomList);
	EventDatabaseHandler edb = new EventDatabaseHandler();
	GroupDatabaseHandler gdb = new GroupDatabaseHandler();

	public void initialize(URL location, ResourceBundle resources) {
		final core.Event e = new core.Event(SessionData.id);
		invitedLW.setFocusTraversable(false);
		descTF.setFocusTraversable(false);
		members.setFocusTraversable(true);
		rooms.setFocusTraversable(true);
		ArrayList<Object> eInfo = SessionData.eventInfo;
		titleTF.setText(e.getName());
		dateDP.setValue((LocalDate)eInfo.get(0));
		String text = (String) eInfo.get(1);
		startTF.setText(text.split(" ")[1]);
		String text1 = (String) eInfo.get(2);
		endTF.setText(text1.split(" ")[1]);
		descTF.setText((String)eInfo.get(3));
		rooms.setValue((String) eInfo.get(4));
		listViewList.addAll(e.getInvited());
		listViewList.addAll(e.getParticipants());
		listViewList.addAll(e.getDeclined());
		invitedLW.setItems(listViewList);
		roomHbox.getChildren().add(rooms);
		StackPane sp = new StackPane();
		sp.getChildren().add(members);
		grid.add(sp, 1, 5);
		
		invitedLW.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0,
					String arg1,final String arg2) {
				if (lwContainer.getChildren().size() > 1) {
					lwContainer.getChildren().remove(0);
				}
				StackPane sp = new StackPane();
				Button remove = new Button("remove");
				remove.setMinWidth(70);
				sp.getChildren().add(remove);
				lwContainer.getChildren().add(0, sp);
				remove.setOnAction(new EventHandler<ActionEvent>() {
					@Override public void handle(ActionEvent arg0) {
						listViewList.remove(arg2);
						lwContainer.getChildren().remove(0);
					}
				});
			}
		});
		
		members.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue<? extends String> arg0,
					String arg1, String arg2) {
				if (!listViewList.contains(arg2)) {
					listViewList.add(arg2.split("<")[0]);
				}
			}
		});
		
		startTF.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0,
					String arg1, String arg2) {
				fillRoomBox();
			}
		});
		endTF.textProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue<? extends String> arg0,
					String arg1, String arg2) {
				fillRoomBox();
			}
			
		});
		descTF.textProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue<? extends String> arg0,
					String arg1, String arg2) {
				fillRoomBox();
			}
			
		});
		roomTF.textProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				fillRoomBox();
			}
			
		});
		
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				ScreenNavigator.loadVista(SessionData.prevScreen);
			}
		});
		update.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				String title = titleTF.getText();
				if (title.length() < 1) {
					return;
				}
				
				LocalDate date = dateDP.getValue();
				String sDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				
				String startTime = startTF.getText();
				String endTime = endTF.getText();
				assert startTime.matches("[0-9]{2}:[0-9]{2}");
				assert endTime.matches("[0-9]{2}:[0-9]{2}");
				if (startTime.compareTo(endTime) == 1) {
					return;
				}
				
				String start = sDate + " " +  startTime;
				String end = sDate + " " + endTime;
				
				String description = descTF.getText();
				if (description == null) {
					description = "";
				}

				String roomId = rooms.getValue();
				if (roomId.length() == 0) {
					return;
				}
				
				e.removeAllPersons();
				for (String username: listViewList) {
					username = username.split("<")[0];
					if (edb.addPerson(e.getEventID(), username)) {
						System.out.println("added " + username + " to event:" + e);
					} else {
						System.out.println(username + "could not be added to event:" + e);
					}	
				}
				e.editEvent(title, start, end, description, roomId);
				SessionData.allEvents = SessionData.person.getCalendar().getEvents();
				SessionData.allNotifications = SessionData.person.getNotifications();
				ScreenNavigator.loadVista(SessionData.prevScreen);
			}
		});
	}
	
	public void fillRoomBox() {
		if (dateDP.getValue() == null) {
			roomList.clear();
			rooms.setItems(roomList);
			return;
		}
		if (!startTF.getText().matches("[0-9]{2}:[0-9]{2}")) {
			roomList.clear();
			rooms.setItems(roomList);
			return;
		}
		if (!endTF.getText().matches("[0-9]{2}:[0-9]{2}")) {
			roomList.clear();
			rooms.setItems(roomList);
			return;
		}
		if (roomTF.getText().length() == 0) {
			roomList.clear();
			rooms.setItems(roomList);
			return;
		}
		LocalDate date = dateDP.getValue();
		String sDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		roomList = SessionData.availableRooms(sDate + " " +startTF.getText(), sDate + " " +endTF.getText(), roomTF.getText());
		rooms.setItems(roomList);
	}
}
