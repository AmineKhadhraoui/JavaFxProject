package org.JavaFxProject.Hotel.Controllers;

import org.JavaFxProject.Hotel.Entities.Bill;
import org.JavaFxProject.Hotel.Services.BillService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BillController implements Initializable {

    @FXML
    private TableColumn<Bill, String> Amount;

    @FXML
    private TableColumn<Bill, String> Date;

    @FXML
    private TableColumn<Bill, String> billID;

    @FXML
    private TableColumn<Bill, String> cusIDNumber;

    @FXML
    private TableColumn<Bill, String> customerName;

    @FXML
    private TableColumn<Bill, String> roomNumber;

    @FXML
    private TableView<Bill> billTable;

    @FXML
    private TextField search;

    private BillService billService;
    private ObservableList<Bill> bills;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        billService = new BillService();
        bills = FXCollections.observableArrayList();

        roomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        cusIDNumber.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        billID.setCellValueFactory(new PropertyValueFactory<>("billID"));
        Amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        Date.setCellValueFactory(new PropertyValueFactory<>("date"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        loadBills();
        billTable.setItems(bills);
    }

    private void loadBills() {
        bills.clear();
        List<Bill> billList = billService.getAllBills();
        bills.addAll(billList);
    }

    @FXML
    public void handleSearchKey(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            String searchText = search.getText();
            if (searchText.isEmpty()) {
                loadBills();
            } else {
                List<Bill> searchResults = billService.searchBillsByDate(searchText);
                bills.clear();
                bills.addAll(searchResults);
            }
        }
    }

    @FXML
    public void clickBill(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            Bill selectedBill = billTable.getSelectionModel().getSelectedItem();
            if (selectedBill != null) {
                String path = "C:\\Users\\Mr.Cuong\\IdeaProjects\\HotelManagement\\res\\";
                File file = new File(path + "bill" + selectedBill.getBillID() + ".pdf");
                if (file.toString().endsWith(".pdf")) {
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file);
                } else {
                    Desktop desktop = Desktop.getDesktop();
                    desktop.open(file);
                }
            }
        }
    }
}

