package org.JavaFxProject.Hotel.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.StageStyle;
import org.JavaFxProject.Hotel.Entities.Customer;
import org.JavaFxProject.Hotel.Services.CheckInService;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class CheckInController implements Initializable {
    @FXML private Label amount;
    @FXML private Label days;
    @FXML private Label price;
    @FXML private TextField cEmail;
    @FXML private TextField cGender;
    @FXML private TextField cName;
    @FXML private TextField cNationality;
    @FXML private TextField cNumber;
    @FXML private TextField cPhone;
    @FXML private Button submit;
    @FXML private DatePicker inDate;
    @FXML private DatePicker outDate;
    @FXML private ComboBox<String> rNo;
    @FXML private ComboBox<String> rType;

    private CheckInService checkInService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkInService = new CheckInService();
        loadRoomTypes();
    }

    private void loadRoomTypes() {
        rType.getItems().clear();
        rType.getItems().addAll(checkInService.getRoomTypes());
    }

    @FXML
    public void handleSelectRoomType() {
        if (rType.getValue() != null && !rType.getValue().isEmpty()) {
            rNo.getItems().clear();
            rNo.getItems().addAll(checkInService.getAvailableRoomNumbers(rType.getValue()));
        }
    }

    @FXML
    public void handleSelectRoomNumber() {
        if (rNo.getValue() != null && !rNo.getValue().isEmpty()) {
            double roomPrice = checkInService.getRoomPrice(rNo.getValue());
            price.setText("Price: " + roomPrice);
        }
    }

    @FXML
    public void handleCheckInPick() {
        // This method can be expanded if needed
    }

    @FXML
    public void handleCheckOutPick() {
        if (inDate.getValue() != null && outDate.getValue() != null) {
            int daysDiff = outDate.getValue().compareTo(inDate.getValue());
            days.setText("Total days: " + daysDiff);

            if (!price.getText().isEmpty()) {
                double priceVal = Double.parseDouble(price.getText().replace("Price: ", ""));
                amount.setText("Total Amount: " + (priceVal * daysDiff));
            }
        }
    }

    @FXML
    public void handleSubmitAction() {
        if (validateInputs()) {
            Customer customer = new Customer(
                    Integer.parseInt(cNumber.getText()),
                    cName.getText(),
                    cEmail.getText(),
                    cPhone.getText(),
                    cGender.getText(),
                    cNationality.getText()
            );

            String checkInDate = inDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String checkOutDate = outDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            boolean success = checkInService.processCheckIn(customer, rNo.getValue(), checkInDate, checkOutDate);

            if (success) {
                showAlert("Check In Successful", "Message");
                clearFields();
            } else {
                showAlert("Check In Failed", "Error Message");
            }
        } else {
            showAlert("Every Field is required", "Error Message");
        }
    }

    private boolean validateInputs() {
        return !cName.getText().isEmpty() &&
                !cEmail.getText().isEmpty() &&
                !cGender.getText().isEmpty() &&
                !cNationality.getText().isEmpty() &&
                !cNumber.getText().isEmpty() &&
                !cPhone.getText().isEmpty() &&
                rNo.getValue() != null &&
                inDate.getValue() != null &&
                outDate.getValue() != null;
    }

    private void clearFields() {
        cName.clear();
        cEmail.clear();
        cGender.clear();
        cNationality.clear();
        cNumber.clear();
        cPhone.clear();
        rNo.getSelectionModel().clearSelection();
        rType.getSelectionModel().clearSelection();
        inDate.setValue(null);
        outDate.setValue(null);
        price.setText("");
        days.setText("");
        amount.setText("");
    }

    private void showAlert(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
