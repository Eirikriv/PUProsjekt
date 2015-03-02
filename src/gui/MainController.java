package GUI;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class MainController {

    @FXML private StackPane screenHolder;
 
    public void setVista(Node node) {
        screenHolder.getChildren().clear();
        screenHolder.getChildren().add(node);
    }
    
 
}