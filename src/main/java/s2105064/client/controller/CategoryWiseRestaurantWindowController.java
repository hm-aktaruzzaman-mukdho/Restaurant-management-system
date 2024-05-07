package s2105064.client.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import s2105064.CommonClass.SocketWrapper;
import s2105064.client.CustomerClient;
import s2105064.client.MainClientApp;

import java.net.URL;
import java.util.*;

public class CategoryWiseRestaurantWindowController {

    public TableView<Map.Entry<String, ArrayList<String>>> datatableview =new TableView<>();
    public TableColumn<Map.Entry<String, ArrayList<String>>, String> categorytable;
    public TableColumn<Map.Entry<String, ArrayList<String>>, String> restaurantscolumn;
    MainClientApp main;

    CustomerClient customerClient;
    public void setCustomerObject(CustomerClient customerclient)
    {
        this.customerClient = customerclient;
    }
    public void setMain(MainClientApp main) {
        this.main = main;
    }
    public void goBack(ActionEvent actionEvent) throws Exception{
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

    public void initializeTable() {
        try {
            SocketWrapper serversocket = customerClient.getServersocket();
            serversocket.write("search");
            serversocket.write(16);
            HashMap<String,ArrayList<String>> gottendatafromserver = (HashMap<String, ArrayList<String>>) serversocket.read();

            ObservableList<Map.Entry<String, ArrayList<String>>> entryList = FXCollections.observableArrayList(gottendatafromserver.entrySet());

            categorytable.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKey()));
            restaurantscolumn.setCellValueFactory(data -> {
                ArrayList<String> toppings = data.getValue().getValue();
                String toppingsString = String.join(", ", toppings);
                return new SimpleStringProperty(toppingsString);
            });

            datatableview.setItems(entryList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

