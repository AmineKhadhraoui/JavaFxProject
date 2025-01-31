package org.JavaFxProject.Hotel.Services;

import org.JavaFxProject.Hotel.Entities.Room;
import org.JavaFxProject.Hotel.Utils.DBConnection;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomService {
    private Connection connection;
    private DBConnection dbConnection;

    public RoomService() {
        dbConnection = new DBConnection();
        connection = dbConnection.getConnection();
    }

    public List<Room> getAllRooms() {
        List<Room> roomList = new ArrayList<>();
        String query = "SELECT * FROM rooms";
        try (PreparedStatement pst = connection.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                int roomNumber = rs.getInt("roomNumber");
                int price = rs.getInt("price");
                String roomType = rs.getString("roomType");
                String status = rs.getString("status");
                roomList.add(new Room(roomNumber, price, roomType, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomList;
    }

    public void addRoom(Room room) {
        String query = "INSERT INTO rooms (roomNumber, roomType, price, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, room.getNumber());
            pst.setString(2, room.getType());
            pst.setInt(3, room.getPrice());
            pst.setString(4, room.getStatus());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Room> searchRoomsByNumber(int roomNumber) {
        List<Room> roomList = new ArrayList<>();
        String query = "SELECT * FROM rooms WHERE roomNumber = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, roomNumber);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int price = rs.getInt("price");
                String roomType = rs.getString("roomType");
                String status = rs.getString("status");
                roomList.add(new Room(roomNumber, price, roomType, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomList;
    }
}

