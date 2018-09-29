package sample;

import com.jfoenix.controls.JFXButton;
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
import java.net.SocketException;

public class Controller_Retailer_Signup {
    @FXML
    JFXButton back;
    @FXML
    JFXTextField shopname,address;
    @FXML
    Label checklabel;

    User user=Main.user;
    Retailer serverretailer;

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
        Stage primaryStage = (Stage) back.getScene().getWindow();
        primaryStage.setScene(new Scene(p, 1303,961));
    }

    public void onsubmitclicked(ActionEvent actionEvent) {
        String retaileruid = UidGenerator.generateuid();
        Retailer retailer = new Retailer(user);
        retailer.setRetailerID(retaileruid);
        if(!shopname.getText().isEmpty()&&!address.getText().isEmpty())
        {retailer.setRetailName(shopname.getText());
        retailer.setAddress(address.getText());}
        else{
            checklabel.setText("Enter Both Fields");
            return;
        }
        RetailerRRequest retailerRRequest = new RetailerRRequest(retailer);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Socket socket = Main.socket;
                    ObjectOutputStream objectOutputStream =Main.oos;
                    objectOutputStream.writeObject(retailerRRequest);
                    ObjectInputStream objectInputStream = Main.ois;
                    serverretailer=(Retailer)objectInputStream.readObject();
                    Main.retailer=serverretailer;
                }catch (SocketException e){
                    System.out.println(e);
                }catch (IOException e){
                    System.out.println(e);
                }catch (ClassNotFoundException e){
                    System.out.println(e);
                }finally {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if(serverretailer.getRetailerRegisterStatus().equals(String.valueOf(RetailerRStatus.FAILED_FROM_CLIENT)))
                                checklabel.setText("Server Unreachable");
                            else if(serverretailer.getRetailerRegisterStatus().equals(String.valueOf(RetailerRStatus.SUCCESS))){
                                checklabel.setText("Success");
                                FXMLLoader fxmlLoader = new FXMLLoader();
                                fxmlLoader.setLocation(getClass().getResource("retailer.fxml"));
                                try {
                                    fxmlLoader.load();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Controller_Retailer controller = fxmlLoader.getController();

                                Parent p = fxmlLoader.getRoot();
                                Stage primaryStage = (Stage) back.getScene().getWindow();
                                primaryStage.setScene(new Scene(p, 1081,826));
                            }


                        }
                    });
                }



//
            }
        }).start();

    }

    public void onaddressclicked(ActionEvent actionEvent) {
    }
}
