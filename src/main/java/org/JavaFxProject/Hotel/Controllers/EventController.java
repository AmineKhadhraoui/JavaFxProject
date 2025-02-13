package org.JavaFxProject.Hotel.Controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.JavaFxProject.Hotel.Entities.Event;
import org.JavaFxProject.Hotel.Services.EventService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EventController implements Initializable {

    @FXML
    private TableView<Event> eventTable;

    @FXML
    private TableColumn<Event, Integer> eventID;

    @FXML
    private TableColumn<Event, String> eventName;

    @FXML
    private TableColumn<Event, String> eventDate;
    // Vous pouvez utiliser TableColumn<Event, LocalDate>
    // si votre entité "Event" stocke un LocalDate au lieu d'un String.

    @FXML
    private TableColumn<Event, String> eventDescription;

    @FXML
    private TextField search; // Champ de recherche

    private EventService eventService;
    private ObservableList<Event> eventList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        eventService = new EventService();
        eventList = FXCollections.observableArrayList();

        // Associe chaque colonne à la propriété correspondante de l'entité Event
        eventID.setCellValueFactory(new PropertyValueFactory<>("eventID"));
        eventName.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        eventDate.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        eventDescription.setCellValueFactory(new PropertyValueFactory<>("eventDescription"));

        // Charge la liste initiale des événements
        loadEvents();

        // Injecte la liste observable dans la TableView
        eventTable.setItems(eventList);
    }

    /**
     * Récupère la liste de tous les événements via le service
     * et la stocke dans l'ObservableList pour affichage.
     */
    private void loadEvents() {
        eventList.clear();
        List<Event> events = eventService.getAllEvents(); // À implémenter dans EventService
        eventList.addAll(events);
    }

    /**
     * Bouton "Ajouter" : Ouvre la fenêtre AddEvent.fxml
     */
    @FXML
    public void handleAddAction(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org.JavaFxProject.Hotel/AddEvent.fxml"));
        Parent root = loader.load();

        // On suppose que vous avez un AddEventController similaire à AddStaffController
        AddEventController controller = loader.getController();
        controller.setEventList(eventList);

        Stage stage = new Stage();
        controller.setDialogStage(stage);
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Bouton "Modifier" : Ouvre la fenêtre EditEvent.fxml
     * pour l’événement sélectionné dans la table
     */
    @FXML
    public void handleEditAction(javafx.event.ActionEvent actionEvent) throws IOException {
        Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org.JavaFxProject.Hotel/EditEvent.fxml"));
            Parent root = loader.load();

            // On suppose que vous avez un EditEventController similaire à EditStaffController
            EditEventController controller = loader.getController();
            controller.setEvent(selectedEvent);
            controller.setEventList(eventList);

            Stage stage = new Stage();
            controller.setDialogStage(stage);
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    /**
     * Bouton "Supprimer" : Supprime l’événement sélectionné.
     */
    @FXML
    public void handleDeleteAction(javafx.event.ActionEvent actionEvent) {
        Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            eventService.deleteEvent(selectedEvent.getEventID()); // À implémenter
            loadEvents(); // Recharge la table
        }
    }

    /**
     * Gère le double-clic sur un élément de la TableView (exemple).
     * Vous pouvez adapter la logique selon vos besoins.
     */
    @FXML
    public void clickItem(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                // Exemple de condition : si la date est après une date précise,
                // on ouvre un détail, etc.
                // if (selectedEvent.getEventDate().isAfter(LocalDate.now())) { ... }

                // Ou simplement ouvrir un détail :
                Stage detailsStage = new Stage();
                Parent root = FXMLLoader.load(
                        getClass().getResource("/org.JavaFxProject.Hotel/EventDetails.fxml"));
                Scene scene = new Scene(root);
                detailsStage.setScene(scene);
                detailsStage.show();
            }
        }
    }

    /**
     * Recherche par nom (ou autre champ) à chaque frappe dans le TextField "search".
     */
    @FXML
    public void handleSearchKey(KeyEvent keyEvent) {
        if (keyEvent.getEventType() == KeyEvent.KEY_RELEASED) {
            String searchText = search.getText().trim();
            if (searchText.isEmpty()) {
                // Si champ vide, on recharge la liste complète
                loadEvents();
            } else {
                // Sinon, on effectue une recherche via le service
                List<Event> searchResults = eventService.searchEventsByName(searchText);
                eventList.clear();
                eventList.addAll(searchResults);
            }
        }
    }
}
