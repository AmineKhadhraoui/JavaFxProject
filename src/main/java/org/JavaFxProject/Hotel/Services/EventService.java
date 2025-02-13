package org.JavaFxProject.Hotel.Services;

import org.JavaFxProject.Hotel.Entities.Event;
import org.JavaFxProject.Hotel.Utils.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EventService {

    private Connection connection;

    public EventService() {
        DBConnection db = new DBConnection();
        this.connection = db.getConnection();
    }

    /**
     * Ajoute un nouvel événement dans la base de données.
     */
    public void addEvent(Event event) {
        String query = "INSERT INTO events (eventName, eventDate, eventDescription) "
                + "VALUES (?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, event.getEventName());
            // Conversion LocalDate vers java.sql.Date
            pst.setDate(2, Date.valueOf(event.getEventDate()));
            pst.setString(3, event.getEventDescription());

            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Met à jour un événement existant dans la base de données.
     */
    public void updateEvent(Event event) {
        String query = "UPDATE events "
                + "SET eventName = ?, eventDate = ?, eventDescription = ? "
                + "WHERE eventID = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, event.getEventName());
            pst.setDate(2, Date.valueOf(event.getEventDate()));
            pst.setString(3, event.getEventDescription());
            pst.setInt(4, event.getEventID());

            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Supprime un événement de la base de données via son ID.
     */
    public void deleteEvent(int eventID) {
        String query = "DELETE FROM events WHERE eventID = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, eventID);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Récupère la liste de tous les événements.
     */
    public List<Event> getAllEvents() {
        List<Event> eventsList = new ArrayList<>();
        String query = "SELECT * FROM events";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                // Conversion java.sql.Date -> LocalDate
                Event event = new Event(
                        rs.getInt("eventID"),
                        rs.getString("eventName"),
                        rs.getDate("eventDate").toLocalDate(),
                        rs.getString("eventDescription")
                );
                eventsList.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventsList;
    }

    /**
     * Recherche des événements par nom (ou partie de nom).
     */
    public List<Event> searchEventsByName(String name) {
        List<Event> searchResults = new ArrayList<>();
        String query = "SELECT * FROM events WHERE eventName LIKE ?";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, "%" + name + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Event event = new Event(
                        rs.getInt("eventID"),
                        rs.getString("eventName"),
                        rs.getDate("eventDate").toLocalDate(),
                        rs.getString("eventDescription")
                );
                searchResults.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchResults;
    }
}
