package pl.commit.connection.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import pl.commit.connection.component.TitledPaneConnection;
import pl.commit.connection.dao.ConnectionDataDAO;
import pl.commit.connection.model.ConnectionData;
import pl.commit.connection.model.ConnectionStatusSnapshot;

import java.net.URL;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DashboardContentConnectionsController implements Initializable {

    @FXML
    private ScrollPane content_add_connections;

    @FXML
    private ScrollPane content_add_customer;

    @FXML
    private ScrollPane content_connections;

    @FXML
    private Button dashboard_content_add_connection_btn;

    @FXML
    private Button dashboard_content_add_customer_btn;

    @FXML
    private Button dashboard_content_connections_btn;

    @FXML
    ScrollPane content_edit_connections;

    @FXML
    private VBox list_connection;

    List<ConnectionData> listConnection = new ArrayList<>();

    private Map<Integer, ConnectionStatusSnapshot> prevSnapshot = new HashMap<>();

    void showScrollPaneContent(ScrollPane paneToShow){
        // ukrycie wszystkich paneli
        content_add_connections.setVisible(false);
        content_add_connections.setManaged(false);
        content_edit_connections.setVisible(false);
        content_edit_connections.setManaged(false);
        content_connections.setVisible(false);
        content_connections.setManaged(false);
        content_add_customer.setVisible(false);
        content_add_customer.setManaged(false);
        // Pokazanie wybranego panelu
        paneToShow.setVisible(true);
        paneToShow.setManaged(true);
        // Usunięcie klasy aktywnego przycisku
        if(paneToShow == content_edit_connections){
            dashboard_content_connections_btn.getStyleClass().remove("dashboard_btn_active");
            dashboard_content_add_connection_btn.getStyleClass().remove("dashboard_btn_active");
            dashboard_content_add_customer_btn.getStyleClass().remove("dashboard_btn_active");
        }
    }

    private void setActiveBtn(Button button){
        String activeBtnClass = "dashboard_btn_active";
        // usuniecie klasy btnActive z wszystkich przycisków
        dashboard_content_connections_btn.getStyleClass().remove(activeBtnClass);
        dashboard_content_add_connection_btn.getStyleClass().remove(activeBtnClass);
        dashboard_content_add_customer_btn.getStyleClass().remove(activeBtnClass);
        // dodanie klasy btnActive do przycisku
        button.getStyleClass().add(activeBtnClass);
    }

    public void fetchConnectionData() {
        listConnection = null;
        ConnectionDataDAO connectionDataDAO = new ConnectionDataDAO();
        Optional<List<ConnectionData>> list = connectionDataDAO.getConnectionDataList();

        if (list.isPresent()){
            listConnection = list.get();
            prevSnapshot = new ConnectionDataDAO().getConnectionStatusSnapshot();
        }
    }

    public void showConnectionData() {
        if(!listConnection.isEmpty()){
            list_connection.getChildren().clear();
            for(ConnectionData connectionData : listConnection){
                TitledPaneConnection.show(list_connection, connectionData, this);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Pokazanie na start panelu z połączeniami
        showScrollPaneContent(content_connections);
        setActiveBtn(dashboard_content_connections_btn);

        // obsługa przycisków
        dashboard_content_connections_btn.setOnAction(
            actionEvent -> {
                showScrollPaneContent(content_connections);
                setActiveBtn(dashboard_content_connections_btn);
            });
        dashboard_content_add_connection_btn.setOnAction(
            actionEvent -> {
                showScrollPaneContent(content_add_connections);
                setActiveBtn(dashboard_content_add_connection_btn);
            });
        dashboard_content_add_customer_btn.setOnAction(
            actionEvent -> {
                showScrollPaneContent(content_add_customer);
                setActiveBtn(dashboard_content_add_customer_btn);
            });

        // Pobranie danych do połączeń
        fetchConnectionData();

        // Wyswietlenie danych połączeń
        showConnectionData();

        // ustawienie timera co 5 sekund, sprawdzenie aktualnie zalogowanego pracownika
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(runnable -> {
            Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            return thread;
        });
        executor.scheduleAtFixedRate(() -> {
            Map<Integer, ConnectionStatusSnapshot> currentSnapshot =
                    new ConnectionDataDAO().getConnectionStatusSnapshot();

            System.out.println("Sprawdzenie aktualnie zalogowanego pracownika");

            if (!currentSnapshot.equals(prevSnapshot)){
                System.out.println("Aktualizacja danych - zmiana danych");
                javafx.application.Platform.runLater(() -> {
                    prevSnapshot = currentSnapshot;
                    fetchConnectionData();
                    showConnectionData();
                });
            }

        }, 0, 5, TimeUnit.SECONDS);
    }
}
