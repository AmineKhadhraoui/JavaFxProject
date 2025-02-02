package org.JavaFxProject.Hotel.Services;

import org.JavaFxProject.Hotel.Entities.Customer;
import org.JavaFxProject.Hotel.Entities.Reservation;
import org.JavaFxProject.Hotel.Utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerService {
    private Connection connection;
    private DBConnection dbConnection;

    public CustomerService() {
        dbConnection = new DBConnection();
        connection = dbConnection.getConnection();
    }

    public Customer getCustomerDetails(int roomNumber) {
        String query = "SELECT c.*, res.checkInDate, res.checkOutDate, (r.price * DATEDIFF(res.checkOutDate, res.checkInDate)) AS Total " +
                "FROM customers c INNER JOIN reservations res ON c.customerIDNumber = res.customerIDNumber " +
                "INNER JOIN rooms r ON r.roomNumber = res.roomNumber WHERE r.roomNumber=?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, roomNumber);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Customer(
                        rs.getInt("customerIDNumber"),
                        rs.getString("customerName"),
                        rs.getString("customerEmail"),
                        rs.getString("customerPhoneNo"),
                        rs.getString("customerGender"),
                        rs.getString("customerNationality")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Reservation getReservationDetails(int roomNumber) {
        String query = "SELECT res.reservationID, res.roomNumber, res.customerIDNumber, " +
                "res.checkInDate, res.checkOutDate, res.status, c.customerName, " +
                "DATEDIFF(res.checkOutDate, res.checkInDate) AS totalDays, " +
                "(r.price * DATEDIFF(res.checkOutDate, res.checkInDate)) AS totalPrice " +
                "FROM reservations res " +
                "INNER JOIN rooms r ON r.roomNumber = res.roomNumber " +
                "INNER JOIN customers c ON c.customerIDNumber = res.customerIDNumber " +
                "WHERE r.roomNumber=?";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, roomNumber);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Reservation(
                        rs.getInt("reservationID"),
                        rs.getInt("roomNumber"),
                        rs.getInt("customerIDNumber"),
                        rs.getString("checkInDate"),
                        rs.getString("checkOutDate"),
                        rs.getInt("totalDays"),
                        rs.getDouble("totalPrice"),
                        rs.getString("status"),
                        rs.getString("customerName")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}