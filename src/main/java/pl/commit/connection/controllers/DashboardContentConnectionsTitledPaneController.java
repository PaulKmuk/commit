package pl.commit.connection.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import pl.commit.connection.component.SingleConnection;
import pl.commit.connection.model.ConnectionData;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardContentConnectionsTitledPaneController implements Initializable {

   @FXML
   private TitledPane connect_title;

   @FXML
   private VBox titledPane_list_connection;

   ConnectionData connectionData;

   DashboardContentConnectionsController parentController;

   public void setParentController(DashboardContentConnectionsController parentController){
      this.parentController = parentController;
   }

   public void showConnectionData() {
      System.out.println("showConnectionData");
      if(connectionData != null){
         System.out.println("connectionData != null");
         List<ConnectionData.SingleConnectionData> listConnection = connectionData.getListConnection();
         titledPane_list_connection.getChildren().clear();
         for(ConnectionData.SingleConnectionData singleConnection : listConnection){
            SingleConnection.show(titledPane_list_connection, singleConnection,  parentController);
         }
      }
   }

   public void setConnectionData(ConnectionData connectionData) {
      this.connectionData = connectionData;
      updateUI();
   }

   public void updateUI() {
      if(connectionData != null){
         connect_title.setText(connectionData.getCompany());
         connect_title.setMaxWidth(Double.MAX_VALUE);
         showConnectionData();
      }
   }

   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {

   }
}
