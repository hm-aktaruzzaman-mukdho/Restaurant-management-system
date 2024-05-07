package s2105064.client.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import s2105064.CommonClass.Food;

import java.util.HashMap;

public class FoodMoreDetails {
    public Label foodname;
    public Label category;
    public Label price;
    public TextField quantity;
    public Label status;

    public Food food;
    private HashMap<Food, Integer> foodcart;

    public void setFood(Food food) {
        this.food = food;
    }

    public void addtoCart(ActionEvent actionEvent) {
        if (quantity.getText().isEmpty() || Integer.parseInt(quantity.getText()) == 0) {
            status.setText("!!! Enter quantity first !!!");
        } else {
            if (foodcart.containsKey(food)) {
                System.out.println("Food exists");
                int x = foodcart.get(food);
                foodcart.put(food, x + Integer.parseInt(quantity.getText()));
            } else {
                foodcart.put(food, Integer.valueOf(quantity.getText()));
            }
            quantity.setText("");
            status.setText(" Food added to Cart");
        }

    }

    public void showData() {
        foodname.setText(food.getName());
        category.setText(food.getCatagory());
        price.setText(String.valueOf(food.getPrice()));
    }

    public void setFoodCart(HashMap<Food, Integer> foodcart) {
        this.foodcart = foodcart;
    }
}
