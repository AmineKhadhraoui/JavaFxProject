package org.JavaFxProject.Hotel.Controllers;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.JavaFxProject.Hotel.Entities.Customer;
import org.JavaFxProject.Hotel.Entities.Reservation;
import org.JavaFxProject.Hotel.Services.CustomerService;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    public static int selectedRoomNumber;

    @FXML
    private TextField email;
    @FXML
    private TextField gender;
    @FXML
    private TextField inDate;
    @FXML
    private TextField name;
    @FXML
    private TextField nationality;
    @FXML
    private TextField outDate;
    @FXML
    private TextField phone;
    @FXML
    private TextField price;

    private CustomerService customerService;

    public static void setSelectedRoomNumber(int selectedRoomNumber) {
        CustomerController.selectedRoomNumber = selectedRoomNumber;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerService = new CustomerService();
        if (selectedRoomNumber != 0) {
            Customer customer = customerService.getCustomerDetails(selectedRoomNumber);
            Reservation reservation = customerService.getReservationDetails(selectedRoomNumber);

            if (customer != null && reservation != null) {
                price.setText(String.valueOf(reservation.getTotalPrice()));
                name.setText(customer.getCustomerName());
                email.setText(customer.getCustomerEmail());
                phone.setText(customer.getCustomerPhoneNo());
                gender.setText(customer.getCustomerGender());
                nationality.setText(customer.getCustomerNationality());
                inDate.setText(reservation.getCheckInDate());
                outDate.setText(reservation.getCheckOutDate());
            }

            price.setEditable(false);
            name.setEditable(false);
            email.setEditable(false);
            phone.setEditable(false);
            gender.setEditable(false);
            nationality.setEditable(false);
            inDate.setEditable(false);
            outDate.setEditable(false);
        }
    }
}
