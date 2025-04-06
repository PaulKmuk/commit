package pl.commit.connection.controllers;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.stage.Popup;
import javafx.util.Duration;
import pl.commit.connection.dao.ConnectionDataDAO;
import pl.commit.connection.dao.EmployeeDAO;
import pl.commit.connection.model.ConnectionData;
import pl.commit.connection.pages.EditConnection;
import pl.commit.connection.utils.RdpLauncher;

import java.net.URL;
import java.util.ResourceBundle;

public class SingleConnectionController implements Initializable {

   @FXML
   private Button single_con_conection_btn;

   @FXML
   private Button single_con_edit_btn;

   @FXML
   private Button single_con_copy_btn;

   @FXML
   private TextArea single_con_description;

   @FXML
   private HBox single_con_frame;

   @FXML
   private TextField single_con_data;

   @FXML
   private Label single_con_user_online;

   ConnectionData.SingleConnectionData singleConnectionData;

   DashboardContentConnectionsController parentController;

   public void setParentController(DashboardContentConnectionsController parentController){
      this.parentController = parentController;
   }

   public void setSingleConnectionData(ConnectionData.SingleConnectionData singleConnectionData) {
      this.singleConnectionData = singleConnectionData;
      updateUI();
   }

   public void actionCopyBtn(){
      if(singleConnectionData != null && singleConnectionData.getPassword() != null) {
         Clipboard clipboard = Clipboard.getSystemClipboard();
         ClipboardContent content = new ClipboardContent();
         content.putString(singleConnectionData.getPassword());
         clipboard.setContent(content);
         showToast("Skopiowane do schowka");
      }
   }

   public void actionEditBtn(){
      System.out.println(singleConnectionData.toString());
//      parentController.showScrollPaneContent(parentController.content_edit_connections);
      //  zmiana koncepcji na popup
      EditConnection.show(singleConnectionData, parentController);
   }

   public void actionConnectBtn() {
      RdpLauncher.openRdpCredentials(singleConnectionData);
   }

   //   przenieść do osobnej klasy !!!!
   private void showToast(String message) {
      Popup popup = new Popup();
      Label label = new Label(message);
      label.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-padding: 10px; -fx-background-radius: 5;");
      popup.getContent().add(label);
      popup.setAutoHide(true);
      popup.show(single_con_copy_btn.getScene().getWindow());

      PauseTransition delay = new PauseTransition(Duration.seconds(2));
      delay.setOnFinished(e -> popup.hide());
      delay.play();
   }

   public void updateUI() {
      if(singleConnectionData != null){
         String ip_user_password =
                 singleConnectionData.getIp() + " / " + singleConnectionData.getUsrlogin() + "@" + singleConnectionData.getPassword();

         single_con_data.setText(ip_user_password);
         single_con_description.setText(singleConnectionData.getDescription());
         if(singleConnectionData.getIsActive() == 1){
            single_con_user_online.setText(EmployeeDAO.getUserLoginByGid(singleConnectionData.getActivuser()));
            single_con_user_online.getStyleClass().add("userOnline");
         } else {
            single_con_user_online.setText("Brak");
         }
      }
   }

   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {
      single_con_conection_btn.setOnAction(actionEvent -> actionConnectBtn());
      single_con_edit_btn.setOnAction(actionEvent -> actionEditBtn());
      single_con_copy_btn.setOnAction(actionEvent -> actionCopyBtn());
   }
}
