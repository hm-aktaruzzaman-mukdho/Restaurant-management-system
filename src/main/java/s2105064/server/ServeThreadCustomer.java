package s2105064.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import s2105064.CommonClass.Food;
import s2105064.CommonClass.SocketWrapper;

public class ServeThreadCustomer implements Runnable {

    private final SocketWrapper customersocket;
    //private ArrayList<Restaurant> restaurantDatabase;
    private final RestaurantManager restaurantManager;
    private final HashMap<String, SocketWrapper> restaurantmap;
    private final HashMap<String, SocketWrapper> customermap;
    //private final String customerserial;
    private final HashMap<String,String> customercredentials;

    private String username;
    private String password;

    HashMap<Food,Integer> orderedfood;
    public final Thread thread;

    public ServeThreadCustomer(SocketWrapper customersocket, RestaurantManager restaurantManager, HashMap<String, SocketWrapper> restaurantmap, HashMap<String, SocketWrapper> customermap,HashMap<Food,Integer> orderedfood,HashMap<String,String> customercredentials) {
        this.restaurantManager = restaurantManager;
        this.customersocket = customersocket;
        this.restaurantmap = restaurantmap;
        this.customermap = customermap;
        //this.customerserial=customerserial;
        this.orderedfood=orderedfood;
        this.customercredentials=customercredentials;
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        try {
            boolean keepGoing=true;
            while(keepGoing) {
                this.username = (String) customersocket.read();
                this.password = (String) customersocket.read();

                if(customercredentials.containsKey(username)&&customercredentials.get(username).equals(password)) {
                    if(customermap.containsKey(username))
                    {
                        customersocket.write("present");
                        continue;//This is not clear to me yet.
                    }
                    customersocket.write("accepted");
                    customermap.put(username,customersocket);
                }
                else
                {
                    customersocket.write("declined");
                    continue;
                }
                String command;
                //Checks the restaurant name and searchs all the food and sends to the restaurant client.
                while (keepGoing) {
                    command = (String) customersocket.read();
                    //System.out.println(command);
                    if (command.equalsIgnoreCase("search")) {
                        int functioncount = (Integer) customersocket.read();
                        if (functioncount == 11) {
                            customersocket.write(restaurantManager.SearchRestaurantbyName((String) customersocket.read()));
                        } else if (functioncount == 12) {
                            double minscore = ((Double) customersocket.read());
                            double maxscore = (Double) customersocket.read();
                            customersocket.write(restaurantManager.SearchRestaurantbyScore(minscore, maxscore));
                        } else if (functioncount == 13) {
                            String category = (String) customersocket.read();
                            customersocket.write(restaurantManager.SearchRestaurantbyCatagory(category));
                        } else if (functioncount == 14) {
                            String price = (String) customersocket.read();
                            customersocket.write(restaurantManager.SearchRestaurantbyPrice(price));
                        } else if (functioncount == 15) {
                            String zipcode = (String) customersocket.read();
                            customersocket.write(restaurantManager.SearchRestaurantbyZipCode(zipcode));
                        } else if (functioncount == 16) {
                            customersocket.write(restaurantManager.CatagoryWiseRestaurant());
                        } else if (functioncount == 21) {
                            String food_name = (String) customersocket.read();
                            customersocket.write(restaurantManager.SearchFoodbyName(food_name));
                        } else if (functioncount == 22) {
                            String food_name = (String) customersocket.read();
                            String restaurantname = (String) customersocket.read();
                            customersocket.write(restaurantManager.SearchFoodbyNameinRestaurant(food_name, restaurantname));
                        } else if (functioncount == 23) {
                            String category = (String) customersocket.read();
                            customersocket.write(restaurantManager.SearchFoodbyCatagory(category));
                        } else if (functioncount == 24) {
                            String category = (String) customersocket.read();
                            String restaurantname = (String) customersocket.read();
                            customersocket.write(restaurantManager.SearchFoodbyCatagoryinRestaurant(category, restaurantname));
                        } else if (functioncount == 25) {
                            String lowprice = (String) customersocket.read();
                            String maxprice = (String) customersocket.read();
                            customersocket.write(restaurantManager.SearchFoodbyRange(Double.parseDouble(lowprice), Double.parseDouble(maxprice)));
                        } else if (functioncount == 26) {
                            String lowprice = (String) customersocket.read();
                            String maxprice = (String) customersocket.read();
                            String restaurantname = (String) customersocket.read();
                            customersocket.write(restaurantManager.SearchFoodbyRangeinRestaurant(Double.parseDouble(lowprice), Double.parseDouble(maxprice), restaurantname));
                        } else if (functioncount == 27) {
                            String restaurantname = (String) customersocket.read();
                            customersocket.write(restaurantManager.CostliestFoodinRestaurant(restaurantname));
                        } else if (functioncount == 28) {
                            customersocket.write(restaurantManager.RestaurantListwithFoodCount());
                        }
                    } else if (command.equals("takeorder")) {
                        //Do the works of orders.
                        HashMap<Food, Integer> temporder = (HashMap<Food, Integer>) customersocket.read();

                        for (Food f : temporder.keySet())//Saves all order to server.
                        {
                            if (orderedfood.containsKey(f)) {
                                int x = orderedfood.get(f);
                                orderedfood.put(f, x + temporder.get(f));
                            } else {
                                orderedfood.put(f, temporder.get(f));
                            }
                        }

                        //Sends restaurant specific orders to logged in restaurants.
                        for (String name : restaurantmap.keySet()) {
                            SocketWrapper socket = restaurantmap.get(name);
                            HashMap<Food, Integer> restaurantspecific = new HashMap<>();
                            for (Food f : temporder.keySet()) {
                                if (f.getRestaurantName().equalsIgnoreCase(name)) {
                                    restaurantspecific.put(f, temporder.get(f));
                                    System.out.println(f.FoodcontentforConsole());
                                }
                            }
                            socket.write("orders");
                            socket.write(restaurantspecific);
                        }

                    } else if(command.equals("logout"))
                    {
                        customermap.remove(username);
                        break;
                    }
                    else if (command.equals("terminate")) {
                        //remove customer client from hashmap
                        keepGoing = false;
                        customermap.remove(username);
                        customersocket.closeConnection();
                        //customermap.remove(customerserial);
                    }

                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

}
