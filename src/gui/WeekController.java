package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class WeekController implements Initializable {
	
	@FXML private GridPane weekGrid;
	@FXML private Button upButton;
	@FXML private Button downButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//weekGrid.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, null, null, new Insets(15, 15, 15, 15))));
		for (int x=0; x<8; x++) {
			StackPane sp = (StackPane)weekGrid.getChildren().get(x);
			sp.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, null, null, new Insets(0, 0, 0, 15))));
			
		}
	}

}
