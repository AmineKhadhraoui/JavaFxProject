package org.JavaFxProject.Hotel.Controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.JavaFxProject.Hotel.Entities.Staff;
import org.JavaFxProject.Hotel.Services.StaffService;

public class EditStaffController {

    @FXML
    private TextField staffID;
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
    private Staff currentStaff;

    @FXML
    public void initialize() {
        staffService = new StaffService();
    }

    public void setStaff(Staff staff) {
        this.currentStaff = staff;
        staffID.setText(String.valueOf(staff.getStaffID()));
        staffName.setText(staff.getName());
        staffAge.setText(String.valueOf(staff.getAge()));
        staffGender.setText(staff.getGender());
        staffPosition.setText(staff.getPosition());
        staffPhone.setText(staff.getPhone());
        staffEmail.setText(staff.getEmail());
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setStaffList(ObservableList<Staff> staffList) {
        this.staffList = staffList;
    }

    @FXML
    public void handleSaveAction() {
        try {
            if (staffName.getText().isEmpty() || staffAge.getText().isEmpty() || staffGender.getText().isEmpty()
                    || staffPosition.getText().isEmpty() || staffPhone.getText().isEmpty() || staffEmail.getText().isEmpty()) {
                return;
            }

            Staff updatedStaff = new Staff(
                    Integer.parseInt(staffID.getText()),
                    staffName.getText(),
                    Integer.parseInt(staffAge.getText()),
                    staffGender.getText(),
                    staffPosition.getText(),
                    staffPhone.getText(),
                    staffEmail.getText()
            );

            staffService.updateStaff(updatedStaff);

            if (staffList != null && currentStaff != null) {
                int index = staffList.indexOf(currentStaff);
                if (index >= 0) {
                    staffList.set(index, updatedStaff);
                }
            }

            if (dialogStage != null) {
                dialogStage.close();
            }
        } catch (NumberFormatException e) {

        } catch (Exception e) {
        }
    }
}