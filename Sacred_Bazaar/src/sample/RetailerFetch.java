package sample;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RetailerFetch implements Serializable {
    public User userFetch(String userID){
        User user = new User();
        String query="Select * from users where UserID=?";
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
            preparedStatement.setString(1,userID);
            ResultSet rs=preparedStatement.executeQuery();
            while (rs.next()){
                user.setId(rs.getString(1));
                user.setEmail(rs.getString(2));
                user.setPhone(rs.getString(5));
                user.setFirstName(rs.getString(3));
                user.setLastName(rs.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    public Retailer fetch(String retailerID){
        String query="Select * from retailers where RetailerID=?";
        Retailer retailer=null;
        try {
            PreparedStatement preparedStatement=Main.connection.prepareStatement(query);
            preparedStatement.setString(1,retailerID);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                retailer = new Retailer(userFetch(resultSet.getString(1)));
                retailer.setRetailerID(resultSet.getString(2));
                retailer.setRetailName(resultSet.getString(3));
                retailer.setAddress(resultSet.getString(4));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return retailer;
    }
    public List<Retailer> fetchByName(String search){
        String query="Select * from retailers WHERE BusinessName LIKE '%"+search+"%' order by BusinessName";
        Retailer retailer=null;
        List<Retailer> retailerList=new ArrayList<>();
        try {
            PreparedStatement preparedStatement=Main.connection.prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                retailer = new Retailer(userFetch(resultSet.getString(1)));
                retailer.setRetailerID(resultSet.getString(2));
                retailer.setRetailName(resultSet.getString(3));
                retailer.setAddress(resultSet.getString(4));
                retailerList.add(retailer);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return retailerList;
    }
}
