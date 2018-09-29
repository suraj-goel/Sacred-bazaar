package sample;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;


//controller for addproducts..
public class Controller_addproducts {

    User user = Main.user;
    @FXML
    Label user_email,status;
    @FXML
    JFXTextField name;
    @FXML
    JFXComboBox<String> category;
    @FXML
    JFXComboBox<Offer> offer;
    @FXML
    JFXTextField quantity,price;
    @FXML
    JFXDatePicker mfgdate,expdate,offerenddate;
    volatile  Categories c;
    volatile  String productaddstatus;
    volatile List<Offer> serverlist;


//    Initialises the list view with available categories
    public void initialize(){
        CategoriesRequest cr = new CategoriesRequest();
        new Thread(() -> {
            try{
                Socket socket = Main.socket;

                ObjectOutputStream objectOutputStream = Main.oos;
                objectOutputStream.writeObject(cr);
                ObjectInputStream objectInputStream = Main.ois;
                c = (Categories) objectInputStream.readObject();
            }catch (Exception e){

                System.out.println(e);
            }finally {

                Platform.runLater(() -> {
                            category.getItems().setAll(c.getCategories());

                            user_email.setText(user.getEmail());
                        }
                );
            }

        }).start();


//        to get the list of offers....
        OfferRequest or = new OfferRequest();
        try {
            or.setRetailerID(Main.retailer.getRetailerID());
        }catch (NullPointerException e){}
        new Thread(() -> {
            try{
                Socket socket = Main.socket;


                ObjectOutputStream objectOutputStream = Main.oos;
                objectOutputStream.writeObject(or);
                objectOutputStream.flush();

                ObjectInputStream objectInputStream = Main.ois;
//                send the getoffer request and recieve a list of already set offers..
                serverlist = (List<Offer>)objectInputStream.readObject();
            }catch (Exception e){

                System.out.println(e);
            }finally {

                Platform.runLater(() -> {
                        offer.getItems().setAll(this.serverlist);
//                    pass the list of offer that is recieved
                        }
                );



            }

        }).start();


    }


//    back to retailer scene
    public void onbackclicked(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("retailer.fxml"));
        try{
            fxmlLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        Controller_Retailer controller = fxmlLoader.getController();


        Parent p = fxmlLoader.getRoot();
        Stage primaryStage = (Stage) user_email.getScene().getWindow();
        primaryStage.setScene(new Scene(p,1081,826));
    }

    public void onsetimageclicked(ActionEvent actionEvent) {
    }



//    sending a product object and getting a string to check failure or success
    @FXML
    public void onsubmitclicked(ActionEvent actionEvent) {
        Product product = new Product();
        if(name.getText().isEmpty()||category.getValue()==null||price.getText().isEmpty()||quantity.getText().isEmpty()||offer.getValue()==null)
        {
            status.setText("Enter all fields");
            return;
        }
        product.setName(name.getText());
        product.setCategory(category.getValue());
        product.setPrice(price.getText());
        product.setQuantityavailable(Integer.parseInt(quantity.getText()));
        Date mfg;
        Date exp,enddate;
        product.setCurrentOffer(offer.getValue());
        if(mfgdate.getValue()!=null&&expdate.getValue()!=null&&offerenddate.getValue()!=null) {
            mfg = Date.from(mfgdate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            exp = Date.from(expdate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            enddate = Date.from(offerenddate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        }else{
            status.setText("Enter Dates");
            return;
        }
        product.setProductid(UidGenerator.generateuid());


        product.setRetailerid(Controller_products.retailer.getRetailerID());

        product.setExpiry(exp);
        product.setMfd(mfg);


//      sending a new product object inside a product AddRequest object
        ProductAddRequest pra = new ProductAddRequest(product);
        pra.setDate(enddate);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Socket socket = Main.socket;
                    ObjectOutputStream oos = Main.oos;
                    oos.writeObject(pra);
                    oos.flush();
                    ObjectInputStream ois = Main.ois;
                    productaddstatus = (String)ois.readObject();


                }catch (Exception e){
                    System.out.println(e);
                }finally {
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {

                            if(productaddstatus.equals(String.valueOf(ProductAStatus.SUCCESS))){
                                FXMLLoader fxmlLoader = new FXMLLoader();
                                fxmlLoader.setLocation(getClass().getResource("retailer.fxml"));
                                try {
                                    fxmlLoader.load();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Controller_Retailer controller = fxmlLoader.getController();


                                Parent p = fxmlLoader.getRoot();
                                Stage primaryStage = (Stage) name.getScene().getWindow();
                                primaryStage.setScene(new Scene(p, 1081,826));
                            }else{
                                status.setText("Error");
                            }
                        }
                    });
                }
            }
        }).start();

    }


//    Methods currently not used.
//    Combobox methods..
    public void oncategoryclicked(ActionEvent actionEvent) {
    }

    public void onofferclicked(ActionEvent actionEvent) {

    }
}
