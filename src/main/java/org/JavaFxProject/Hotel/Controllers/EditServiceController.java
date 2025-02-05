package org.JavaFxProject.Hotel.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.JavaFxProject.Hotel.Entities.Services;
import org.JavaFxProject.Hotel.Services.ServiceService;

public class EditServiceController {

    @FXML
    private TextField serviceID;
    @FXML
    private TextField serviceName;
    @FXML
    private TextField serviceDescription;
    @FXML
    private TextField servicePrice;

    private ServiceService serviceService;

    @FXML
    public void initialize() {
        serviceService = new ServiceService();
    }

    public void setService(Services service) {
        serviceID.setText(String.valueOf(service.getServiceID()));
        serviceName.setText(service.getServiceName());
        serviceDescription.setText(service.getServiceDescription());
        servicePrice.setText(String.valueOf(service.getServicePrice()));
    }

    @FXML
    public void handleSaveAction() {
        Services updatedService = new Services(
                Integer.parseInt(serviceID.getText()),
                serviceName.getText(),
                serviceDescription.getText(),
                Double.parseDouble(servicePrice.getText())
        );
        serviceService.updateService(updatedService);
    }

}
