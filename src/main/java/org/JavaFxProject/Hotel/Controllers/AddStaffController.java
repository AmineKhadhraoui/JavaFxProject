package org.JavaFxProject.Hotel.Controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.JavaFxProject.Hotel.Entities.Staff;
import org.JavaFxProject.Hotel.Services.StaffService;

import java.net.URL;
import java.util.ResourceBundle;

public class AddStaffController implements Initializable {

    @FXML
    private Button add;

    @FXML
    private TextField staffName;

    @FXML
    private TextField staffAge;

    @FXML
    private TextField staffGender;

    @FXML
    private TextField staffPosition;

    @FXML
    private TextField staffPhone;

    @FXML
    private TextField staffEmail;

    private StaffService staffService;
    private Stage dialogStage;
    private ObservableList<Staff> staffList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        staffService = new StaffService();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setStaffList(ObservableList<Staff> staffList) {
        this.staffList = staffList;
    }

    @FXML
    public void handleAddAction(javafx.event.ActionEvent actionEvent) {
        if (staffName.getText().isEmpty() || staffAge.getText().isEmpty() || staffGender.getText().isEmpty()
                || staffPosition.getText().isEmpty() || staffPhone.getText().isEmpty() || staffEmail.getText().isEmpty()) {
            return;
        }

        String name = staffName.getText();
        int age;
        String gender = staffGender.getText();
        String position = staffPosition.getText();
        String phone = staffPhone.getText();
        String email = staffEmail.getText();

        try {
            age = Integer.parseInt(staffAge.getText());
        } catch (NumberFormatException e) {
            return;
        }

        Staff newStaff = new Staff(0, name, age, gender, position, phone, email);
        staffService.addStaff(newStaff);

        if (staffList != null) {
            staffList.add(newStaff);
        }

        if (dialogStage != null) {
            dialogStage.close();
        }
    }
}