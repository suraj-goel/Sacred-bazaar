package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Controller_products {
    @FXML
    Label user_email,productadded;
    @FXML
    JFXButton logout,name,price;
    @FXML
    JFXListView products,categories;
    @FXML
    JFXTextArea product_description;
    @FXML
    JFXTextField quantity,productname;
    List<Product> serverlist;
    static List<Product> selectedproducts=new ArrayList<Product>();

    User user=Main.user;

    static Retailer retailer;
    volatile Categories c;

    public void initialize(){
        product_description.setEditable(false);
        user_email.setText(Main.user.getEmail());
        categories.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        products.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        selectedproducts.clear();

        CategoriesRequest cr = new CategoriesRequest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Socket socket = Main.socket;

                    ObjectOutputStream objectOutputStream =Main.oos;
                    objectOutputStream.writeObject(cr);
                    objectOutputStream.flush();
                    ObjectInputStream objectInputStream = Main.ois;
                    c = (Categories) objectInputStream.readObject();
                }catch (Exception e){

                    System.out.println(e);
                }finally {

                    Platform.runLater(() -> categories.getItems().setAll(c.getCategories()));



                }

            }
        }).start();





    }
    @FXML
    public void handleclickcategories(){
         String category = (String)categories.getSelectionModel().getSelectedItem();
//         send the above string  to server and rerieve products object...
//        and set the products array list corresponding to the category....
        ProductFetchRequest pr = new ProductFetchRequest(category);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Socket socket =Main.socket;
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
    @FXML
    public void handleclickedproducts(){
        Product product = (Product)products.getSelectionModel().getSelectedItem();
//        set the Text fields and image of the selected product..
        String temp = productinfo(product);
        product_description.setText(temp);
        productadded.setText("");

    }
    @FXML
    public void onrefreshclicked(){

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("products.fxml"));
        try{
            fxmlLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        Controller_products controller = fxmlLoader.getController();

        Parent p = fxmlLoader.getRoot();
        Stage primaryStage = (Stage) logout.getScene().getWindow();
        primaryStage.setScene(new Scene(p,1303,961));
//        can be implemented in a better way.
    }
    @FXML
    public void onviewhistoryclicked(){
        Stage primaryStage = (Stage) logout.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("viewhistory.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1081, 826));

    }
    @FXML
    public void onaddtocartclicked(){
        if(quantity.getText().isEmpty()){
            productadded.setText("Enter quantity");
            return;
        }
        String q = quantity.getText();


        int check=Integer.parseInt(q);

        Product p = (Product)products.getSelectionModel().getSelectedItem();

        if(check<0||check>p.getQuantityavailable()){
            productadded.setText("Invalid quantity of product");
        }else{
            p.setQuantityselected(check);
            selectedproducts.add(p);
            productadded.setText("Product(s) Added");
        }


    }
    @FXML
    public void oncartclicked(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("cart.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Controller_cart controller = fxmlLoader.getController();
        controller.setSelectedProducts(selectedproducts);

        Parent p = fxmlLoader.getRoot();
        Stage primaryStage = (Stage) logout.getScene().getWindow();
        primaryStage.setScene(new Scene(p, 1081,826));


    }

    private void setUser_email(String firstnme){

        user_email.setText(firstnme);
    }

    private String productinfo(Product product){
        String temp="";
        temp=temp+"Name: "+product.getName()+"\n";
        temp=temp+"Category: "+product.getCategory()+"\n";
        temp=temp+"Quantity: "+product.getQuantityavailable()+"\n";
        temp=temp+"Price: "+product.getPrice()+"\n";
        temp=temp+"Mfg: "+product.getMfd()+"\n";
        temp=temp+"Expiry Date: "+product.getExpiry()+"\n";
        temp=temp+"Retailer Name: "+product.getRetailer().getRetailName()+"\n";

        temp+="Retailer Add.: "+product.getRetailer().getAddress()+"\n";
        temp=temp+"Offer: "+product.getCurrentOffer()+"\n";
        return temp;


    }

//    will have a Products class which will have a list of elements and each element will be of type Product.
//    Product class will contain all the details including image.
//    similarly for categories.. or can take categories as static..
//    while loading the data this scene should have a progress bar...
    @FXML
    public void onlogoutclicked(){
        Stage primaryStage = (Stage) logout.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("login_new.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1081, 826));
    }
    @FXML
    public void onretailerclicked() {
        if (user.getRetailerStatus() == 1) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("retailer.fxml"));
            try {
                fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Controller_Retailer controller = fxmlLoader.getController();
//            need to retrieve retailer object from server..
//            creating socket to retrieve the object.
            new Thread(() -> {
                try {
                    Socket socket =Main.socket;
                    ObjectOutputStream oos = Main.oos;
                    oos.writeObject(new RetailerLoginRequest(user));
                    ObjectInputStream ois = Main.ois;
                    retailer= (Retailer)ois.readObject();
                    Main.retailer=retailer;

                }catch (Exception e){
                    System.out.println(e);
                }
            }).start();


            Parent p = fxmlLoader.getRoot();
            Stage primaryStage = (Stage) logout.getScene().getWindow();
            primaryStage.setScene(new Scene(p, 1081,826));
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("retailer_signup.fxml"));
            try {
                fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Controller_Retailer_Signup controller = fxmlLoader.getController();

            Parent p = fxmlLoader.getRoot();
            Stage primaryStage = (Stage) logout.getScene().getWindow();
            primaryStage.setScene(new Scene(p, 1081,826));
        }
    }


    public void onnameclicked(ActionEvent actionEvent) {
        String category = (String)categories.getSelectionModel().getSelectedItem();
//         send the above string  to server and rerieve products object...
//        and set the products array list corresponding to the category....
        ProductFetchRequest pr = new ProductFetchRequest(category);

        if(productname.getText().isEmpty()){
            productadded.setText("Enter name");
            return;
        }
        pr.setName(productname.getText());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Socket socket =Main.socket;
                    ObjectOutputStream oos = Main.oos;
                    oos.writeObject(pr);
                    oos.flush();
                    ObjectInputStream ois =Main.ois;

                    serverlist = (List<Product>) ois.readObject();

                }catch (Exception e){
                    System.out.println(e);
                }finally {
                    Platform.runLater(() -> products.getItems().setAll(serverlist));
                }
            }
        }).start();

    }

    public void onpriceclicked(ActionEvent actionEvent) {
        String category = (String)categories.getSelectionModel().getSelectedItem();
//         send the above string  to server and rerieve products object...
//        and set the products array list corresponding to the category....
        ProductFetchRequest pr = new ProductFetchRequest(category);
        pr.setCheck(1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Socket socket =Main.socket;
                    ObjectOutputStream oos = Main.oos;
                    oos.writeObject(pr);
                    oos.flush();
                    ObjectInputStream ois =Main.ois;

                    serverlist = (List<Product>) ois.readObject();

                }catch (Exception e){
                    System.out.println(e);
                }finally {
                    Platform.runLater(() -> products.getItems().setAll(serverlist));
                }
            }
        }).start();

    }
    public void onextraofferclicked(){
        if(Main.user.isExtraOffer()||Main.user.isBonusOffer()){
            String temp = "Congratulations..!!"+"\n"+"You are eligible for Extra discount."+"\n"+"Effective price will be visible in Reciept"+"\n";
            product_description.setText(temp);
        }
        else {
            product_description.setText("Sorry..!!"+"\n"+"No extra discount available");

        }
    }
}
