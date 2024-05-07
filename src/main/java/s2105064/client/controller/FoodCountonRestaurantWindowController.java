package s2105064.client.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import s2105064.CommonClass.SocketWrapper;
import s2105064.client.CustomerClient;
import s2105064.client.MainClientApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FoodCountonRestaurantWindowController {

    public TableView<Map.Entry<String, Integer>> datatableview;
    public TableColumn<Map.Entry<String, Integer>,String> restaurantname;
    public TableColumn<Map.Entry<String, Integer>,String> foodcount;

    CustomerClient customerClient;
    public void setCustomerObject(CustomerClient customerclient)
    {
        this.customerClient = customerclient;
    }

    MainClientApp main;
    public void setMain(MainClientApp main) {
        this.main = main;
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
            main.showSearchWindow();
        } else {
        }
    }

    public void InitializeTable() throws Exception {
        SocketWrapper serversocket = customerClient.getServersocket();
        serversocket.write("search");
        serversocket.write(28);

        HashMap<String,Integer> datafromserver = (HashMap<String, Integer>) serversocket.read();

        ObservableList<Map.Entry<String, Integer>> entryList = FXCollections.observableArrayList(datafromserver.entrySet());
        restaurantname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
        foodcount.setCellValueFactory(data -> {
            Integer toppings = data.getValue().getValue();
            String toppingsString = String.valueOf(toppings);
            return new SimpleStringProperty(toppingsString);
        });


        datatableview.setItems(entryList);
    }
}
