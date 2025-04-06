package pl.commit.connection.pages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import pl.commit.connection.ConnectionApplication;

import java.io.IOException;

public class Dashboard {

    public void show(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(ConnectionApplication.class.getResource("dashboard.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("ERPwincash - Dashboard");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
