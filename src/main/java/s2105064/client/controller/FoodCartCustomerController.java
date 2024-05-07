package s2105064.client.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.util.Callback;
import s2105064.CommonClass.Food;
import s2105064.CommonClass.SocketWrapper;
import s2105064.client.CustomerClient;
import s2105064.client.MainClientApp;

import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FoodCartCustomerController {
    public TableView<Map.Entry<Food, Integer>> datatableshow;
    public TableColumn<Map.Entry<Food, Integer>,String> restaurantid;
    public TableColumn<Map.Entry<Food, Integer>,String> foodname;
    public TableColumn<Map.Entry<Food, Integer>,String> category;
    public TableColumn<Map.Entry<Food, Integer>,Double> price;
    public TableColumn<Map.Entry<Food, Integer>,Double> totalitemprice;
    public TableColumn<Map.Entry<Food, Integer>,String> quantity;
    public TableColumn<Map.Entry<Food, Integer>,Void> deletebutton;
    public Label orderstatus;

    public Label totalcount;
    public Label cartcost;

    private CustomerClient customerclient;

    private HashMap<Food,Integer> customercart;

    private MainClientApp main;

    public void goBack(ActionEvent actionEvent) throws Exception {
        main.showSearchWindow();
    }

    public void placeOrder(ActionEvent actionEvent) throws Exception {
        if(customercart.size() == 0) {
            orderstatus.setText("Cart is Empty");
            return;
        }
        orderstatus.setText("Order Placed");
        SocketWrapper serverSocket =customerclient.getServersocket();
        //Get the server socket and send the data to the server.
        serverSocket.write("takeorder");
        serverSocket.write(customercart);
        //Write code to clear cart
        customercart.clear();
        setTableData();
    }

    public void ShowDataOnScrene()
    {
        customercart=customerclient.getOrderpoolcustomer();
        setTableData();
        restaurantid.setCellValueFactory(Data -> new SimpleObjectProperty<>(Data.getValue().getKey().getRestaurantName()));
        foodname.setCellValueFactory(Data -> new SimpleStringProperty(Data.getValue().getKey().getName()));
        category.setCellValueFactory(Data -> new SimpleStringProperty(Data.getValue().getKey().getCatagory()));
        price.setCellValueFactory(Data -> new SimpleObjectProperty<>(Data.getValue().getKey().getPrice()));
        totalitemprice.setCellValueFactory(Data -> new SimpleObjectProperty<>(Data.getValue().getKey().getPrice()*Data.getValue().getValue()));
        quantity.setCellValueFactory(Data -> new SimpleObjectProperty(Data.getValue().getValue()));

        addButtonToTable();

    }

    private void setTableData()
    {
        ObservableList<Map.Entry<Food,Integer>> entrylist = FXCollections.observableArrayList(customercart.entrySet());
        datatableshow.setItems(entrylist);
        int itemcount=0;
        double cartprice=0;
        for (Food f:customercart.keySet()) {
            itemcount+=customercart.get(f);
            cartprice+=f.getPrice()*customercart.get(f);
        }
        totalcount.setText("Total items :"+itemcount);
        cartcost.setText("Total Cost : $"+cartprice);
    }

    private void addButtonToTable()
    {

        deletebutton.setCellFactory(new Callback<TableColumn<Map.Entry<Food, Integer>, Void>, TableCell<Map.Entry<Food, Integer>, Void>>() {
            @Override
            public TableCell<Map.Entry<Food, Integer>, Void> call(final TableColumn<Map.Entry<Food, Integer>, Void> param) {
                return new TableCell<Map.Entry<Food, Integer>, Void>() {
                    private final Button actionButton = new Button("Delete Item");

                    {
                        actionButton.setOnAction((ActionEvent event) -> {
                            Food item = getTableView().getItems().get(getIndex()).getKey();
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

    private void handleButtonClick(Food item)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure you want to delete food from cart");
        //alert.setContentText("Are you sure you want to continue?");
        ButtonType okButton = new ButtonType("Yes");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonType.CANCEL.getButtonData());
        alert.getButtonTypes().setAll(okButton, cancelButton);
        Optional<ButtonType> result = alert.showAndWait();
        // Check which button was pressed
        if (result.isPresent() && result.get() == okButton) {
            customercart.remove(item);
            setTableData();
        } else {
        }
    }

    public void setMain(MainClientApp main) {
        this.main=main;
    }

    public void setCustomerClient(CustomerClient customerclient) {
        this.customerclient=customerclient;
    }

    public void clearcart(ActionEvent actionEvent) {
        customercart.clear();
        orderstatus.setText("Cart Cleared");
        setTableData();
    }
}
