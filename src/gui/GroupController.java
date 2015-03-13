package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import database.GroupDatabaseHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class GroupController implements Initializable {
	@FXML private ListView<String> listPeople;
	@FXML private VBox leftContainer;
	
	GroupDatabaseHandler gdb = new GroupDatabaseHandler();
	ObservableList<String> peopleInList = FXCollections.observableArrayList();
	
	@Override public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<String> groups = new ArrayList<String>(core.Program.getAllGroups(SessionData.username));
		ObservableList<String> observableGroups = FXCollections.observableArrayList(groups);
		FilterComboBox fcb = new FilterComboBox(observableGroups);
		
		leftContainer.getChildren().add(fcb);
		
		fcb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue<? extends String> arg0,
					String arg1, String arg2) {
				ArrayList<String> people = gdb.getGroupMembers(arg0.getValue().split(":")[0]);
				for (String s: people) {
					peopleInList.add(s);
				}
				listPeople.setItems(peopleInList);
			}
		});
	}

}
