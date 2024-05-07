package s2105064.client.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import s2105064.client.MainClientApp;
import s2105064.client.RestaurantClient;
import s2105064.CommonClass.Food;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class RestaurantHomeScrene {
    public Label descriptiontoftable;

    public TableView<Food> restaurantdatatable = new TableView<>();
    public TableColumn<Food, String> Food_Name;
    public TableColumn<Food, String> Category;
    public TableColumn<Food, Double> Price;
    RestaurantClient restaurantClient;

    MainClientApp main;


    public void setRestaurantClient(RestaurantClient restaurantClient) {
        this.restaurantClient = restaurantClient;
    }

    public void showDataonScrene() {
        descriptiontoftable.setText("Menu List of " + restaurantClient.getName());
        ArrayList<Food> menulistonrestaurant = restaurantClient.getMenuListinRestaurant();
        //Write code to display data on table.

        ObservableList<Food> listfortable = FXCollections.observableArrayList();

        Food_Name.setCellValueFactory(new PropertyValueFactory<Food, String>("Name"));
        Category.setCellValueFactory(new PropertyValueFactory<Food, String>("Catagory"));
        Price.setCellValueFactory(new PropertyValueFactory<Food, Double>("Price"));

        listfortable.addAll(menulistonrestaurant);

        restaurantdatatable.setItems(listfortable);

    }

    public void setMain(MainClientApp mainclientapp) {
        this.main = mainclientapp;
    }

    public void addFood(ActionEvent actionEvent) throws Exception {
        //When going to add food the cart update thread must be held.
        main.showAddFoodWindow();
    }

    public void showCart(ActionEvent actionEvent) throws Exception {
        main.showFoodCartRestaurant();
    }

    public void logOut(ActionEvent actionEvent) throws Exception {
        //Send logout message to server.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("YOU ARE ABOUT TO LOGOUT");
        //alert.setContentText("Are you sure you want to continue?");
        ButtonType okButton = new ButtonType("LOG OUT");
        ButtonType cancelButton = new ButtonType("CANCEL", ButtonType.CANCEL.getButtonData());
        alert.getButtonTypes().setAll(okButton, cancelButton);
        Optional<ButtonType> result = alert.showAndWait();
        // Check which button was pressed
        if (result.isPresent() && result.get() == okButton) {
            restaurantClient.getServerSocket().write("logout");
            restaurantClient.getServerSocket().closeConnection();
            //Terminate the thread.
            main.showRestaurantLogin();
        } else {
        }

    }
}
