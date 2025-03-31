package pl.commit.connection.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import pl.commit.connection.dao.EmployeeDAO;
import pl.commit.connection.database.DatabaseConnection;
import pl.commit.connection.model.Employee;
import pl.commit.connection.pages.Dashboard;
import pl.commit.connection.utils.EmployeeLogged;
import pl.commit.connection.utils.ValidationInputsLogin;

import javax.xml.namespace.QName;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class LoginController {
    @FXML
    private StackPane login_form;

    @FXML
    private Button login_login_btn;

    @FXML
    private TextField login_login_textField;

    @FXML
    private Button login_password_btn;

    @FXML
    private PasswordField login_password_textField;

    public void login() {
        String login = login_login_textField.getText();
        String password = login_password_textField.getText();
        String checkInputs = ValidationInputsLogin.checkInputLoginPassword(login, password);

        if(checkInputs.equals("OK")){
            EmployeeDAO employeeDAO = new EmployeeDAO();
            Optional<Employee> optionalEmployee = employeeDAO.getEmployeeByLoginAndPassword(login, password);

            if(optionalEmployee.isPresent()){
                Employee employee = optionalEmployee.get();
                // Poprawne zalogowanie
                EmployeeLogged.setEmployee(employee);
                System.out.println(EmployeeLogged.getEmployee().toString());
                // Załadowanie okna dashbordu
                Dashboard dashboard = new Dashboard();
                dashboard.show();

                login_login_textField.setText("");
                login_password_textField.setText("");
                login_form.getScene().getWindow().hide();
            } else {
                System.out.println("Nie znaleziono użytkownika dla podanelo loginu i hasła");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informacja");
                alert.setHeaderText(null);
                alert.setContentText("Nie znaleziono użytkownika dla podanego loginu i hasła");
                alert.showAndWait();
                return;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacja");
            alert.setHeaderText(null);
            alert.setContentText(checkInputs);
            alert.showAndWait();
            return;
        }
    }

    public void cancel() {
        System.exit(0);
    }
}