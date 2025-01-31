package org.JavaFxProject.Hotel.Controllers;

import org.JavaFxProject.Hotel.Entities.Room;
import org.JavaFxProject.Hotel.Services.RoomService;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RoomController implements Initializable {

    @FXML
    private TableColumn<Room, String> price;

    @FXML
    private TableColumn<Room, String> roomNumber;

    @FXML
    private TableView<Room> roomTable;

    @FXML
    private TableColumn<Room, String> roomType;

    @FXML
    private TextField search;

    @FXML
    private TableColumn<Room, String> status;

    private RoomService roomService;
    private ObservableList<Room> rooms;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roomService = new RoomService();
        rooms = FXCollections.observableArrayList();

        roomNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        roomType.setCellValueFactory(new PropertyValueFactory<>("type"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadRooms();
        roomTable.setItems(rooms);
    }

    private void loadRooms() {
        rooms.clear();
        List<Room> roomList = roomService.getAllRooms();
        rooms.addAll(roomList);
    }

    @FXML
    public void handleAddAction(javafx.event.ActionEvent actionEvent) throws IOException {
        Stage add = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/org.JavaFxProject.Hotel/addroom.fxml"));
        Scene scene = new Scene(root);
        add.setScene(scene);
        add.show();
    }

    @FXML
    public void clickItem(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            Room selectedRoom = roomTable.getSelectionModel().getSelectedItem();
            if (selectedRoom != null && selectedRoom.getStatus().equals("Booked")) {
               // CustomerController.setSelectedRoomNumber(selectedRoom.getNumber());
                Stage add = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("customerinfo.fxml"));
                Scene scene = new Scene(root);
                add.setScene(scene);
                add.show();
            }
        }
    }

    @FXML
    public void handleSearchKey(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            String searchText = search.getText();
            if (searchText.isEmpty()) {
                loadRooms();
            } else {
                List<Room> searchResults = roomService.searchRoomsByNumber(Integer.parseInt(searchText));
                rooms.clear();
                rooms.addAll(searchResults);
            }
        }
    }
}

