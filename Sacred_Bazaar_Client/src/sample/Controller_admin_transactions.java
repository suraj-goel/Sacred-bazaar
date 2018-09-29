package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class Controller_admin_transactions {
    @FXML
    JFXButton back;
    volatile List<User> serverlist;
    UserFetchRequest ufr;
    @FXML
    JFXTextArea transactions;
    @FXML
    JFXListView users;
    volatile List<CartProduct> list;
    User user;
    MoneySpendRequest msr;
    String temp;

//    initialising list view with the available users in the database.
    public void initialize(){

        users.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ufr = new UserFetchRequest();
        new Thread(() -> {
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

                Platform.runLater(() -> users.getItems().setAll(serverlist));

            }

        }).start();
       user = (User)users.getSelectionModel().getSelectedItem();

    }

//    scene returned back to admin main page.
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
    private String print(){
       temp="Transactions: "+"\n";
        int i =0;
        for(CartProduct cp : this.list){
            ++i;
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            String date = df.format(cp.getOrderDate());
            temp= temp+ i+". Product: "+cp.getProduct().getName()+" Date:"+date+" CP: ₹"+cp.getTransactionCost()+"Retailer: "+cp.getProduct().getRetailer().getRetailName()+"\n";

        }
        return temp;
    }

//    User selected from the listview displays his/her info..
    public void handleclickedusers(MouseEvent mouseEvent) {
        user = (User)users.getSelectionModel().getSelectedItem();
        msr = new MoneySpendRequest(user);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Socket socket = Main.socket;

                    ObjectOutputStream objectOutputStream = Main.oos;
                    objectOutputStream.writeObject(msr);
                    objectOutputStream.flush();
                    ObjectInputStream objectInputStream = Main.ois;
                    list = (List<CartProduct>)objectInputStream.readObject();

                    Platform.runLater(() -> {

                        transactions.setText(print());
                    });

                }catch (Exception e){

                    System.out.println(e);
                }

            }
        }).start();

    }


}
