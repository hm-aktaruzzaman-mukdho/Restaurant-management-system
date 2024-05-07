package s2105064.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import s2105064.CommonClass.Restaurant;
import s2105064.CommonClass.Food;
////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////
//This class is subjected to be removed.

////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////
public class FileRead implements Runnable{

    private String RESTAURANT_DATABASE_FILE;
    private String FOOD_DATABASE_FILE;
    private ArrayList<Restaurant> restaurantsDatabase;
    private ArrayList<Food> fooditemsDatabase;

    public FileRead(String RESTAURANT_DATABASE_FILE, String FOOD_DATABASE_FILE, ArrayList<Restaurant> restaurantsDatabase,ArrayList<Food> fooditemsDatabase)
    {
        this.RESTAURANT_DATABASE_FILE =RESTAURANT_DATABASE_FILE;
        this.FOOD_DATABASE_FILE=FOOD_DATABASE_FILE;
        this.restaurantsDatabase=restaurantsDatabase;
        this.fooditemsDatabase=fooditemsDatabase;
    }

    public void run()
    {
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

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
