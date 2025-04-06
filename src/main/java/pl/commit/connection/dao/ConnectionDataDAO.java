package pl.commit.connection.dao;

import pl.commit.connection.database.DatabaseConnection;
import pl.commit.connection.model.ConnectionData;
import pl.commit.connection.model.ConnectionStatusSnapshot;
import pl.commit.connection.utils.EmployeeLogged;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ConnectionDataDAO {

   public Optional<List<ConnectionData>> getConnectionDataList() {

      List<ConnectionData> conectionDataList = new ArrayList<>();

      Connection connection = null;
      connection = DatabaseConnection.getConnection();
      String sql = "SELECT * FROM connecthdr WHERE status = 'A'";
      try(PreparedStatement statement = connection.prepareStatement(sql);
          ResultSet resultSet = statement.executeQuery()){

         while(resultSet.next()){
            List<ConnectionData.SingleConnectionData> singleConnectionList = new ArrayList<>();
            int idConnect = resultSet.getInt("gid");

            String sqlSingleConnection = "SELECT * FROM connectdtl WHERE status = 'A' AND conid = ?";
            try(PreparedStatement statementSingle = connection.prepareStatement(sqlSingleConnection)){
               statementSingle.setInt(1, idConnect);
               ResultSet resultSetSingle = statementSingle.executeQuery();

               while(resultSetSingle.next()){
                  ConnectionData.SingleConnectionData singleConnect = new ConnectionData.SingleConnectionData(
                          resultSetSingle.getInt("gid"),
                          resultSetSingle.getInt("conid"),
                          resultSetSingle.getString("ip"),
                          resultSetSingle.getString("password"),
                          resultSetSingle.getString("description"),
                          resultSetSingle.getString("status"),
                          resultSetSingle.getInt("isactive"),
                          resultSetSingle.getInt("activuser"),
                          resultSetSingle.getTimestamp("insstmp").toLocalDateTime(),
                          resultSetSingle.getTimestamp("updstmp").toLocalDateTime(),
                          resultSetSingle.getString("usrlogin")
                  );
                  singleConnectionList.add(singleConnect);
               }
            } catch (SQLException e) {
               throw new RuntimeException(e);
            }

            ConnectionData connectionData = new ConnectionData(
                    resultSet.getInt("gid"),
                    resultSet.getString("company"),
                    resultSet.getString("status"),
                    resultSet.getTimestamp("insstmp").toLocalDateTime(),
                    resultSet.getTimestamp("updstmp").toLocalDateTime(),
                    singleConnectionList
            );
            conectionDataList.add(connectionData);
         }

         return Optional.of(conectionDataList);
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   public static String getCompanyNameByGid(int gid){
      Connection connection = DatabaseConnection.getConnection();
      String sql = "SELECT company FROM connecthdr WHERE gid = ? AND status = 'A'";
      try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
         preparedStatement.setInt(1, gid);
         ResultSet resultSet = preparedStatement.executeQuery();
         if(resultSet.next()){
            return resultSet.getString("company");
         } else {
            return "Not found record";
         }
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   public Map<Integer, ConnectionStatusSnapshot> getConnectionStatusSnapshot() {

      Map<Integer, ConnectionStatusSnapshot> snapshotMap = new HashMap<>();

      Connection connection = DatabaseConnection.getConnection();
      String sql = "SELECT gid, isactive, activuser FROM connectdtl WHERE status = 'A'";

      try(PreparedStatement preparedStatement = connection.prepareStatement(sql);
         ResultSet resultSet = preparedStatement.executeQuery()) {

         while (resultSet.next()) {
            int gid = resultSet.getInt("gid");
            int isActive = resultSet.getInt("isactive");
            int activUser = resultSet.getInt("activuser");
            ConnectionStatusSnapshot snapshot = new ConnectionStatusSnapshot(
                    gid,
                    isActive,
                    activUser
            );
            snapshotMap.put(gid, snapshot);
         }
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
      return snapshotMap;
   }

   public static void removeSingleConnection(ConnectionData.SingleConnectionData singleConnectionData) {
      // Usunięcie połączenia zmienia status na 'D'
      Connection connection = DatabaseConnection.getConnection();
      String sql = "UPDATE connectdtl SET status = 'D' WHERE gid = ?";
      try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
         preparedStatement.setInt(1, singleConnectionData.getGid());
         preparedStatement.executeUpdate();
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   public static void updateSingleConnection(ConnectionData.SingleConnectionData singleConnectionData) {
      Connection connection = DatabaseConnection.getConnection();
      String sql = "UPDATE connectdtl SET " +
              "ip = ?, password = ?, description = ?, usrlogin = ?, updstmp = SYSDATE, status = ? " +
              "WHERE gid = ?";
      try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
         preparedStatement.setString(1, singleConnectionData.getIp());
         preparedStatement.setString(2, singleConnectionData.getPassword());
         preparedStatement.setString(3, singleConnectionData.getDescription());
         preparedStatement.setString(4, singleConnectionData.getUsrlogin());
         preparedStatement.setString(5, singleConnectionData.getStatus());
         preparedStatement.setInt(6, singleConnectionData.getGid());

         preparedStatement.executeUpdate();
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   public static void setConnectedUser(ConnectionData.SingleConnectionData singleConnectionData, boolean iscConnect) {

      Connection connection = DatabaseConnection.getConnection();
      int user = EmployeeLogged.getEmployee().getId();
      if(iscConnect){
         String sql = "UPDATE connectdtl SET isactive = 1, activuser = ?, updstmp = SYSDATE WHERE gid = ?";

         try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, user);
            preparedStatement.setInt(2, singleConnectionData.getGid());
            preparedStatement.executeUpdate();
         } catch (SQLException e) {
            throw new RuntimeException(e);
         }
      } else {
         String sql = "UPDATE connectdtl SET isactive = 0, activuser = NULL, updstmp = SYSDATE WHERE gid = ?";
         try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, singleConnectionData.getGid());
            preparedStatement.executeUpdate();
         } catch (SQLException e) {
            throw new RuntimeException(e);
         }
      }
   }
}
