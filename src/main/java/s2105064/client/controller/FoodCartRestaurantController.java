package s2105064.client.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import s2105064.CommonClass.Food;
import s2105064.client.MainClientApp;
import s2105064.client.RestaurantClient;
import java.util.Map;

public class FoodCartRestaurantController {
    public TableView<Map.Entry<Food, Integer>> datatableshow;
    public TableColumn<Map.Entry<Food, Integer>,String> foodname;
    public TableColumn<Map.Entry<Food, Integer>,String> category;
    public TableColumn<Map.Entry<Food, Integer>,Double> price;
    public TableColumn<Map.Entry<Food,Integer>,String> quantity;
    public TableColumn<Map.Entry<Food,Integer>,String> customername;

    RestaurantClient restaurantclient;

    public void setRestaurantclient(RestaurantClient restaurantclient) {
        this.restaurantclient=restaurantclient;
    }

    MainClientApp main;

    public void setMain(MainClientApp main) {
        this.main = main;
    }

    public void goBack(ActionEvent actionEvent) throws Exception {
            restaurantclient.getServerSocket().write("killupdate");
            main.showRestaurantHomeScrene();
    }

    public void showDataonTable() {
        foodname.setCellValueFactory(Data -> new SimpleStringProperty(Data.getValue().getKey().getName()));
        category.setCellValueFactory(Data -> new SimpleStringProperty(Data.getValue().getKey().getCatagory()));
        price.setCellValueFactory(Data -> new SimpleObjectProperty<>(Data.getValue().getKey().getPrice()));
        customername.setCellValueFactory(Data -> new SimpleStringProperty(Data.getValue().getKey().getCustomername()));
        quantity.setCellValueFactory(Data -> {
            Object o= Data.getValue().getValue();
            String x=String.valueOf(o);
            return new SimpleStringProperty(x);
        });

        new CartUpdateThread(restaurantclient,datatableshow);
    }
}
