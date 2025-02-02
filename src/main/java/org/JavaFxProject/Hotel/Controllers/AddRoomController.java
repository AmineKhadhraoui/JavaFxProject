package org.JavaFxProject.Hotel.Controllers;

import org.JavaFxProject.Hotel.Entities.Room;
import org.JavaFxProject.Hotel.Services.RoomService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

    public class AddRoomController implements Initializable {

        @FXML
        private Button add;

        @FXML
        private TextField number;

        @FXML
        private TextField price;

        @FXML
        private TextField type;

        private RoomService roomService;

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            roomService = new RoomService();
        }

        @FXML
        public void handleAddAction(javafx.event.ActionEvent actionEvent) {
            int roomNumber = Integer.parseInt(number.getText());
            int roomPrice = Integer.parseInt(price.getText());
            String roomType = type.getText();
            Room newRoom = new Room(roomNumber, roomPrice, roomType, "Not Booked");
            roomService.addRoom(newRoom);
        }
    }


