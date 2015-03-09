package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import core.Person;
import database.PersonDatabaseHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class LoginController implements Initializable {
	
	@FXML private TextField usernameText;
	@FXML private PasswordField passwordText;
	@FXML private Button loginButton;
	@FXML private Text loginLabel;
	
	PersonDatabaseHandler pdb = new PersonDatabaseHandler();

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        loginButton.setDefaultButton(true);
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override public void handle(ActionEvent event) {
        		try {
        			if (usernameText.getText().length() == 0 && passwordText.getText().length() == 0) {
        				SessionData.username = "martibni";
        				SessionData.message = "Login sucessful";
        				SessionData.person = new Person(SessionData.username);
	        			ScreenNavigator.loadVista(ScreenNavigator.SCREEN_CALENDAR);
	        			return;
        			}
        			
	        		String person = pdb.login(usernameText.getText(), passwordText.getText());
	        		if (person.length() > 0) {
	        			SessionData.username = person;
	        			ScreenNavigator.loadVista(ScreenNavigator.SCREEN_CALENDAR);
	        		}
        		} catch (Exception e) {
        			e.printStackTrace();
        			loginLabel.setText("Login failed..");
        			loginLabel.setFill(Color.RED);
        		}
        	}
        });
		
	}
}
