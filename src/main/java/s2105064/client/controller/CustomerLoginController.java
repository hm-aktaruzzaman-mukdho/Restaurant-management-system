package s2105064.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import s2105064.client.CustomerClient;
import s2105064.client.MainClientApp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

public class CustomerLoginController {
    private MainClientApp main;

    @FXML
    private TextField customerid;
    @FXML
    private PasswordField passwordfield;
    @FXML
    private Button loginasguest;
    @FXML
    private Button logincustomer;
    @FXML
    private Label loginmessage;



    CustomerClient customerclient;


    public void createConnection() throws Exception {
        loginasguest.setVisible(false);
        customerclient=new CustomerClient(InetAddress.getLocalHost().getHostName(), 2222);
        customerclient.getServersocket().write("customer");
    }

    @FXML
    public void logInCustomer(ActionEvent event) throws Exception {
        if(customerid.getText().isEmpty()||passwordfield.getText().isEmpty())
        {
            loginmessage.setText("Please enter your User ID and Password!!!");
            return;
        }
        customerclient.setCredentials(customerid.getText(),passwordfield.getText());
        customerclient.connectAndCommunicate();
        if(customerclient.isAccepted()==1) {
            main.setCustomerClient(customerclient);
            main.showSearchWindow();
        }
        if(customerclient.isAccepted()==0)
        {
            loginmessage.setText("You are already logged in");
        }
        else if(customerclient.isAccepted()==2){
            loginmessage.setText("Wrong username and password");
        }
    }

    @FXML
    public void loginAsGuest(ActionEvent event) {

    }

    @FXML
    public void resetInput(ActionEvent event) {
        this.customerid.setText("");
        this.passwordfield.setText("");
    }

    public void goBack(ActionEvent actionEvent) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("YOU ARE ABOUT TO GO BACK TO CLIENT SELECTION PAGE");
        //alert.setContentText("Are you sure you want to continue?");
        ButtonType okButton = new ButtonType("OK");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonType.CANCEL.getButtonData());
        alert.getButtonTypes().setAll(okButton, cancelButton);
        Optional<ButtonType> result = alert.showAndWait();
        // Check which button was pressed
        if (result.isPresent() && result.get() == okButton) {
            customerclient.terminateconnection();
            main.showHomeScrene();
        } else {
        }
    }

    public void setMain(MainClientApp mainClientApp) {
        this.main=mainClientApp;
    }
}
