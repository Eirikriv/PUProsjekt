package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
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
	
	private ObservableList<String> listViewList = FXCollections.observableArrayList();
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		FilterComboBox members = new FilterComboBox(SessionData.allMembers);
		FilterComboBox groups = new FilterComboBox(SessionData.allGroups);
		FilterComboBox rooms = new FilterComboBox(SessionData.allRooms);
		listMembersField.setItems(listViewList);
		
		members.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
					addToListView(newValue);
					System.out.println(newValue);
					System.out.println(listViewList.size());
				}
		});
		
		appointmentContainer.add(members, 1, 3);
		appointmentContainer.add(groups, 1, 4);
		
		StackPane hb = new StackPane();
		HBox sp = new HBox();
		TextField tf = new TextField();
		
		sp.getChildren().addAll(tf, rooms);
		hb.getChildren().add(sp);
		sp.setAlignment(Pos.CENTER);
		appointmentContainer.add(hb, 1, 5);
		
	}
	
	public void navigateBack(MouseEvent e) {
		ScreenNavigator.loadVista(ScreenNavigator.SCREEN_CALENDAR);
	}
	
	public void keyStroke(KeyEvent e) {
		System.out.println(e.getCharacter());
	}
	
	public void addToListView(String item) {
		this.listViewList.add(item);
	}

}
