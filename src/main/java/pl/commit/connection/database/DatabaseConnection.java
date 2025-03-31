package pl.commit.connection.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static final String PROPERTIES_FILE = "database.properties";

    // etoda zwraca połączenie do bazy
    public static Connection getConnection() {
        Properties properties = loadProperties();
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.user"),
                    properties.getProperty("db.password")
            );
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // metoda która usuwa podane połącznie do bazy
    public static void closeConnection(Connection connection) {
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Pobranie danych bazy z config/database.properties
    private static Properties loadProperties() {
        Properties properties =new Properties();
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)){
            if (input == null) {
                throw new SQLException("Unable to find " + PROPERTIES_FILE);
            }
            properties.load(input);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}
