package s2105064.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import s2105064.CommonClass.Food;
import s2105064.CommonClass.Restaurant;
import s2105064.CommonClass.SocketWrapper;

public class RestaurantClient {

    private SocketWrapper serversocket;
    private String hostname;
    private int port;
    private String restaurantname;
    private String password;
    private boolean isaccepted;//If the restaurant is acceoted or not.
    private ArrayList<Food> menuListinRestaurant;
    private Restaurant myidentity;

    private HashMap<Food,Integer> orderpoolrestaurant;

    public HashMap<Food, Integer> getOrderpoolrestaurant() {
        return orderpoolrestaurant;
    }

    public RestaurantClient(String hostname, int port) throws IOException {
        this.hostname = hostname;
        this.port = port;
        orderpoolrestaurant=new HashMap<Food,Integer>();
        serversocket = new SocketWrapper(hostname, port);
    }


    public void setCredentials(String restaurantname, String password) {
        this.restaurantname = restaurantname;
        this.password = password;
    }

    public boolean isAccepted() {
        return isaccepted;
    }

    public ArrayList<Food> getMenuListinRestaurant() {
        return menuListinRestaurant;
    }

    public String getName() {
        return this.myidentity.getName();
    }

    public int getID() {
        return this.myidentity.getId();
    }

    public SocketWrapper getServerSocket() {
        return serversocket;
    }


    public void connectandcommunicate() {
        Scanner scanner = new Scanner(System.in);

        //Restaurant name and id scanned from console.It will be taken from UI.
        //String restaurantname = scanner.nextLine();
        //String password = scanner.nextLine();

        isaccepted = false;
        try {
            //A server socket is cconnected.
            //Scanned restaurantname and id is sent to server for verification.
            //serversocket.write("Restaurant,"+restaurantname+","+password);

            //serversocket.write("Restaurant");
            //This line is crutial.Without this server won't know what type of client is trying to connect.

            serversocket.write(restaurantname);
            serversocket.write(password);

            //Checks the verifcation status.Accepted for success.or false if they don't match.
            String status = (String) serversocket.read();

            //This boolean variable controls if any further actions will be done.
            isaccepted = status.equalsIgnoreCase("Accepted");
            //If the server has accepted the restaurant,Getting all of their menulist.
            if (isaccepted) {
                myidentity = (Restaurant) serversocket.read();
                System.out.println("Restaurant " + myidentity.getName() + " accepted");

                serversocket.write("givefood");
                Object object = serversocket.read();
                menuListinRestaurant = (ArrayList<Food>) object;
                System.out.println(myidentity.RestaurantcontentforConsole());
            } else if (status.equalsIgnoreCase("alreadypresent")) {
                System.out.println("You are already logged in from some where else");
            } else {
                System.out.println("You are not accepted");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public int addFoodtoRestaurant(String foodname, String foodcategory, String price) {
        Food foodtoadd = new Food(myidentity.getId(), foodname, foodcategory, Double.parseDouble(price));
        int status;
        try {
            serversocket.write("addfood");
            serversocket.write(foodtoadd);
            status = (int) serversocket.read();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //System.out.println(status);
        return status;
    }

    public void terminateconnection() throws Exception {
        serversocket.write("terminate");
        serversocket.closeConnection();
    }
}
