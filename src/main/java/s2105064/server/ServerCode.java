package s2105064.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import s2105064.CommonClass.Restaurant;
import s2105064.CommonClass.Food;
import s2105064.CommonClass.SocketWrapper;

public class ServerCode {

    private final int port;
    private final String RESTAURANT_DATABASE_FILE;
    private final String FOOD_DATABASE_FILE;
    private final String CUSTOMER_DATABASE_FILE;
    private final ArrayList<Restaurant> restaurantsDatabase = new ArrayList<>();
    private final ArrayList<Food> fooditemsDatabase = new ArrayList<>();
    private final HashMap<String,String> customercredentials = new HashMap<>();

    private int initialfoodcount;
    private int initrestcount;

    private RestaurantManager restaurantManager;

    private final HashMap<String, SocketWrapper> restaurantmap;//Keeps track of the restaurants logged in.
    private final HashMap<String, SocketWrapper> customersmap;//Keep track of the Customers logged in.
    private int customercount;


    private final HashMap<Food,Integer> orderedfood = new HashMap<>();


    public ServerCode(int port, String RESTAURANT_DATABASE_FILE, String FOOD_DATABASE_FILE,String CUSTOMER_DATABASE_FILE) {
        this.port = port;
        this.RESTAURANT_DATABASE_FILE = RESTAURANT_DATABASE_FILE;
        this.FOOD_DATABASE_FILE = FOOD_DATABASE_FILE;
        this.CUSTOMER_DATABASE_FILE=CUSTOMER_DATABASE_FILE;
        this.restaurantmap = new HashMap<>();
        this.customersmap = new HashMap<>();
        this.customercount = 1;
    }

