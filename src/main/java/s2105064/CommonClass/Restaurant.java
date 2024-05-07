package s2105064.CommonClass;

import java.io.Serializable;
import java.util.ArrayList;

public class Restaurant implements Serializable {
    private int Id;
    private String Name;
    private double Score;
    private String Pricerange;
    private String ZipCode;
    private ArrayList<String> Catagory;

    public Restaurant(int Id,String Name,double Score,String Pricerange,String ZipCode,ArrayList<String> Catagory) {
        this.Id=Id;
        this.Name=Name;
        this.Score=Score;
        this.Pricerange=Pricerange;
        this.ZipCode=ZipCode;
        this.Catagory=Catagory;
    }

    public Restaurant()
    {
        Catagory=new ArrayList<>();
    }
    
    public Restaurant(String inpuString)
    {
        //Split this line and get every data element.
        String[] inpuStringArray=inpuString.split(",",-1);
        this.Id=Integer.parseInt(inpuStringArray[0]);
        this.Name=inpuStringArray[1];
        this.Score=Double.parseDouble(inpuStringArray[2]);
        this.Pricerange=inpuStringArray[3];
        this.ZipCode=inpuStringArray[4];
        this.Catagory=new ArrayList<>();
        for(int i=5;i<inpuStringArray.length;i++)
        {
            
                this.Catagory.add(inpuStringArray[i]);
        }
    }

    // public void setId(int Id) {
    //     this.Id = Id;
    // }

    // public void setName(String Name) {
    //     this.Name = Name;
    // }

    // public void setScore(double Score) {
    //     this.Score = Score;
    // }

    // public void setPrice(String Pricerange) {
    //     this.Pricerange = Pricerange;
    // }

    // public void setZipCode(String ZipCode) {
    //     this.ZipCode = ZipCode;
    // }

    public void setCatagory(int index,String Catagory) {
        //this.Catagory[index-1] = Catagory;
        this.Catagory.set(index-1, Catagory);
    }

    public int getId() {
        return this.Id;
    }

    public String getName() {
        return this.Name;
    }

    public double getScore() {
        return this.Score;
    }

    public String getPrice() {
        return this.Pricerange;
    }

    public String getZipCode() {
        return this.ZipCode;
    }

    public String getCatagory() {
        return String.join(",",this.Catagory);
    }

    public ArrayList<String> getCatagories()
    {
        return this.Catagory;
    }

    public boolean DoesNameContains(String subname)
    {
        //String tempname=Name.toUpperCase();
        subname=subname.trim();
        if(subname.equals(""))
        {
            return false;//If empty or space is inputted return false.
        }
        if(Name.toUpperCase().contains(subname.toUpperCase()))
        {
            return true;
        }
        else
            return false;
    }
    
    public boolean isinCatagories(String checkCatagory)
    {
        
        checkCatagory=checkCatagory.trim();
        if(checkCatagory.equals(""))
        {
            return false;
        }
        for (String string : Catagory) {
            //String[] wordsofcatagory=string.split(" ",-1);
            //for(var word:wordsofcatagory)
            {
                if(string.toUpperCase().contains(checkCatagory.toUpperCase()))
                {
                    return true;
                }
            }
        }
        return false;
    }

    //Overload equals method
    public boolean equals(Restaurant obj)
    {
        if(this.Name.equalsIgnoreCase(obj.Name))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    //A method that returns a string in comma seperated format
    public String Restaurantcontentforfile()
    {
        String obj;
        obj=Id+","+Name+","+Score+","+Pricerange+","+ZipCode+","+String.join(",",Catagory);
        return obj;
    }

    public String RestaurantcontentforConsole()
    {
        String obj;
        obj="\tRestaurant ID : "+Id+"\n\tRestaurant Name : "+Name+"\n\tScore : "+Score+"\n\tPrice : "+Pricerange+"\n\tZip code : "+ZipCode+"\n\tCatagories : "+String.join(",",Catagory);
        return obj;
    }
}