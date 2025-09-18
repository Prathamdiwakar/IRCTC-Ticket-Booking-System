package org.irctc.db;

import org.irctc.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class UserDAO {
    public boolean registerUser(User user){
        String query = "INSERT INTO users(name, email, password) VALUES(?,?,?)";
        try(Connection connectionn = DatabaseConnection.getconnection();
            PreparedStatement preparedStatement = connectionn.prepareStatement(query)){

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());

            int row = preparedStatement.executeUpdate();
            return row>0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String loginUser(String email, String password) {
          String query = "SELECT * FROM users WHERE email=? AND password =?";
         try(Connection connection = DatabaseConnection.getconnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){

             preparedStatement.setString(1, email);
             preparedStatement.setString(2,password);

             //by using var for better understanding:---
             var rs = preparedStatement.executeQuery();
             if (rs.next()) {
                 return rs.getString("role");
             }

         } catch (SQLException e) {
         throw new RuntimeException(e);
         }
         return null; // login failed
    }
}
