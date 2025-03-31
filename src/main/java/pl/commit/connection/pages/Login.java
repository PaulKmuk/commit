package pl.commit.connection.pages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.commit.connection.ConnectionApplication;

import java.io.IOException;

public class Login {

    public void show(Stage stage){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(ConnectionApplication.class.getResource("login_view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("ERPwincash");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
