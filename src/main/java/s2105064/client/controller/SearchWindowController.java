package s2105064.client.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import s2105064.CommonClass.SocketWrapper;
import s2105064.client.MainClientApp;

import java.util.Optional;

public class SearchWindowController {

    private MainClientApp main;
    private SocketWrapper serversocket;

    public void setServersocket(SocketWrapper serversocket) {
        this.serversocket = serversocket;
    }
    public void logOut(ActionEvent actionEvent) throws Exception {
        //Write code that will send a message to server to close this client socket.
        //And close the server socket.


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
            serversocket.write("logout");
            main.showCustomerLogin();
        } else {
        }
        //Show alert.

    }



    public void setMain(MainClientApp mainClientApp) {
        this.main=mainClientApp;
    }

    public void searchRestaurantbyDifferentOption(ActionEvent actionEvent) throws Exception {
        main.showSearchRestaurantWindow();
    }
    public void searchFoodbyDifferentOption(ActionEvent actionEvent) throws Exception {
        main.showSearchFoodWindow();
    }

    public void catagoryWiseRestaurantList(ActionEvent actionEvent) throws Exception {
        main.showCategoryWiseRestaurant();
    }

    public void foodCountonRestaurant(ActionEvent actionEvent) throws Exception {
        main.showFoodCountOnRestaurant();
    }

    public void showCart(ActionEvent actionEvent) throws Exception {
        main.showFoodCartCustomer();
    }
}
