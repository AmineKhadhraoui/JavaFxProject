package org.JavaFxProject.Hotel.Controllers;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.JavaFxProject.Hotel.Entities.Staff;
import org.JavaFxProject.Hotel.Services.StaffService;
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

public class StaffController implements Initializable {

    @FXML
    private TableColumn<Staff, Integer> staffID;

    @FXML
    private TableColumn<Staff, String> staffName;

    @FXML
    private TableColumn<Staff, Integer> staffAge;

    @FXML
    private TableColumn<Staff, String> staffGender;

    @FXML
    private TableColumn<Staff, String> staffPosition;

    @FXML
    private TableColumn<Staff, String> staffPhone;

    @FXML
    private TableColumn<Staff, String> staffEmail;

    @FXML
    private TableView<Staff> staffTable;

    @FXML
    private TextField search;

    private StaffService staffService;
    private ObservableList<Staff> staffList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        staffService = new StaffService();
        staffList = FXCollections.observableArrayList();

        staffID.setCellValueFactory(new PropertyValueFactory<>("staffID"));
        staffName.setCellValueFactory(new PropertyValueFactory<>("name"));
        staffAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        staffGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        staffPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        staffPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        staffEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        loadStaff();
        staffTable.setItems(staffList);
    }


    private void loadStaff() {
        staffList.clear();
        List<Staff> staff = staffService.getAllStaff();
        staffList.addAll(staff);
    }


    @FXML
    public void handleAddAction(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org.JavaFxProject.Hotel/AddStaff.fxml"));
        Parent root = loader.load();

        AddStaffController controller = loader.getController();
        controller.setStaffList(staffList);

        Stage stage = new Stage();
        controller.setDialogStage(stage);
        stage.setScene(new Scene(root));
        stage.show();
    }


    @FXML
    public void handleEditAction(javafx.event.ActionEvent actionEvent) throws IOException {
        Staff selectedStaff = staffTable.getSelectionModel().getSelectedItem();
        if (selectedStaff != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org.JavaFxProject.Hotel/EditStaff.fxml"));
            Parent root = loader.load();

            EditStaffController controller = loader.getController();
            controller.setStaff(selectedStaff);
            controller.setStaffList(staffList);

            Stage stage = new Stage();
            controller.setDialogStage(stage);
            stage.setScene(new Scene(root));
            stage.show();
        }
    }


    @FXML
    public void handleDeleteAction(javafx.event.ActionEvent actionEvent) {
        Staff selectedStaff = staffTable.getSelectionModel().getSelectedItem();
        if (selectedStaff != null) {
            staffService.deleteStaff(selectedStaff.getStaffID());
            loadStaff();
        }
    }

    /**
     * Gère le double-clic sur un élément de la TableView.
     */
    @FXML
    public void clickItem(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            Staff selectedStaff = staffTable.getSelectionModel().getSelectedItem();
            if (selectedStaff != null) {
                if (selectedStaff.getAge() > 30) {
                    Stage add = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/org.JavaFxProject.Hotel/staffdetails.fxml"));
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
            String searchText = search.getText().trim();
            if (searchText.isEmpty()) {
                loadStaff();
            } else {
                List<Staff> searchResults = staffService.searchStaffByName(searchText);
                staffList.clear();
                staffList.addAll(searchResults);
            }
        }
    }
}