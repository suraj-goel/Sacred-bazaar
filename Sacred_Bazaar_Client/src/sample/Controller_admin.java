package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller_admin {
    @FXML
    JFXButton customers;


//  scene --> soldproducts scene.
    public void onsoldproductsclicked(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) customers.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("soldproduct.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1081, 826));
    }

//    returns back to admin login scene..
    public void onlogoutclicked(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) customers.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("adminlogin.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1081, 826));
    }

//    scene --> deleteproduct
    public void onremoveproductclicked(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) customers.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("admin_deleteproduct.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1303, 961));
    }

//    scene changed to customers
    public void oncustomersclicked(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) customers.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("customers.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1081, 826));
    }


//    scene changed to addcategory
    public void onaddcategoryclicked(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) customers.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("addcategory.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1081, 826));
    }

//    scene changed to admin's add product
    public void onaddproductclicked(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("admin_addproduct.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Controller_adminaddproduct controller = fxmlLoader.getController();


        Parent p = fxmlLoader.getRoot();
        Stage primaryStage = (Stage) customers.getScene().getWindow();
        primaryStage.setScene(new Scene(p, 1081,826));
    }

//    scene changes to remove category scene
    public void onremovecategoryclicked(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) customers.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("deletecategory.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1081, 826));

    }

//    scene to set extra offers for special customers.
    public void onextraoffersclicked(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) customers.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("admin_extraoffer.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1081, 826));
    }
}
