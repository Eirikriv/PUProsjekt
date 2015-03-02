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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class ViewController implements Initializable {
	
	@FXML private Button createGroupButton;
	@FXML private VBox leftContainer;
	@FXML private VBox rightContainer;
	@FXML private VBox calBox;
	
	public String username;
	public Stage stage;
	public GridPane gp = new GridPane();
	public Label monthText = new Label();
	
	public Calendar cal = Calendar.getInstance();

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		assert createGroupButton != null : "fx:id=\"createGroupButton\" was not injected: check your FXML file 'CalendarScreen.fxml'.";
		assert leftContainer != null : "fx:id=\"leftContainer\" was not injected: check your FXML file 'CalendarScreen.fxml'.";
		assert rightContainer != null : "fx:id=\"rightContainer\" was not injected: check your FXML file 'CalendarScreen.fxml'.";
		
		
		this.username = "Martin";
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
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public void setCalendarInfo() {
		this.monthText.setText(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " + cal.get(Calendar.YEAR));
		System.out.println(monthText.getWidth());
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		gp.getChildren().clear();
		
		String[] daystext = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
		for (int x=0; x<7; x++) {
			StackPane dayContainer = new StackPane();
			dayContainer.getChildren().add(new Label(daystext[x]));
			dayContainer.setMinSize(80, 30);
			gp.add(dayContainer, x, 0);
		}
		Calendar calStart = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();
		calStart.setTime(cal.getTime());
		calEnd.setTime(cal.getTime());
		calEnd.add(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH)-1);
		
		if (day == 1) {
			calStart.add(Calendar.DATE, -6);
		} else if (day == 2) {
			calStart.add(Calendar.DATE, -7);
		} else {
			calStart.add(Calendar.DATE, -day+2);
		}

		for (int x = 1; x<7; x++) {
			for (int y = 0; y<7; y++) {
				StackPane z = new StackPane();
				if (calStart.before(cal) || (calStart.after(calEnd))) {
					z.setBackground(new Background(new BackgroundFill(Paint.valueOf("red"), null, null), null));
				} else {
					z.setBackground(new Background(new BackgroundFill(Paint.valueOf("green"), null, null), null));
				}
				Label l = new Label(Integer.toString(calStart.get(Calendar.DATE)));
				StackPane.setAlignment(l, Pos.TOP_LEFT);
				z.getChildren().add(l);
				z.setMinSize(80, 50);
				gp.add(z, y, x);
				gp.setGridLinesVisible(true);
				
				calStart.add(Calendar.DATE, 1);
			}
		}
	}

	public void fillCalendar(VBox box) {
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		monthText.setMinWidth(90);
		monthText.setAlignment(Pos.CENTER);
		Button bLeft = new Button("<-");
		Button bRight = new Button("->");
		bLeft.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				cal.add(Calendar.MONTH, -1);
				setCalendarInfo();
				event.consume();
			}
		});
		bRight.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				cal.add(Calendar.MONTH, 1);
				setCalendarInfo();
				event.consume();
			}
		});
		Label username = new Label("user: " + this.username);
		Label showCal = new Label("show calendar\nfor : ");
		StackPane sp = new StackPane();
		StackPane calsp = new StackPane();
		sp.getChildren().addAll(username, showCal);
		HBox titleBox = new HBox();
		titleBox.getChildren().addAll(bLeft, monthText, bRight);
		calsp.getChildren().add(titleBox);
		
		StackPane.setAlignment(titleBox, Pos.CENTER);
		StackPane.setAlignment(username, Pos.TOP_LEFT);
		StackPane.setAlignment(showCal, Pos.TOP_RIGHT);
		StackPane.setMargin(username, new Insets((double)5));
		StackPane.setMargin(showCal, new Insets((double)5));
		
		box.getChildren().addAll(sp, calsp);

		titleBox.setAlignment(Pos.CENTER);
		gp.setGridLinesVisible(true);
		gp.setAlignment(Pos.CENTER);
		
		setCalendarInfo();
		calsp.setPadding(new Insets(15, 0, 0, 0));
		box.getChildren().add(gp);
		
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