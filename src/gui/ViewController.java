package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ListIterator;
import java.util.Locale;
import java.util.ResourceBundle;

import core.Event;
import database.EventDatabaseHandler;
import database.PersonDatabaseHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class ViewController implements Initializable {
	
	@FXML private Button createGroupButton;
	@FXML private VBox leftContainer;
	@FXML private VBox rightContainer;
	@FXML private VBox calBox;
	@FXML private AnchorPane eventContainer;
	@FXML private GridPane eventGrid;
	@FXML private TabPane tabPane;
	@FXML private Tab eTab;
	
	public String username;
	public Stage stage;
	public GridPane gp = new GridPane();
	public Label monthText = new Label();
	Label message = new Label();
	
	public Calendar cal = Calendar.getInstance();
	PersonDatabaseHandler pdb = new PersonDatabaseHandler();
	EventDatabaseHandler edb = new EventDatabaseHandler();

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		if (SessionData.eventTab) {
			eTab.isSelected();
		}
		message.setText(SessionData.message);
		SessionData.allEvents = SessionData.person.getCalendar().updateCalendar();
		int rowCount = 1;
		int columnCount = 0;
		SessionData.cal = this.cal;
		getAllPeople();
		getAllGroups();
		getAllRooms();
		
		if (SessionData.person.isAdmin()) {
			Tab tab = new Tab();
			try {
				tab.setContent((Node) FXMLLoader.load(getClass().getResource(ScreenNavigator.SCREEN_ADMIN)));
			} catch (IOException e) {
				e.printStackTrace();
			}
			tab.setText("Admin");
			tabPane.getTabs().add(tab);
		}
		
		for (final Event event: SessionData.allEvents) {
			String[] eventInfo = {event.getName(), event.getDesc(), event.getStart(), event.getEnd()};
			for (int x = 0; x<4; x++) {
				StackPane itemContainer = new StackPane();
				Label itemText = new Label(eventInfo[x]);
				if (x == 0) {
					itemText.setOnMouseClicked(new EventHandler<MouseEvent>() {
						public void handle(MouseEvent event1) {
							SessionData.id = event.getEventID();
							SessionData.eventTab = true;
							SessionData.prevScreen = ScreenNavigator.MAIN;
							ScreenNavigator.loadVista(ScreenNavigator.SCREEN_EVENT);
						}
					});
				}
				itemContainer.getChildren().add(itemText);
				eventGrid.add(itemContainer, columnCount, rowCount);
				columnCount += 1;
			}
			columnCount = 0;
			rowCount += 1;
		}
		
		this.username = SessionData.username;
		fillCalendar(calBox);

		FilterComboBox fcb = new FilterComboBox(SessionData.allGroups);
		
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
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		gp.getChildren().clear();
		
		String[] daystext = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
		for (int x=1; x<8; x++) {
			StackPane dayContainer = new StackPane();
			dayContainer.getChildren().add(new Label(daystext[x-1]));
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
		
		Calendar weekCal = Calendar.getInstance();
		weekCal.setTime(calStart.getTime());
		for (int x = 1; x<7; x++) {
			StackPane z = new StackPane();
			z.setMinSize(50, 50);
			final Label l = new Label(Integer.toString(weekCal.get(Calendar.WEEK_OF_YEAR)));
			l.setUnderline(true);
			l.setOnMouseEntered(new EventHandler<MouseEvent>() {
				@Override public void handle(MouseEvent arg0) {
					l.setTextFill(Paint.valueOf("blue"));
				}
			});
			
			l.setOnMouseExited(new EventHandler<MouseEvent>() {
				@Override public void handle(MouseEvent arg0) {
					l.setTextFill(Paint.valueOf("black"));
				}
			});
			
			z.getChildren().add(l);
			gp.add(z, 0, x);
			weekCal.add(Calendar.WEEK_OF_YEAR, 1);
			
			z.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override public void handle(MouseEvent arg0) {
					StackPane s = (StackPane)arg0.getSource();
					Label l  = (Label)s.getChildren().get(0);
					SessionData.currentWeek = l.getText();
					ScreenNavigator.loadVista(ScreenNavigator.SCREEN_WEEK);
				}
			});
		}

		for (int x = 1; x<7; x++) {
			for (int y = 1; y<8; y++) {
				StackPane z = new StackPane();
				if (calStart.before(cal) || (calStart.after(calEnd))) {
					z.setBackground(new Background(new BackgroundFill(Color.web("0xECECEC"), null, null), null));
				} else {
					z.setBackground(new Background(new BackgroundFill(Color.web("0xD1EFFF"), null, null), null));
				}
				Label l = new Label(Integer.toString(calStart.get(Calendar.DATE)));
				StackPane.setAlignment(l, Pos.TOP_LEFT);
				z.getChildren().add(l);
				z.setMinSize(80, 50);
				gp.add(z, y, x);
				
				calStart.add(Calendar.DATE, 1);
			}
		}
		
		gp.setHgap(2);
		gp.setVgap(2);
	}
	
	public void navigateback(ActionEvent e) {
		System.out.println("clicked");
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
		sp.getChildren().addAll(username, showCal, message);
		HBox titleBox = new HBox();
		titleBox.getChildren().addAll(bLeft, monthText, bRight);
		calsp.getChildren().add(titleBox);
		
		StackPane.setAlignment(message, Pos.CENTER);
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
		StackPane buttonContainer = new StackPane();
		buttonContainer.setMinHeight(100);
		Button createEvent = new Button("New event");
		StackPane.setAlignment(createEvent, Pos.CENTER_RIGHT);
		StackPane.setMargin(createEvent, new Insets(0,70,0,0));
		buttonContainer.getChildren().add(createEvent);
		box.getChildren().add(buttonContainer);
		
		
		createEvent.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				eventContainer.getChildren().clear();
				try {
					eventContainer.getChildren().add((Node) FXMLLoader.load(getClass().getResource(ScreenNavigator.SCREEN_NEW_APPOINTMENT)));
					tabPane.getSelectionModel().select(2);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
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
	
	
	public void addToListBox(String name, boolean checked, final VBox list) {
		HBox hbox = new HBox();
		CheckBox cb = new CheckBox();
		cb.selectedProperty().set(true);
		final Button b = new Button("remove");
		b.setStyle("-fx-color:red; -fx-");
		b.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				Label l = (Label)b.getParent().getChildrenUnmodifiable().get(1);
				removeFromListBox(l.getText(), list);
			}
		});
		
		hbox.getChildren().addAll(cb, new Label(name), b);
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
		leftContainer.getChildren().add(new FilterComboBox(SessionData.allGroups));
		leftContainer.getChildren().add(b);
		
	}
	
	public static void getAllPeople() {
		try {
			System.out.println("a");
			ArrayList<core.Person> personNames = core.Program.getAllPersons();
			System.out.println("b");
			ArrayList<String> peoples = new ArrayList<String>();
			for (int x=0; x<personNames.size();x++){
				peoples.add(personNames.get(x).getName() + "<" + personNames.get(x).getPrimaryKey() + ">");
			}
			ObservableList<String> people = FXCollections.observableArrayList(peoples);
			SessionData.allMembers = people;
		} catch (Exception e) {
			
		}
		
	}
	
	public static void getAllRooms() {
		ArrayList<core.Room> room = core.Program.getAllRooms();
		ArrayList<String> roomNames = new ArrayList<String>();
		for (int x=0; x<room.size(); x++) {
			roomNames.add(room.get(x).getPrimaryKey() + " [" +room.get(x).getCapacity() + "]");
		}
		ObservableList<String> rooms = FXCollections.observableArrayList(roomNames);
		SessionData.allRooms = rooms;
	}
	
	public static void getAllGroups() {
		ArrayList<GroupData> groups = new ArrayList<GroupData>();
		ArrayList<String> groupNames = SessionData.person.getAllGroups();
		
		for (String g: groupNames) {
			String sLeft = g.split(":")[0];
			String sRight = g.split(":")[1];
			groups.add(new GroupData(sLeft, sRight));
		}
		
		
		ObservableList<String> people = FXCollections.observableArrayList(groupNames);
		SessionData.allGroups = people;
	}
	
	
}