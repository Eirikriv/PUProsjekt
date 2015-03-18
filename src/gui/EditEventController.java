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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class EditEventController implements Initializable{
	@FXML HBox titleBox;
	@FXML DatePicker dateDP;
	@FXML TextField startTF;
	@FXML TextField endTF;
	@FXML TextField descTF;
	@FXML TextField roomTF;
	@FXML ListView<String> invitedLW;
	
	private ObservableList<String> listViewList = FXCollections.observableArrayList();
	private ObservableList<String> roomList = FXCollections.observableArrayList();
	private FilterComboBox members = new FilterComboBox(SessionData.allMembers);
	private FilterComboBox rooms = new FilterComboBox(roomList);
	EventDatabaseHandler edb = new EventDatabaseHandler();
	GroupDatabaseHandler gdb = new GroupDatabaseHandler();

	public void initialize(URL location, ResourceBundle resources) {
		core.Event e = new core.Event(SessionData.id);
		invitedLW.setFocusTraversable(false);
		descTF.setFocusTraversable(false);
		members.setFocusTraversable(true);
		rooms.setFocusTraversable(true);
		Label title = new Label(e.getName());
		titleBox.getChildren().add(title);
		ArrayList<Object> eInfo = SessionData.eventInfo;
		dateDP.setValue((LocalDate)eInfo.get(0));
		startTF.setText((String) eInfo.get(1));
		endTF.setText((String)eInfo.get(2));
		descTF.setText((String)eInfo.get(3));
		rooms.setValue((String) eInfo.get(4));
		listViewList.addAll(e.getInvited());
		listViewList.addAll(e.getParticipants());
		listViewList.addAll(e.getDeclined());
		invitedLW.setItems(listViewList);
		
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
