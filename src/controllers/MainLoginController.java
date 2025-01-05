package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Main;

public class MainLoginController {
    private Main main;

    @FXML
    private Button adminButton;

    @FXML
    private Button clubButton;

    @FXML
    private ImageView backgroundImageView;

    @FXML
    public void initialize() {
        Image backgroundImage = new Image(getClass().getResourceAsStream("/views/images/background.jpg"));
        backgroundImageView.setImage(backgroundImage);
    }

    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    public void admin() {
        try {
            main.showAdminLogin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void club() {
        try {
            main.showClubLogin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
