package org.JavaFxProject.Hotel.Services;

import org.JavaFxProject.Hotel.Entities.Bill;
import org.JavaFxProject.Hotel.Utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillService {
    private Connection connection;
    private DBConnection dbConnection;

    public BillService() {
        dbConnection = new DBConnection();
        connection = dbConnection.getConnection();
    }

    public List<Bill> getAllBills() {
        List<Bill> billList = new ArrayList<>();
        String query = "SELECT b.*, res.roomNumber, res.customerIDNumber, c.customerName FROM bills b " +
                "INNER JOIN reservations res ON b.reservationID = res.reservationID " +
                "INNER JOIN customers c ON res.customerIDNumber = c.customerIDNumber";
        try (PreparedStatement pst = connection.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                int billID = rs.getInt("billID");
                String customerName = rs.getString("customerName");
                int customerID = rs.getInt("customerIDNumber");
                String date = rs.getString("billDate");
                int amount = rs.getInt("billAmount");
                int roomNumber = rs.getInt("roomNumber");
                billList.add(new Bill(billID, customerName, customerID, date, amount, roomNumber));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billList;
    }

    public List<Bill> searchBillsByDate(String date) {
        List<Bill> billList = new ArrayList<>();
        String query = "SELECT b.*, res.roomNumber, res.customerIDNumber, c.customerName FROM bills b " +
                "INNER JOIN reservations res ON b.reservationID = res.reservationID " +
                "INNER JOIN customers c ON res.customerIDNumber = c.customerIDNumber " +
                "WHERE b.billDate LIKE ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, date + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int billID = rs.getInt("billID");
                String customerName = rs.getString("customerName");
                int customerID = rs.getInt("customerIDNumber");
                String billDate = rs.getString("billDate");
                int amount = rs.getInt("billAmount");
                int roomNumber = rs.getInt("roomNumber");
                billList.add(new Bill(billID, customerName, customerID, billDate, amount, roomNumber));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billList;
    }
}

