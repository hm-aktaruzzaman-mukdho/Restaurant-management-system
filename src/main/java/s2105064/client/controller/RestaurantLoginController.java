package s2105064.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import s2105064.CommonClass.Food;
import s2105064.client.MainClientApp;
import s2105064.client.RestaurantClient;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Optional;

public class RestaurantLoginController {

    MainClientApp main;
    @FXML
    private Label loginmessage;

    @FXML
    private Button loginrestaurant;

    @FXML
    private PasswordField passwordfield;

    @FXML
    private TextField restaurantidfield;
    RestaurantClient restaurantclient;

    public void createclient() throws Exception {
        restaurantclient=new RestaurantClient(InetAddress.getLocalHost().getHostName(),2222);
        restaurantclient.getServerSocket().write("restaurant");
    }

    @FXML
    void logInRestaurant(ActionEvent event) throws Exception {
        if(restaurantidfield.getText().equals("")||passwordfield.getText().equals("")) {
            loginmessage.setText("Blank Restaurant name or ID");
            return;
        }
        restaurantclient.setCredentials(restaurantidfield.getText(),passwordfield.getText());
        restaurantclient.connectandcommunicate();

        main.setRestaurantClient(restaurantclient);
        if(restaurantclient.isAccepted()) {
            main.showRestaurantHomeScrene();
        }
        else
        {
            loginmessage.setText("Invalid Restaurant name or ID");
        }
    }

    @FXML
    void resetInput(ActionEvent event) {
        this.restaurantidfield.setText("");
        this.passwordfield.setText("");
    }

    public void goBack(ActionEvent actionEvent) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("YOU ARE ABOUT TO GO BACK");
        //alert.setContentText("Are you sure you want to continue?");
        ButtonType okButton = new ButtonType("OK");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonType.CANCEL.getButtonData());
        alert.getButtonTypes().setAll(okButton, cancelButton);
        Optional<ButtonType> result = alert.showAndWait();
        // Check which button was pressed
        if (result.isPresent() && result.get() == okButton) {
            restaurantclient.terminateconnection();
            main.showHomeScrene();
        } else {
        }
    }

    public void setMain(MainClientApp mainClientApp) {
        this.main=mainClientApp;
    }
}
