package s2105064.CommonClass;


import java.io.Serializable;
import java.util.Locale;

public class Food implements Serializable {
    private String RestaurantName;
    private String Customername;
    private int RestaurantId;
    private String Catagory;
    private String Name;
    private Double Price;

    public Food(int RestaurantId, String Catagory, String Name, Double Price) {
        this.RestaurantName = "";
        this.RestaurantId = RestaurantId;
        this.Catagory = Catagory;
        this.Name = Name;
        this.Price = Price;
    }

    public Food() {
        this.RestaurantName = "";
        this.Customername = "";
        this.RestaurantId = 0;
        this.Catagory = null;
        this.Name = null;
        this.Price = 0.0;

    }

    public Food(Food food) {
        this.RestaurantName = food.RestaurantName;
        this.RestaurantId = food.RestaurantId;
        this.Customername = food.Customername;
        this.Name = food.Name;
        this.Catagory = food.Catagory;
        this.Price = food.Price;

    }

    public Food(String inpuString) {
        //Split this string and get every data element.
        String[] temp = inpuString.split(",", -1);
        this.RestaurantName = "";
        this.Customername = "";
        this.RestaurantId = Integer.parseInt(temp[0]);
        this.Catagory = temp[1];
        this.Name = temp[2];
        this.Price = Double.parseDouble(temp[3]);
    }

    public void setRestaurantName(String name) {
        this.RestaurantName = name;
    }

    public void setCustomername(String customername) {
        this.Customername = customername;
    }

    public void setRestaurantId(int RestaurantId) {
        this.RestaurantId = RestaurantId;
    }

    public void setCatagory(String Catagory) {
        this.Catagory = Catagory;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setPrice(Double Price) {
        this.Price = Price;
    }

    public String getRestaurantName() {
        return this.RestaurantName;
    }

    public String getCustomername() {
        return Customername;
    }

    public int getRestaurantId() {
        return this.RestaurantId;
    }

    public String getCatagory() {
        return this.Catagory;
    }

    public String getName() {
        return this.Name;
    }

    public double getPrice() {
        return this.Price;
    }

    public boolean equals(Food obj) {
        if (this.RestaurantId == obj.RestaurantId && this.Name.equalsIgnoreCase(obj.Name) && this.Price == Price && this.Catagory.equalsIgnoreCase(obj.Catagory)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean DoesNameContains(String subname) {
        //String tempname=Name.toUpperCase();
        subname = subname.trim();
        if (subname.equals("")) {
            return false;//If empty or space is inputted return false.
        }
        if (Name.toUpperCase().contains(subname.toUpperCase())) {
            return true;
        } else
            return false;
    }

    public String Foodcontentforfile() {
        return new String(RestaurantId + "," + Catagory + "," + Name + "," + Price);
    }

    public String FoodcontentforConsole() {
        String outString;
        outString = "Name : " + Name + "\nCatagory : " + Catagory + "\nPrice : " + Price + "\n Restaurant ID : " + RestaurantId + "\n Customer :" + Customername;

        return outString;
    }

    @Override
    public int hashCode() {
        return Name.hashCode() + RestaurantId + Catagory.hashCode() + Customername.hashCode() + Price.hashCode();
    }

//    @Override
//    public boolean equals(Object obj)
//    {
//        if (this == obj)
//            return true;
//        if(obj == null)
//            return false;
//        if(!(obj instanceof Food))
//            return false;
//
//        Food food=(Food)obj;
//        return Name.equals(food.Name) && Catagory.equals(food.Catagory)&& Price.compareTo(food.Price)==0 && RestaurantId== food.RestaurantId && Customername.equals(food.Customername);
//    }
}
