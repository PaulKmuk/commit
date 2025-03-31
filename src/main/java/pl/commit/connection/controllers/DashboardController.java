package pl.commit.connection.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import pl.commit.connection.ConnectionApplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private VBox dashboard_content;

    public void loadFXML(String fxmlPath){
        try {
            FXMLLoader loader = new FXMLLoader(ConnectionApplication.class.getResource(fxmlPath));
            Node content = loader.load();
            dashboard_content.getChildren().setAll(content);
            VBox.setVgrow(content, Priority.ALWAYS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadFXML("dashboard_content_connections.fxml");
    }
}
