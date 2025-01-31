package  org.JavaFxProject.Hotel.Services;
import org.JavaFxProject.Hotel.Utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardService {
    private Connection connection;
    private DBConnection dbConnection;

    public DashboardService() {
        dbConnection = new DBConnection();
        connection = dbConnection.getConnection();
    }

    public int getTotalRooms() {
        String query = "SELECT COUNT(roomNumber) AS totalRooms FROM rooms";
        return executeCountQuery(query);
    }

    public int getBookedRooms() {
        String query = "SELECT COUNT(roomNumber) AS totalBooked FROM rooms WHERE status = 'Booked'";
        return executeCountQuery(query);
    }

    public int getAvailableRooms() {
        String query = "SELECT COUNT(roomNumber) AS totalNotBooked FROM rooms WHERE status = 'Not Booked'";
        return executeCountQuery(query);
    }

    public double getTotalEarnings() {
        String query = "SELECT SUM(b.billAmount) AS totalEarnings FROM bills b " +
                "INNER JOIN reservations res ON res.reservationID = b.reservationID";
        return executeDoubleQuery(query);
    }

    public double getPendingEarnings() {
        String query = "SELECT SUM((r.price * DATEDIFF(res.checkOutDate, res.checkInDate))) AS totalPendings " +
                "FROM reservations res INNER JOIN rooms r ON r.roomNumber = res.roomNumber " +
                "WHERE res.status = 'Checked In'";
        return executeDoubleQuery(query);
    }

    private int executeCountQuery(String query) {
        try (PreparedStatement pst = connection.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private double executeDoubleQuery(String query) {
        try (PreparedStatement pst = connection.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
