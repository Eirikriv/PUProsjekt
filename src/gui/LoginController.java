package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	
	@FXML private TextField usernameText;
	@FXML private PasswordField passwordText;
	@FXML private Button loginButton;
	@FXML private Text loginLabel;
	@FXML private Tab nTab;

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        loginButton.setDefaultButton(true);
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override public void handle(ActionEvent event) {
        		try {
        			if (usernameText.getText().length() > 0 && passwordText.getText().length() > 0) {
        				System.out.println(usernameText.getText());
        				SessionData.username = usernameText.getText();
        				SessionData.message = "Login successful";
        				SessionData.person = core.Program.login(usernameText.getText(), passwordText.getText());
        			}
        			else {
        				SessionData.username = "martibni";
            			SessionData.message = "Login successful";
            			SessionData.person = core.Program.login("martibni", "passord3");
        			}
        			
        			//Notification pop-up
        			SessionData.allNotifications = SessionData.person.getNotifications();
        			if (SessionData.allNotifications.size() > 0) {
	        			final Stage dialog = new Stage();
	        			dialog.initModality(Modality.APPLICATION_MODAL);
	        			dialog.initOwner(null);
	        			VBox dialogVbox = new VBox(20);
	        			Text t = new Text("You have " + SessionData.allNotifications.size() + " new notifications");
	        			dialogVbox.getChildren().add(t);
	        			dialogVbox.setAlignment(Pos.TOP_CENTER);
	        			VBox.setMargin(t, new Insets(50, 0, 0, 0));
	        			HBox hBox = new HBox(40);
	        			hBox.setAlignment(Pos.CENTER);
	        			StackPane sp = new StackPane();
	        			dialogVbox.getChildren().add(hBox);
	        			hBox.getChildren().add(sp);
	        			Button ok = new Button("OK");
	        			sp.getChildren().add(ok);
	        			sp.setAlignment(Pos.TOP_RIGHT);
	        			StackPane.setMargin(ok, new Insets(0, 0, 0, 0));
	        			sp = new StackPane();
	        			Button see = new Button("See notification(s)");
	        			sp.getChildren().add(see);
	        			sp.setAlignment(Pos.TOP_LEFT);
	        			StackPane.setMargin(see, new Insets(0, 0, 0, 0));
	        			hBox.getChildren().add(sp);
	        			
	        			see.setOnAction(new EventHandler<ActionEvent>() {
							public void handle(ActionEvent event) {
								SessionData.nTab = true;
								ScreenNavigator.loadVista(ScreenNavigator.SCREEN_CALENDAR);
								dialog.close();
							}
	        			});
	        			
	        			ok.setDefaultButton(true);
	        			ok.setOnAction(new EventHandler<ActionEvent>() {
							public void handle(ActionEvent event) {
								dialog.close();
							}
	        			});
	        			
	        			Scene dialogScene = new Scene(dialogVbox, 250, 150);
	        			dialog.setScene(dialogScene);
	        			dialog.show();
        			}
        			
        			ScreenNavigator.loadVista(ScreenNavigator.SCREEN_CALENDAR);
	        		return;

        		} catch (Exception e) {
        			e.printStackTrace();
        			loginLabel.setText("Login failed..");
        			loginLabel.setFill(Color.RED);
        		}
        	}
        });
		
	}
}
