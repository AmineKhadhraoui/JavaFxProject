package org.JavaFxProject.Hotel.Controllers;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.JavaFxProject.Hotel.Entities.Services;
import org.JavaFxProject.Hotel.Services.ServiceService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ServiceController implements Initializable {

    @FXML
    private TableColumn<Services, Integer> serviceID;

    @FXML
    private TableColumn<Services, String> serviceName;

    @FXML
    private TableColumn<Services, String> serviceDescription;

    @FXML
    private TableColumn<Services, Double> servicePrice;

    @FXML
    private TableView<Services> serviceTable;

    @FXML
    private TextField search;

    private ServiceService serviceService;
    private ObservableList<Services> services;
// comment
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serviceService = new ServiceService();
        services = FXCollections.observableArrayList();

        serviceID.setCellValueFactory(new PropertyValueFactory<>("serviceID"));
        serviceName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        serviceDescription.setCellValueFactory(new PropertyValueFactory<>("serviceDescription"));
        servicePrice.setCellValueFactory(new PropertyValueFactory<>("servicePrice"));

        loadServices();
        serviceTable.setItems(services);
    }

    private void loadServices() {
        services.clear();
        List<Services> serviceList = serviceService.getAllServices();
        services.addAll(serviceList);
    }

    @FXML
    public void handleAddAction(javafx.event.ActionEvent actionEvent) throws IOException {
        Stage add = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/org.JavaFxProject.Hotel/AddService.fxml"));
        Scene scene = new Scene(root);
        add.setScene(scene);
        add.show();
    }

    @FXML
    public void handleEditAction(javafx.event.ActionEvent actionEvent) throws IOException {
        Services selectedService = serviceTable.getSelectionModel().getSelectedItem();
        if (selectedService != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org.JavaFxProject.Hotel/EditService.fxml"));
            Parent root = loader.load();

            // Pass the selected service to the EditServiceController
            EditServiceController controller = loader.getController();
            controller.setService(selectedService);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    @FXML
    public void handleDeleteAction(javafx.event.ActionEvent actionEvent) {
        Services selectedService = serviceTable.getSelectionModel().getSelectedItem();
        if (selectedService != null) {
            serviceService.deleteService(selectedService.getServiceID());
            loadServices();  // Reload the table after deletion
        }
    }

    @FXML
    public void clickItem(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            Services selectedService = serviceTable.getSelectionModel().getSelectedItem();
            if (selectedService != null) {
                // Example: Check if the service price is above a certain threshold
                if (selectedService.getServicePrice() > 100) {
                    // Load a new scene for the service details
                    Stage add = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/org.JavaFxProject.Hotel/servicedetails.fxml"));
                    Scene scene = new Scene(root);
                    add.setScene(scene);
                    add.show();
                }
            }
        }
    }

    @FXML
    public void handleSearchKey(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            String searchText = search.getText();
            if (searchText.isEmpty()) {
                loadServices();
            } else {
                try {
                    int searchID = Integer.parseInt(searchText);
                    List<Services> searchResults = serviceService.searchServiceByID(searchID);
                    services.clear();
                    services.addAll(searchResults);
                } catch (NumberFormatException e) {
                    // Ignore invalid input
                }
            }
        }
    }
}
