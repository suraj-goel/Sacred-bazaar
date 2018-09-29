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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import javax.xml.soap.SOAPElementFactory;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Controller_reg {
    @FXML
    JFXButton back;
    @FXML
    JFXTextField firstname;
    @FXML
    JFXTextField lastname;
    @FXML
    JFXTextField email;
    @FXML
    JFXTextField contact;
    @FXML
    JFXPasswordField password;
    @FXML
    Label check_label;

    @FXML
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
    String check=null;
    @FXML
    public void onsubmitclicked(ActionEvent actionEvent){
        SignupRequest signupRequest;
        if(!password.getText().isEmpty())
         signupRequest = new SignupRequest(HashGenerator.hash(password.getText()));
        else{
            check_label.setText("password required");
            return;
        }

        if(!email.getText().isEmpty())
            signupRequest.setEmail(email.getText());
        else{
            check_label.setText("email required");
            return;
        }
        if(!firstname.getText().isEmpty())
            signupRequest.setFirstName(firstname.getText());
        else{
            check_label.setText("FirstName required");
            return;
        }

        signupRequest.setFirstName(firstname.getText());
        signupRequest.setLastName(lastname.getText());
        signupRequest.setPhone(contact.getText());
        signupRequest.setId(UidGenerator.generateuid());
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Socket socket = new Socket(Main.serverip,Main.portno);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(signupRequest);
                    objectOutputStream.flush();
                    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    check = (String)objectInputStream.readObject();
                    System.out.println(check);

                }catch (Exception e){
                    System.out.println(e);
                }
                finally{
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if(check==null)
                                check_label.setText("Server Unreachable");
                            else if(check.equals(String.valueOf(SignupStatus.FAILED)))
                                check_label.setText("Error");
                            else if(check.equals(String.valueOf(SignupStatus.SUCCESS)))
                                check_label.setText("Successfull");


                        }
                    });
                }


            }
        });
        t.start();


    }

}
