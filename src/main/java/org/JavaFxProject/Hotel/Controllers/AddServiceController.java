package org.JavaFxProject.Hotel.Controllers;

import org.JavaFxProject.Hotel.Entities.Services;
import org.JavaFxProject.Hotel.Services.ServiceService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddServiceController implements Initializable {

    @FXML
    private Button add;

    @FXML
    private TextField serviceName;

    @FXML
    private TextField serviceDescription;

    @FXML
    private TextField servicePrice;

    private ServiceService serviceService;
// comment
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serviceService = new ServiceService();
    }

    @FXML
    public void handleAddAction(javafx.event.ActionEvent actionEvent) {
        // Check for empty fields
        if (serviceName.getText().isEmpty() || serviceDescription.getText().isEmpty() || servicePrice.getText().isEmpty()) {
            System.out.println("All fields must be filled out.");
            return;
        }

        String name = serviceName.getText();
        String description = serviceDescription.getText();
        double price = 0;

        // Check if price is a valid number
        try {
            price = Double.parseDouble(servicePrice.getText());
        } catch (NumberFormatException e) {
            System.out.println("Invalid price format.");
            return;
        }

        // Create a new Services object with the input data
        Services newService = new Services(0, name, description, price); // Assuming ID is generated by the DB
        serviceService.addService(newService); // Add the new service to the database

        System.out.println("Service added successfully.");
    }
}
