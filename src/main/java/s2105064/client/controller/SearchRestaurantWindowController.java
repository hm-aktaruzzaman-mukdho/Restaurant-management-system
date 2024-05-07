package s2105064.client.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import s2105064.CommonClass.Food;
import s2105064.CommonClass.Restaurant;
import s2105064.CommonClass.SocketWrapper;
import s2105064.client.CustomerClient;
import s2105064.client.MainClientApp;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;


public class SearchRestaurantWindowController implements Initializable {


    
    public Label firstlabel;
    public Label secondlabel;

    public TableView<Restaurant> foodtable;
    public TableColumn<Restaurant,String> food_name;
    public TableColumn<Restaurant,String> price;
    public TableColumn<Restaurant,String> zipcode;
    public TableColumn<Restaurant,String> category;
    public TableColumn<Restaurant,Double> score;

    public TextField first_userinput;
    public TextField second_userinput;
    public ComboBox<String> dropdownmenulist;

    private int searchtype;
    ArrayList<Restaurant> searchedrestaurants;
    private CustomerClient customerclient;
    private MainClientApp main;


    public void setMain(MainClientApp mainClientApp) {
        this.main = mainClientApp;
    }

    public void setCustomerClient(CustomerClient customerClient) {
        this.customerclient = customerClient;
    }

    public void getSelectionType(ActionEvent actionEvent) {
        String selected = dropdownmenulist.getSelectionModel().getSelectedItem();
        if (selected.equals("Search Restaurant by Name")) {
            firstlabel.setVisible(true);
            firstlabel.setText("Restaurant Name");
            first_userinput.setVisible(true);
            secondlabel.setVisible(false);
            second_userinput.setVisible(false);
            searchtype=11;
        }
        if (selected.equals("Search Restaurant by Score")) {
            firstlabel.setVisible(true);
            firstlabel.setText("First Score");
            first_userinput.setVisible(true);
            secondlabel.setVisible(true);
            secondlabel.setText("Second Score");
            second_userinput.setVisible(true);
            searchtype=12;
        }
        if (selected.equals("Search Restaurant by Category")) {
            firstlabel.setVisible(true);
            firstlabel.setText("Category");
            first_userinput.setVisible(true);

            secondlabel.setVisible(false);
            second_userinput.setVisible(false);
            searchtype=13;
        }
        if (selected.equals("Search Restaurant by Price")) {
            firstlabel.setVisible(true);
            firstlabel.setText("Starting Price");
            first_userinput.setVisible(true);
            secondlabel.setVisible(true);
            secondlabel.setText("Max Price");
            second_userinput.setVisible(true);
            searchtype=14;
        }
        if (selected.equals("Search Restaurant by ZipCode")) {
            firstlabel.setVisible(true);
            firstlabel.setText("Zip Code");
            first_userinput.setVisible(true);
            secondlabel.setVisible(false);
            second_userinput.setVisible(false);
            searchtype=15;
        }
    }

    public void performSearch(ActionEvent actionEvent) throws Exception {
        SocketWrapper customersocket=customerclient.getServersocket();
        if(searchtype==11)
        {
            customersocket.write("search");
            customersocket.write(11);
            customersocket.write(first_userinput.getText());
            searchedrestaurants=(ArrayList<Restaurant>) customersocket.read();
        }else if(searchtype==12)
        {
            customersocket.write("search");
            customersocket.write(12);
            customersocket.write(first_userinput.getText());
            customersocket.write(second_userinput.getText());
            searchedrestaurants=(ArrayList<Restaurant>) customersocket.read();
        }
        else if (searchtype==13)
        {
            customersocket.write("search");
            customersocket.write(13);
            customersocket.write(first_userinput.getText());
            searchedrestaurants=(ArrayList<Restaurant>) customersocket.read();
        }
        else if (searchtype==14)
        {
            customersocket.write("search");
            customersocket.write(14);
            customersocket.write(first_userinput.getText());
            customersocket.write(second_userinput.getText());
            searchedrestaurants=(ArrayList<Restaurant>) customersocket.read();
        }
        else if (searchtype==15)
        {
            customersocket.write("search");
            customersocket.write(15);
            customersocket.write(first_userinput.getText());
            searchedrestaurants=(ArrayList<Restaurant>) customersocket.read();
        }

        ObservableList<Restaurant> datafortable = FXCollections.observableArrayList();
        datafortable.addAll(searchedrestaurants);
        food_name.setCellValueFactory(new PropertyValueFactory<Restaurant,String>("Name"));
        category.setCellValueFactory(new PropertyValueFactory<Restaurant,String>("Catagory"));
        price.setCellValueFactory(new PropertyValueFactory<Restaurant,String>("Price"));
        score.setCellValueFactory(new PropertyValueFactory<Restaurant,Double>("Score"));
        zipcode.setCellValueFactory(new PropertyValueFactory<Restaurant,String>("ZipCode"));


        foodtable.setItems(datafortable);


    }

    public void goBack(ActionEvent actionEvent) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("YOU ARE ABOUT TO GO BACK TO FOOD SELECTION SEARCH PAGE");
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> menus = FXCollections.observableArrayList();
        menus.add("Search Restaurant by Name");
        menus.add("Search Restaurant by Score");
        menus.add("Search Restaurant by Category");
        menus.add("Search Restaurant by Price");
        menus.add("Search Restaurant by ZipCode");
        dropdownmenulist.setItems(menus);
    }
}
