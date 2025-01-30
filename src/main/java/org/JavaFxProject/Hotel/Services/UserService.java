package org.JavaFxProject.Hotel.Services;

import org.JavaFxProject.Hotel.Entities.User;
import org.JavaFxProject.Hotel.Utils.DBConnection;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

    public class UserService {
        private Connection connection;
        private DBConnection dbConnection;

        public UserService() {
            dbConnection = new DBConnection();
            connection = dbConnection.getConnection();
        }

        public boolean registerUser(User user) {
            String insert = "INSERT INTO users(name, username, password, gender, securityQuestion, answer, address) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try {
                connection = dbConnection.getConnection();
                PreparedStatement pst = connection.prepareStatement(insert);

                pst.setString(1, user.getName());
                pst.setString(2, user.getUsername());
                pst.setString(3, user.getPassword());
                pst.setString(4, user.getGender());
                pst.setString(5, user.getSecurityQuestion());
                pst.setString(6, user.getAnswer());
                pst.setString(7, user.getAddress());

                pst.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        public boolean searchUser(String username) {
            String query = "SELECT * FROM users WHERE username=?";
            try {
                PreparedStatement pst = connection.prepareStatement(query);
                pst.setString(1, username);
                ResultSet rs = pst.executeQuery();
                return rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        public String getSecurityQuestion(String username) {
            String query = "SELECT securityQuestion FROM users WHERE username=?";
            try {
                PreparedStatement pst = connection.prepareStatement(query);
                pst.setString(1, username);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getString("securityQuestion");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        public boolean updatePassword(String username, String password, String securityQuestion, String answer) {
            String query = "SELECT * FROM users WHERE username=? AND securityQuestion=? AND answer=?";
            try {
                PreparedStatement pst = connection.prepareStatement(query);
                pst.setString(1, username);
                pst.setString(2, securityQuestion);
                pst.setString(3, answer);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    String update = "UPDATE users SET password=? WHERE username=?";
                    pst = connection.prepareStatement(update);
                    pst.setString(1, password);
                    pst.setString(2, username);
                    pst.executeUpdate();
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

