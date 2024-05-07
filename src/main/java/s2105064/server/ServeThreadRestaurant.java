package s2105064.server;

import java.util.ArrayList;
import java.util.HashMap;

import s2105064.CommonClass.Restaurant;
import s2105064.CommonClass.SocketWrapper;
import s2105064.CommonClass.Food;

public class ServeThreadRestaurant implements Runnable {

    private SocketWrapper restaurantsocket;
    private RestaurantManager restaurantManager;
    private HashMap<String, SocketWrapper> restaurantmap;
    HashMap<Food,Integer> orderedfood;


    ServeThreadRestaurant(SocketWrapper restaurantsocket, RestaurantManager restaurantManager, HashMap<String, SocketWrapper> restaurantmap,HashMap<Food,Integer> orderedfood) {
        this.restaurantsocket = restaurantsocket;
        this.restaurantManager = restaurantManager;
        this.restaurantmap = restaurantmap;
        this.orderedfood=orderedfood;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        try {
            System.out.println("Restaurant serving started");
            boolean continuework = true;
            while (continuework) {
                String restaurantname = (String) restaurantsocket.read();
                String restaurantID = (String) restaurantsocket.read();


                System.out.println("Restaurant " + restaurantname + " is trying to connect");
                /*
                 * Check if the restaurant in present in the database with perfect id matches.
                 * name and id pass password,
                 * */


                Restaurant restaurant_toconnect = restaurantManager.doesRestaurantExist(restaurantname);

                if (restaurant_toconnect != null && restaurant_toconnect.getId() == Integer.parseInt(restaurantID))
                //If the restaurant is present in the database and their id matches the one comming from the client.
                {
                    //Check if the restaurant is already connected to the server.
                    //If yes Show message  appropriately and close the thread.
                    if (restaurantmap.containsKey(restaurant_toconnect.getName())) {
                        restaurantsocket.write("alreadypresent");
                        continue;
                    }

                    restaurantmap.put(restaurant_toconnect.getName(), restaurantsocket);
                    //Use formal Name Otherwise there could be multiple connection foe the same restaurant.
                    //Put the restaurant name with their socket wrapper in the hashmap

                    System.out.println("Restaurant " + restaurantname + " is connected");
                    //Shows messages to server.

                    restaurantsocket.write("accepted");
                    restaurantsocket.write(restaurant_toconnect);
                    //Sending the client accepted message.without this the client won't do anything.
                    //A new thread is beling started creaded where the client will operate.


                    while (continuework) {
                        String command = (String) restaurantsocket.read();
                        if (command.equalsIgnoreCase("givefood")) {
                            ArrayList<Food> foodList = restaurantManager.SearchFoodbyRestaurant(restaurantname);
                            restaurantsocket.write(foodList);
                        } else if (command.equalsIgnoreCase("logout")) {
                            //restaurantsocket.closeConnection();//socket is closed.
                            //System.out.println("Restaurant " + restaurantname + " disconnected.");
                            restaurantsocket.write("kill");
                            restaurantmap.remove(restaurant_toconnect.getName());
                            //restaurantsocket.write("logout");
                            break;
                        }
                        else if (command.equalsIgnoreCase("killupdate")){
                            restaurantsocket.write("kill");
                        }
                        else if (command.equalsIgnoreCase("terminate")) {
                            restaurantsocket.closeConnection();
                            continuework = false;
                            break;
                        } else if (command.equals("addfood")) {
                            //Reads the food sent by the client to add.
                            Food foodtoadd = (Food) restaurantsocket.read();
                            //Adds the food and send the food adding status to the restaurant.
                            //To show if the food was added successfully or not.
                            restaurantsocket.write(restaurantManager.AddFood(foodtoadd));
                            //System.out.println("food adding request");
                        }
                        else if(command.equals("givesavedorders"))
                        {
                            HashMap<Food,Integer> restaurantspecific = new HashMap<>();
                            for(Food f:orderedfood.keySet())//Checking need to be done for every active restaurant
                            {
                                if(f.getRestaurantId()==restaurant_toconnect.getId())
                                {
                                    restaurantspecific.put(f,orderedfood.get(f));
                                }
                            }
                            restaurantsocket.write(restaurantspecific);
                        }
                    }
                } else {
                    restaurantsocket.write("failed");
                    //restaurantsocket.closeConnection();
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("thread exit");
    }


}
