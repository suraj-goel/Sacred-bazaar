package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login_new.fxml"));
        primaryStage.setTitle("Market");
        primaryStage.setScene(new Scene(root, 1081, 826));
//        primaryStage.setMaximized(true);
        primaryStage.show();
    }
    static String serverip = "localhost";
    static int portno = 6963;
    static User user;
    static Retailer retailer;
    static Socket socket;
    static String bill;
    static ObjectInputStream ois;
    static ObjectOutputStream oos;


    public static void main(String[] args) {
        launch(args);
    }
}
