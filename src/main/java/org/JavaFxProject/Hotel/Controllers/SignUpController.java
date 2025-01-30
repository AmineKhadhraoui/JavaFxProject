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
import org.JavaFxProject.Hotel.Services.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class SignUpController implements Initializable{

    @FXML private TextField address;
    @FXML private TextField answer;
    @FXML private RadioButton female;
    @FXML private RadioButton male;
    @FXML private TextField name;
    @FXML private TextField password;
    @FXML private ComboBox<String> question;
    @FXML private Button signup;
    @FXML private TextField username;

    private UserService userService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userService = new UserService();
        initializeSecurityQuestions();
    }

    private void initializeSecurityQuestions() {
        question.getItems().removeAll(question.getItems());
        question.getItems().addAll(
                "What is the name of your first pet?",
                "What was your first car?",
                "What elementary school did you attend?",
                "What is the name of the town where you were born?"
        );
    }

    @FXML
    public void handleSignupAction(javafx.event.ActionEvent actionEvent) {
        String name_text = name.getText();
        String username_text = username.getText();
        String password_text = password.getText();
        String gender_text = getGender();
        String question_text = question.getSelectionModel().getSelectedItem();
        String answer_text = answer.getText();
        String address_text = address.getText();

        if (validateInputs(name_text, username_text, password_text, gender_text,
                question_text, answer_text, address_text)) {

            User newUser = new User(name_text, username_text, password_text, gender_text,
                    question_text, answer_text, address_text);

            if (userService.registerUser(newUser)) {
                showAlert("Register Successfully", "Message");
            } else {
                showAlert("Registration Failed", "Error Message");
            }
        } else {
            showAlert("Every Field is required", "Error Message");
        }
    }

    private boolean validateInputs(String... inputs) {
        for (String input : inputs) {
            if (input == null || input.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @FXML
    public void handleLoginButton(javafx.event.ActionEvent actionEvent) throws IOException {
        signup.getScene().getWindow().hide();
        Stage login = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/org.JavaFxProject.Hotel/login.fxml"));
        Scene scene = new Scene(root);
        login.setScene(scene);
        login.show();
    }

    public String getGender() {
        if (male.isSelected()) return "Male";
        if (female.isSelected()) return "Female";
        return "";
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
