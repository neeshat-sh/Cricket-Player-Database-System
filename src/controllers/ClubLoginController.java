package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import dto.LoginDTO;
import main.Main;

public class ClubLoginController {

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
    void loginAction(ActionEvent event) {
        String userName = userText.getText();
        String password = passwordText.getText();
        LoginDTO loginDTO = new LoginDTO();
        main.setClubName(userName);
        main.setClientName(userName);
        loginDTO.setUserName(userName.trim().toLowerCase());
        loginDTO.setPassword(password);
        try {
            main.getClient().getSocketWrapper().write(loginDTO);
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
