package org.JavaFxProject.Hotel.Controllers;
import org.JavaFxProject.Hotel.Entities.Reservation;
import org.JavaFxProject.Hotel.Services.BillService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BillInfoController implements Initializable {

    public static int selectedResID;
    public static Reservation selectedReservation;

    @FXML
    private TextField Amount;
    @FXML
    private Button print;
    @FXML
    private TextField customerIDNumber;
    @FXML
    private TextField customerName;
    @FXML
    private TextField roomNumber;

    private BillService billService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        billService = new BillService();
        if (selectedResID != 0) {
            Reservation reservation = billService.getReservationDetails(selectedResID);
            if (reservation != null) {
                roomNumber.setText(String.valueOf(reservation.getRoomNumber()));
                customerIDNumber.setText(String.valueOf(reservation.getCustomerIDNumber()));
                customerName.setText(reservation.getCustomerName());
                Amount.setText(String.valueOf(reservation.getTotalPrice()));
            }
            roomNumber.setEditable(false);
            customerIDNumber.setEditable(false);
            customerName.setEditable(false);
            Amount.setEditable(false);
        }
    }

    @FXML
    public void handlePrintAction(javafx.event.ActionEvent actionEvent) throws IOException {
        if (!selectedReservation.getStatus().equals("Checked Out")) {
            billService.createBill(selectedReservation.getResID(), selectedReservation.getCheckOutDate(), selectedReservation.getTotalPrice());
            billService.updateRoomStatus(selectedReservation.getRoomNumber());
            billService.updateReservationStatus(selectedReservation.getResID());
        }

        String billDetails = billService.getBillDetails(selectedResID);
        if (billDetails != null) {
            createBill(billDetails, selectedResID);
        }
    }

    private void createBill(String billDetails, int billID) throws IOException {
        String path = "C:\\Users\\Mr.Cuong\\IdeaProjects\\HotelManagement\\res\\";
        try (FileOutputStream fos = new FileOutputStream(path + "bill" + billID + ".pdf")) {
            fos.write(billDetails.getBytes());
        }

        File file = new File(path + "bill" + billID + ".pdf");
        if (file.toString().endsWith(".pdf")) {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file);
        } else {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        }
    }
}
