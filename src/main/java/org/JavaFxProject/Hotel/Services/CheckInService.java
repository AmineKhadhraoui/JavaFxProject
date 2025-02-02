package org.JavaFxProject.Hotel.Services;
import org.JavaFxProject.Hotel.Entities.Customer;
import org.JavaFxProject.Hotel.Utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class CheckInService {
    private Connection connection;
    private DBConnection dbConnection;

    public CheckInService() {
        dbConnection = new DBConnection();
        connection = dbConnection.getConnection();
    }

    public List<String> getRoomTypes() {
        List<String> roomTypes = new ArrayList<>();
        String query = "SELECT DISTINCT roomType FROM rooms";
        try (PreparedStatement pst = connection.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                roomTypes.add(rs.getString("roomType"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomTypes;
    }

    public List<String> getAvailableRoomNumbers(String roomType) {
        List<String> roomNumbers = new ArrayList<>();
        String query = "SELECT roomNumber FROM rooms WHERE roomType=? AND status='Not Booked'";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, roomType);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                roomNumbers.add(rs.getString("roomNumber"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomNumbers;
    }

    public double getRoomPrice(String roomNumber) {
        String query = "SELECT price FROM rooms WHERE roomNumber=?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, roomNumber);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getDouble("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public boolean processCheckIn(Customer customer, String roomNumber, String checkInDate, String checkOutDate) {
        try {
            connection.setAutoCommit(false);

            // Insert customer
            String insertCustomer = "INSERT INTO customers(customerIDNumber, customerName, customerNationality, customerGender, customerPhoneNo, customerEmail) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pst = connection.prepareStatement(insertCustomer)) {
                pst.setInt(1, customer.getCustomerIDNumber());
                pst.setString(2, customer.getCustomerName());
                pst.setString(3, customer.getCustomerNationality());
                pst.setString(4, customer.getCustomerGender());
                pst.setString(5, customer.getCustomerPhoneNo());
                pst.setString(6, customer.getCustomerEmail());
                pst.executeUpdate();
            }

            // Create reservation
            String insertReservation = "INSERT INTO reservations(customerIDNumber, roomNumber, checkInDate, checkOutDate) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pst = connection.prepareStatement(insertReservation)) {
                pst.setInt(1, customer.getCustomerIDNumber());
                pst.setString(2, roomNumber);
                pst.setString(3, checkInDate);
                pst.setString(4, checkOutDate);
                pst.executeUpdate();
            }

            // Update room status
            String updateRoom = "UPDATE rooms SET status='Booked' WHERE roomNumber=?";
            try (PreparedStatement pst = connection.prepareStatement(updateRoom)) {
                pst.setString(1, roomNumber);
                pst.executeUpdate();
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
