package org.JavaFxProject.Hotel.Services;

import org.JavaFxProject.Hotel.Entities.Services;
import org.JavaFxProject.Hotel.Utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceService {
    private Connection connection;
    private DBConnection dbConnection;

    public ServiceService() {
        dbConnection = new DBConnection();
        connection = dbConnection.getConnection();
    }

    public List<Services> getAllServices() {
        List<Services> serviceList = new ArrayList<>();
        String query = "SELECT * FROM services";
        try (PreparedStatement pst = connection.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                int serviceID = rs.getInt("serviceID");
                String serviceName = rs.getString("serviceName");
                String serviceDescription = rs.getString("serviceDescription");
                double servicePrice = rs.getDouble("servicePrice");
                serviceList.add(new Services(serviceID, serviceName, serviceDescription, servicePrice));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serviceList;
    }

    public void deleteService(int serviceID) {
        String query = "DELETE FROM services WHERE serviceID = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, serviceID);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Services> searchServiceByID(int serviceID) {
        List<Services> serviceList = new ArrayList<>();
        String query = "SELECT * FROM services WHERE serviceID = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, serviceID);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("serviceID");
                    String name = rs.getString("serviceName");
                    String description = rs.getString("serviceDescription");
                    double price = rs.getDouble("servicePrice");
                    serviceList.add(new Services(id, name, description, price));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serviceList;
    }
    public void updateService(Services service) {
        String query = "UPDATE services SET serviceName = ?, serviceDescription = ?, servicePrice = ? WHERE serviceID = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, service.getServiceName());
            pst.setString(2, service.getServiceDescription());
            pst.setDouble(3, service.getServicePrice());
            pst.setInt(4, service.getServiceID());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addService(Services service) {
        String query = "INSERT INTO services (serviceName, serviceDescription, servicePrice) VALUES (?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, service.getServiceName());
            pst.setString(2, service.getServiceDescription());
            pst.setDouble(3, service.getServicePrice());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
