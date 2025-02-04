package org.JavaFxProject.Hotel.Services;

import org.JavaFxProject.Hotel.Entities.Staff;
import org.JavaFxProject.Hotel.Utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffService {
    private Connection connection;

    public StaffService() {
        DBConnection db = new DBConnection();
        this.connection = db.getConnection();
    }

    public void addStaff(Staff staff) {
        String query = "INSERT INTO staff (name, age, gender, position, phone, email) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, staff.getName());
            pst.setInt(2, staff.getAge());
            pst.setString(3, staff.getGender());
            pst.setString(4, staff.getPosition());
            pst.setString(5, staff.getPhone());
            pst.setString(6, staff.getEmail());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStaff(Staff staff) {
        String query = "UPDATE staff SET name = ?, age = ?, gender = ?, position = ?, phone = ?, email = ? WHERE staffID = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, staff.getName());
            pst.setInt(2, staff.getAge());
            pst.setString(3, staff.getGender());
            pst.setString(4, staff.getPosition());
            pst.setString(5, staff.getPhone());
            pst.setString(6, staff.getEmail());
            pst.setInt(7, staff.getStaffID());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStaff(int staffID) {
        String query = "DELETE FROM staff WHERE staffID = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, staffID);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Staff> getAllStaff() {
        List<Staff> staffList = new ArrayList<>();
        String query = "SELECT * FROM staff";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                staffList.add(new Staff(
                        rs.getInt("staffID"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("position"),
                        rs.getString("phone"),
                        rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }
}

