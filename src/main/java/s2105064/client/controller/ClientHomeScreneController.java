package s2105064.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import s2105064.client.MainClientApp;

public class ClientHomeScreneController {

    MainClientApp main;

    @FXML
    private Button selectedcustomer;

    @FXML
    private Button selectedrestaurant;

    @FXML
    void selectedCustomer(ActionEvent event) throws Exception {
            //selectedcustomer.setText("Selected Customer");
        main.showCustomerLogin();
    }

    @FXML
    void selectedRestaurant(ActionEvent event) throws Exception {
        //selectedrestaurant.setText("Selected Restaurant");
        main.showRestaurantLogin();

    }

    public void setMain(MainClientApp mainClientApp) {
        this.main = mainClientApp;
    }
}
