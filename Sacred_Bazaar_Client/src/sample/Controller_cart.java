package sample;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
import javafx.collections.ModifiableObservableListBase;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Controller_cart {
    @FXML
    JFXListView products;
    @FXML
    JFXTextArea bill_description;
    @FXML
    Label status1;

    volatile String status;
    User user;
    List<Product> selectedproducts=Controller_products.selectedproducts;


    public void setSelectedProducts(List<Product> selectedproducts) {
        this.selectedproducts=selectedproducts;
    }
    public void initialize(){
        products.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        products.getItems().setAll(selectedproducts);

    }

    public void onbackclicked(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("products.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Controller_products controller = fxmlLoader.getController();


        Parent p = fxmlLoader.getRoot();
        Stage primaryStage = (Stage) products.getScene().getWindow();
        primaryStage.setScene(new Scene(p, 1303,961));
    }

    public void onbuyclicked(ActionEvent actionEvent) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket =Main.socket;
                    ObjectOutputStream oos = Main.oos;
                    List<CartProduct> cartproducts = new ArrayList<>();
                    CartProduct temp;
                    for(Product p: selectedproducts){
                        System.out.println(p.getName());
                        temp = new CartProduct(p);
                        temp.setOrderID(UidGenerator.generateuid(p.getProductid()));
                        Date current = new Date();
                        temp.setOrderDate(current);
                        temp.setQuantity(p.getQuantityselected());
                        temp.setOffer(p.getCurrentOffer());
                        temp.setTransactionCost(p.getPrice()*p.getQuantityselected());
                        cartproducts.add(temp);
                    }
                    Date current = new Date();
                    ProductBuyRequest pbr = new ProductBuyRequest(cartproducts,Main.user);
                    pbr.setDateofOrder(current);
                    oos.writeObject(pbr);
                    oos.flush();

                    ObjectInputStream ois =Main.ois;
                    status = (String)ois.readObject();
                    Main.bill = status;


                }catch (Exception e){
                    System.out.println(e);
                }finally {


                    if (status!=null) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                FXMLLoader fxmlLoader = new FXMLLoader();
                                fxmlLoader.setLocation(getClass().getResource("finalscene.fxml"));
                                try {
                                    fxmlLoader.load();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Controller_finalscene controller = fxmlLoader.getController();


                                Parent p = fxmlLoader.getRoot();
                                Stage primaryStage = (Stage) products.getScene().getWindow();
                                primaryStage.setScene(new Scene(p, 1081, 826));
                            }
                        });

                    }else{
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                status1.setText("Error");
                            }
                        });
                    }
                }
            }
        }).start();






    }

    public void onremoveitemclicked(ActionEvent actionEvent) {
        products.getItems().remove(products.getSelectionModel().getSelectedItem());
    }


    public void onbillclicked(ActionEvent actionEvent) {
       bill_description.setText(bill());




    }
    private String bill(){
        String bill="Bill:"+"\n";

        for(Product p : selectedproducts){
            double check = 0;
            check = discamount(p);

            if(p.getCurrentOffer().getType().equals(String.valueOf(OfferType.X_PERCENT_DISCOUNT))||(p.getCurrentOffer().getType().equals(String.valueOf(OfferType.BUY_X_GET_Y))&&p.getCurrentOffer().getX()>p.getQuantityselected()))
             {
                bill += "" + p.getName() + ": " + " MRP: " + p.getPrice() * p.getQuantityselected() + " Price: " + (p.getPrice() * p.getQuantityselected() - check) + " Q:" + p.getQuantityselected() + "\n";
            }else
                bill += "" + p.getName() + ": " + " MRP: " + p.getPrice() * p.getQuantityselected() + " Price: " + (p.getPrice() * p.getQuantityselected() - check) + " Q:" + (p.getQuantityselected()+p.getCurrentOffer().getY()) + "\n";
        }
        return bill;
    }
    private double total(){
        double total=0;
        for(Product p : selectedproducts){
            total+=p.getPrice()*p.getQuantityselected();
        }
        return total;
    }
    private double discamount(Product p){
        double total = p.getPrice()*p.getQuantityselected();
        if(p.getCurrentOffer().getType().equals(String.valueOf(OfferType.BUY_X_GET_Y))){
            if(p.getQuantityselected()<p.getCurrentOffer().getX())
                return -1;
            else{
                total = 0;
                return total;
            }
        }else{
            total =(p.getCurrentOffer().getX()/100*total);
            return total;
        }
    }

    public void handleclickedproducts(MouseEvent mouseEvent) {
//        price of eachproduct..
    }
}
