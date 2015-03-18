package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

import core.Event;
import database.EventDatabaseHandler;
import database.PersonDatabaseHandler;
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
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
	@FXML private VBox calBox;
	@FXML private AnchorPane eventContainer;
	@FXML private GridPane eventGrid;
	@FXML private TabPane tabPane;
	@FXML private Tab groupTab;
	@FXML private Tab nTab;
	@FXML private GridPane nGrid;
	
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
			SessionData.eventTab = false;
			tabPane.getSelectionModel().select(2);
		} else if (SessionData.nTab) {
			SessionData.nTab = false;
			tabPane.getSelectionModel().select(3);
		}
		String name = "";
		if (SessionData.allNotifications.size() > 0) {
			name = nTab.getText() + " (" +SessionData.allNotifications.size()+ ")";
		} else {
			name = nTab.getText();
		}
		nTab.setText(name);
		message.setText(SessionData.message);
		SessionData.allEvents = SessionData.person.getCalendar().updateCalendar();
		SessionData.allVisibleEvents = SessionData.person.getCalendar().updateCalendarToVisible();
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
				final Label itemText = new Label(eventInfo[x]);
				if (x == 0) {
					boolean isVisible = false;
					for (Event e: SessionData.allVisibleEvents) {
						if (e.getEventID().equals(event.getEventID())){
							isVisible = true;
						}
					}
					if (!isVisible) {
						itemText.setText(eventInfo[x] + " [Hidden]");
					}
					itemText.setUnderline(true);
					itemText.setOnMouseEntered(new EventHandler<MouseEvent>() {
						public void handle(MouseEvent event) {
							itemText.setTextFill(Color.web("0x949494"));
						}
					});
					itemText.setOnMouseExited(new EventHandler<MouseEvent>() {
						public void handle(MouseEvent event) {
							itemText.setTextFill(Paint.valueOf("black"));
						}
					});
					itemText.setOnMouseClicked(new EventHandler<MouseEvent>() {
						public void handle(MouseEvent event1) {
							SessionData.id = event.getEventID();
							SessionData.eventTab = true;
							SessionData.prevScreen = ScreenNavigator.SCREEN_CALENDAR;
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
		try {
			groupTab.setContent((Node) FXMLLoader.load(getClass().getResource(ScreenNavigator.SCREEN_GROUP)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int x = 0;
		if (SessionData.allNotifications.size() == 0) {
			StackPane sp = new StackPane();
			Label l = new Label("You dont have any unseen notifications");
			sp.getChildren().add(l);
			nGrid.add(sp, 0, 0);
		}
		x = 1;
		for (final core.Notification n: SessionData.allNotifications) {
			StackPane sp1 = new StackPane();
			final Label eventName = new Label(n.getEvent().getName());
			eventName.setUnderline(true);
			eventName.setOnMouseEntered(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					eventName.setTextFill(Color.web("0x949494"));
				}
			});
			eventName.setOnMouseExited(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					eventName.setTextFill(Paint.valueOf("black"));
				}
			});
			eventName.setOnMouseClicked(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					SessionData.id = n.getEvent().getEventID();
					SessionData.nTab = true;
					SessionData.prevScreen = ScreenNavigator.SCREEN_CALENDAR;
					ScreenNavigator.loadVista(ScreenNavigator.SCREEN_EVENT);
				}
			});
			sp1.getChildren().add(eventName);
			sp1.setAlignment(Pos.CENTER_LEFT);
			sp1.setPadding(new Insets(0,0,0,20));
			StackPane sp0 = new StackPane();
			Label message = new Label(n.getMessage());
			HBox buttons = new HBox();
			sp0.getChildren().add(message);
			sp0.setAlignment(Pos.CENTER_LEFT);
			
			if (n.getMessage().compareTo("You were added to the event") == 0) {
				StackPane bAccept = new StackPane();
				bAccept.setMinSize(100, 30);
				Button accept = new Button("accept");
				accept.setOnAction(new EventHandler<ActionEvent>() {
					@Override public void handle(ActionEvent e) {
						SessionData.person.acceptInvitation(n.getEvent().getEventID());
						SessionData.nTab = true;
						SessionData.allNotifications = SessionData.person.getNotifications(); 
						ScreenNavigator.loadVista(ScreenNavigator.SCREEN_CALENDAR);
					}
				});
				accept.setTextFill(Paint.valueOf("0x008920"));
				bAccept.getChildren().add(accept);
				StackPane bDecline = new StackPane();
				Button decline = new Button("decline");
				decline.setOnAction(new EventHandler<ActionEvent>() {
					@Override public void handle(ActionEvent e) {
						SessionData.person.declineInvitation(n.getEvent().getEventID());
						SessionData.nTab = true;
						SessionData.allNotifications = SessionData.person.getNotifications(); 
						ScreenNavigator.loadVista(ScreenNavigator.SCREEN_CALENDAR);
					}
				});
				
				decline.setTextFill(Paint.valueOf("0x970000"));
				bDecline.getChildren().add(decline);
				buttons.getChildren().addAll(sp1, bAccept, bDecline);
				HBox.setMargin(buttons, new Insets(20));
			}
			
			nGrid.addRow(x, buttons, sp0);
			x++;
		}
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
		
		final Calendar weekCal = Calendar.getInstance();
		weekCal.setTime(calStart.getTime());
		for (int x = 1; x<7; x++) {
			StackPane z = new StackPane();
			z.setMinSize(50, 50);
			final Label l = new Label(Integer.toString(weekCal.get(Calendar.WEEK_OF_YEAR)));
			l.setUnderline(true);
			l.setOnMouseEntered(new EventHandler<MouseEvent>() {
				@Override public void handle(MouseEvent arg0) {
					l.setTextFill(Color.web("0x949494"));
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
			final int weekIndex = 7+x-1;
			for (int y = 1; y<8; y++) {
				final StackPane z = new StackPane();
				if (calStart.before(cal) || (calStart.after(calEnd))) {
					z.setBackground(new Background(new BackgroundFill(Color.web("0xECECEC"), null, null), null));
					z.setOnMouseEntered(new EventHandler<MouseEvent>(){
						public void handle(MouseEvent event) {
							z.setBackground(new Background(new BackgroundFill(Color.web("0xF2F2F2"), null, null), null));
						}
					});
					z.setOnMouseExited(new EventHandler<MouseEvent>() {
						public void handle(MouseEvent event) {
							z.setBackground(new Background(new BackgroundFill(Color.web("0xECECEC"), null, null), null));
						}
					});
				} else {
					z.setBackground(new Background(new BackgroundFill(Color.web("0xD1EFFF"), null, null), null));
					z.setOnMouseEntered(new EventHandler<MouseEvent>() {
						public void handle(MouseEvent event) {
							z.setBackground(new Background(new BackgroundFill(Color.web("0xDEF3FF"), null, null), null));
						}
					});
					z.setOnMouseExited(new EventHandler<MouseEvent>() {
						public void handle(MouseEvent event) {
							z.setBackground(new Background(new BackgroundFill(Color.web("0xD1EFFF"), null, null), null));
						}
					});
				}
				Label l = new Label(Integer.toString(calStart.get(Calendar.DATE)));
				StackPane.setAlignment(l, Pos.TOP_LEFT);
				z.getChildren().add(l);
				z.setMinSize(80, 50);
				z.setOnMousePressed(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent event) {
						StackPane s = (StackPane)gp.getChildren().get(weekIndex);
						Label l = (Label)s.getChildren().get(0);
						SessionData.currentWeek = l.getText(); 
						ScreenNavigator.loadVista(ScreenNavigator.SCREEN_WEEK);
					}
				});
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
	
	public static void getAllPeople() {
		try {
			ArrayList<core.Person> personNames = core.Program.getAllPersons();
			ArrayList<String> peoples = new ArrayList<String>();
			for (int x=0; x<personNames.size();x++){
				peoples.add(personNames.get(x).getPrimaryKey() + "<" + personNames.get(x).getName() + ">");
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
	
	public void logout() {
		SessionData.username = null;
		SessionData.person = null;
		SessionData.allEvents = null;
		SessionData.allGroups = null;
		SessionData.allMembers = null;
		SessionData.allNotifications = null;
		SessionData.allRooms = null;
		SessionData.currentWeek = null;
		SessionData.cal = null;
		SessionData.eventTab = false;
		SessionData.nTab = false;
		SessionData.id = null;
		SessionData.message = null;
		SessionData.prevScreen = null;
		ScreenNavigator.loadVista(ScreenNavigator.SCREEN_LOGIN);
	}
}