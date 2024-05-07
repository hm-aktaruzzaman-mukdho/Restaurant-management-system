package s2105064.client.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import s2105064.CommonClass.Food;
import s2105064.CommonClass.SocketWrapper;
import s2105064.client.RestaurantClient;

import java.util.HashMap;
import java.util.Map;

public class CartUpdateThread implements Runnable {
    private final TableView<Map.Entry<Food, Integer>> datatableshow;
    RestaurantClient restaurantclient;
    HashMap<Food, Integer> orderpoolrestaurant;


    CartUpdateThread(RestaurantClient restaurantclient, TableView<Map.Entry<Food, Integer>> datatableshow) {
        this.restaurantclient = restaurantclient;
        this.orderpoolrestaurant = restaurantclient.getOrderpoolrestaurant();
        this.datatableshow = datatableshow;
        new Thread(this).start();
    }

    @Override
    public void run() {
        SocketWrapper socket = restaurantclient.getServerSocket();
        boolean keepAlive = true;
        try {
            socket.write("givesavedorders");
            while (keepAlive) {
                Object readdata=socket.read();
                if (readdata instanceof String)
                {
                    String command = (String) readdata;
                    if (command.equalsIgnoreCase("orders")) {
                        HashMap<Food, Integer> gottendata = (HashMap<Food, Integer>) socket.read();
                        for (Food f : gottendata.keySet()) {
                            if (orderpoolrestaurant.containsKey(f)) {
                                int x = orderpoolrestaurant.get(f);
                                x=x+gottendata.get(f);
                                orderpoolrestaurant.put(f, x);
                            } else {
                                orderpoolrestaurant.put(f, gottendata.get(f));
                            }

                        }
                    } else if (command.equalsIgnoreCase("kill")) {
                        keepAlive = false;
                        orderpoolrestaurant.clear();
                        break;
                    }
                    Platform.runLater(() -> {
                        ObservableList<Map.Entry<Food, Integer>> entrylist = FXCollections.observableArrayList(orderpoolrestaurant.entrySet());
                        datatableshow.setItems(entrylist);
                    });
                }
                else
                {
                    System.out.println("initial orders");
                    HashMap<Food, Integer> tempdata = (HashMap<Food, Integer>) readdata;
                    orderpoolrestaurant.putAll(tempdata);
                    Platform.runLater(() -> {
                        ObservableList<Map.Entry<Food, Integer>> entrylist = FXCollections.observableArrayList(orderpoolrestaurant.entrySet());
                        datatableshow.setItems(entrylist);
                    });
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
