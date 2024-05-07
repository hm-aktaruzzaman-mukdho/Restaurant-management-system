package s2105064.client.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import s2105064.CommonClass.SocketWrapper;
import s2105064.client.CustomerClient;
import s2105064.client.MainClientApp;
import s2105064.CommonClass.Food;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class SearchFoodWindowController implements Initializable {
    public ComboBox<String> dropdownmenulist;
    public Label first_label;
    public Label second_label;
    public Label third_label;
    public TextField first_userinput;
    public TextField second_userinput;
    public TextField restaurantnameinput;
    public TableView<Food> datatable ;
    public TableColumn<Food,String> restaurantid;
    public TableColumn<Food, String> foodname_column;
    public TableColumn<Food, String> category_column;
    public TableColumn<Food, String> price_column;
    public TableColumn<Food, Void> buttoncolumn;

    private MainClientApp main;
    private int searchtype;
    private CustomerClient customerclient;
    private ArrayList<Food> searchedfoodList;

    public void setClientObject(CustomerClient client) {
        this.customerclient = client;
    }

    public void setMain(MainClientApp main) {
        this.main = main;
    }

    public void getSelectionType(ActionEvent actionEvent) {
        String selected = dropdownmenulist.getSelectionModel().getSelectedItem();
        if (selected.equals("Search Food by Name")) {
            first_label.setVisible(true);
            first_label.setText("Food Name");
            first_userinput.setVisible(true);
            second_label.setVisible(false);
            third_label.setVisible(false);
            second_userinput.setVisible(false);
            restaurantnameinput.setVisible(false);
            searchtype=21;
        }
        if (selected.equals("Search Food by Name in A Restaurant")) {
            first_label.setVisible(true);
            first_label.setText("Food Name");
            first_userinput.setVisible(true);
            second_label.setVisible(false);
            second_userinput.setVisible(false);
            third_label.setVisible(true);
            restaurantnameinput.setVisible(true);
            searchtype=22;
        }
        if (selected.equals("Search Food by Category")) {
            first_label.setVisible(true);
            first_label.setText("Category");
            first_userinput.setVisible(true);
            second_label.setVisible(false);
            second_userinput.setVisible(false);
            third_label.setVisible(false);
            restaurantnameinput.setVisible(false);
            searchtype=23;
        }
        if (selected.equals("Search Food by Category in A Restaurant")) {
            first_label.setVisible(true);
            first_label.setText("Category");
            first_userinput.setVisible(true);
            second_label.setVisible(false);
            third_label.setVisible(true);
            second_userinput.setVisible(false);
            restaurantnameinput.setVisible(true);
            searchtype=24;
        }
        if (selected.equals("Search Food by Price")) {
            first_label.setVisible(true);
            first_label.setText("Lowest Price");
            first_userinput.setVisible(true);
            second_label.setVisible(true);
            second_label.setText("Max Price");
            third_label.setVisible(false);
            second_userinput.setVisible(true);
            restaurantnameinput.setVisible(false);
            searchtype=25;
        }
        if (selected.equals("Search Food by Price in A Restaurant")) {
            first_label.setVisible(true);
            first_label.setText("Low Price");
            first_userinput.setVisible(true);
            second_label.setVisible(true);
            second_label.setText("Max Price");
            third_label.setVisible(true);
            second_userinput.setVisible(true);
            restaurantnameinput.setVisible(true);
            searchtype=26;
        }
        if (selected.equals("Costliest Food Item on A Restaurant")) {
            first_label.setVisible(true);
            first_label.setText("Restaurant Name");
            first_userinput.setVisible(true);
            second_label.setVisible(false);
            third_label.setVisible(false);
            second_userinput.setVisible(false);
            restaurantnameinput.setVisible(false);
            searchtype=27;
        }
    }

    public void performSearch(ActionEvent actionEvent) throws Exception {
        SocketWrapper serversocket = customerclient.getServersocket();
        if(searchtype==21)
        {
            serversocket.write("search");
            serversocket.write(21);
            serversocket.write(first_userinput.getText());
            searchedfoodList=(ArrayList<Food>)serversocket.read();
        }
        else if(searchtype==22)
        {
            serversocket.write("search");
            serversocket.write(22);
            serversocket.write(first_userinput.getText());
            serversocket.write(restaurantnameinput.getText());
            searchedfoodList =(ArrayList<Food>) serversocket.read();
        }
        else if(searchtype==23)
        {
            serversocket.write("search");
            serversocket.write(23);
            serversocket.write(first_userinput.getText());
            searchedfoodList =(ArrayList<Food>) serversocket.read();
        }
        else if(searchtype==24)
        {
            serversocket.write("search");
            serversocket.write(24);
            serversocket.write(first_userinput.getText());
            serversocket.write(restaurantnameinput.getText());
            searchedfoodList =(ArrayList<Food>) serversocket.read();
        }
        else if(searchtype==25)
        {
            serversocket.write("search");
            serversocket.write(25);
            serversocket.write(first_userinput.getText());
            serversocket.write(second_userinput.getText());
            searchedfoodList =(ArrayList<Food>) serversocket.read();
        }
        else if(searchtype==26)
        {
            serversocket.write("search");
            serversocket.write(26);
            serversocket.write(first_userinput.getText());
            serversocket.write(second_userinput.getText());
            serversocket.write(restaurantnameinput.getText());
            searchedfoodList =(ArrayList<Food>) serversocket.read();
        }
        else if(searchtype==27)
        {
            serversocket.write("search");
            serversocket.write(27);
            serversocket.write(first_userinput.getText());
            searchedfoodList =(ArrayList<Food>) serversocket.read();
        }
        else
        {
            return;

        }

        ObservableList<Food> datafortable = FXCollections.observableArrayList();
        datafortable.addAll(searchedfoodList);
        restaurantid.setCellValueFactory(new PropertyValueFactory<Food,String>("RestaurantName"));
        foodname_column.setCellValueFactory(new PropertyValueFactory<Food,String>("Name"));
        category_column.setCellValueFactory(new PropertyValueFactory<Food,String>("Catagory"));
        price_column.setCellValueFactory(new PropertyValueFactory<Food,String>("Price"));

        addButtonToTable();
        datatable.setItems(datafortable);
    }

    private void addButtonToTable()
    {
        buttoncolumn.setCellFactory(new Callback<TableColumn<Food, Void>, TableCell<Food, Void>>() {
            @Override
            public TableCell<Food, Void> call(final TableColumn<Food, Void> param) {
                return new TableCell<Food, Void>() {
                    private final Button actionButton = new Button("More Details");

                    {
                        actionButton.setOnAction((ActionEvent event) -> {
                            Food item = getTableView().getItems().get(getIndex());
                            handleButtonClick(item);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(actionButton);
                        }
                    }
                };
            }
        });
    }

    public void handleButtonClick(Food food) {
        try {
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/s2105064/client/FoodMoreDetails.fxml"));
            Parent root = loader.load();

            FoodMoreDetails controller = loader.getController();
            Food foodaddtocart=new Food(food);
            //Seting the customer name in individual food.
            //System.out.println(customerclient.getUsername());
            foodaddtocart.setCustomername(customerclient.getUsername());

            controller.setFood(foodaddtocart);
            controller.setFoodCart(customerclient.getOrderpoolcustomer());
            controller.showData();
            newStage.setTitle("More Details About Food");
            newStage.setScene(new Scene(root, 600, 400));
            newStage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> menus = FXCollections.observableArrayList();
        menus.add("Search Food by Name");
        menus.add("Search Food by Name in A Restaurant");
        menus.add("Search Food by Category");
        menus.add("Search Food by Category in A Restaurant");
        menus.add("Search Food by Price");
        menus.add("Search Food by Price in A Restaurant");
        menus.add("Costliest Food Item on A Restaurant");
        first_label.setVisible(false);
        second_label.setVisible(false);
        third_label.setVisible(false);
        first_userinput.setVisible(false);
        second_userinput.setVisible(false);
        restaurantnameinput.setVisible(false);
        dropdownmenulist.setItems(menus);
    }

    public void goBack(ActionEvent actionEvent) throws Exception {
            main.showSearchWindow();
    }
}
