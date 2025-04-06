package pl.commit.connection.component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import pl.commit.connection.ConnectionApplication;
import pl.commit.connection.controllers.DashboardContentConnectionsController;
import pl.commit.connection.controllers.SingleConnectionController;
import pl.commit.connection.model.ConnectionData;

import java.io.IOException;

public class SingleConnection {

   public static void show(VBox vBox, ConnectionData.SingleConnectionData singleConnectionData, DashboardContentConnectionsController parentController) {
      try {
         FXMLLoader loader = new FXMLLoader(ConnectionApplication.class.getResource("single_connection.fxml"));
         Node node = loader.load();

         SingleConnectionController controller = loader.getController();
         controller.setParentController(parentController);
         controller.setSingleConnectionData(singleConnectionData);

         vBox.getChildren().add(node);
         VBox.setVgrow(node, Priority.ALWAYS);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
}
