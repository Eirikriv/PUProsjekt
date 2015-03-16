package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class EventController implements Initializable {
	@FXML private StackPane screenHolder;
	@FXML private GridPane grid;
	@FXML private HBox titleBox;
	@FXML private HBox createdBox;
	@FXML private Button backButton;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String eventID = SessionData.id;
		core.Event e = new core.Event(eventID);
		Label title = new Label(e.getName());
		Label createdBy = new Label(e.getOwner());
		Label start = new Label(e.getStart());
		Label end = new Label(e.getEnd());
		Label desc = new Label(e.getDesc());
		Label room = new Label(e.getRoom());
		String in = "";
		ArrayList<String> ins = e.getInvited();
		for (int i = 0; i < ins.size(); i++) {
			if (i != 0)
				in += ", ";
			in += ins.get(i);
		}
		Label invited = new Label(in);
		String p = "";
		ArrayList<String> ps = e.getParticipants();
		for (int i = 0; i < ps.size(); i++) {
			if (i != 0)
				p += ", ";
			p += ps.get(i);
		}
		Label participants = new Label(p);
		String d = "";
		ArrayList<String> ds = e.getDeclined();
		for (int i = 0; i < ds.size(); i++) {
			if (i != 0)
				d += ", ";
			d += ds.get(i);
		}
		Label declined = new Label(d);
		Label[] list = new Label[]{start, end, desc, room, invited, participants, declined};
		
		title.setFont(new Font("Arial", 25));
		titleBox.getChildren().add(title);
		titleBox.setAlignment(Pos.CENTER);
		
		createdBox.getChildren().add(createdBy);
		createdBox.setAlignment(Pos.CENTER);
		
		
		for (int i = 0; i < list.length; i++) {
			StackPane sp = new StackPane();
			sp.getChildren().add(list[i]);
			StackPane.setAlignment(list[i], Pos.CENTER_LEFT);
			grid.add(sp, 1, i);
		}
		StackPane.setAlignment(desc, Pos.TOP_LEFT);
		StackPane.setMargin(desc, new Insets(5,0,0,0));
		
		if(!SessionData.person.hasDeclined(SessionData.id)) {
			Button decline = new Button("Decline");
			decline.setTextFill(Paint.valueOf("0x970000"));
			screenHolder.getChildren().add(decline);
			StackPane.setAlignment(decline, Pos.BOTTOM_CENTER);
			StackPane.setMargin(decline, new Insets(0, 0, 30, 50));
			decline.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					SessionData.person.declineInvitation(SessionData.id);
					SessionData.allNotifications = SessionData.person.getNotifications();
					ScreenNavigator.loadVista(ScreenNavigator.SCREEN_EVENT);
				}
			});
			
			
		} else if(!SessionData.person.hasAccepted(SessionData.id)){
			Button accept = new Button("Accept");
			accept.setTextFill(Paint.valueOf("0x008920"));
			screenHolder.getChildren().add(accept);
			StackPane.setAlignment(accept, Pos.BOTTOM_CENTER);
			StackPane.setMargin(accept, new Insets(0,100,30,0));
			accept.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					SessionData.person.acceptInvitation(SessionData.id);
					SessionData.allNotifications = SessionData.person.getNotifications();
					ScreenNavigator.loadVista(ScreenNavigator.SCREEN_EVENT);
				}
			});
			if (SessionData.person.isVisible(SessionData.id)) {
				Button hide = new Button("Hide event");
				screenHolder.getChildren().add(hide);
				StackPane.setAlignment(hide, Pos.BOTTOM_CENTER);
				StackPane.setMargin(hide, new Insets(0, 0, 30, 50));
				hide.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						SessionData.person.changeVisibility(SessionData.id, 0);
						ScreenNavigator.loadVista(ScreenNavigator.SCREEN_EVENT);
					}
				});
			} else {
				Button unhide = new Button("Unhide event");
				screenHolder.getChildren().add(unhide);
				StackPane.setAlignment(unhide, Pos.BOTTOM_CENTER);
				StackPane.setMargin(unhide, new Insets(0, 0, 30, 50));
				unhide.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						SessionData.person.changeVisibility(SessionData.id, 1);
						ScreenNavigator.loadVista(ScreenNavigator.SCREEN_EVENT);
						
					}
				});
			}
		}
		
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				ScreenNavigator.loadVista(SessionData.prevScreen);
			}
		});
	}
}
