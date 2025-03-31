package pl.commit.connection;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.commit.connection.pages.Login;

import java.io.IOException;

public class ConnectionApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Login login = new Login();
        login.show(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}