package sample;

import com.jfoenix.controls.JFXDatePicker;
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
import java.time.ZoneId;
import java.util.Date;

public class Controller_adminextraoffer {
    @FXML
    Label status;
    @FXML
    JFXDatePicker offer1enddate,offer2enddate;
    @FXML
    JFXTextField threshold;
    volatile String check;


//      setting threshold(x) of Extra type offer1
//    also setting end date of both extra offers.
    public void onsubmitclicked(ActionEvent actionEvent) {
        if(threshold.getText().isEmpty()||offer1enddate.getValue()==null||offer2enddate.getValue()==null){
            status.setText("Enter all fields");
            return;
        }

        Date enddate1 = Date.from(offer1enddate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date enddate2 = Date.from(offer2enddate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        String thresh = threshold.getText();

        ExtraOfferSetRequest eosr = new ExtraOfferSetRequest();
        eosr.setPricex(Double.parseDouble(thresh));
        eosr.setEndDateOffer(enddate2);
        eosr.setEndDate(enddate1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Socket socket =Main.socket;
                    ObjectOutputStream oos = Main.oos;
                    oos.writeObject(eosr);
                    oos.flush();
                    ObjectInputStream ois = Main.ois;
                    check = (String)ois.readObject();


                }catch (Exception e){
                    System.out.println(e);
                }finally {
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {

                            if(check.equals(String.valueOf(ExtraOfferSetStatus.SUCCESS))){
                                FXMLLoader fxmlLoader = new FXMLLoader();
                                fxmlLoader.setLocation(getClass().getResource("admin_mainpage.fxml"));
                                try {
                                    fxmlLoader.load();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Controller_admin controller = fxmlLoader.getController();


                                Parent p = fxmlLoader.getRoot();
                                Stage primaryStage = (Stage) status.getScene().getWindow();
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
        Stage primaryStage = (Stage) status.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("admin_mainpage.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1081, 826));
    }
}
