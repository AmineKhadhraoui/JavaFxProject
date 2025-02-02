package org.JavaFxProject.Hotel.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.JavaFxProject.Hotel.Entities.Reservation;
import org.JavaFxProject.Hotel.Services.CheckOutService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CheckOutController implements Initializable {
    @FXML private TableColumn<Reservation, String> checkIn;
    @FXML private TableColumn<Reservation, String> checkOut;
    @FXML private TableColumn<Reservation, String> customerName;
    @FXML private TableColumn<Reservation, String> roomNumber;
    @FXML private TableView<Reservation> roomTable;
    @FXML private TextField search;
    @FXML private TableColumn<Reservation, String> totalDays;
    @FXML private TableColumn<Reservation, String> totalPrice;
    @FXML private TableColumn<Reservation, String> status;
    @FXML private ComboBox<String> sort;

    private CheckOutService checkOutService;
    private ObservableList<Reservation> reservations;
    private List<Reservation> reservationList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkOutService = new CheckOutService();
        reservations = FXCollections.observableArrayList();

        initializeComboBox();
        initializeTableColumns();
        loadReservations();
    }

    private void initializeComboBox() {
        sort.getItems().addAll("Today", "Checked In", "Checked Out");
    }

    private void initializeTableColumns() {
        roomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        checkIn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        checkOut.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        totalDays.setCellValueFactory(new PropertyValueFactory<>("totalDays"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadReservations() {
        reservationList = checkOutService.getAllReservations();
        reservations.clear();
        reservations.addAll(reservationList);
        roomTable.setItems(reservations);
    }

    @FXML
    public void handleSearchKey(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            String searchText = search.getText();
            List<Reservation> searchResults = checkOutService.getReservationsByRoomNumber(searchText, reservationList);
            reservations.clear();
            reservations.addAll(searchResults);
        }
    }

    @FXML
    public void handleComboboxSelection() {
        String selectedFilter = sort.getValue();
        if (selectedFilter != null) {
            List<Reservation> filteredResults = checkOutService.filterReservations(selectedFilter, reservationList);
            reservations.clear();
            reservations.addAll(filteredResults);
        }
    }

    @FXML
    public void clickItem(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            Reservation selectedReservation = roomTable.getSelectionModel().getSelectedItem();
            if (selectedReservation != null) {
                openBillInfoWindow(selectedReservation);
            }
        }
    }

    private void openBillInfoWindow(Reservation selectedReservation) throws IOException {
        BillInfoController.setSelectedReservationID(selectedReservation.getResID());
        BillInfoController.setSelectedReservation(selectedReservation);

        Stage billInfoStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/org.JavaFxProject.Hotel/billinfo.fxml"));
        billInfoStage.setScene(new Scene(root));
        billInfoStage.show();
    }

    public void updateTable(Reservation reservation) {
        checkOutService.updateReservationStatus(reservation, "Checked Out");
        loadReservations(); // Refresh the table
    }
}
