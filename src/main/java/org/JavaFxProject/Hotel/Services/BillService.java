package org.JavaFxProject.Hotel.Services;



import org.JavaFxProject.Hotel.Entities.Bill;
import org.JavaFxProject.Hotel.Entities.Reservation;
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

    public Reservation getReservationDetails(int resID) {
        String query = "SELECT res.reservationID, res.roomNumber, c.customerIDNumber, c.customerName, (r.price * DATEDIFF(res.checkOutDate, res.checkInDate)) AS totalPrice " +
                "FROM customers c INNER JOIN reservations res ON c.customerIDNumber = res.customerIDNumber " +
                "INNER JOIN rooms r ON r.roomNumber = res.roomNumber WHERE res.reservationID=?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, resID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Reservation(
                        rs.getInt("reservationID"),
                        rs.getInt("roomNumber"),
                        rs.getInt("customerIDNumber"),
                        rs.getString("checkInDate"),
                        rs.getString("checkOutDate"),
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

    public void createBill(int resID, String billDate, double billAmount) {
        String query = "INSERT INTO bills(reservationID, billDate, billAmount) VALUES (?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, resID);
            pst.setString(2, billDate);
            pst.setDouble(3, billAmount);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateRoomStatus(int roomNumber) {
        String query = "UPDATE rooms SET status='Not Booked' WHERE roomNumber=?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, roomNumber);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateReservationStatus(int resID) {
        String query = "UPDATE reservations SET status='Checked Out' WHERE reservationID=?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, resID);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getBillDetails(int billID) {
        String query = "SELECT b.billID, c.customerIDNumber, c.customerName, c.customerPhoneNo, r.roomNumber, r.roomType, r.price, res.checkInDate, res.checkOutDate, " +
                "(r.price * DATEDIFF(res.checkOutDate, res.checkInDate)) AS totalPrice, DATEDIFF(res.checkOutDate, res.checkInDate) AS totalDay " +
                "FROM bills b INNER JOIN reservations res ON b.reservationID = res.reservationID " +
                "INNER JOIN rooms r ON r.roomNumber = res.roomNumber " +
                "INNER JOIN customers c ON c.customerIDNumber = res.customerIDNumber " +
                "WHERE b.billID=?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, billID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return String.format(
                        "Bill ID: %s\nCustomer Details:\nName: %s\nID Number: %s\nMobile Number: %s\n\nRoom Details:\nRoom Number: %s\nRoom Type: %s\nPrice Per Day: %s\n\nCheck In Date: %s\nCheck Out Date: %s\nNumber of Days Stay: %s\nTotal Amount Paid: %s",
                        rs.getString("billID"),
                        rs.getString("customerName"),
                        rs.getString("customerIDNumber"),
                        rs.getString("customerPhoneNo"),
                        rs.getString("roomNumber"),
                        rs.getString("roomType"),
                        rs.getString("price"),
                        rs.getString("checkInDate"),
                        rs.getString("checkOutDate"),
                        rs.getString("totalDay"),
                        rs.getString("totalPrice")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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



