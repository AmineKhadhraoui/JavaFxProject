package org.JavaFxProject.Hotel.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.JavaFxProject.Hotel.Entities.Staff;
import org.JavaFxProject.Hotel.Services.StaffService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StaffController implements Initializable {

    @FXML
    private TableView<Staff> staffTable;

    @FXML
    private TableColumn<Staff, Integer> colStaffID;

    @FXML
    private TableColumn<Staff, String> colName;

    @FXML
    private TableColumn<Staff, Integer> colAge;

    @FXML
    private TableColumn<Staff, String> colGender;

    @FXML
    private TableColumn<Staff, String> colPosition;

    @FXML
    private TableColumn<Staff, String> colPhone;

    @FXML
    private TableColumn<Staff, String> colEmail;

    @FXML
    private TextField txtName, txtAge, txtGender, txtPosition, txtPhone, txtEmail;

    @FXML
    private Button btnAdd, btnUpdate, btnDelete;

    private StaffService staffService;
    private ObservableList<Staff> staffList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        staffService = new StaffService();
        staffList = FXCollections.observableArrayList();

        colStaffID.setCellValueFactory(new PropertyValueFactory<>("staffID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        loadStaff();
        staffTable.setItems(staffList);
    }

    private void loadStaff() {
        staffList.clear();
        List<Staff> staffs = staffService.getAllStaff();
        staffList.addAll(staffs);
    }

    @FXML
    private void addStaff() {
        Staff staff = new Staff(0, txtName.getText(), Integer.parseInt(txtAge.getText()), txtGender.getText(),
                txtPosition.getText(), txtPhone.getText(), txtEmail.getText());
        staffService.addStaff(staff);
        loadStaff();
    }

    @FXML
    private void updateStaff() {
        Staff selectedStaff = staffTable.getSelectionModel().getSelectedItem();
        if (selectedStaff != null) {
            selectedStaff.setName(txtName.getText());
            selectedStaff.setAge(Integer.parseInt(txtAge.getText()));
            selectedStaff.setGender(txtGender.getText());
            selectedStaff.setPosition(txtPosition.getText());
            selectedStaff.setPhone(txtPhone.getText());
            selectedStaff.setEmail(txtEmail.getText());
            staffService.updateStaff(selectedStaff);
            loadStaff();
        }
    }

    @FXML
    private void deleteStaff() {
        Staff selectedStaff = staffTable.getSelectionModel().getSelectedItem();
        if (selectedStaff != null) {
            staffService.deleteStaff(selectedStaff.getStaffID());
            loadStaff();
        }
    }

    @FXML
    private void selectStaff(MouseEvent event) {
        Staff selectedStaff = staffTable.getSelectionModel().getSelectedItem();
        if (selectedStaff != null) {
            txtName.setText(selectedStaff.getName());
            txtAge.setText(String.valueOf(selectedStaff.getAge()));
            txtGender.setText(selectedStaff.getGender());
            txtPosition.setText(selectedStaff.getPosition());
            txtPhone.setText(selectedStaff.getPhone());
            txtEmail.setText(selectedStaff.getEmail());
        }
    }
}

