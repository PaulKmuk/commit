package pl.commit.connection.component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import pl.commit.connection.ConnectionApplication;
import pl.commit.connection.controllers.DashboardContentConnectionsController;
import pl.commit.connection.controllers.DashboardContentConnectionsTitledPaneController;
import pl.commit.connection.model.ConnectionData;

import java.io.IOException;

public class TitledPaneConnection {

   public static void show(VBox vBox, ConnectionData connectionData, DashboardContentConnectionsController parentController){
      try {
         FXMLLoader loader = new FXMLLoader(ConnectionApplication.class.getResource("dashboard_content_connections_titledPane.fxml"));
         Node node = loader.load();

         //  Przekazanie obiektu connectionData do controllera
         DashboardContentConnectionsTitledPaneController controller = loader.getController();
         controller.setParentController(parentController);
         controller.setConnectionData(connectionData);


         vBox.getChildren().add(node);
         VBox.setVgrow(node, Priority.ALWAYS);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
}
