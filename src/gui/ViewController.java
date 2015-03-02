package gui;

import java.net.URL;
import java.util.Calendar;
import java.util.ListIterator;
import java.util.Locale;
import java.util.ResourceBundle;

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
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ViewController implements Initializable {
	
	@FXML private Button createGroupButton;
	@FXML private VBox leftContainer;
	@FXML private VBox rightContainer;
	@FXML private VBox calBox;

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		assert createGroupButton != null : "fx:id=\"createGroupButton\" was not injected: check your FXML file 'CalendarScreen.fxml'.";
		assert leftContainer != null : "fx:id=\"leftContainer\" was not injected: check your FXML file 'CalendarScreen.fxml'.";
		assert rightContainer != null : "fx:id=\"rightContainer\" was not injected: check your FXML file 'CalendarScreen.fxml'.";
		
		
		
		fillCalendar(calBox);
		
		ObservableList<String> groupItems = FXCollections.observableArrayList("Martin", "Alexander", "Anders");
		FilterComboBox fcb = new FilterComboBox(groupItems);
		
		leftContainer.getChildren().add(1, fcb);
		
		fcb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
				public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
					addToListBox(newValue, true, rightContainer);
				}
		});
		
		createGroupButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				initCreateGroup();
				FilterComboBox fcb = (FilterComboBox) leftContainer.getChildren().get(3);
				fcb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
					public void changed(ObservableValue<? extends String> observable,
							String oldValue, String newValue) {
							addToListBox(newValue, true, rightContainer);
							if (rightContainer.getChildren().size() >= 2) {
								
							}
						}
				});
			}
		});
		
	}

	public void fillCalendar(VBox box) {
		Calendar cal = Calendar.getInstance();
		String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
		Label monthText = new Label(month);
		box.getChildren().add(monthText);
		VBox.setMargin(monthText, new Insets((double)15));
		
		
	}
	
	public void removeFromListBox(String name, VBox list) {
		ListIterator<Node> li = list.getChildren().listIterator();
		while (li.hasNext()) {
			HBox box = (HBox) li.next();
			Label l = (Label) box.getChildren().get(1);
			if (l.getText().compareTo(name) == 0) {
				list.getChildren().remove(box);
				return;
			}
		}
	}
	
	
	public void addToListBox(String name, boolean checked, VBox list) {
		HBox hbox = new HBox();
		CheckBox cb = new CheckBox();
		cb.selectedProperty().set(true);
		Button b = new Button("remove");
		b.setStyle("-fx-color:red; -fx-");
		b.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				Label l = (Label)b.getParent().getChildrenUnmodifiable().get(1);
				removeFromListBox(l.getText(), list);
			}
		});
		
		hbox.getChildren().add(cb);
		hbox.getChildren().add(new Label(name));
		hbox.getChildren().add(b);
		
		rightContainer.getChildren().add(hbox);
		
	}
	
	public void initCreateGroup() {
		leftContainer.getChildren().clear();
		rightContainer.getChildren().clear();
		
		Button b = new Button("Create Group");
		b.setVisible(false);
		
		leftContainer.getChildren().add(new Label("Group name"));
		leftContainer.getChildren().add(new TextField());
		leftContainer.getChildren().add(new Label("Add members"));
		leftContainer.getChildren().add(new FilterComboBox(getAllPeople()));
		leftContainer.getChildren().add(b);
		
	}
	
	public ObservableList<String> getAllPeople() {
		//change this to fetch from db
		String[] fetchedPeople = {"Martin", "Alex", "Cecilie"};
		ObservableList<String> people = FXCollections.observableArrayList(fetchedPeople);
		return people;
	}
}