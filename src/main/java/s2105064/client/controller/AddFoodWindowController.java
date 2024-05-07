package s2105064.client.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import s2105064.client.MainClientApp;
import s2105064.client.RestaurantClient;

import java.util.Optional;

public class AddFoodWindowController {
    public TextField food_name;
    public TextField food_category;
    public TextField food_price;
    public Label addingstatus;
    private MainClientApp main;

    RestaurantClient restaurantClient;

    public void setRestaurantClient(RestaurantClient restaurantClient) {
        this.restaurantClient=restaurantClient;
    }

    public void setMain(MainClientApp main) {
        this.main = main;
    }

    public void addtheFood(ActionEvent actionEvent) {
        int status = restaurantClient.addFoodtoRestaurant(food_name.getText(),food_category.getText(),food_price.getText());
        if(status==0)
        {
            addingstatus.setText("Food already Exists in the database");
        }
        else if(status==1)
        {
            addingstatus.setText("Food Successfully Added to the database");
        }
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
            this.main.showRestaurantHomeScrene();
        } else {
        }
    }
}
