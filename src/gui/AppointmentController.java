package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import database.EventDatabaseHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
	@FXML private DatePicker dateField;
	@FXML private TextField startField;
	@FXML private TextField endField;
	@FXML private ListView<String> listMembersField;
	@FXML private Button searchRoomsButton;
	@FXML private TextArea descriptionText;
	
	TextField tf = new TextField();
	private ObservableList<String> listViewList = FXCollections.observableArrayList();
	private ObservableList<String> roomList = FXCollections.observableArrayList();
	private FilterComboBox members = new FilterComboBox(SessionData.allMembers);
	private FilterComboBox groups = new FilterComboBox(SessionData.allGroups);
	private FilterComboBox rooms = new FilterComboBox(roomList);
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listMembersField.setItems(listViewList);
		
		members.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
					addToListView(newValue);
				}
		});
		
		appointmentContainer.add(members, 1, 4);
		appointmentContainer.add(groups, 1, 5);
		
		StackPane hb = new StackPane();
		HBox sp = new HBox();
		
		sp.getChildren().addAll(tf, rooms);
		hb.getChildren().add(sp);
		sp.setAlignment(Pos.CENTER);
		appointmentContainer.add(hb, 1, 6);
		
	}
	
	public void navigateBack(MouseEvent e) {
		ScreenNavigator.loadVista(ScreenNavigator.SCREEN_CALENDAR);
	}
	
	public void fillRoomBox(ActionEvent e) {
		LocalDate date = dateField.getValue();
		String sDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		roomList = SessionData.availableRooms(sDate + " " +startField.getText(), sDate + " " +endField.getText(), tf.getText());
		rooms.setItems(roomList);
		
	}
	
	public void keyStroke(KeyEvent e) {
		System.out.println(e.getCharacter());
	}
	
	public void addToListView(String item) {
		if (!listViewList.contains(item)) {
			this.listViewList.add(item);
		}
	}
	
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
		if (startTime.compareTo(endTime) == -1) {
			System.out.println("startime compare endTime");
			return;
		}
		
		String start = sDate + " " +  startTime;
		String end = sDate + " " + endTime;
		String description = descriptionText.getText();
		if (description.length() == 0) {
			description = null;
		}
		
		String roomId = rooms.getValue();
		if (roomId.length() == 0) {
			return;
		}
		
		String[] data = {title, startTime, endTime, description, roomId};
		
		edb.add(data);
	}

}
