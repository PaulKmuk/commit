module pl.commit.connection {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires ojdbc6;
//    requires com.oracle.database.jdbc;


    opens pl.commit.connection to javafx.fxml;
    exports pl.commit.connection;
    exports pl.commit.connection.controllers;
    opens pl.commit.connection.controllers to javafx.fxml;
    exports pl.commit.connection.pages;
    opens pl.commit.connection.pages to javafx.fxml;
}