package GUI;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {
	
	@FXML private TextField usernameText;
	@FXML private PasswordField passwordText;
	@FXML private Button newButton;
	@FXML private Button loginButton;

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		assert usernameText != null : "fx:id=\"usernameText\" was not injected: check your FXML file 'LoginScreen.fxml'.";
        assert passwordText != null : "fx:id=\"passwordText\" was not injected: check your FXML file 'LoginScreen.fxml'.";
        assert newButton != null : "fx:id=\"newButton\" was not injected: check your FXML file 'LoginScreen.fxml'.";
        assert loginButton != null : "fx:id=\"loginbutton\" was not injected: check your FXML file 'LoginScreen.fxml'.";
        
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override public void handle(ActionEvent event) {
        		System.out.println("Clicked login button");
        		System.out.println("username: " + usernameText.getText());
        		System.out.println("password: " + passwordText.getText());
        		
        		ScreenNavigator.loadVista(ScreenNavigator.SCREEN_CALENDAR);
        	}
        });
        
        newButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override public void handle(ActionEvent event) {
        		System.out.println("Clicked new button");
        	}
        });
		
	}
}