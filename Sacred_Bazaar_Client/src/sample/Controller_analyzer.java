package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Controller_analyzer {
    public CategoryAxis xaxis;
    public NumberAxis yaxis;
    @FXML
    BarChart bc;
    @FXML
    JFXButton back;
    List<GraphCategory> serverlist;
    @FXML
    JFXDatePicker startdate,enddate;
    @FXML
    Label status;
    static Date start=new Date(2000,01,1),end=new Date(2050,01,1);
    private ObservableList<String> categories = FXCollections.observableArrayList();
    private List<String> servercategories = new ArrayList<>();
    private List<Integer> servercustomers = new ArrayList<>();
    AnalyseCategoryRequest acr;
    public void initialize(){
        xaxis.setLabel("Categories");
        yaxis.setLabel("Customers");
        acr = new AnalyseCategoryRequest(Main.retailer.getRetailerID());
        acr.setStartDate(start);
        acr.setEndDate(end);
        series = new XYChart.Series<>();
        try{
            Socket socket =Main.socket;


            ObjectOutputStream objectOutputStream = Main.oos;
            objectOutputStream.writeObject(acr);
            objectOutputStream.flush();

            ObjectInputStream objectInputStream = Main.ois;

            serverlist = (List<GraphCategory>)objectInputStream.readObject();
        }catch (Exception e){

            System.out.println(e);
        }
        for(GraphCategory gc : serverlist){
            servercategories.add(gc.getCategory());
            servercustomers.add(gc.getNumber());
        }
        categories.addAll(servercategories);
        xaxis.setCategories(categories);
        XYChart.Series<String,Integer> series = new XYChart.Series<>();
        for(GraphCategory gc : serverlist){
            series.getData().add(new XYChart.Data<>(gc.getCategory(),gc.getNumber()));
        }

        bc.getData().add(series);

    }
    public void onbackclicked(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) back.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("retailer.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1081, 826));
    }
    XYChart.Series<String,Integer> series;
    public void onupdateclicked(ActionEvent actionEvent) {



        if(startdate.getValue()!=null&&enddate.getValue()!=null) {
            start = Date.from(startdate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            end = Date.from(enddate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

        }else{
            status.setText("Enter Dates");
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("analyze.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Controller_analyzer controller = fxmlLoader.getController();


        Parent p = fxmlLoader.getRoot();
        Stage primaryStage = (Stage) back.getScene().getWindow();
        primaryStage.setScene(new Scene(p, 1081,826));
//        acr.setStartDate(start);
//        acr.setEndDate(end);
//        try{
//            Socket socket = new Socket(Main.serverip,Main.portno);
//
//
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
//            objectOutputStream.writeObject(acr);
//            objectOutputStream.flush();
//
//            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
////                send the getoffer request and recieve a list of already set offers..
//            serverlist = (List<GraphCategory>)objectInputStream.readObject();
//        }catch (Exception e){
//
//            System.out.println(e);
//        }
//        for(GraphCategory gc : serverlist){
//            servercategories.add(gc.getCategory());
//            servercustomers.add(gc.getNumber());
//        }
//        categories.addAll(servercategories);
//        xaxis.setCategories(categories);
//
//        for(GraphCategory gc : serverlist){
//            series.getData().add(new XYChart.Data<>(gc.getCategory(),gc.getNumber()));
//        }
//
//        bc.getData().add(series);



    }
}
