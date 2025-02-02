package org.JavaFxProject.Hotel.Services;

import org.JavaFxProject.Hotel.Entities.Reservation;
import org.JavaFxProject.Hotel.Utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CheckOutService {
    private Connection connection;
    private DBConnection dbConnection;

    public CheckOutService() {
        dbConnection = new DBConnection();
        connection = dbConnection.getConnection();
    }

    public List<Reservation> getAllReservations() {
        List<Reservation> reservationList = new ArrayList<>();
        String query = "SELECT res.status, res.reservationID, res.roomNumber, res.customerIDNumber, " +
                "c.customerName, res.checkInDate, res.checkOutDate, " +
                "DATEDIFF(res.checkOutDate, res.checkInDate) AS totalDays, " +
                "(r.price * DATEDIFF(res.checkOutDate, res.checkInDate)) AS totalPrice " +
                "FROM customers c " +
                "INNER JOIN reservations res ON c.customerIDNumber = res.customerIDNumber " +
                "INNER JOIN rooms r ON r.roomNumber = res.roomNumber";

        try (PreparedStatement pst = connection.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                reservationList.add(new Reservation(
                        rs.getInt("reservationID"),          // resID
                        rs.getInt("roomNumber"),             // roomNumber
                        rs.getInt("customerIDNumber"),       // customerIDNumber
                        rs.getString("checkInDate"),         // checkInDate
                        rs.getString("checkOutDate"),        // checkOutDate
                        rs.getInt("totalDays"),             // totalDays
                        rs.getDouble("totalPrice"),          // totalPrice
                        rs.getString("status"),              // status
                        rs.getString("customerName")         // customerName
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservationList;
    }

    public List<Reservation> getReservationsByRoomNumber(String roomNumber, List<Reservation> reservationList) {
        List<Reservation> filteredList = new ArrayList<>();
        for (Reservation reservation : reservationList) {
            if (String.valueOf(reservation.getRoomNumber()).startsWith(roomNumber)) {
                filteredList.add(reservation);
            }
        }
        return filteredList;
    }

    public List<Reservation> filterReservations(String filterType, List<Reservation> reservationList) {
        List<Reservation> filteredList = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (Reservation reservation : reservationList) {
            switch (filterType) {
                case "Today":
                    if (reservation.getCheckOutDate().equals(today.toString()) &&
                            reservation.getStatus().equals("Checked In")) {
                        filteredList.add(reservation);
                    }
                    break;

                case "Checked In":
                    if (reservation.getStatus().equals("Checked In")) {
                        filteredList.add(reservation);
                    }
                    break;

                case "Checked Out":
                    if (reservation.getStatus().equals("Checked Out")) {
                        filteredList.add(reservation);
                    }
                    break;
            }
        }
        return filteredList;
    }

    public void updateReservationStatus(Reservation reservation, String newStatus) {
        String query = "UPDATE reservations SET status = ? WHERE reservationID = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, newStatus);
            pst.setInt(2, reservation.getResID());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
