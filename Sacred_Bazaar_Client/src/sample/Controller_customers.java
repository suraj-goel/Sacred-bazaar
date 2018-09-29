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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Controller_customers {
    public JFXListView customers;
    public Label username,status;
    public JFXButton back,name,retailerstatus;
    @FXML
    JFXTextField search;
    @FXML
    JFXTextArea customer_description;

    volatile List<User> serverlist;
    UserFetchRequest ufr;

//retrieve customers from the server and add the functionality of deleting them..'
    public void initialize(){
        username.setText("admin");
        customers.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ufr = new UserFetchRequest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Socket socket = Main.socket;

                    ObjectOutputStream objectOutputStream = Main.oos;
                    objectOutputStream.writeObject(ufr);
                    objectOutputStream.flush();
                    ObjectInputStream objectInputStream = Main.ois;
                    serverlist = (List<User>) objectInputStream.readObject();
                }catch (Exception e){

                    System.out.println(e);
                }finally {

                    Platform.runLater(() -> customers.getItems().setAll(serverlist));

                }

            }
        }).start();
    }


    public void onbackclicked(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) back.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("admin_mainpage.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1081, 826));
    }



    public void handleclickedcustomers(MouseEvent mouseEvent) {
        User user = (User)customers.getSelectionModel().getSelectedItem();
        customer_description.setText(custominfo(user));

    }
    private String custominfo(User user){
        String temp = "Info:"+"\n";
        temp+="Name: "+user.getFirstName()+" "+user.getLastName()+"\n";
        temp+="Email-Id: "+user.getEmail()+"\n";
        boolean isretailer;
        isretailer= user.getRetailerStatus()==1 ? true :false;
        temp+="Retailer: "+ isretailer + "\n";
        temp+="Contact: "+user.getPhone()+"\n";
        return temp;
    }

    public void onnameclicked(ActionEvent actionEvent) {
        if(search.getText().isEmpty()){
            status.setText("Enter name");
            return;
        }
        ufr = new UserFetchRequest(search.getText());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Socket socket =Main.socket;

                    ObjectOutputStream objectOutputStream =Main.oos;
                    objectOutputStream.writeObject(ufr);
                    objectOutputStream.flush();
                    ObjectInputStream objectInputStream = Main.ois;
                    serverlist = (List<User>) objectInputStream.readObject();
                    Platform.runLater(() -> customers.getItems().setAll(serverlist));
                }catch (Exception e){

                    System.out.println(e);
                }
            }
        }).start();

    }

    public void onretailerstatusclicked(ActionEvent actionEvent) {
        ufr = new UserFetchRequest(1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Socket socket = Main.socket;

                    ObjectOutputStream objectOutputStream = Main.oos;
                    objectOutputStream.writeObject(ufr);
                    objectOutputStream.flush();
                    ObjectInputStream objectInputStream = Main.ois;
                    serverlist = (List<User>) objectInputStream.readObject();
                    Platform.runLater(() -> customers.getItems().setAll(serverlist));
                }catch (Exception e){

                    System.out.println(e);
                }
            }
        }).start();


    }
}
