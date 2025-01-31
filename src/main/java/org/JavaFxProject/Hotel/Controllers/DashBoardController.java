package org.JavaFxProject.Hotel.Controllers;
import org.JavaFxProject.Hotel.Services.DashboardService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {
    @FXML
    private Label avaRoom;
//comment
    @FXML
    private Label bookedRoom;

    @FXML
    private Label earning;

    @FXML
    private Label pending;

    @FXML
    private Label totalRoom;

    private DashboardService dashboardService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dashboardService = new DashboardService();
        updateDashboard();
    }

    private void updateDashboard() {
        // Update room statistics
        totalRoom.setText(String.valueOf(dashboardService.getTotalRooms()));
        bookedRoom.setText(String.valueOf(dashboardService.getBookedRooms()));
        avaRoom.setText(String.valueOf(dashboardService.getAvailableRooms()));

        // Update earnings and pending amounts
        earning.setText(String.format("%.2f", dashboardService.getTotalEarnings()));
        pending.setText(String.format("%.2f", dashboardService.getPendingEarnings()));
    }
}
