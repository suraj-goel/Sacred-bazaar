package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import java.util.ArrayList;
import java.util.List;

public class Controller_addoffer {
    List<String> combo = new ArrayList<>();
    @FXML
    JFXComboBox<String> offertype;
    @FXML
    JFXButton submit,back;
    @FXML
    JFXTextField x,y,discount;
    @FXML
    Label status;
    String check;
//    initialises combobox with available type of offers
    public void initialize(){
        combo.add("Buy x get y");
        combo.add("Get x% Discount");
        offertype.getItems().setAll(combo);
    }


//    back to retailer
    public void onbackclicked(){
        Stage primaryStage = (Stage) back.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("retailer.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1081, 826));
    }


//    if request sent to server is success then offer is added and back to retailer scene.
    public void onsubmitclicked(){
        Offer offer=new Offer();
        if(offertype.getSelectionModel().getSelectedItem().equals("Buy x get y")){
            offer.setType(String.valueOf(OfferType.BUY_X_GET_Y));
            offer.setOfferID(UidGenerator.generateuid());
            offer.setRetailerID(Main.retailer.getRetailerID());
            offer.setRetailer(Main.retailer);
            if(x.getText().isEmpty()||y.getText().isEmpty()){
                status.setText("Enter x,y");
                return;
            }
            else{
                offer.setX(Integer.parseInt(x.getText()));
                offer.setY(Integer.parseInt(y.getText()));
            }
        }else{
            offer.setType(String.valueOf(OfferType.X_PERCENT_DISCOUNT));
            offer.setOfferID(UidGenerator.generateuid());
            offer.setRetailerID(Main.retailer.getRetailerID());
            offer.setRetailer(Main.retailer);
            if(discount.getText().isEmpty()){
                status.setText("Enter discount");
                return;
            }
            else{
                offer.setX(Integer.parseInt(discount.getText()));
            }

        }
        OfferAddRequest oar = new OfferAddRequest(offer);
        try {
            Socket socket = Main.socket;
            ObjectOutputStream oos = Main.oos;
            oos.writeObject(oar);
            oos.flush();
            ObjectInputStream ois = Main.ois;
//            recieve success from server...
              check = (String)ois.readObject();
        }catch (Exception e){
            System.out.println(e);
        }finally {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if(check.equals(String.valueOf(OfferStatus.SUCCESS))){
                        Stage primaryStage = (Stage) back.getScene().getWindow();
                        Parent root = null;
                        try {

                            root = FXMLLoader.load(getClass().getResource("retailer.fxml"));
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                        primaryStage.setScene(new Scene(root, 1081, 826));

                    }else{
                        status.setText("Error");
                    }
                }
            });
        }


    }
//      event not required
    public void onoffertypeclicked(ActionEvent actionEvent) {
    }
}
