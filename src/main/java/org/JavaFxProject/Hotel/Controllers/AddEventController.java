package org.JavaFxProject.Hotel.Controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.JavaFxProject.Hotel.Entities.Event;
import org.JavaFxProject.Hotel.Services.EventService;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddEventController implements Initializable {

    @FXML
    private Button addButton;  // Bouton "Ajouter"

    @FXML
    private TextField eventNameField;  // Champ texte pour le nom

    @FXML
    private TextField eventDateField;  // Champ texte pour la date (ou utilisez un DatePicker si vous préférez)

    @FXML
    private TextField eventDescriptionField; // Champ texte pour la description

    private EventService eventService;
    private Stage dialogStage;
    private ObservableList<Event> eventList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        eventService = new EventService();
    }

    /**
     * Affecte la fenêtre de dialogue pour pouvoir la fermer plus tard.
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Récupère la liste observable depuis le EventController.
     * On pourra ainsi y ajouter le nouvel événement (ou la recharger).
     */
    public void setEventList(ObservableList<Event> eventList) {
        this.eventList = eventList;
    }

    /**
     * Méthode déclenchée lors du clic sur le bouton "Ajouter".
     */
    @FXML
    public void handleAddAction(javafx.event.ActionEvent actionEvent) {
        // Vérification que tous les champs obligatoires sont remplis
        if (eventNameField.getText().isEmpty() || eventDateField.getText().isEmpty()) {
            // Vous pouvez afficher un message d'erreur ou bloquer l'action
            return;
        }

        String name = eventNameField.getText().trim();
        String dateString = eventDateField.getText().trim();
        String description = eventDescriptionField.getText().trim();

        // Convertir la date (String) en LocalDate
        LocalDate eventDate;
        try {
            eventDate = LocalDate.parse(dateString);
            // Format par défaut : "yyyy-MM-dd". Adaptez si nécessaire.
        } catch (Exception e) {
            // Si la conversion échoue, annulez ou gérez l'erreur
            System.out.println("Format de date invalide !");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Format de date invalide");
            alert.setHeaderText("Le format de la date n’est pas correct.");
            alert.setContentText("Veuillez saisir une date au format YYYY-MM-DD.");
            alert.showAndWait();
            return;
        }

        // Créer le nouvel objet Event
        // Supposons que le constructeur Event soit : new Event(int id, String name, LocalDate date, String desc)
        Event newEvent = new Event(0, name, eventDate, description);

        // Ajouter dans la base via le service
        eventService.addEvent(newEvent);

        // Mettre à jour la liste observable si on veut rafraîchir la TableView
        // Option 1 : recharger toute la liste depuis la BD
        // eventList.setAll(eventService.getAllEvents());

        // Option 2 : ajouter seulement l'événement créé (si l'ID auto-généré n'est pas indispensable tout de suite)
        if (eventList != null) {
            eventList.add(newEvent);
        }

        // Fermer la fenêtre
        if (dialogStage != null) {
            dialogStage.close();
        }
    }
}
