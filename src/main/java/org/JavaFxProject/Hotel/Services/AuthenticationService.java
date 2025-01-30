package org.JavaFxProject.Hotel.Services;

import org.JavaFxProject.Hotel.Entities.User;
import org.JavaFxProject.Hotel.Utils.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class AuthenticationService {
    private Connection connection;
    private DBConnection dbConnection;

    public AuthenticationService() {
        dbConnection = new DBConnection();
    }

    public boolean authenticateUser(User user) throws SQLException {
        connection = dbConnection.getConnection();
        String query = "SELECT * FROM users WHERE username=? AND password=?";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                User.setCurrentUserName(rs.getString("name"));
                return true;
            }
            return false;
        }
    }
}
