package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InventoryTableViewController {

    public void changeSceneButtonPushed(ActionEvent event) throws IOException {
        Parent parentScene = FXMLLoader.load(getClass().getResource("/view/AutomobileTableView.fxml"));
        Scene scene = new Scene(parentScene);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
