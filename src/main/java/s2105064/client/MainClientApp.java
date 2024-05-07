package s2105064.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import s2105064.client.controller.*;

public class MainClientApp extends Application {

    private Stage stage;

    private RestaurantClient restaurantClient;
    private CustomerClient customerclient;
    private final double WIDTH = 900;
    private final double HEIGHT = 800;

    public void setRestaurantClient(RestaurantClient restaurantClient) {
        this.restaurantClient = restaurantClient;
    }

    public void setCustomerClient(CustomerClient customerclient) {
        this.customerclient = customerclient;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
//        serveripaddress=InetAddress.getLocalHost().getHostAddress();
//        System.out.println(serveripaddress);
//        port=2222;
        showHomeScrene();
    }

    public void showHomeScrene() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/s2105064/client/ClientHomeScrene.fxml"));
        Parent root = loader.load();

        ClientHomeScreneController controller = loader.getController();
        controller.setMain(this);

        stage.setTitle("Client Selection Page");
        stage.setScene(new Scene(root, WIDTH, HEIGHT));
        stage.show();
    }

    public void showRestaurantLogin() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/s2105064/client/RestaurantLogin.fxml"));
        Parent root = loader.load();

        RestaurantLoginController controller = loader.getController();
        controller.setMain(this);
        controller.createclient();

        stage.setTitle("Restaurant Login");
        stage.setScene(new Scene(root, WIDTH, HEIGHT));
        stage.show();

    }

    public void showCustomerLogin() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/s2105064/client/CustomerLogin.fxml"));
        Parent root = loader.load();

        CustomerLoginController controller = loader.getController();
        controller.setMain(this);
        controller.createConnection();

        stage.setTitle("Customer Login");
        stage.setScene(new Scene(root, WIDTH, HEIGHT));
        stage.show();
    }


    public void showSearchWindow() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/s2105064/client/SearchWindow.fxml"));
        Parent root = loader.load();

        SearchWindowController controller = loader.getController();
        controller.setServersocket(customerclient.getServersocket());
        controller.setMain(this);

        stage.setTitle("Search Restaurant or Food Window");
        stage.setScene(new Scene(root, WIDTH, HEIGHT));
        stage.show();
    }


    public void showSearchRestaurantWindow() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/s2105064/client/SearchRestaurantWindow.fxml"));
        Parent root = loader.load();

        SearchRestaurantWindowController controller = loader.getController();
        controller.setMain(this);
        controller.setCustomerClient(customerclient);

        stage.setTitle("Search Restaurant As Per Your Requirement Window");
        stage.setScene(new Scene(root, WIDTH, HEIGHT));
        stage.show();
    }

    public void showSearchFoodWindow() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/s2105064/client/SearchFoodWindow.fxml"));
        Parent root =loader.load();

        SearchFoodWindowController controller = loader.getController();
        controller.setClientObject(customerclient);
        controller.setMain(this);

        stage.setTitle("Search Food and Restaurants Window");
        stage.setScene(new Scene(root, WIDTH, HEIGHT));
        stage.show();
    }

    public void showAddFoodWindow() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/s2105064/client/AddFoodWindow.fxml"));
        Parent root = loader.load();

        AddFoodWindowController controller = loader.getController();
        controller.setRestaurantClient(restaurantClient);
        controller.setMain(this);

        stage.setTitle("Search Restaurant or Food Window");
        stage.setScene(new Scene(root, WIDTH, HEIGHT));
        stage.show();
    }

    public void showRestaurantHomeScrene() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/s2105064/client/RestaurantHomeScrene.fxml"));
        Parent root = loader.load();

        RestaurantHomeScrene controller = loader.getController();
        controller.setRestaurantClient(restaurantClient);
        controller.setMain(this);
        controller.showDataonScrene();

        stage.setTitle("Restaurant Home Screne");
        stage.setScene(new Scene(root, WIDTH, HEIGHT));
        stage.show();
    }

    public void showFoodCartCustomer() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/s2105064/client/FoodCartCustomer.fxml"));
        Parent root = loader.load();

        FoodCartCustomerController controller = loader.getController();
        controller.setCustomerClient(customerclient);
        controller.setMain(this);
        controller.ShowDataOnScrene();

        stage.setTitle("Restaurant Home Screne");
        stage.setScene(new Scene(root, WIDTH, HEIGHT));
        stage.show();
    }

    public void showFoodCartRestaurant() throws Exception
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/s2105064/client/FoodCartRestaurant.fxml"));
        Parent root = loader.load();

        FoodCartRestaurantController controller = loader.getController();
        controller.setRestaurantclient(restaurantClient);
        controller.setMain(this);
        controller.showDataonTable();

        stage.setTitle("Restaurant Home Screne");
        stage.setScene(new Scene(root, WIDTH, HEIGHT));
        stage.show();
    }

    public void showCategoryWiseRestaurant() throws Exception
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/s2105064/client/CategoryWiseRestaurantWindow.fxml"));
        Parent root = loader.load();

        CategoryWiseRestaurantWindowController controller = loader.getController();
        controller.setMain(this);
        controller.setCustomerObject(customerclient);
        controller.initializeTable();

        stage.setTitle("Restaurant Home Screne");
        stage.setScene(new Scene(root, WIDTH, HEIGHT));
        stage.show();
    }

    public void showFoodCountOnRestaurant() throws Exception
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/s2105064/client/FoodCountonRestaurantWindow.fxml"));
        Parent root = loader.load();

        FoodCountonRestaurantWindowController controller = loader.getController();
        controller.setMain(this);
        controller.setCustomerObject(customerclient);

        controller.InitializeTable();

        stage.setTitle("Restaurant Home Screne");
        stage.setScene(new Scene(root, WIDTH, HEIGHT));
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }

}