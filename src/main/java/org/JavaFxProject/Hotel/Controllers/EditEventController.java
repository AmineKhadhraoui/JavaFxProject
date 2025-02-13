package org.JavaFxProject.Hotel.Controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.JavaFxProject.Hotel.Entities.Event;
import org.JavaFxProject.Hotel.Services.EventService;

import java.time.LocalDate;

public class EditEventController {

    @FXML
    private TextField eventID;
    @FXML
    private TextField eventName;
    @FXML
    private TextField eventDate;
    @FXML
    private TextField eventDescription;

    private EventService eventService;
    private Stage dialogStage;
    private ObservableList<Event> eventList;
    private Event currentEvent; // L'événement en cours de modification

    @FXML
    public void initialize() {
        eventService = new EventService();
    }

    /**
     * Appelé depuis l'EventController après avoir chargé le FXML.
     * On remplit les champs à l'écran avec les infos de l'événement sélectionné.
     */
    public void setEvent(Event event) {
        this.currentEvent = event;

        // On convertit les valeurs de l'objet en texte
        eventID.setText(String.valueOf(event.getEventID()));
        eventName.setText(event.getEventName());
        // Si eventDate est de type LocalDate, vous pouvez faire :
        eventDate.setText(String.valueOf(event.getEventDate()));
        eventDescription.setText(event.getEventDescription());
    }

    /**
     * Permet de mémoriser la fenêtre (Stage) de ce formulaire,
     * afin de la fermer après validation ou annulation.
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Récupère la liste observable de l'EventController,
     * pour mettre à jour la TableView après modification.
     */
    public void setEventList(ObservableList<Event> eventList) {
        this.eventList = eventList;
    }

    /**
     * Méthode déclenchée par un bouton "Enregistrer"/"Save".
     */
    @FXML
    public void handleSaveAction() {
        try {
            if (eventName.getText().isEmpty() || eventDate.getText().isEmpty()) {
                return;
            }


            LocalDate dateParsed = LocalDate.parse(eventDate.getText().trim());

            Event updatedEvent = new Event(
                    Integer.parseInt(eventID.getText().trim()),
                    eventName.getText().trim(),
                    dateParsed,
                    eventDescription.getText().trim()
            );


            eventService.updateEvent(updatedEvent);


            if (eventList != null && currentEvent != null) {
                int index = eventList.indexOf(currentEvent);
                if (index >= 0) {
                    eventList.set(index, updatedEvent);
                }
            }

            if (dialogStage != null) {
                dialogStage.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
