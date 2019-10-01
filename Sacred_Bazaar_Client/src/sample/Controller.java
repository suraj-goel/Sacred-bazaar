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
import javafx.stage.Stage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


// Main Scene That opens up when the application loads
public class Controller {
//    FXML attributes
    @FXML
    JFXButton login;
    @FXML
    JFXButton signup;
    @FXML
    JFXTextField email;
    @FXML
    JFXPasswordField password;
    @FXML
    Label check_label;







//scene changed back to main login page after successful signup
    @FXML
    public void onsignupclicked(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) signup.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("reg.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1081, 826));
    }
//    check is a user that is set volatile so that a new check reference is not created in new thread..
    volatile User check = null;


//    scene changes to main scene of buying products..
    public void onloginclicked(){
         check=null;
        LoginRequest loginRequest;
        if(!email.getText().isEmpty()&&!password.getText().isEmpty())
            loginRequest = new LoginRequest(email.getText(),HashGenerator.hash(password.getText()));

        else{
            check_label.setText("Enter Both Fields");
            return;
        }
        new Thread(() -> {
            try{
                Socket socket = new Socket(Main.serverip,Main.portno);
                Main.socket=socket;
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                Main.oos = objectOutputStream;
                objectOutputStream.writeObject(loginRequest);
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Main.ois = objectInputStream;
                check = (User)objectInputStream.readObject();
                Main.user=check;
                Main.socket=socket;

            }catch (Exception e){
                check=new User();
                check.setVerificationStatus(String.valueOf(LoginStatus.FAILED_FROM_CLIENT));
                System.out.println(e);
            }finally {
                Platform.runLater(() -> {
                    if(check.getVerificationStatus().equals(String.valueOf(LoginStatus.FAILED_FROM_CLIENT)))
                        check_label.setText("Server Unreachable");
                    else if(check.getVerificationStatus().equals(String.valueOf(LoginStatus.INCORRECT_PASSWORD)))
                        check_label.setText("Invalid Credentials");
                    else if (check.getVerificationStatus().toUpperCase().equals(String.valueOf(LoginStatus.OTHER)))
                        check_label.setText("Server-Side Error");
                });
            }

        }).start();


//        here the operations are done in a thread so that ui does not become unresponsive.


        while (check==null){}
//while loop so that inside the thread value of check is changed


        if(check!=null){

            if(check.getVerificationStatus().equals(String.valueOf(LoginStatus.FAILED_FROM_CLIENT)))
                check_label.setText("Server Unreachable");
            else if (check.getVerificationStatus().equals(String.valueOf(LoginStatus.INCORRECT_PASSWORD)))
                check_label.setText("Invalid Credentials");
            else if(check.getVerificationStatus().toUpperCase().equals(String.valueOf(LoginStatus.OTHER)))
                check_label.setText("Server-Side Error");
            else if(check.getVerificationStatus().toUpperCase().equals(String.valueOf(LoginStatus.VERIFIED))){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("products.fxml"));
                try{
                    fxmlLoader.load();
                }catch (IOException e){
                    e.printStackTrace();
                }
                Controller_products controller = fxmlLoader.getController();

                Parent p = fxmlLoader.getRoot();

                Stage primaryStage = (Stage) signup.getScene().getWindow();
                primaryStage.setScene(new Scene(p,1303,961));

            }
        }



    }

//    scene changes to admin login scene.
    public void onadminclicked(){
        Stage primaryStage = (Stage) signup.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("adminlogin.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1081, 826));
    }



}
