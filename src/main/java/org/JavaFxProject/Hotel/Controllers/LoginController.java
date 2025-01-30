package org.JavaFxProject.Hotel.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.JavaFxProject.Hotel.Entities.User;
import org.JavaFxProject.Hotel.Services.AuthenticationService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    @FXML private Button forgotpassword;
    @FXML private Button login;
    @FXML private PasswordField password;
    @FXML private TextField username;

    private AuthenticationService authService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        authService = new AuthenticationService();
    }

    @FXML
    public void handleSignupButton(javafx.event.ActionEvent actionEvent) throws IOException {
        navigateToScene("/org.JavaFxProject.Hotel/signup.fxml");
    }

    @FXML
    public void handleLoginAction(javafx.event.ActionEvent actionEvent) {
        String username_text = username.getText();
        String password_text = password.getText();

        if (validateInputs(username_text, password_text)) {
            try {
                User loginUser = new User(username_text, password_text);

                if (authService.authenticateUser(loginUser)) {
                    navigateToScene("/org.JavaFxProject.Hotel/homepage.fxml");
                } else {
                    showAlert("Username or Password is not Correct", "Error Message");
                }
            } catch (SQLException | IOException e) {
                showAlert("Login Error: " + e.getMessage(), "Error Message");
                e.printStackTrace();
            }
        } else {
            showAlert("Please enter username and password", "Error Message");
        }
    }

    @FXML
    public void handleForgotAction(javafx.event.ActionEvent actionEvent) throws IOException {
        navigateToScene("/org.JavaFxProject.Hotel/forgotpassword.fxml");
    }

    private boolean validateInputs(String... inputs) {
        for (String input : inputs) {
            if (input == null || input.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void navigateToScene(String fxmlFile) throws IOException {
        login.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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