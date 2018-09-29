package sample;

import com.jfoenix.controls.JFXDatePicker;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class Controller_viewhistory {

    public JFXDatePicker startdate;
    public JFXDatePicker enddate;
    @FXML
    JFXListView<CartProduct> products;
    @FXML
    JFXTextArea product_description;
    @FXML
    Label status,username,status2,totalamount;
    double amount;

    List<CartProduct> list;
    public void initialize(){
//        get a list of products from server and set them in products listview....
//        add a field date when product was sold and and that in product info method..
//        or get a list of different type of objects..
//        add a field of total amount send for a set of products..
        username.setText(Main.user.getEmail());
        MoneySpendRequest msr = new MoneySpendRequest(Main.user);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Socket socket =Main.socket;

                    ObjectOutputStream objectOutputStream =Main.oos;
                    objectOutputStream.writeObject(msr);
                    objectOutputStream.flush();
                    ObjectInputStream objectInputStream = Main.ois;
                    list = (List<CartProduct>)objectInputStream.readObject();
                    amount=calculateAmount();
                }catch (Exception e){

                    System.out.println(e);
                }finally {

                    Platform.runLater(() -> {
                        products.getItems().setAll(list);
                        totalamount.setText("Total Amount: "+amount);
                    });



                }

            }
        }).start();

    }
    private double calculateAmount(){
        double amount=0;
        for(CartProduct p:list){
            amount+= p.getTransactionCost();
        }
        return amount;
    }
    public void handleclickedproducts(MouseEvent mouseEvent) {
        CartProduct cartproduct = (CartProduct)products.getSelectionModel().getSelectedItem();
//        set the Text fields and image of the selected product..
        String temp = productinfo(cartproduct);
        product_description.setText(temp);
    }

    public void onbackclicked(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) product_description.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("products.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1303, 961));
    }
    private String productinfo(CartProduct cartproduct){
        String temp="";
        temp=temp+"Name:"+cartproduct.getProduct().getName()+"\n";
        temp=temp+"Category:"+cartproduct.getProduct().getCategory()+"\n";
        temp=temp+"Quantity:"+cartproduct.getProduct().getQuantityavailable()+"\n";
        temp=temp+"Price:"+cartproduct.getProduct().getPrice()+"\n";
        temp=temp+"Mfg:"+cartproduct.getProduct().getMfd()+"\n";
        temp=temp+"Expiry Date:"+cartproduct.getProduct().getExpiry()+"\n";
        temp=temp+"Retailer Name:"+cartproduct.getProduct().getRetailer().getRetailName()+"\n";
        temp=temp+"Offer:"+cartproduct.getProduct().getCurrentOffer()+"\n"+"\n";
        temp+="Date of Purchase: "+cartproduct.getOrderDate();


        return temp;


    }

    public void onsearchclicked(ActionEvent actionEvent) {
        if(enddate.getValue()==null||startdate.getValue()==null){
            status2.setText("Enter dates");
            return;
        }else{
            MoneySpendRequest msr = new MoneySpendRequest(Main.user);
            Date start = Date.from(startdate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date end = Date.from(enddate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            msr.setStart(start);
            msr.setEnd(end);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Socket socket =Main.socket;

                        ObjectOutputStream objectOutputStream = Main.oos;
                        objectOutputStream.writeObject(msr);
                        objectOutputStream.flush();
                        ObjectInputStream objectInputStream =Main.ois;
                        list = (List<CartProduct>)objectInputStream.readObject();
                        amount=calculateAmount();
                    }catch (Exception e){

                        System.out.println(e);
                    }finally {

                        Platform.runLater(() -> {
                            products.getItems().setAll(list);
                            totalamount.setText("Total Amount: "+amount);
                        }
                        );
                    }

                }
            }).start();

        }
    }
}
