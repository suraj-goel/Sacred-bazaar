package sample;

import com.jfoenix.controls.*;
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
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class Controller_editrequest {
    @FXML
    Label user_email,productadded;
    List<Product> serverlist;
    String producteditstatus;

    @FXML
    JFXListView products,categories;

    @FXML
    JFXTextField quantity;
    @FXML
    JFXComboBox<String> category;
    @FXML
    JFXTextArea product_description;
    @FXML
    Label status;
    @FXML
    JFXTextField name;

    @FXML
    JFXTextField price;
    @FXML
    JFXDatePicker mfgdate,expdate,offerenddate;
    @FXML
    JFXComboBox<Offer> offer;
    volatile List<Offer> offerlist;

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
                Platform.runLater(() -> category.getItems().setAll(c.getCategories()));


            }

        }).start();
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
                offerlist = (List<Offer>)objectInputStream.readObject();
            }catch (Exception e){

                System.out.println(e);
            }finally {

                Platform.runLater(() -> {
                            offer.getItems().setAll(this.offerlist);
//                    pass the list of offer that is recieved
                        }
                );



            }

        }).start();





    }

    public void oncategoryclicked(ActionEvent actionEvent) {

    }

    public void onbackclicked(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("retailer.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Controller_Retailer controller = fxmlLoader.getController();


        Parent p = fxmlLoader.getRoot();
        Stage primaryStage = (Stage) category.getScene().getWindow();
        primaryStage.setScene(new Scene(p, 1081,826));
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
        temp=temp+"Current Info:"+"\n";
        temp=temp+"Name:"+product.getName()+"\n";
        temp=temp+"Category:"+product.getCategory()+"\n";
        temp=temp+"Quantity:"+product.getQuantityavailable()+"\n";
        temp=temp+"Price:"+product.getPrice()+"\n";
        temp=temp+"Mfg:"+product.getMfd()+"\n";
        temp=temp+"Expiry Date:"+product.getExpiry()+"\n";
        temp=temp+"Offer:"+product.getCurrentOffer()+"\n";
        return temp;


    }

    public void handleclickedproducts(MouseEvent mouseEvent) {
        Product product = (Product)products.getSelectionModel().getSelectedItem();
        name.setText(product.getName());
        category.setValue(product.getCategory());
        price.setText(product.getPrice().toString());
        Integer q = (Integer)product.getQuantityavailable();
        quantity.setText(q.toString());

        //        set the Text fields and image of the selected product..
        String temp = productinfo(product);
        product_description.setText(temp);
    }

    public void onsubmitclicked(ActionEvent actionEvent) {
        Product selectedproduct = (Product)products.getSelectionModel().getSelectedItem();
        Product product = new Product();
        product.setName(name.getText());
        product.setCategory(category.getValue());
        product.setPrice(price.getText());
        Date mfg,exp,enddate;
        product.setQuantityavailable(Integer.parseInt(quantity.getText()));
        if(mfgdate.getValue()!=null)
            mfg = Date.from(mfgdate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        else
            mfg=product.getMfd();
        if(expdate.getValue()!=null)
            exp = Date.from(expdate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        else
            exp=product.getExpiry();
        if(offerenddate.getValue()!=null){
            enddate = Date.from(offerenddate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        else{
            productadded.setText("Enter end date");
            return;
        }
        product.setCurrentOffer(offer.getValue());


//        System.out.println(mfgdate.getValue().toEpochDay()+"\n"+exp);
        Product currentproduct= (Product)products.getSelectionModel().getSelectedItem();
        product.setProductid(currentproduct.getProductid());
//        System.out.println(Controller_products.retailer.getRetailerID());

        product.setRetailerid(Controller_products.retailer.getRetailerID());

        product.setExpiry(exp);
        product.setMfd(mfg);

//        System.out.println(mfg);
//        System.out.println(exp);

        ProductEditRequest pre = new ProductEditRequest(product);
        pre.setDate(enddate);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Socket socket = Main.socket;
                    ObjectOutputStream oos = Main.oos;
                    oos.writeObject(pre);
                    oos.flush();
                    ObjectInputStream ois = Main.ois;
                    producteditstatus = (String)ois.readObject();


                }catch (Exception e){
                    System.out.println(e);
                }finally {
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            System.out.println(ProductAStatus.SUCCESS);
                            if(producteditstatus.equals(String.valueOf(ProductAStatus.SUCCESS))){
                                FXMLLoader fxmlLoader = new FXMLLoader();
                                fxmlLoader.setLocation(getClass().getResource("retailer.fxml"));
                                try {
                                    fxmlLoader.load();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Controller_Retailer controller = fxmlLoader.getController();
//                                controller.setuser(user);

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

    public void onofferclicked(ActionEvent actionEvent) {
    }
}

