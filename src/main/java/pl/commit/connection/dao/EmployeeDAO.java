package pl.commit.connection.dao;

import javafx.scene.control.Label;
import pl.commit.connection.database.DatabaseConnection;
import pl.commit.connection.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class EmployeeDAO {

    public static String getUserLoginByGid(int gid){
        Connection connection = null;
        connection = DatabaseConnection.getConnection();
        String sql = "SELECT login FROM employee WHERE gid = ?";
        System.out.println(sql);
        System.out.println(gid);
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, gid);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getString("login");
            } else {
                return "not found";
            }
        } catch (SQLException e) {
           throw new RuntimeException(e);
        }
    }

    public Optional<Employee> getEmployeeByLoginAndPassword(String login, String password) {
        Connection connection = null;
        connection = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM employee WHERE login = ? AND password = ? AND status = 'A'";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                Employee employee = mapResultSetToEmployee(resultSet);
                return Optional.of(employee);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Employee mapResultSetToEmployee(ResultSet resultSet) throws SQLException {
        return new Employee(
                resultSet.getInt("gid"),
                resultSet.getString("name"),
                resultSet.getString("login"),
                resultSet.getString("password"),
                resultSet.getString("status"),
                resultSet.getTimestamp("insstmp").toLocalDateTime(),
                resultSet.getTimestamp("updstmp").toLocalDateTime()
        );
    }
}
