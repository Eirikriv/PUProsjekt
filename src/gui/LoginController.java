package gui;

import java.net.URL;
import java.util.ResourceBundle;
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

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        loginButton.setDefaultButton(true);
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override public void handle(ActionEvent event) {
        		try {
        			SessionData.username = "martibni";
        			SessionData.message = "Login sucessful";
        			System.out.println("a");
        			SessionData.person = core.Program.login("martibni", "password3");
        			System.out.println("b");
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
