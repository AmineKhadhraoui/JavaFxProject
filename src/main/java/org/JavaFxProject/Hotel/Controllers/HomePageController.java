package org.JavaFxProject.Hotel.Controllers;



import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    @FXML
    private Label adminName;

    @FXML
    private Button bill;

    @FXML
    private Button dash;

    @FXML
    private Button checkin;

    @FXML
    private Button checkout;

    @FXML
    private Button room;

    @FXML
    private Button events;

    @FXML
    private Button staff;

    @FXML
    private Button services;

    @FXML
    private AnchorPane holdPane;

    public static String name;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        adminName.setText(name);
    }

    // Helper method to load FXML files and set the node
    private void loadFXML(String fxmlFile, Button activeButton) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(fxmlFile));
            setNode(pane);
            resetButtonStyles();
            activeButton.setStyle("-fx-background-color: #2D3347; -fx-text-fill: #ffffff");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to reset all button styles
    private void resetButtonStyles() {
        room.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000");
        checkin.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000");
        checkout.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000");
        bill.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000");
        dash.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000");
        events.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000");
        staff.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000");
        services.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000");
    }

    // Helper method to set the node with a fade transition
    private void setNode(Node node) {
        holdPane.getChildren().clear();
        holdPane.getChildren().add(node);
        FadeTransition ft = new FadeTransition(Duration.millis(1000), node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    @FXML
    public void createRoom(javafx.event.ActionEvent actionEvent) {
        loadFXML("/org.JavaFxProject.Hotel/room.fxml", room);
    }

    @FXML
    public void createCheckIn(javafx.event.ActionEvent actionEvent) {
        loadFXML("/org.JavaFxProject.Hotel/checkin.fxml", checkin);
    }

    @FXML
    public void createCheckOut(javafx.event.ActionEvent actionEvent) {
        loadFXML("/org.JavaFxProject.Hotel/checkout.fxml", checkout);
    }

    @FXML
    public void createCustomerBill(javafx.event.ActionEvent actionEvent) {
        loadFXML("/org.JavaFxProject.Hotel/bill.fxml", bill);
    }

    @FXML
    public void createDash(javafx.event.ActionEvent actionEvent) {
        loadFXML("/org.JavaFxProject.Hotel/dashboard.fxml", dash);
    }

    @FXML
    public void createEvents(javafx.event.ActionEvent actionEvent) {
        loadFXML("/org.JavaFxProject.Hotel/events.fxml", events);
    }

    @FXML
    public void createStaff(javafx.event.ActionEvent actionEvent) {
        loadFXML("/org.JavaFxProject.Hotel/staff.fxml", staff);
    }

    @FXML
    public void createServices(javafx.event.ActionEvent actionEvent) {
        loadFXML("/org.JavaFxProject.Hotel/services.fxml", services);
    }

    @FXML
    public void handleLogout(MouseEvent event) throws IOException {
        bill.getScene().getWindow().hide();
        Stage login = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/org.JavaFxProject.Hotel/login.fxml"));
        Scene scene = new Scene(root);
        login.setScene(scene);
        login.show();
    }
}