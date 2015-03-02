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
        stage.setHeight(height);
        stage.setWidth(width);
        stage.centerOnScreen();
        
    }
    
    public void setStage(Stage stage) {
    	this.stage = stage;
    }
   
    
 
}