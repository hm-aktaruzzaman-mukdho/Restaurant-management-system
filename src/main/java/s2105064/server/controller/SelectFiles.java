package s2105064.server.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import s2105064.server.MainServer;

public class SelectFiles {
    MainServer main;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }


    public void setMain(MainServer main)
    {
        this.main=main;
    }
}