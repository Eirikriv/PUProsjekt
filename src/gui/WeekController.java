package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.GridPane;

public class WeekController implements Initializable {
	
	@FXML private GridPane weekGrid;
	@FXML private Button upButton;
	@FXML private Button downButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		weekGrid.setBorder(new Border(new BorderStroke(null, null, null, null, new Insets(15, 0, 0, 0))));
		
	}

}
