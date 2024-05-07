package s2105064.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainServer /*extends Application*/ {

    private static String RESTAURANT_DATABASE_FILE="D:\\Code\\Level-1_Term-2\\CSE_108\\Java_Code\\Project_Part_2\\Server\\src\\main\\java\\s2105064\\DatabaseFiles\\restaurantout.txt";
    private static String FOOD_DATABASE_FILE="D:\\Code\\Level-1_Term-2\\CSE_108\\Java_Code\\Project_Part_2\\Server\\src\\main\\java\\s2105064\\DatabaseFiles\\menuout.txt";
    private static String CUSTOMER_DATABASE_FILE="D:\\Code\\Level-1_Term-2\\CSE_108\\Java_Code\\Project_Part_2\\Server\\src\\main\\java\\s2105064\\DatabaseFiles\\customer.txt";
//    @Override
//    public void start(Stage stage) throws IOException {
        //System.out.println("Starting " + stage);
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/s2105064/server/SelectFiles.fxml"));
//
//        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();

//    }

    public static void main(String[] args) throws Exception{
        //launch();
        ServerCode server = new ServerCode(2222,RESTAURANT_DATABASE_FILE,FOOD_DATABASE_FILE,CUSTOMER_DATABASE_FILE);
        server.startServer();

    }
}