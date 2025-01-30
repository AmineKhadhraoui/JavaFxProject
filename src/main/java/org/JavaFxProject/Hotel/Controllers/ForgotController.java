package org.JavaFxProject.Hotel.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.JavaFxProject.Hotel.Services.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ForgotController implements Initializable {
    @FXML
    private TextField answer;

    @FXML
    private Button login;

    @FXML
    private TextField password;

    @FXML
    private TextField question;

    @FXML
    private Button save;

    @FXML
    private Button search;

    @FXML
    private TextField username;

    private UserService userService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userService = new UserService();
    }

    @FXML
    public void handleLoginButton(javafx.event.ActionEvent actionEvent) throws IOException {
        login.getScene().getWindow().hide();
        Stage signup = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/org.JavaFxProject.Hotel/login.fxml"));
        Scene scene = new Scene(root);
        signup.setScene(scene);
        signup.show();
    }

    @FXML
    public void handleSaveAction(javafx.event.ActionEvent actionEvent) {
        String username_text = username.getText();
        String password_text = password.getText();
        String question_text = question.getText();
        String answer_text = answer.getText();

        if (username_text.isEmpty() || password_text.isEmpty() || question_text.isEmpty() || answer_text.isEmpty()) {
            showAlert("Every Field is required", "Error Message");
        } else {
            boolean isUpdated = userService.updatePassword(username_text, password_text, question_text, answer_text);
            if (isUpdated) {
                showAlert("Password Set Successfully", "Message");
            } else {
                showAlert("Wrong Answer", "Error Message");
            }
        }
    }

    @FXML
    public void handleSearchAction(javafx.event.ActionEvent actionEvent) {
        String username_text = username.getText();
        if (username_text.isEmpty()) {
            showAlert("Username is required", "Error Message");
        } else {
            boolean userExists = userService.searchUser(username_text);
            if (userExists) {
                String securityQuestion = userService.getSecurityQuestion(username_text);
                question.setText(securityQuestion);
                question.setEditable(false);
            } else {
                showAlert("Incorrect Username", "Error Message");
            }
        }
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