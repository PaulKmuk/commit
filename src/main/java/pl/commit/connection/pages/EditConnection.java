package pl.commit.connection.pages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.commit.connection.ConnectionApplication;
import pl.commit.connection.controllers.DashboardContentConnectionsController;
import pl.commit.connection.controllers.DashboardEditConnectionController;
import pl.commit.connection.model.ConnectionData;

import java.io.IOException;

public class EditConnection {

   public static void show(ConnectionData.SingleConnectionData singleConnectionData, DashboardContentConnectionsController parentCotroller) {
      try {
         FXMLLoader loader = new FXMLLoader(ConnectionApplication.class.getResource("dashboard_edit_connection.fxml"));
         Scene scene = new Scene(loader.load());
         Stage stage = new Stage();
         stage.setTitle("ERPwincash - Edit Connection");
         stage.setScene(scene);

         DashboardEditConnectionController controller = loader.getController();
         controller.setSingleConnectionData(singleConnectionData);
         controller.setDashboardContentConnectionsController(parentCotroller);


         stage.show();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
}
