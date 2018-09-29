package sample;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;


import java.io.IOException;

public class Controller_Retailer {
    User user=Main.user;
    Retailer retailer=Main.retailer;

    @FXML
    JFXButton logout;





    @FXML
    public void onaddproductclicked(){

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addproduct.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Controller_addproducts controller = fxmlLoader.getController();


        Parent p = fxmlLoader.getRoot();
        Stage primaryStage = (Stage) logout.getScene().getWindow();
        primaryStage.setScene(new Scene(p, 1081,826));
    }
    @FXML
    public void oneditproductclicked(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("editproducts.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Controller_editrequest controller = fxmlLoader.getController();

        Parent p = fxmlLoader.getRoot();
        Stage primaryStage = (Stage) logout.getScene().getWindow();
        primaryStage.setScene(new Scene(p, 1303,961));

    }
    @FXML
    public void onsoldproductsclicked(){
        Stage primaryStage = (Stage) logout.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("soldproduct.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1081, 826));

    }
    @FXML
    public void onlogoutclicked(){

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("products.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Controller_products controller = fxmlLoader.getController();

        Parent p = fxmlLoader.getRoot();
        Stage primaryStage = (Stage) logout.getScene().getWindow();
        primaryStage.setScene(new Scene(p, 1303,961));

    }

    public void onremoveproductclicked(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) logout.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("deleteproduct.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1303, 961));
    }
    public void onaddofferclicked(){
        Stage primaryStage = (Stage) logout.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("addoffer.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1081, 826));
    }

    public void onanalyzerclicked(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("analyze.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Controller_analyzer controller = fxmlLoader.getController();


        Parent p = fxmlLoader.getRoot();
        Stage primaryStage = (Stage) logout.getScene().getWindow();
        primaryStage.setScene(new Scene(p, 1081,826));
    }

    public void onanalyzer2clicked(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("analyze2.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Controller_analyzer2 controller = fxmlLoader.getController();


        Parent p = fxmlLoader.getRoot();
        Stage primaryStage = (Stage) logout.getScene().getWindow();
        primaryStage.setScene(new Scene(p, 1081,826));
    }
}
