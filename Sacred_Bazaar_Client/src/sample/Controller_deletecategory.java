package sample;

import com.jfoenix.controls.JFXComboBox;
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

public class Controller_deletecategory {
    @FXML
    JFXComboBox<String> category;
    volatile  Categories c;
    volatile String check;
    @FXML
    Label status;

    public void initialize() {
        CategoriesRequest cr = new CategoriesRequest();
        new Thread(() -> {
            try {
                Socket socket =Main.socket;

                ObjectOutputStream objectOutputStream = Main.oos;
                objectOutputStream.writeObject(cr);
                ObjectInputStream objectInputStream = Main.ois;
                c = (Categories) objectInputStream.readObject();
            } catch (Exception e) {

                System.out.println(e);
            } finally {

                Platform.runLater(() -> {
                            category.getItems().setAll(c.getCategories());


                        }
                );


            }

        }).start();
    }
    public void onsubmitclicked(ActionEvent actionEvent) {
        CategoryRemoveRequest crr = new CategoryRemoveRequest(category.getSelectionModel().getSelectedItem());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Socket socket = Main.socket;
                    ObjectOutputStream oos = Main.oos;
                    oos.writeObject(crr);
                    oos.flush();
                    ObjectInputStream ois = Main.ois;
                    check = (String)ois.readObject();


                }catch (Exception e){
                    System.out.println(e);
                }finally {
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {

                            if(check.equals(String.valueOf(CategoryRemoveStatus.SUCCESS))){
                                FXMLLoader fxmlLoader = new FXMLLoader();
                                fxmlLoader.setLocation(getClass().getResource("admin_mainpage.fxml"));
                                try {
                                    fxmlLoader.load();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Controller_admin controller = fxmlLoader.getController();


                                Parent p = fxmlLoader.getRoot();
                                Stage primaryStage = (Stage) category.getScene().getWindow();
                                primaryStage.setScene(new Scene(p, 1081,826));
                            }else{
                                status.setText("Error");
                            }
                        }
                    });
                }
            }
        }).start();
    }

    public void onbackclicked(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) category.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("admin_mainpage.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1081, 826));
    }

    public void oncategoryclicked(ActionEvent actionEvent) {
    }
}
