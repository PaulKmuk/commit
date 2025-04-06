package pl.commit.connection.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pl.commit.connection.dao.ConnectionDataDAO;
import pl.commit.connection.model.ConnectionData;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardEditConnectionController implements Initializable {

   @FXML
   private Button edit_connection_cancel_btn;

   @FXML
   private Label edit_connection_company;

   @FXML
   private TextArea edit_connection_description;

   @FXML
   private TextField edit_connection_ip;

   @FXML
   private TextField edit_connection_password;

   @FXML
   private Button edit_connection_save_btn;

   @FXML
   private Button edit_connection_remove_btn;

   @FXML
   private TextField edit_connection_user;

   DashboardContentConnectionsController parentController;

   ConnectionData.SingleConnectionData singleConnectionData;

   ConnectionData.SingleConnectionData newSingleConnectionData;

   public void setDashboardContentConnectionsController(DashboardContentConnectionsController parentController) {
      this.parentController = parentController;
   }

   public void setSingleConnectionData(ConnectionData.SingleConnectionData singleConnectionData) {
      this.singleConnectionData = singleConnectionData;
      setDataToEdit();
   }

   public void setDataToEdit() {
      if(singleConnectionData != null){
         String companyName =
                 ConnectionDataDAO.getCompanyNameByGid(singleConnectionData.getConid());
         edit_connection_company.setText(companyName);
         edit_connection_ip.setText(singleConnectionData.getIp());
         edit_connection_user.setText(singleConnectionData.getUsrlogin());
         edit_connection_password.setText(singleConnectionData.getPassword());
         edit_connection_description.setText(singleConnectionData.getDescription());
      }
   }

   public void actionSaveBtn() {
      newSingleConnectionData = new ConnectionData.SingleConnectionData(
              singleConnectionData.getGid(),
              singleConnectionData.getConid(),
              edit_connection_ip.getText(),
              edit_connection_password.getText(),
              edit_connection_description.getText(),
              singleConnectionData.getStatus(),
              singleConnectionData.getIsActive(),
              singleConnectionData.getActivuser(),
              singleConnectionData.getInsstmp(),
              singleConnectionData.getUpdstmp(),
              edit_connection_user.getText()
      );
      System.out.println(newSingleConnectionData.toString());
      ConnectionDataDAO.updateSingleConnection(newSingleConnectionData);
      parentController.fetchConnectionData();
      parentController.showConnectionData();
      actionCancelBtn();
   }

   public void actionCancelBtn() {
      Stage stage = (Stage) edit_connection_cancel_btn.getScene().getWindow();
      stage.close();
   }

   public void actionRemoveBtn() {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Potwierdzenie");
      alert.setHeaderText(null);
      alert.setContentText("Czy na pewno chcesz usunąć to połączenie?");

      Optional<ButtonType> result = alert.showAndWait();

      if(result.isPresent() && result.get() == ButtonType.OK){
         ConnectionDataDAO.removeSingleConnection(singleConnectionData);
         parentController.fetchConnectionData();
         parentController.showConnectionData();
         actionCancelBtn();
      } else {
         return;
      }
   }

   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {
      edit_connection_cancel_btn.setOnAction((actionEvent) -> actionCancelBtn());
      edit_connection_save_btn.setOnAction((actionEvent -> actionSaveBtn()));
      edit_connection_remove_btn.setOnAction((actionEvent -> actionRemoveBtn()));

      // ustawienie na start focus na label z company
      // aby startowy focus nie byl na input
      edit_connection_company.setFocusTraversable(true);
   }
}
