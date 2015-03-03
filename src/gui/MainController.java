package gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainController {

    @FXML private StackPane screenHolder;
    private Stage stage;
 
    public void setVista(Node node, double height, double width) {
        screenHolder.getChildren().clear();
        screenHolder.getChildren().add(node);
        sizeTo(height, width);
        
    }
    
    public void setStage(Stage stage) {
    	this.stage = stage;
    }
    
    public void sizeTo(double height, double width) {
    	stage.setHeight(height);
        stage.setWidth(width);
        stage.centerOnScreen();
    }
   
    
 
}