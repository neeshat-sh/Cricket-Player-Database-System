package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import dto.AdminLoginDTO;
import main.Main;

public class AdminLoginController {
    private Main main;

    @FXML
    private TextField userText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button resetButton;

    @FXML
    private Button loginButton;

    @FXML
    private ImageView backgroundImageView;

    @FXML
    public void initialize() {
        Image backgroundImage = new Image(getClass().getResourceAsStream("/views/images/background.jpg"));
        backgroundImageView.setImage(backgroundImage);
    }

    @FXML
    public void handleLogin(ActionEvent event) {
        String userName = userText.getText().trim().toLowerCase();
        String password = passwordText.getText();
        AdminLoginDTO adminLoginDTO = new AdminLoginDTO(userName.trim().toLowerCase(), password);

        try {
            main.getClient().getSocketWrapper().write(adminLoginDTO);
            // System.out.println("Admin login request sent");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void resetAction(ActionEvent event) {
        userText.setText(null);
        passwordText.setText(null);
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
