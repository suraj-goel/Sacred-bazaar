package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
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
import java.util.List;


public class Controller_adminlogin {
    @FXML
    JFXTextField email;
    @FXML
    JFXButton back;
    @FXML
    JFXPasswordField password;
    @FXML
    Label status;
    AdminLoginRequest alr;
    volatile String check;

//
    public void onloginclicked(ActionEvent actionEvent) {
        alr=new AdminLoginRequest(email.getText(),password.getText());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Socket socket = new Socket(Main.serverip,Main.portno);
                    Main.socket =socket;
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    Main.oos = oos;
                    oos.writeObject(alr);
//                    create an admin object containing login details of admin..
                    oos.flush();
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    Main.ois = ois;
//                    recieve success status.
                    check = (String)ois.readObject();
                    if(check.equals(String.valueOf(AdminLoginStatus.SUCCESS))){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Stage primaryStage = (Stage) back.getScene().getWindow();
                                Parent root = null;
                                try {

                                    root = FXMLLoader.load(getClass().getResource("admin_mainpage.fxml"));
                                }catch(IOException e){
                                    e.printStackTrace();
                                }
                                primaryStage.setScene(new Scene(root, 1081, 826));
                            }
                        });
                    }else{
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                status.setText("Error");
                            }
                        });

                    }


                }catch (Exception e){
                    System.out.println(e);
                }


            }
        }).start();
    }

    public void onbackclicked(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) back.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("login_new.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1081, 826));
    }
}
