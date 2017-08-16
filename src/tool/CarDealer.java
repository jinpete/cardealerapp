package tool;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class CarDealer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        createDb();

        Parent root = FXMLLoader.load(getClass().getResource("/view/AutomobileTableView.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createDb() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        DbCreator.connectDb();
        DbCreator.createAutomobileTable();
        DbCreator.insertAutomobiles();
    }
}
