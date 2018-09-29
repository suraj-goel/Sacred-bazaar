package sample;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Controller_adminremoveproduct {
    @FXML
    Label user_email;


    @FXML
    JFXListView products,categories;


    @FXML
    JFXTextArea product_description;
    @FXML
    Label status;


    List<Product> serverlist;




    volatile Categories c;
    public void initialize(){
        categories.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        products.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        CategoriesRequest cr = new CategoriesRequest();
        new Thread(() -> {
            try{
                Socket socket =Main.socket;

                ObjectOutputStream objectOutputStream = Main.oos;
                objectOutputStream.writeObject(cr);
                objectOutputStream.flush();
                ObjectInputStream objectInputStream = Main.ois;
                c = (Categories) objectInputStream.readObject();
            }catch (Exception e){

                System.out.println(e);
            }finally {

                Platform.runLater(() -> categories.getItems().setAll(c.getCategories()));



            }

        }).start();
    }
    public void handleclickcategories(MouseEvent mouseEvent) {
        String category = (String)categories.getSelectionModel().getSelectedItem();
//         send the above string  to server and rerieve products object...
//        and set the products array list corresponding to the category....
        ProductFetchRequest pr = new ProductFetchRequest(category);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Socket socket = Main.socket;

                    ObjectOutputStream oos = Main.oos;
                    oos.writeObject(pr);
                    oos.flush();
                    ObjectInputStream ois = Main.ois;
                    serverlist = (List<Product>) ois.readObject();
                }catch (Exception e){
                    System.out.println(e);
                }finally {
                    Platform.runLater(() -> products.getItems().setAll(serverlist));
                }
            }
        }).start();

    }
    private String productinfo(Product product){
        String temp="";
        temp=temp+"Current Info: "+"\n";
        temp=temp+"Name: "+product.getName()+"\n";
        temp=temp+"Category: "+product.getCategory()+"\n";
        temp=temp+"Quantity: "+product.getQuantityavailable()+"\n";
        temp=temp+"Price: "+product.getPrice()+"\n";
        temp=temp+"Mfg: "+product.getMfd()+"\n";
        temp=temp+"Expiry Date: "+product.getExpiry()+"\n";
        temp=temp+"Offer: "+product.getCurrentOffer()+"\n";
        temp+="Retailer: "+product.getRetailer().getRetailName()+"\n";
        return temp;


    }
    ProductDeleteRequest pdr;
    volatile String check;
    public void handleclickedproducts(MouseEvent mouseEvent) {
        Product product = (Product)products.getSelectionModel().getSelectedItem();


        //        set the Text fields and image of the selected product..
        String temp = productinfo(product);
        product_description.setText(temp);
    }

    public void ondeleteclicked(ActionEvent actionEvent) {
        Product p = (Product)products.getSelectionModel().getSelectedItem();
        pdr = new ProductDeleteRequest(p);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Socket socket = Main.socket;
                    ObjectOutputStream oos = Main.oos;
                    oos.writeObject(pdr);
//                    create an productdeleterequest and send it to server
                    oos.flush();
                    ObjectInputStream ois = Main.ois;
                    check = (String)ois.readObject();
                    if(check.equals(String.valueOf(ProductAStatus.SUCCESS))){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                status.setText("Success");
                                Stage primaryStage = (Stage) status.getScene().getWindow();
                                Parent root = null;
                                try {

                                    root = FXMLLoader.load(getClass().getResource("admin_mainpage.fxml"));
                                }catch(IOException e){
                                    e.printStackTrace();
                                }
                                primaryStage.setScene(new Scene(root, 1081, 826));
                            }
                        });

                    }else{
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                status.setText("Error");
                            }
                        });
                    }

                }catch (Exception e){
                    System.out.println(e);
                }
//                    if success is returned then bring the scene of admin in Platform.runLater()(not in finally block);
//                    and also set label status to be successful or error.
//                if success is returned then change the scene back to retailer.fxml...


            }
        }).start();
    }




    public void onbackclicked(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) products.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("admin_mainpage.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1081, 826));
    }
}