    public void startServer() throws Exception{
        System.out.println("SERVER STARTED");
        System.out.println("File reading started");
        Thread filereadthread = new Thread(this::readFoodDatabaseFile);
        Thread restaurantreadthread = new Thread(this::readRestaurantfile);
        Thread customerreadthread = new Thread(this::readCustomerDatabaseFile);
        filereadthread.start();
        restaurantreadthread.start();
        customerreadthread.start();
        filereadthread.join();
        restaurantreadthread.join();
        customerreadthread.join();

        restaurantManager = new RestaurantManager(restaurantsDatabase, fooditemsDatabase);

        new Thread(this::serveroperations).start();
        //Accepting client
        try {

            ServerSocket serversocket = new ServerSocket(port);
            while (true) {
                Socket clientsocket = serversocket.accept();
                serveClient(clientsocket);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void readRestaurantfile() {
        try {
            BufferedReader restaurantReader = new BufferedReader(new FileReader(RESTAURANT_DATABASE_FILE));
            while (true) {
                String line = restaurantReader.readLine();
                if ((line == null)) {
                    break;
                }
                Restaurant tempRestaurant = new Restaurant(line);
                restaurantsDatabase.add(tempRestaurant);
            }
            restaurantReader.close();
            initrestcount = restaurantsDatabase.size();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Restaurant file reading complete");
    }

    private void readFoodDatabaseFile() {
        try {
            BufferedReader foodReader = new BufferedReader(new FileReader(FOOD_DATABASE_FILE));
            while (true) {
                String line = foodReader.readLine();
                if (line == null) {
                    break;
                }

                Food tempFood = new Food(line);
                fooditemsDatabase.add(tempFood);
            }
            foodReader.close();
            initialfoodcount = fooditemsDatabase.size();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Menu file reading complete");
    }

    private void readCustomerDatabaseFile()
    {
        try{
            BufferedReader customerreader=new BufferedReader(new FileReader(CUSTOMER_DATABASE_FILE));
            while (true)
            {
                String line = customerreader.readLine();
                if (line == null)
                    break;

                String[] credentials=line.split(",",-1);
                customercredentials.put(credentials[0], credentials[1]);

            }
            customerreader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void serveroperations() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter Command");
            System.out.println("------->  1. Add Restaurant");
            System.out.println("------->  2. Save Data");
            int command = scanner.nextInt();
            scanner.nextLine();
            if (command == 1) {
                Restaurant temprestaurant;

                int Id;
                String Name;
                double Score;
                String Pricerange;
                String ZipCode;
                ArrayList<String> Catagory = new ArrayList<>();

                System.out.println("\n******** Add a restaurant to the Database ********\n");
                Id = restaurantManager.generateUniqueID();
                System.out.print("  Enter Name of the Restaurant : ");
                Name = scanner.nextLine();
                System.out.print("  Enter Score of the Restaurant : ");
                Score = scanner.nextDouble();
                scanner.nextLine();

                System.out.print("  Enter Price of the restaurant : ");
                while (true) {
                    Pricerange = scanner.nextLine();
                    // Check if the the price is made of $$$
                    Boolean validprice = true;
                    for (int i = 0; i < Pricerange.length(); i++) {
                        if (Pricerange.charAt(i) != '$') {
                            validprice = false;
                            break;
                        }
                    }
                    if (Pricerange.equals(""))
                        validprice = false;
                    if (validprice) {
                        break;
                    } else {
                        System.out.print("  Enter valid price(Consisting only $ sign) : ");
                    }

                }

                System.out.print("  Enter Zip Code of the Restaurant : ");
                ZipCode = scanner.nextLine();

                System.out.println("  Enter maximum 3 or minimum 1 catagory of the restaurant:");
                for (int i = 0; i < 3; ) {
                    boolean flag = true;
                    System.out.print("  Enter catagory : ");
                    String temp = scanner.nextLine();
                    flag = !(temp.equals(""));// False if no input.
                    if (flag) {
                        Catagory.add(temp);
                        i++;
                    } else {
                        if (i == 0) {
                            System.out.println("    !!! Enter at least one catagory !!! ");
                        } else {
                            i++;
                        }
                    }
                }

                temprestaurant = new Restaurant(Id, Name, Score, Pricerange, ZipCode, Catagory);

                boolean flag = restaurantManager.AddRestaurant(temprestaurant);
                if (flag) {
                    System.out.println("    !!! Restaurant is added successfully to the database !!!");
                    System.out.println("    !!! Make sure to save Data.Otherwise you will lose the changes !!!");
                } else {
                    System.out.println("    !!! Restaurant name is already present in the database !!!");
                }
            } else if (command == 2) {
                //Write code to save the databases to file and close the server.
                /*
                 * Write all data of restaurant database and food databse to files.
                 * If the database is unchanged(The length of the arraylist is same,since there
                 * is no remove food or resturant method),
                 * Then no need to write to database.
                 */
                if (initrestcount != restaurantsDatabase.size()) {
                    // Code for file writing.
                    try {
                        BufferedWriter Restaurantwriter = new BufferedWriter(new FileWriter(RESTAURANT_DATABASE_FILE));

                        for (int i = 0; i < restaurantsDatabase.size(); i++) {
                            Restaurantwriter.write(restaurantsDatabase.get(i).Restaurantcontentforfile());
                            //Restaurantwriter.newLine();
                            Restaurantwriter.write(System.lineSeparator());
                            // null word is being written.
                        }

                        Restaurantwriter.close();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                if (initialfoodcount != fooditemsDatabase.size()) {
                    try {
                        BufferedWriter Foodwriter = new BufferedWriter(new FileWriter(FOOD_DATABASE_FILE));

                        for (int i = 0; i < fooditemsDatabase.size(); i++) {
                            Foodwriter.write(fooditemsDatabase.get(i).Foodcontentforfile());
                            Foodwriter.newLine();
                        }

                        Foodwriter.close();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            } else if (command == 3) {
                for (Restaurant r : restaurantsDatabase) {
                    System.out.println(r.getName());
                }
            }
        }
    }

    private void serveClient(Socket clientsocket) {
        try {
            SocketWrapper clientsocketwrapper = new SocketWrapper(clientsocket);

            String clienttype = (String) clientsocketwrapper.read();

            if (clienttype.equalsIgnoreCase("Restaurant")) {
                new ServeThreadRestaurant(clientsocketwrapper, restaurantManager, restaurantmap,orderedfood);
            } else if (clienttype.equalsIgnoreCase("customer")) {
                //Adds customer to list of hashmap.

                //Customer serial is unnecessary.
                //String customerserial = clienttype + customercount;
                //customersmap.put(customerserial, clientsocketwrapper);
                //System.out.println("Customer " + customercount + " is Connected");
                //System.out.println(customerserial);
                //customercount++;
                new ServeThreadCustomer(clientsocketwrapper, restaurantManager, restaurantmap, customersmap,orderedfood,customercredentials);
            } else {
                clientsocketwrapper.closeConnection();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
