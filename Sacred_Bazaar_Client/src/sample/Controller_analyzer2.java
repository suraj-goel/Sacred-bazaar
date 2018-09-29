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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Controller_analyzer2 {
    public CategoryAxis xaxis;
    public NumberAxis yaxis;
    @FXML
    BarChart bc;
    @FXML
    JFXButton back;
    List<LineGraphDate> serverlist;

    @FXML
    Label status;
    XYChart.Series<String, Integer> series;

    private ObservableList<String> categories = FXCollections.observableArrayList();
    private List<String> servercategories = new ArrayList<>();
    private List<Integer> servercustomers = new ArrayList<>();
    AnalyseUserRequest aur;

    public void initialize() {
        xaxis.setLabel("Categories");
        yaxis.setLabel("Customers");
        aur = new AnalyseUserRequest(Main.retailer.getRetailerID());
        series = new XYChart.Series<>();
        try {
            Socket socket = Main.socket;


            ObjectOutputStream objectOutputStream = Main.oos;
            objectOutputStream.writeObject(aur);
            objectOutputStream.flush();

            ObjectInputStream objectInputStream = Main.ois;
//                send the getoffer request and recieve a list of already set offers..
            serverlist = (List<LineGraphDate>) objectInputStream.readObject();
        } catch (Exception e) {

            System.out.println(e);
        }
        for (LineGraphDate gc : serverlist) {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            String date = df.format(gc.getPurchaseDate());
            servercategories.add(date);
            servercustomers.add(gc.getUsers());
        }
        categories.addAll(servercategories);
        xaxis.setCategories(categories);
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        for (LineGraphDate gc : serverlist) {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            String date = df.format(gc.getPurchaseDate());
            series.getData().add(new XYChart.Data<>(date, gc.getUsers()));
        }

        bc.getData().add(series);

    }

    public void onbackclicked(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) back.getScene().getWindow();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("retailer.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1081, 826));
    }
}